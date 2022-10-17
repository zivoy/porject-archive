def lin(start, end, accelaration):
    vel = 0
    current = start
    goat = []
    if start < end:
        while current < end:
            if current < (start+end)/2:
                vel += accelaration
            else:
                vel -= accelaration
            current += vel
            goat.append(current)
    else:
        while current > end:
            if current < (start+end)/2:
                vel += accelaration
            else:
                vel -= accelaration
            current += vel
            goat.append(current)
    goat.append(end)
    return goat
