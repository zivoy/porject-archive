# importing the requests library
import requests
import arrow

class Api:
    def __init__(self, base_url, params=dict()):
        self.url = base_url
        self.params = params

    def get(self, url, params=None, **kwargs):
        url = self.url + url
        if params is not None:
            for k, j in self.params.items():
                params[k] = j
        elif self.params != {}:
            params = self.params

        return requests.get(url, params, **kwargs)


newpp = Api("http://osusr.rorre.xyz")

#r = newpp.get("/c", {'user': "zivoy"})

#print(r.json())

osu_api = Api("https://osu.ppy.sh/api", {"k": "833807d3574ded4121a45614226891c5abfabce6"})

#us = osu_api.get('/get_user', {"u": "zivoy"})

#print(us.json())

#top = osu_api.get('/get_user_best', {"u": "zivoy", "limit": 5})

#print(top.json())

MODS = {
    "NF": 2 ** 0,
    "EZ": 2 ** 1,
    "TD": 2 ** 2,
    "HD": 2 ** 3,
    "HR": 2 ** 4,
    "SD": 2 ** 5,
    "DT": 2 ** 6,
    "RX": 2 ** 7,
    "HT": 2 ** 8,
    "NC": 2 ** 9,
    "FL": 2 ** 10,
    "AT": 2 ** 11,
    "SO": 2 ** 12,
    "AP": 2 ** 13,
    "PF": 2 ** 14,
    "4K": 2 ** 15,
    "5K": 2 ** 16,
    "6K": 2 ** 17,
    "7K": 2 ** 18,
    "8K": 2 ** 19,
    "FI": 2 ** 20,
    "RD": 2 ** 21,
    "LM": 2 ** 22,
    "TR": 2 ** 23,
    "9K": 2 ** 24,
    "10K": 2 ** 25,
    "1K": 2 ** 26,
    "3K": 2 ** 27,
    "2K": 2 ** 28,
    "V2": 2 ** 29
}
r_MODS = {v: k for k, v in MODS.items()}

def get_top(user, index, rb=None, ob=None):
    index = min(max(index, 1), 100)
    limit = 100 if rb or ob else index
    response = osu_api.get('/get_user_best', {"u": user, "limit": limit})
    response = response.json()

    if len(response) == 0:
        return False, f"No top plays found for {user}"

    for i in range(len(response)):
        response[i]["date"] = arrow.get(response[i]["date"])

    if rb:
        response = sorted(response, key=lambda k: k["date"], reverse=True)
    if ob:
        response = sorted(response, key=lambda k: k["date"])

    if len(response) < index:
        index = len(response)

    recent_raw = response[index-1]

    return True, recent_raw


def parse_mods(mod_int):
    mod_bin = bin(mod_int)[2:][::-1]
    mods = list()
    for i in range(len(mod_bin)):
        if mod_bin[i] == '1' :
            mods.append(r_MODS[1 << i])
    return mods


_, top3 = get_top("zivoy", 3)

print(top3)
