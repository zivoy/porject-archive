from collections import defaultdict



def read_s():
    lines = []
    for i in range(int(input())):
        lines.append( map(int, input().split()[1:]))
    return lines

def make_index(lines):
    numbers = defaultdict(set)
    for i, line in enumerate(lines):
        for j in line:
            numbers[j].add(i)
    return numbers


def read_q():
    return [list(map(int, input().split())) for _ in range(int(input()))]


def shared(pair, numbers):
    return numbers[pair[0]].intersection(numbers[pair[1]])


def get_total_fits(S, Q):
    numbers = make_index(S)
    return sum(len(shared(pair, numbers)) for pair in Q)


def main():
    s = read_s()
    q = read_q()
    print(get_total_fits(s, q))

def test1():
    s = [[1, 2, 4 ],
         [2, 3, 1],
         [3, 4]]
    q = [[1, 2],
         [3, 4]]
    assert(3 == get_total_fits(s, q))


if __name__ == "__main__":
    test1()


"""
1 2 4
2 3 1
3 4

1 2
3 4


> 3
"""

