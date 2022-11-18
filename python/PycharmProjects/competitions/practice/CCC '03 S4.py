async def uniqe(string):
    lst = set()
    count=len(set(string))+2
    for cut in range(2, len(string)):
        for pos in range(len(string)-cut+1):
            lst.add(string[pos:cut+pos])
        count += len(lst)
        lst = set()
    print(count)

import sys
import asyncio


async def main(items):
    await asyncio.gather(*[uniqe(i) for i in items])

asyncio.run(main(sys.stdin.read().split('\n')[1:-1]))
"""
2
abc
aaa
"""