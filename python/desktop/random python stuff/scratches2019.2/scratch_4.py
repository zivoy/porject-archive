response = [
  {
    "beatmap_id": "1285526",
    "score_id": "2842025914",
    "score": "3145391",
    "maxcombo": "402",
    "count50": "0",
    "count100": "23",
    "count300": "254",
    "countmiss": "0",
    "countkatu": "18",
    "countgeki": "60",
    "perfect": "0",
    "enabled_mods": "576",
    "user_id": "13895594",
    "date": "2019-07-01 22:04:09",
    "rank": "S",
    "pp": "102.69"
  },
  {
    "beatmap_id": "1253771",
    "score_id": "2846198237",
    "score": "1799669",
    "maxcombo": "322",
    "count50": "0",
    "count100": "9",
    "count300": "197",
    "countmiss": "1",
    "countkatu": "7",
    "countgeki": "41",
    "perfect": "0",
    "enabled_mods": "64",
    "user_id": "13895594",
    "date": "2019-07-08 01:07:18",
    "rank": "A",
    "pp": "101.704"
  },
  {
    "beatmap_id": "1711830",
    "score_id": "2852544010",
    "score": "297974",
    "maxcombo": "121",
    "count50": "1",
    "count100": "28",
    "count300": "83",
    "countmiss": "3",
    "countkatu": "12",
    "countgeki": "10",
    "perfect": "0",
    "enabled_mods": "64",
    "user_id": "13895594",
    "date": "2019-07-17 00:51:54",
    "rank": "C",
    "pp": "91.6388"
  },
  {
    "beatmap_id": "1570183",
    "score_id": "2863142959",
    "score": "3417242",
    "maxcombo": "503",
    "count50": "3",
    "count100": "57",
    "count300": "263",
    "countmiss": "0",
    "countkatu": "34",
    "countgeki": "37",
    "perfect": "0",
    "enabled_mods": "0",
    "user_id": "13895594",
    "date": "2019-08-01 03:23:50",
    "rank": "A",
    "pp": "88.2523"
  },
  {
    "beatmap_id": "1905191",
    "score_id": "2869252333",
    "score": "1660912",
    "maxcombo": "326",
    "count50": "4",
    "count100": "46",
    "count300": "211",
    "countmiss": "1",
    "countkatu": "30",
    "countgeki": "30",
    "perfect": "0",
    "enabled_mods": "64",
    "user_id": "13895594",
    "date": "2019-08-09 22:29:57",
    "rank": "B",
    "pp": "84.4377"
  }
]

import arrow

for i in range(len(response)):
    response[i]["date"] = arrow.get(response[i]["date"])

print(sorted(response,key=lambda k:k["date"], reverse=True))