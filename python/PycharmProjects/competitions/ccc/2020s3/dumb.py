h=input()
s=input()
letters = list(h)
words = list()
for i in range(len(s)-len(h)):
    if set


    if s[i] in h:
        subletters = letters.copy()
        subletters.remove(s[i])
        word=s[i]
        for j in range(1,len(subletters)+1):
            if s[i+j] in subletters:
                subletters.remove(s[i+j])
                word += s[i+j]
            else:
                break
        if word not in words and not subletters:
            words.append(word)

print(len(words))

