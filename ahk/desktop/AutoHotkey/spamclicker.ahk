M:: ;Starting hotkeys
loop
{
MouseClick
keywait, N, D T0.01 ;Exit out of loop
if errorlevel = 0
break
}
return