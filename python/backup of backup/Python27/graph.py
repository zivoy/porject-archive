class Graph:
    def __init__(self):
        self.list_of_vertices = dict()
    def addVertex(self, vnum):
        if vnum in self.list_of_vertices:
            return
        self.list_of_vertices[vnum]=Vertex()
    
    def getVertex(self,v):
        return self.list_of_vertices[v]
    def getNeighbors(self,v):
        return self.getVertex(v).list_of_neighbors
    
    def addEdge(self,v1, v2):
        if v1 not in self.list_of_vertices:
            print v1, "not in",self.list_of_vertices
            return
        self.getVertex(v1).addNeighbor(v2)
        self.getVertex(v2).addNeighbor(v1)

    
class Vertex:
    def __init__(self):
        self.list_of_neighbors = list() # se

    def addNeighbor(self, n):
        self.list_of_neighbors = list(set(self.list_of_neighbors).add(n))

if __name__ == "__main__":
    print "hello"
    g=Graph()
    for i in range(10):
        g.addVertex(i)
        print "vertex", i
    for i in range(10):
        for j in range(3):
            print "adding edge", pow(i+3,j+1)%10
            g.addEdge(i,pow(i+3,j+1)%10)
