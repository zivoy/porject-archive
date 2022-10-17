HotKey1 := "F12" ; F12
HotKey2 := "^Down" ; ctrl + Down

HotKey, %HotKey1%, DoTheKey
HotKey, %HotKey2%, DoTheKey
Return

DoTheKey:
MsgBox You pressed "%A_ThisHotKey%"!
Return