#Persistent
#NoTrayIcon
SetTimer, Check, 15000
return

Check:
	if (A_TimeIdle >= 900000)
	{
		WinGetTitle, Title, Minecraft
		IfWinExist, %Title%
		{
			WinClose %Title%
			MsgBox, Sagi stop leaving mincraft on.
		}
	}