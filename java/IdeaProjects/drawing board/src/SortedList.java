import java.util.AbstractList;
import java.util.ArrayList;
import java.util.LinkedList;


public class SortedList<N> extends AbstractList<N> {

    private ArrayList<N> internalList = new ArrayList<N>();

    public SortedList(Class<LinkedList> linkedListClass) {

    }

    // Note that add(E e) in AbstractList is calling this one
    @Override
    public void add(int position, N e) {
        internalList.add(e);
        internalList.sort(null);
    }

    @Override
    public N get(int i) {
        return internalList.get(i);
    }

    @Override
    public int size() {
        return internalList.size();
    }

}
