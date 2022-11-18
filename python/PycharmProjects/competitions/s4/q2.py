index = 0
string = dict()
for i in range(int(input())):
    command = input()
    if command == "h" and index > 0:
        index -= 1
    elif command == "l":
        index += 1
    elif command[0] == "r":
        if len(command) == 1:
            if index in string:
                del string[index]
                continue
        string[index] = command[1]

print("".join([string[i] for i in sorted(string.keys())]))
