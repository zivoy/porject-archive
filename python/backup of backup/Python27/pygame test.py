# Import a library of functions called 'pygame'
import pygame
from math import pi
import numpy
from pygame.locals import *
# Initialize the game engine
pygame.init()
 
# Define the colors using RGB format
BLACK = (  0,   0,   0)
WHITE = (255, 255, 255)
BLUE =  (  0,   0, 255)
GREEN = (  0, 255,   0)
RED =   (255,   0,   0)
GRAY = (211, 211, 211)

# Set the height and width of the screen
size = [1320, 720]
screen = pygame.display.set_mode(size)
 
pygame.display.set_caption("Game of life test")
 
#Loop until the user clicks the close button.
done = False
#clock = pygame.time.Clock()
 # All drawing code happens after the for loop and but
    # inside the main while done==False loop

    
 
board = numpy.zeros([72, 132])
count_n_board = numpy.zeros([72,132])
last_click = []
loop = False


def draw_all():
        #for pos in coords:
        #pygame.draw.rect(screen, BLACK, pos + [10, 10])
    screen.fill( WHITE)
    for y in range(0,720,10):
        pygame.draw.line(screen, GRAY, [0, y], [1320, y], 1)
        
    for x in range(0,1320,10):
        pygame.draw.line(screen, GRAY, [x, 720], [x, 0], 1)
    
    for r in range(72):
        for c in range(132):
            if board[r,c]!=0:   
                pygame.draw.rect(screen, BLACK, [(c*10+1), (r*10+1), 9.5, 9.5])
    pygame.display.flip()    

def count_neighbours(row,col): # returns the number of neighbors
    # TODO ::: count neighbors
    neighbours=0
    if board[row+1,col]:
        neighbours+=1
    if board[row-1,col]:
        neighbours+=1
    if board[row,col+1]:
        neighbours+=1
    if board[row,col-1]:
        neighbours+=1
    if board[row+1,col+1]:
        neighbours+=1
    if board[row-1,col+1]:
        neighbours+=1
    if board[row+1,col-1]:
        neighbours+=1
    if board[row-1,col-1]:
        neighbours+=1
    return neighbours

def count_all_neighbours():
    for r in range(1,71):
        for c in range(1,131):
            count_n_board[r,c] = count_neighbours(r,c)
            
    #loop over rows
            
      #loop over columns
        # fill count_n_board at row,column with a cal to count_neighbours

def update_board():
    count_all_neighbours()
    for r in range(72):
        for c in range(132):
            if count_n_board[r,c] == 3:
                board[r,c] = 1
            elif count_n_board[r,c] == 2:
                pass
            else:
                board[r,c] = 0
    draw_all()

draw_all()
while not done:   
    for event in pygame.event.get(): # User did something
        pygame.event.pump()
        key = pygame.key.get_pressed()

            
        pos = [round((x - 5),-1) for x in pygame.mouse.get_pos()]
        
        row,col = pos[1]/10, pos[0]/10
        
        if event.type == pygame.QUIT: # If user clicked close
            done=True # Flag that its done so it will exit this loop
            
        if pygame.mouse.get_pressed()[2]:
            update_board()
            
        if pygame.mouse.get_pressed()[0]:
            if board[row,col] == 1 and [row,col] != last_click:
                board[row,col]=0
                last_click = row,col
            elif board[row,col] == 0 and [row,col] != last_click:
                board[row,col]=1
                last_click = row,col
            draw_all()
            pygame.time.wait (200)
            break
        
        if key[pygame.K_l]:
            if loop == False:
                print "loop started"
                loop = True
                
        while loop:
            update_board()
            if key[pygame.K_s]:
                loop = False
                print "loop stoped"
            pygame.time.wait (200)
            
pygame.quit()
