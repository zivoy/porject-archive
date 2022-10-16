import requests
from bs4 import BeautifulSoup
import arrow


class steamItem:
    def __init__(self, name, steamId, promoType, promoStart, promoEnd):
        self.name = name
        self.steamId = steamId
        self.promoType = promoType
        self.promoStart = promoStart
        self.promoEnd = promoEnd

    def getApp(self):
        return "store.steampowered.com/app/" + self.steamId

    def getImgUrl(self, small=False):
        aug = ""
        if small:
            aug = "_292x136"
        return f"http://cdn.akamai.steamstatic.com/steam/apps/{self.steamId}" \
            f"/header{aug}.jpg?t={arrow.now().timestamp}"

    def __str__(self):
        txt = self.name.replace("\n", "\\n")
        return f"{self.steamId} - {txt} - {self.promoType} deal started " \
            f"{self.promoStart.humanize()} and ends {self.promoEnd.humanize()}"

    def __hash__(self):
        return hash((self.steamId, self.promoType,
                     self.promoStart, self.promoEnd))

    def __eq__(self, other):
        if not isinstance(other, steamItem):
            return False

        return (self.steamId == other.steamId) and \
               (self.promoType == other.promoType) and \
               (self.promoStart == other.promoStart) and \
               (self.promoEnd == other.promoEnd)


url = "https://steamdb.info/upcoming/free/"

page = requests.get(url, headers={'user-agent': 'Mozilla/5.0 ()'})

soup = BeautifulSoup(page.content, 'html.parser')

table = soup.findAll("tbody")
elements = [j for s in [i.findAll("tr") for i in table] for j in s]

# table = soup.find("tbody")
# elements = table.findAll("tr")

available = list()

for i in elements:
    try:
        steamid = i.attrs["data-appid"]

        tablePart = i.findAll("td")
        name = tablePart[1].text.replace("\n\n", "")
        promoType = tablePart[3].text.strip(" ").lower()
        start = tablePart[4].attrs["data-sort"]
        end = tablePart[5].attrs["data-sort"]

        start = arrow.get(start, "X")
        end = arrow.get(end, "X")

        r = steamItem(name, steamid, promoType, start, end)

        available.append(r)
    except:
        print(i.findAll("td"))
####
keepable = [i for i in available if i.promoType == "keep"]

[print(i, end="\n\n") for i in keepable]
