#Persistent
SetTimer,Timer,300
Return

Timer:
     MouseGetPos,x1,y1
     Sleep,500
     MouseGetPos,x2,y2
     If ((x1<>x2) or (y1<>y2))             ;-- Checking to see if the mouse has moved.
         {
         msgbox, 262208,Mouse ,You moved`nUse Esc to quit this script , 1
         Return
         }
return
esc::exitapp