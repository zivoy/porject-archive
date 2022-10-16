Blackchar='\033[0;30m'
Whitechar='\033[0;37m'
On_White='\033[47m'
On_Black='\033[40m'
Color_Off='\033[0m'

state = Whitechar + On_White
string = ""
for i in range (1,21):
    for j in range(1,41):
        string+="  "
        if (i*j)%17 ==0:
            state=Blackchar+On_Black
            string+=state
        elif (i*j+1)%11 ==0:
            state=Whitechar+On_White
            string+=state
    string += Color_Off+"\n"+state
    
string += Color_Off
print (string)