from collections import Counter
import string

def count_letters(word):
    global count
    wordsList = string.split(word)
    count = Counter()
    for words in wordsList:
        for letters in set(words):
            return count[letters]

word = raw_input("enter word: ")
print count_letters(word)
