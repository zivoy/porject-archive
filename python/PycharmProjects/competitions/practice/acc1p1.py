import sys


def update(stri, start, end):
    end = start+end
    p1 = stri[:start]
    p2 = stri[end:]
    middle = stri[start:end]
    return p1+middle[::-1]+p2


def queary(stri, start, end):
    end=start+end
    prts = stri[start:end].split("0")
    if not prts:
        return 0
    return len(max(prts))


#inp = sys.stdin.read().split("\n")[1:]
inp = "4 3\n0101\n2 1 3\n1 2 3\n2 1 3".split("\n")[1:]
string = inp[0]
for i in inp[1:]:
    if i:
        j = list(map(int,i.split()))
        if j[0] == 1:
            string = update(string, j[1],j[2])
        if j[0] == 2:
            print(queary(string, j[1],j[2]))

"4 3\n0101\n2 1 3\n1 2 2\n2 1 3\n"
