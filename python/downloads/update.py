#!/bin/python3

import requests
import os
import sys
import time
import shutil

latestSnapshot = False

argv = sys.argv[1:]
if len(argv) < 2:
    sys.exit("not enought data given usage: path update_timeout")
path = os.path.abspath(argv[0])

if not os.path.exists(path) or not os.path.isdir(path):
    sys.exit("no such directory")
os.chdir(path)

if not argv[1].isdigit():
    sys.exit("bad timeout given")

versions = requests.get("https://launchermeta.mojang.com/mc/game/version_manifest.json").json()
#if versions["latest"]["release"] == versions["latest"]["snapshot"]:
#    print("no new snapshot")
if latestSnapshot:
    for i in versions["versions"]:
        if i["id"] == versions["latest"]["snapshot"] or i["type"] == "snapshot":
            version = i
else:
    version = versions["versions"][0]

if os.path.exists("./server_version"):
    with open("./server_version","r") as f:
        if f.read() == version["id"]:
            print(f"version {version['id']} already installed")
            exit(0)

versionData = requests.get(version["url"]).json()
serverUrl = versionData["downloads"]["server"]["url"]

print(f"Downloading server version: {versionData['id']}")

serverFile = requests.get(serverUrl)
with open("./minecraft_server.temp", "wb") as f:
    f.write(serverFile.content)

print("finished download")
with open("./server_version","w") as f:
    f.write(version["id"])

# stop server

# waiting for server to shutdown (adjust acordingly)
time.sleep(int(argv[1]))#60)
if os.path.exists("./minecraft_server.jar"):
    os.remove("./minecraft_server.jar")
os.rename("./minecraft_server.temp", "./minecraft_server.jar")

# create world backups
if os.path.exists("./backups/world.preupdate.zip"):
    os.remove("./backups/world.preupdate.zip")
shutil.make_archive("./backups/world.preupdate.zip", 'zip', "./world")

# start server
