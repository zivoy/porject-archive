angle, time = input().split()
A = float(angle)
timep = list(map(int,time.split(":")))
tm = timep[0] + 60*timep[1]
tim = tm/24*360

sunAngle = round(A-tim)
if sunAngle<0:
    sunAngle= 360+sunAngle
print(sunAngle%360)
