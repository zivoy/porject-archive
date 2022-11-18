#SingleInstance Ignore
#IfWinActive, Star Citizen

ScrollUpButton := "XButton1" ; mouse button 4
ScrollDownButton := "XButton2" ; mouse button 5

scrollAmount := 1

HotKey, %ScrollUpButton%, ScrollUpHotkey
HotKey, %ScrollDownButton%, ScrollDownHotkey
Return

ScrollUpHotkey:
	while GetKeyState(ScrollUpButton,"p")
	{
		Send {WheelDown %scrollAmount%}
		sleep 1000ms
	}
return

ScrollDownHotkey:
	while GetKeyState(ScrollDownButton,"p")
	{
		Send {WheelUp %scrollAmount%}
		sleep 1000ms
	}
return
