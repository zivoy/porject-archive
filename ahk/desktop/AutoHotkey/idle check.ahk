#Persistent
SetTimer, Check, 6000
return

Check:
IfGreater, A_TimeIdle, 6000
MsgBox, The last keyboard or mouse activity was at least 6 seconds ago.