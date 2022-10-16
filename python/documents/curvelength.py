import bpy

C = bpy.context

def deleteItems(items):
    override = C.copy()
    override["selected_objects"] = items
    with C.temp_override(**override):
        bpy.ops.object.delete()
        
def selectItem(item):   
    bpy.ops.object.select_all(action='DESELECT')
    item.select_set(True)
    C.view_layer.objects.active = item

def getCurveLength(curve):
    if not (curve and curve.select_get()) or (curve and curve.type != 'CURVE'):
        return 'not a curve object'
    
    if len(curve.data.splines) > 1:
        return "please use a curve with only one spline"
    
    em = (curve.mode == 'EDIT')
    
    bpy.ops.object.mode_set()
    bpy.ops.object.convert(target='MESH', keep_original=True)
    mesh = C.active_object
    ve = mesh.data.vertices
    dd = le = 0
    for i in mesh.data.edges:
        dd = ve[i.vertices[0]].co - ve[i.vertices[1]].co
        le += dd.length
    le = round(le,4)
    
    # cleanup
    deleteItems([mesh])
    selectItem(curve)
    if em: bpy.ops.object.mode_set(mode='EDIT', toggle=False)
    return le
    
    
def getCurveLengths(curve,percentages=False):
    if not (curve and curve.select_get()) or (curve and curve.type != 'CURVE'):
        return 'not a curve object'
    
    if len(curve.data.splines) > 1:
        return "please use a curve with only one spline"
    
    collection = bpy.data.collections.new("CurveLengthsTEMP")
    bpy.context.scene.collection.children.link(collection)
    items = [collection]
    results = [0]
    
    
    bpy.ops.object.mode_set(mode = 'EDIT')
    bpy.ops.curve.select_all(action='DESELECT')
    bpy.ops.object.mode_set(mode = 'OBJECT')
    
    nPoints = len(curve.data.splines[0].bezier_points)
    for i in range(nPoints-1):
        partialCurve = curve.copy()
        partialCurve.data = curve.data.copy()
        partialCurve.animation_data_clear()
        collection.objects.link(partialCurve)
        items.append(partialCurve)
        
        if i+2 == nPoints:
            continue
        
        spline = partialCurve.data.splines[0]
        for j in range(i+2,nPoints):
            point = spline.bezier_points[j]
            point.select_control_point = True

        bpy.ops.object.mode_set(mode = 'EDIT')
        s=bpy.ops.curve.delete(type='VERT')
        bpy.ops.object.mode_set(mode = 'OBJECT')
        if "FINISHED" not in s:
            print(i+2,spline,partialCurve)
            continue
    
    for i in items[1:]:  
        selectItem(i)
        length = getCurveLength(i)
        results.append(length)
        
    deleteItems(items)
    selectItem(curve)
        
    if percentages:
        last = results[-1]
        for i in range(len(results)-1):
            i += 1
            results[i] = results[i]/last
    return results

ob = C.active_object
print(getCurveLengths(ob,True))
