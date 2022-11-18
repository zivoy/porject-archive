SoundGet, master_volume
MsgBox, Master volume is %master_volume% percent.

SoundGet, master_mute, , mute
MsgBox, Master Mute is currently %master_mute%.

SoundGet, bass_level, Master, bass
if ErrorLevel
    MsgBox, Error Description: %ErrorLevel%
else
    MsgBox, The BASS level for MASTER is %bass_level% percent.

SoundGet, microphone_mute, Microphone, mute
if microphone_mute = Off
    MsgBox, The microphone is not muted.