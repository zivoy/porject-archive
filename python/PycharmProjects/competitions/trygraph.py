from collections import defaultdict, deque


class graph:
    class connection:
        def __init__(self, a, b):
            self.a, self.b = sorted([a, b])

        def tuple(self):
            return self.a, self.b

        def __str__(self):
            return f"{self.a} and {self.b}"

        def __hash__(self):
            return hash(self.tuple())

        def __eq__(self, other):
            if not isinstance(other, graph.connection):
                return False
            return self.tuple() == other.tuple()

        def __ne__(self, other):
            return not self.__eq__(other)

        def __getitem__(self, item):
            return self.tuple()[item]

    def __init__(self, connections=None):
        # connections is [a,b,weight]
        self.connections = defaultdict(list)
        self.connected = defaultdict(list)
        self.nodes = set()
        if connections is None:
            connections = []
        for i in connections:
            self.connections[self.connection(*i[:2])].append(i[2])
        self.genconnected()

    def add(self, line, weight):
        self.connections[line].append(weight)
        self.genconnected()

    def weight_list(self):
        mindict=defaultdict(list)
        for i in self.connections:
            for j in self.connections[i]:
                mindict[j].append(i)
        return mindict

    def genconnected(self):
        itmdict = defaultdict(list)
        for i in self.connections:
            itmdict[i[0]].append(i[1])
            itmdict[i[1]].append(i[0])
            self.nodes.add(i[0])
            self.nodes.add(i[1])
        self.connected = itmdict

    def pathpossible(self, start, end=None):
        if end is None and isinstance(start, graph.connection):
            start,end = start.tuple()
        if start not in self.nodes or end not in self.nodes:
            return False
        queue = deque(getitms(self.connected, start))
        visited = set()
        while bool(queue):
            itm = queue.popleft()
            visited.add(itm)
            if itm == end:
                return True
            for i in getitms(self.connected, itm):
                if i not in visited and i not in queue:
                    queue.append(i)
        return False

    def minspantree(self):
        def checkall():
            if tree.nodes != self.nodes:
                return False
            p=set()
            for k in tree.connected:
                for v in tree.connected[k]:
                    p.add(v)
            if set(tree.connected) == p:
                return True
            return False

        tree = graph()
        weighted = self.weight_list()
        for i in sorted(weighted):
            if checkall():
                break
            for j in weighted[i]:
                if not tree.pathpossible(j):
                    tree.add(j, i)

        return tree

    def value(self):
        cost = 0
        for i in self.connections:
            cost += sum(self.connections[i])
        return cost

    def diffrence(self, other):
        def pairs(obj):
            lst = list()
            for i in obj.connections:
                lst.extend([(i, j) for j in obj.connections[i]])
            return lst
        return [i for i in pairs(self) if i not in pairs(other)]


def getitms(lst, idx):
    if idx in lst:
        return lst[idx]
    return []


"""t=graph([[1,2,7],[1,3,3],[2,5,3],[2,4,3],[3,4,10],[4,5,5], [1,2,2]])
print(",   ".join([f"{i}: {j}" for i,j in t.connections.items()]))
print()
print(",   ".join([f"{i}: {[str(k) for k in j]}" for i,j in t.weight_list().items()]))
print(graph.connection(3, 2))
print(t.connected)
print(t.pathpossible(1, 5))
print(t.pathpossible(1, 6))
print(t.minspantree().connected)
print(t.value())
print(t.minspantree().value())
print(",    ".join([f"{i}, {j}" for i,j in t.diffrence(t.minspantree())]))"""
f= graph([[1,100,7],[1,100,4],[1,100,4],[2,100,1],[3,100,3],[3,100,2],[1,2,2],[2,3,5]])
print(",    ".join([f"{i}, {j}" for i,j in f.diffrence(f.minspantree())]))
