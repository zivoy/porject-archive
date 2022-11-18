import re
a=input()
b=input()
counts = dict()
for i in set(a):
    counts[i] = a.count(i)
d = re.compile(f"[{'|'.join(set(a))}]{{{len(a)}}}")
found = list()
for i in range(len(b)-len(a)+1):
    m=d.match(b[i:i + len(b)])
    if m and m.group(0) not in found:
        if all([m.group(0).count(j) == counts[j] for j in counts]):
            found.append(m.group(0))
print(len(found))
