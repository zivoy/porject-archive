~capslock::
 {
   settimer, removetooltip, -3000
   if getkeystate("capslock","t")
    {
      tooltip, caps lock is ENABLED
    }
   else
    {
      tooltip, caps lock is DISABLED
    }
 }
return

removetooltip:
 {
   tooltip
 }
return