n = int(input())

numbers = dict()
for i in range(6):
    for j in range(6):
        if i+j == n:
            if j not in numbers and i not in numbers.values():
                numbers[i] = j

print(len(numbers))
