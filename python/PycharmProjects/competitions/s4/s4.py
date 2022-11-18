input()
factors_list = input().split()
int_factors_list = []
input()
numbers_list = input().split()
max_factor = 0
for i in factors_list:
    int_factors_list.append(int(i))
int_factors_list.sort()

out_str = ""
broken = False
int_number = 0
revesedlist = int_factors_list[::-1]

for i in numbers_list:
    int_number = int(i)
    index_num = 0
    solved = False
    for j in revesedlist:
        if j <= int_number:
            index_num += 1
            break
        index_num += 1

    for j in revesedlist[index_num-1:]:
        while int_number % j == 0:
            int_number = int(int_number / j)
            if int_number == 1:
                solved = True
        if solved == True:
            out_str += "1"
            break;
    if solved == False:
        out_str += "0"

print(out_str)
