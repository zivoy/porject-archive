Suspend on

~capslock::
 {
   if getkeystate("capslock","t")
    {
      tabing = true
    }
   else
    {
      tabing = false
    }
 }
return

Enter::
 {
    if tabing == false
       Send {Enter}{Enter}
    else
       Send {Enter}{Tab}{Enter}{Tab}
}

F4::Suspend

Up::Send {Up}{Up}

Down::Send {Down}{Down}

Lalt::
Suspend on
Sleep, 100
Suspend off
return