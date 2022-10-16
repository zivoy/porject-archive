#acc test beat saber
#

import pandas as pd
from tqdm import tqdm
import requests
from bs4 import BeautifulSoup
from time import sleep

def paresePage(url):
    page = requests.get(url)
    return BeautifulSoup(page.content, 'html.parser')

acc = re.compile(r"\d+\.\d+")
mods = re.compile(r"\((.+)\)")
df = pd.DataFrame(columns=["map", "diff", "mapper", "acc","mods"])
url = "https://scoresaber.com/u/76561198158987204/"
pages = int(paresePage(url).findAll("li")[-1].text[1:-1])
stop=False
for i in tqdm(range(1,pages+1)):
    sleep(.3)
    soup = paresePage(url+"&page="+str(i)+"&sort=1")
    table = soup.find("tbody")
    elements = table.findAll("tr")
    for j in elements:
        parts = [i.text for i in j.findAll("span")]
        pp=float(parts[4])
        mod = mods.search(parts[8])
        mod = "" if mod is None else mod.group(1)
        if pp!=0:
            df.loc[len(df)]=  [j.findAll("span")[0].find(text=True),parts[1],parts[2],float(acc.search(parts[8][10:]).group()), mod]
        else:
            stop=True
            break
    if stop:
        break
df = df.sort_values(by=["acc"])
print(df[df.acc<50])
#df[df.mods!=""]