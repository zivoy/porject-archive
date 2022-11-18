InputBox, Amouts, Times, Enter the amount of times you want to send the message`n`t`t      (max 200),, 360, 150,,,,,10

if Amouts is not integer
{
     MsgBox, "%Amouts%" is not an integer
     Return
}
else
    Amouts:= Max(Min(Amouts, 200), 0)

InputBox, UserInput, Message, Please enter a the message you wish to send`nDont forget to click the messaging box you wish to type into before clicking ok, , 400, 170

if ErrorLevel
    Return
x:=0
While x < Amouts
{
    x:=x+1
    Send, {Raw}%UserInput%`n
    Sleep, 200
}
Return