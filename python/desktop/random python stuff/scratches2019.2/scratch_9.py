from socket import *
s=socket(AF_INET, SOCK_DGRAM)
s.bind(('',12345))
import json
while True:
    m=s.recvfrom(1024)[0]
    print (type(json.loads(m.decode())["channel"]["id"]))
