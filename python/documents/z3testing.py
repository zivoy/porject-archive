"""
a b c g1 g2 g3 o
0 0 0 0  0  0  0
0 0 1 0  0  0  0
0 1 0 1  0  0  1
0 1 1 0  1  0  1
1 0 0 0  0  0  0
1 0 1 0  0  0  0
1 1 0 0  0  1  1
1 1 1 0  0  0  0
"""

import z3

a, b, c = z3.Bools("a b c")

nA = z3.Not(a)
nB = z3.Not(b)
nC = z3.Not(c)

g1 = z3.And(nA, b, nC)
g2 = z3.And(nA, b, c)
g3 = z3.And(a, b, nC)

o = z3.Or(g1, g2, g3)
# r= z3.solve(o)
s = z3.Solver()
s.add(o)
# s.check(z3.Not(c))
# print(s.model())
sols = list()
while s.check() == z3.sat:
    sols.append(s.model())
    s.add(z3.Or(a != s.model()[a], b != s.model()[b], c != s.model()[c]))

for s in sols:
    print(f"a = {s[a]}\tb = {s[b]}\tc = {s[c]}")