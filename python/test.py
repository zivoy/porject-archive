succs = dict()
for i in range(6):
  for j in range(6):
    if i+j not in succs:
      succs[i+j] = set()
    string=""
    for k in range(0b100000):
      if sum(map(int,f"{k:b}")) != i:
        continue
      for v in range(0b100000):
        if sum(map(int,f"{v:b}")) != j:
          continue
        string+=f"{k:05b} {v:05b}\t {k^v}\n"
        if (k^v) % 3 == 0:
          succs[i+j].add(tuple(sorted([f"{k:05b}",f"{v:05b}"])))
    #print(string)
succs_key = sorted(succs, key=lambda x: len(succs[x]))
#[print(i,succs[i]) for i in succs_key]
for i in succs:
  items = list(succs[i])
  for j,v in enumerate(items):
    items[j] = (v[0][::-1], v[1][::-1])
  succs[i] = items
[print(f"{i}: {sorted(succs[i])}".replace("'","")) for i in succs]
