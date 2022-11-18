fail = dict()
for r in range(1,100):
  succs = dict()
  for i in range(6):
    for j in range(6):
      if i+j not in succs:
        succs[i+j] = list()
      string=""
      for k in range(0b100000):
        if sum(map(int,f"{k:b}")) != i:
          continue
        for v in range(0b100000):
          if sum(map(int,f"{v:b}")) != j:
            continue
          string+=f"{k:05b} {v:05b}\t {k^v}\n"
          if (k^v) % r == 0:
            succs[i+j].append(f"{k:05b} {v:05b}")
  fails = set()
  for i in succs:
    if len(succs[i]) == 0:
      fails.add(i)
  if fails:
    fail[r] = fails

fail_key = sorted(fail, key=lambda x: len(fail[x]))
[print(i,fail[i]) for i in fail_key]

print(f"avilable numbers = {[i for i in range(100) if i not in fail]}")
