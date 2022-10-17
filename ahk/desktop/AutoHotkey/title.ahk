;WinGetTitle, Title, Minecraft
;MsgBox, The active window is "%Title%".

id := WinExist("A")
MsgBox % id