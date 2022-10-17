from os import environ, system, remove, listdir, path, __file__
from shutil import move, rmtree

program_name = "Remote Volume Controller"
# name of the program
main_file = "server.py"
# the main file that will turn into the exe
libraries_used = ["re", "socket", "subprocess", "sys"]
# add all the libraries used here e.g. pygame, random
files_needed = ["nircmd.exe"]
# this is all the files that you are using can be folders or files
program_description = "This program will allow the remote control of volume"
# program discription
program_version = "0.0.1"
# this is the program version


code = f"""\
import cx_Freeze

cx_Freeze.setup(
    name="{program_name}",
    version = "{program_version}",
    description = "{program_description}",
    options={{"build_exe": {{"packages": {libraries_used},
                           "include_files": {files_needed}}}}},
    executables=[cx_Freeze.Executable("{main_file}")]

)
"""

with open("setup.py", 'w') as setup_file:
    setup_file.write(code)

python_path = path.dirname(path.dirname(__file__))
python = path.join(python_path, "python")
pip = path.join(python_path, "Scripts", "pip")

environ['TCL_LIBRARY'] = path.join(python_path, "tcl", 'tcl8.6')
environ['TK_LIBRARY'] = path.join(python_path, "tcl", 'tk8.6')

libraries_used.append("cx_Freeze")
system(f"{python} -m pip install --upgrade pip")
for i in libraries_used:
    system(f"{pip} install {i}")

#system(f"{python} setup.py build")  # for having just the exe
system(f"{python} setup.py bdist_msi")  # for creating msi installer
#system(f"{python} setup.py bdist_dmg")  # only on mac create an installer

remove("setup.py")

# this part down here leaves only the installer
for i in listdir("dist"):
    move(f"dist/{i}", f"{i}")
rmtree("dist")
rmtree("build")
