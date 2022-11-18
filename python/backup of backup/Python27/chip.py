# Import the math library
# This line is done only once, and at the very top
# of the program.
from math import *
def hi():
 
    # Calculate x using sine and cosine
    x = sin(0) + cos(0)
    print (x)
def t():
    # Calculate Miles Per Gallon
    print("This program calculates mpg.")
 
    # Get miles driven from the user
    milesDriven = input("Enter miles driven:")
    # Convert text entered to a
    # floating point number
    milesDriven = float(milesDriven)
 
    #Get gallons used from the user
    gallonsUsed = input("Enter gallons used:")
    # Convert text entered to a
    # floating point number
    gallonsUsed = float(gallonsUsed)
 
    # Calculate and print the answer
    mpg = milesDriven / gallonsUsed
    print ("Miles per gallon:",mpg)
def p():
    radius = float(input("Radius:"))
    pi = 3.14
    area = pi  * radius ** 2
    return area
    
