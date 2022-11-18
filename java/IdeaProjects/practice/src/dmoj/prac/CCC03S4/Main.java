package dmoj.prac.CCC03S4;

import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = in.nextInt();
        HashMap<String, Integer> answers = new HashMap<>();
        for (int i=0;i<count;i++) {
            String str = in.next();
            System.out.println(distinctSubstring(str,answers));
        }
    }


    public static int distinctSubstring(String str, HashMap<String, Integer> answers) {
        if (answers.containsKey(str)){
            return answers.get(str);
        }
        Set<String> result = new HashSet<>();
        int count = countUniqueCharacters(str)+1;
        for (int cut = 2; cut <= str.length(); cut++) {
            for (int pos = 0; pos <= str.length()-cut; pos++) {
                result.add(str.substring(pos, pos+cut));
            }
            count += result.size();
            result.clear();
        }
        answers.put(str, count);
        return answers.get(str);
    }
    public static int  countUniqueCharacters(String a) {
        char[] charArray = a.toCharArray();

        HashSet<Character> set = new HashSet<>();

        for (char c : charArray) {
            set.add(c);
        }
        return set.size() ;
    }
}

/*
def uniqe(string):
    lst = set()
    count=len(set(string))+1
    for cut in range(2, len(string)+1):
        for pos in range(len(string)-cut+1):
            lst.add(string[pos:cut+pos])
        count += len(lst)
        lst = set()
    return count


for _ in range(int(input())):
    print(uniqe(input()))
2
abc
aaa

5
asf
avx
aaa
xxc
asf

* */
