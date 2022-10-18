import com.zivoy.module3.ListTransverser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Question4Tests {
    ListTransverser listTransverser;

    @Before
    public void setUp() {
        listTransverser = new ListTransverser();
    }

    @After
    public void tearDown() {
        listTransverser = null;
    }

    @Test
    public void exampleFromDoc() {
        int[] input = new int[]{39, 27, 11, 4, 24, 32, 32, 1};
        int[] expected = new int[]{-1, -1, -1, -1, 4, 24, 24, -1};

        int[] actual = listTransverser.regurgitate(input);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testCase1() {
        int[] input = new int[]{1, 4, 5, 2};
        int[] expected = new int[]{-1, 1, 4, 1};

        int[] actual = listTransverser.regurgitate(input);

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testCase2() {
        int[] input = new int[]{1, 3, 8, 9, 5, 7, 6, 0, 5};
        int[] expected = new int[]{-1, 1, 3, 8, 3, 5, 5, -1, 0};

        int[] actual = listTransverser.regurgitate(input);

        Assert.assertArrayEquals(expected, actual);
    }
}
