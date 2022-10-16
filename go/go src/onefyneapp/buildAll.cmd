@echo off
rmdir /s /q builds

set CGO_ENABLED=1

echo building windows
fyne package -os windows -icon icon.png

echo building linux
rem fyne package -os linux -icon icon.png
wsl /home/zivoy/go/bin/fyne package -os linux -icon icon.png

echo building mac
rem fyne package -os darwin -icon icon.png
rem bash -c "CGO_ENABLED=1;/home/zivoy/go/bin/fyne package -os darwin -icon icon.png"

echo moving files
mkdir builds
move onefyneapp* builds >nul

rem fyne package -os android -appID com.example.myapp -icon icon.png
rem C:\Users\zivno\AppData\Local\Android\Sdk\platform-tools> .\adb.exe install "C:\Users\zivno\go\src\onefyneapp\fyne-cross\dist\android\onefyneapp.apk"