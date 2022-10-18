import com.zivoy.module3.BracketConfirmer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Question1Tests {
    BracketConfirmer brackets;
    Map<String, Boolean> inputList;

    @Before
    public void setUp() {
        brackets = new BracketConfirmer();
        inputList = new HashMap<>();
    }

    @After
    public void tearDown() {
        for (Map.Entry<String, Boolean> i : inputList.entrySet()) {
            String input = i.getKey();
            boolean expected = i.getValue();

            boolean actual = brackets.confirm(input);

            Assert.assertEquals('"'+input+'"', expected, actual);
        }

        brackets = null;
        inputList = null;
    }

    @Test
    public void noEndingOnBrackets() {
        inputList.put("as{asd", false);
        inputList.put("a[]as(d", false);
        inputList.put("a[a()sd", false);
        inputList.put("a[sa(sd", false);
    }

    @Test
    public void mismatchedBrackets() {
        inputList.put("{[(])}", false);
        inputList.put("[[a])", false);
        inputList.put("[]3]", false);
        inputList.put("(((sdasdadssd))", false);
    }

    @Test
    public void workingCases() {
        inputList.put("asdas(asdcsc)wd", true);
        inputList.put("hello_world()", true);
        inputList.put("array[12]", true);
        inputList.put("public boolean[] r(int[] bob){ ... }", true);
    }
}
