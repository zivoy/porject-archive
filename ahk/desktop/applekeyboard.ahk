#Warn  ; Enable warnings to assist with detecting common errors. 
#NoEnv ; Recommended for performance and compatibility with future AutoHotkey releases.
#MaxHotkeysPerInterval 1000
#SingleInstance force ; Replace any previous instance 

SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.

; Variable for the modifier key
fnPressed := 0
fnPrevState := 0
ejPressed := 0
ejPrevState := 0
 
; modify modifiers
SetNumLockState, AlwaysOn
LWin::LAlt
LAlt::LWin
RWin::AppsKey
Numlock::Backspace

; modify extra fns0
F13::PrintScreen
F14::ScrollLock
F15::CtrlBreak
;F16::send {Launch_App1}
F17::send {Launch_App2}
F18::run notepad.exe
F19::run taskmgr.exe

