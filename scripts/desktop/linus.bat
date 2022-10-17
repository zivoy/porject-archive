start /B x410.exe /desktop
remubuntu2004.exe run "if [ -z \"$(pidof xfce4-session)\" ]; then export DISPLAY=127.0.0.1:0.0; xfce4-session; pkill '(gpg|ssh)-agent'; fi;"
ubuntu2004.exe run "if [ -z \"$(pidof xfce4-session)\" ]; then export DISPLAY=$(cat /etc/resolv.conf | grep nameserver | awk '{print $2; exit;}'):0.0; xfce4-session; pkill '(gpg|ssh)-agent'; fi;"
