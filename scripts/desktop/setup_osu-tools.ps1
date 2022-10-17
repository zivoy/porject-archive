git clone https://github.com/ppy/osu-tools
cd .\osu-tools
rm .\.gitmodules

Add-Content .\.gitmodules "[submodule `"osu`"]"
Add-Content .\.gitmodules "        path = osu"
Add-Content .\.gitmodules "        url = https://github.com/VINXIS/osu"
Add-Content .\.gitmodules "        branch = joz"

git submodule deinit --all -f
git submodule init
git submodule update --remote

& .\build.ps1