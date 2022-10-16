# wandering server network
a way to have server networking without having everyone know everyone's ips (p2p) or unexpected shutdowns that 
a dedicated server might experience

###plans:
1. upon connecting to a network user requests for the server ip
  user then connects to the server
2. all users keep the ip of the server
3. server communicates to the users via ids
4. if server decides to disconnect, it will _request users to ping ips to build a ping network_ then tell the user with 
  a trusted user with the lowest ping to as many nodes to become the server then it will give the new servers ip 
  to all users and disconnect
5. when moving server, the old server will transfer all ips and ids to the new server along with information about who 
  knows who



###think through
- all users keep the minimum amount of ips required to rebuild the network in case server disappears
- about point 4 italicised, someone can gather all ips using who they pinged and who pinged them
- have the server move if x percent have high ping
- users have to prove their trustworthyness and that they are not compromised to the server 
  in order to be eligible to become the server
- instead of creating an entire network when moving, have the users with average ping 
  (most likely to have ok to everyone) check ping to everyone
- users report their internet speed to the server for consideration to become the server


https://stackoverflow.com/questions/16908714/how-do-you-create-a-peer-to-peer-connection-without-port-forwarding-or-a-centera

https://en.wikipedia.org/wiki/Zero-knowledge_proof

https://en.wikipedia.org/wiki/Distributed_computing

https://en.wikipedia.org/wiki/Leader_election

https://medium.com/starkware/stark-math-the-journey-begins-51bd2b063c71

https://en.wikipedia.org/wiki/Game_server#Host_migration
