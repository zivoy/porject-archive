from socket import *
import sys
ip = '192.168.86.15'

s=socket(AF_INET, SOCK_DGRAM)
s.setsockopt(SOL_SOCKET, SO_BROADCAST, 1)
s.sendto("noam: {}".format(sys.argv[1]).encode(),(ip,44732))
l=socket(AF_INET, SOCK_DGRAM)
l.bind(('',33241))
l.settimeout(5.0)
print(l.recvfrom(1024)[0].decode())
