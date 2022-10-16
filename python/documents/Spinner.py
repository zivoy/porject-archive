import bpy
from math import pi

secondsComp = .3




def transsition(target_rpm, spinVals):
    
    
    
    frame = bpy.context.scene.frame_current
    fps = bpy.context.scene.render.fps
    
        
    if frame == spinVals["lastFrame"]:
        return 0
    
    dF =frame-spinVals["lastFrame"]
    
    if dF >0:
        if (target_rpm != spinVals["startPRM"]):
            spinVals["frameChange"] = frame
            spinVals['prevPRM'] = spinVals['startPRM']
            spinVals['startPRM'] = target_rpm
    else:
        if (spinVals['prevPRM'] != spinVals["startPRM"]):        
            spinVals["frameChange"] = frame
            target_rpm = spinVals['startPRM']
            spinVals['startPRM'] = spinVals['prevPRM']
        
    
    start = velAtRpm(spinVals['prevPRM'])
    end = velAtRpm(target_rpm)
    
    deltaFrame = abs(frame - spinVals["frameChange"])
    deltaSecond = deltaFrame / fps
    cMap = min(max(deltaSecond/secondsComp,0),1)
    
    
    dcel = 6. * (-1/3*cMap**3 + 1/2*cMap**2)
    
    
    if cMap >= 1 and spinVals['prevPRM'] != spinVals["startPRM"]:
        spinVals['prevPRM'] = spinVals["startPRM"]
    
    
    #print(frame,spinVals["frameChange"], cMap, dcel, start + dcel * (end-start))
    
    return (start + dcel * (end-start)) * (dF)



def rotAtFrame(rpm,frame):
    fps = bpy.context.scene.render.fps
    return pi * frame * rpm / (30 * fps)

def velAtRpm(rpm):
    return rotAtFrame(rpm, 1) - rotAtFrame(rpm, 0)


def reset(spinVals, rpm, frame):
    spinVals["prevRot"] = rotAtFrame(rpm,frame)
    spinVals["lastFrame"] = frame
    spinVals["frameChange"] = frame
    spinVals['prevPRM'] = rpm
    spinVals['startPRM'] = rpm


def spinning(obj):
    frame = bpy.context.scene.frame_current
    fps = bpy.context.scene.render.fps
        
    rpm = obj["RPM"]
    
    name = "spin-"+obj.name #+ str(hash(obj.bone))
    
    
    if name not in bpy.data.collections:        
        spinVals = bpy.data.collections.new(name)
        reset(spinVals,rpm,frame)
    else:
        spinVals = bpy.data.collections[name]
        
    if frame == bpy.context.scene.frame_start or frame == bpy.context.scene.frame_end:
        reset(spinVals,rpm,frame)
    
    vel = 0
    
   
        
    vel = transsition(rpm,spinVals) 
    
    spinVals["prevRot"] += vel
    spinVals["lastFrame"] = frame
    return spinVals["prevRot"]

bpy.app.driver_namespace['spinning'] = spinning
for i in bpy.data.collections:
    if i.name.startswith("spin-"):
        bpy.data.collections.remove(i)