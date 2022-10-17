import pandas
from matplotlib import pyplot as plt
#import stuff

csvFile = pandas.read_csv("factions.csv")
#import csv file
player_f = csvFile["is_player_faction"].value_counts()
#put and count the heading "is_player_faction" theyer all 1 or 0
player_f.rename(index={0:'non player',1:'player'},inplace = True)
#mapping names
player_f.plot(kind='pie' , label='')
#plotting
plt.show(block=True)
#making plot stay