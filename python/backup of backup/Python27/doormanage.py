from time import sleep
from blconnect import lego
print "connecting to robot"
robot = lego()
print "connected"
cardlist = list()
managercard = raw_input('insert manager card');#'0008760993'

def open_door():
    print ("opening door...")
    sleep(1)
    robot.forward()
    print ("door open")

def show_list():
    print (cardlist)

def add_card():
    
    newcard = raw_input("please scan new card...")
    cardlist.append(newcard)

def menu_select():
    manage=int(raw_input("hi manager\nwhat do you want to do \n1.open door\n2.show card list\n3.add new card\nwhright the one you want 1,2,3: "))
    if manage == 1 :
        open_door()

    elif manage == 2 :
        show_list()
        robot.play_song()

    elif manage == 3 :
        add_card()
    else:
        print manage, "not found"

def lock():
    
    m = raw_input("please scan card...")
    if m == managercard:
        menu_select()

    else:
        print ("you are not the manager")
        if  m in cardlist:
            open_door()

        else:
            print ("you are not on the list")

def main():
    while True:
        lock()


main()
