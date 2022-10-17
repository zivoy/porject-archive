import sys
from cx_Freeze import setup, Executable

setup(
    name = "Dice",
    version = "1",
    description = "roll the dice",
    executables = [Executable("dice.py", base = "Win32GUI")])
