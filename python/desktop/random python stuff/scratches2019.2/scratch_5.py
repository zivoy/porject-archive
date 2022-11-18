import pyttanko

p = pyttanko.parser()

with open(r"C:\Users\Games\AppData\Local\osu!\Songs\727333 Seeed - Ding [no video]\Seeed - Ding (IceKalt) [Ich versteck' mein' Ehering].osu","r", encoding="utf8") as mp:
    bmp = p.map(mp)

stars = pyttanko.diff_calc().calc(bmp)
starshr = pyttanko.diff_calc().calc(bmp,pyttanko.mods_from_str("HR"))

print(stars,"\n",starshr)
print(bmp.title, bmp.artist)