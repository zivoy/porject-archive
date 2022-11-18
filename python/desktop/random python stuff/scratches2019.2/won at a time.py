import json

with open("export-anime-zivoy.json", "r") as i:
    file = i.read()

data = json.loads(file)

'''
stats = set()

for i in data["entries"]:
    stat = i["status"]
    stats.add(stat)

print(stats)
'''
c= 0
vls ={i:0 for i in range(11)}
stats = ['stalled', 'dropped', 'watched', "won't watch", 'watching', 'want to watch']

for i in data["entries"]:
    if i["status"] == stats[2]:
        print("\n\n\n\n\n\n\n\n\n\n\n")
        for j in i:
            v = i[j]
            if j == "rating":
                v = int(v*2)
                vls[v]+=1
            print(j+":\t\t"+str(v))
        c+=1
        #input()

print(c)
print(vls)
