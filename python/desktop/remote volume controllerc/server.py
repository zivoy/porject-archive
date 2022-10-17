from socket import socket, AF_INET, SOCK_DGRAM, SOL_SOCKET, SO_BROADCAST
import re
from subprocess import call
import sys
vol = re.compile(r"noam: (\d+)")
maxvl = 65535
s=socket(AF_INET, SOCK_DGRAM)
s.bind(('',44732))
import json
while True:
    m=s.recvfrom(1024)
    addr = m[1]
    mtc = vol.search(m[0].decode())
    if mtc:
        dvl = min(max(float(mtc.group(1)),0),100)
        svl = int(round(dvl/100*maxvl))
        'call([f"{sys.path[0]}\\nircmd.exe", "setsysvolume", str(svl)])'
        print(f"setting volume to {dvl}%")
        s.sendto(f"setting volume to {dvl}%".encode(), (addr[0], 33241))
        
s.close()
