Flip(in) {
    VarSetCapacity(out, n:=StrLen(in))
    Loop %n%
        out .= SubStr(in, n--, 1)
    return out
}

^!f:: ;Starting hotkeys
ClipSaved := ClipboardAll 
Send ^c
ClipWait
Clipboard:= Flip(Clipboard)
Send ^v
Clipboard := ClipSaved
ClipSaved =

return