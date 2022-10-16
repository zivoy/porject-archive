#!/usr/bin/env python3

import re
import os

reg = re.compile(r"(.*) server-kon sudo: *(.*) : user NOT in sudoers ;.*PWD=(.*) ; U.*COMMAND=(.*)")

matchs= list()

with open("/var/log/auth.log", "r") as f:
  for i in f:
    j= reg.match(i)
    if j is not None:
      matchs.append(j)

matchs = {" - ".join(i.groups()): i for i in matchs}


hashes = set()


logFile = os.path.join(os.path.dirname(__file__), ".accounted")

if not os.path.exists(logFile):
  open(logFile, "x").close()


with open(logFile, "r") as f:
  for i in f:
    hashes.add(i.strip("\n"))


diffs = set(matchs).difference(hashes)

if diffs:
  with open(logFile, "a") as f:
    for i in diffs:
      f.write(i)
      f.write("\n")

matchs = [matchs[i] for i in diffs]


offenses = dict()

for i in matchs:
  if i.group(2) not in offenses:
    offenses[i.group(2)] = list()
  offenses[i.group(2)].append((i.group(1),i.group(3),i.group(4)))

print(f"\n\n--------{len(offenses)} new sudo offenders since last login--------")
if not matchs:
  print("\n")
  exit()

for i in offenses:
  nOff = len(offenses[i])
  print(f"{nOff} offence{'s' if nOff != 1 else ''} - {i}")

print("\n--------crimes commited--------")
for i in offenses:
  criminal = i
  crimes = offenses[i]
  for crime in crimes:
    time = crime[0]
    place = crime[1]
    charge = crime[2]
    print(f"{criminal} attempted to execute '{charge}' while in '{place}' on {time}")

print("\n----------------\n")