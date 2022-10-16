# Proportional Curve Tilt
# Author: Cole Reed
# Release Date: 3.23.08
# Updated by: zivoy
# Date: 6.21.22
# version 0.2

# Release Notes
# You must be in object mode to use this script
# Additionally after you run the script you'll need change your 3d view so see the update

# To do
# Simple GUI

import bpy
import math
C = bpy.context

# change this number to the total amount of tilt rotation to apply to the curve
tiltRotation = 360

# change this if you want the twisting to be someother by making it be based on how far it is 
# from the start of the curve rather then the number of the handle
proportional = False

# change radians to degrees
def rad2Deg(rad):
    degrs = 180.0 * rad/math.pi
    return degs

# change degrees to radians
def deg2Rad(deg):
    rads = math.pi * deg/180.0
    return rads

def deleteItems(items):
    override = C.copy()
    override["selected_objects"] = items
    with C.temp_override(**override):
        bpy.ops.object.delete()
        
def selectItem(item):   
    bpy.ops.object.select_all(action='DESELECT')
    item.select_set(True)
    C.view_layer.objects.active = item

# get the length of a curve, based on 
# https://blenderartists.org/t/length-of-a-bezier-curve-in-2-5/496672/31
def getCurveLength(curve):
    # was edit mode enabled    
    em = (curve.mode == 'EDIT')
    
    # convert curve to mesh
    bpy.ops.object.mode_set()
    bpy.ops.object.convert(target='MESH', keep_original=True)
    mesh = C.active_object
    # count its length
    ve = mesh.data.vertices
    dd = le = 0
    for i in mesh.data.edges:
        dd = ve[i.vertices[0]].co - ve[i.vertices[1]].co
        le += dd.length
    le = round(le,4)
    
    # cleanup
    deleteItems([mesh])
    selectItem(curve)
    # return to edit mode if necessary
    if em: bpy.ops.object.mode_set(mode='EDIT', toggle=False)
    return le
    
    
def getCurvePointLengths(curve,percentages=False):    
    if len(curve.data.splines) > 1:
        return "please use a curve with only one spline"
    
    # make a collection to store the new objects being made
    collection = bpy.data.collections.new("CurveLengthsTEMP")
    C.scene.collection.children.link(collection)
    items = [collection]
    results = [0]
    
    # deselect every point on the curve
    selectItem(curve)
    bpy.ops.object.mode_set(mode = 'EDIT')
    bpy.ops.curve.select_all(action='DESELECT')
    bpy.ops.object.mode_set(mode = 'OBJECT')
    
    nPoints = len(curve.data.splines[0].bezier_points)
    for i in range(nPoints-1):
        # make a copy of the curve
        partialCurve = curve.copy()
        partialCurve.data = curve.data.copy()
        partialCurve.animation_data_clear()
        collection.objects.link(partialCurve)
        items.append(partialCurve)
        
        # dont bother with the deleting stuff if its the curve itself
        if i+2 == nPoints:
            continue
        
        # select segments to delete
        spline = partialCurve.data.splines[0]
        for j in range(i+2,nPoints):
            point = spline.bezier_points[j]
            point.select_control_point = True

        # delete segments
        bpy.ops.object.mode_set(mode = 'EDIT')
        s=bpy.ops.curve.delete(type='VERT')
        bpy.ops.object.mode_set(mode = 'OBJECT')
        
        # there was a bug so il just leave this here
        if "FINISHED" not in s:
            print(i+2,spline,partialCurve)
            items.pop(-1)
            continue
    
    # measure segments
    for i in items[1:]:  
        selectItem(i)
        length = getCurveLength(i)
        results.append(length)
        
    # cleanup
    deleteItems(items)
    selectItem(curve)
        
    # convert the numbers to percentages
    if percentages:
        last = results[-1]
        for i in range(len(results)-1):
            i += 1
            results[i] = results[i]/last
    return results
    
# sets the tilt amount for all points in a curve based on a total rotation in degrees
def setCurveTilt(curveObj, totalRot,proportional):
    # convert the total rotation from degrees to radians.
    rads = deg2Rad(totalRot)
    
    if proportional:
        # get percentage of each handle along the curve
        rots = getCurvePointLengths(curveObj, True)
        if isinstance(rots,str):
            proportional=False
            print("Can't do proportional by distance:",rots)
    
    # iterate over each curve within the curve object
    for curve in curveObj.data.splines:
        points = curve.bezier_points
                
        # find the amount of radians to increment each point,
        # by dividing the total amount of radians by the total amount of points in the curve,
        # excluding the first point.
        tIncrement = rads / (len(points)-1)
        # iterate over each curve point within the curve
        for point in range(len(points)):
            # find the amount of tilt to apply to the point, by multiplying the tilt increment
            if proportional:
                # by how for it is from the start
                tilt = rads * rots[point]
            else:
                # with the position of the point in the loop.
                tilt = tIncrement * point
            
            # set the points tilt
            points[point].tilt = tilt

# get all selected curve objects and sets their tilts 
def setCurveObjectTilts(tiltRot, proportinalByDistance=False):
    # get all selected objects
    obList = C.selected_objects
    # iterate over the objects
    for o in obList:
        # if the object is of type curve, run setCurveTilt function
        if(o.type == "CURVE"):
            setCurveTilt(o, tiltRot, proportinalByDistance)
            


setCurveObjectTilts(tiltRotation, proportional)