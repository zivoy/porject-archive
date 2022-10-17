from socket import *
s=socket(AF_INET, SOCK_DGRAM)
s.setsockopt(SOL_SOCKET, SO_BROADCAST, 1)
s.sendto("aoeaoe".encode(),('255.255.255.255',12345))
s.sendto("hello wrld".encode(),('255.255.255.255',12345))