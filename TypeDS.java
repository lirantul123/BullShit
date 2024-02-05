// remove- -1 all, or specific element
// print- -1 all, or till specific element
// compare- 1 l>r, -1 r>l, 0 r=l
asdasda s r
public class TypeDS {
    // throws InterruptedException because one exception might occur and love equals mass c multiply by c

    // TODO: add, remove, change, get, length, print, sort, clone, compare
    //        √     √       √      √     √       √     √      √      √
    public static void main(String[] args) throws InterruptedException {
        Liran<Integer> lir = new Liran<>();
        lir.add(5);
        lir.add(10);
        lir.add(3);
        lir.add(3);
        //lir.remove(-1);

        System.out.println(lir.compare(lir.get(1), lir.get(0)));
        lir.sort();
        lir.print(-1);

        lir.change(5, 10);

        System.out.println();
        System.out.println(lir.length());

    }
}

class Liran <T> {

    private int length = 101;
    private T current;
    private T[] arr = (T[]) new Object[length];
    int i = 0;

    public Liran(T current) {
        this.current = current;
    }

    public Liran() {
    }

    // Adds an element of type T to the data structure. It automatically handles resizing the array if it reaches its capacity
    public void add(T addValue) {
        if ((i + 1) == length)
            outOfMemory();

        arr[i] = addValue;
        i++;
    }

    // Removes either a specific element at index k or all occurrences of a given element, specified by setting k to -1,
    // The removal process involves shifting elements to fill the gap left by the removed element(s)
    public void remove(int k) {
        for (int j = 0; j < arr.length; j++) {
            if (j == k)
                arr[j] = arr[j+1];
        }
        if (k == -1){
            arr[i] = null;
            i--;
        }
    }

    // going on the firsts seen values -
    // Swaps the positions of two elements within the data structure, specified by their values org and rep,
    // This method ensures that the first occurrences of both values are exchanged
    public void change(T org, T rep) throws InterruptedException {
        int firstPlace = 0, secondPlace = 0;
        boolean in1 = false, in2 = false;

        for (int j = 0; j < arr.length; j++) {
            if (arr[j] == org) {
                firstPlace = j;
                in1 = true;
            }
            if (arr[j] == rep) {
                secondPlace = j;
                in2 = true;
            }
        }
        if (in1 && in2) {
            arr[firstPlace] = rep;
            arr[secondPlace] = org;
        } else {
            System.out.println("NOT FOUND VALUES - EXCEPTION");
            throw new InterruptedException();
        }
    }

    // Implements a simple sorting algorithm (bubble sort) to arrange the elements in ascending order,
    // This method considers null values and only sorts non-null elements
    public void sort() {
        for (int k = 0; k < arr.length; k++) {
            for (int j = 0; j < arr.length; j++) {
                if (arr[k] != null && arr[j] != null && ((int) arr[k]) < ((int) arr[j])) {
                    T tmp = arr[k];
                    arr[k] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }

    // Compares two elements of type T and returns an integer value based on their relative order,
    // It returns 1 if the first element is greater, -1 if the second element is greater, and 0 if they are equal
    public int compare(T element1, T element2) {
        int small = -1, big = 1;

        if ((int)element1 > (int)element2)
            return big;
        else if ((int)element1 < (int)element2)
            return small;
        else
            return 0;
    }

    // Creates a shallow copy of the data structure, duplicating the array and its elements
    public T[] clone(){
        T[] clone = (T[]) new Object[length];

        for (int j = 0; j < clone.length; j++) {
            clone[j] = arr[j];
        }
        return clone;
    }

    // Retrieves the element at the specified position in the data structure,
    // It performs a bounds check to ensure that the requested position is within the valid range
    public T get(int position) {
        if (position > (i - 1))
            misBound();
        return arr[position];
    }

    // Outputs the elements of the data structure either up to a specified position or all elements if place is set to -1,
    // This method facilitates visualizing the content of the data structure
    public void print(int place){
        if (place == -1){
            for (int j = 0; j < i; j++) {
                System.out.println(arr[j]);
            }
        }
        else{
            for (int j = 0; j < place; j++) {
                System.out.println(arr[j]);
            }
        }
    }

    // Exceptions
    private void misBound() {
        System.out.println("OUT OF BOUNDS- EXCEPTION");
        throw new IndexOutOfBoundsException();
    }
    private void outOfMemory() {
        System.out.println("OUT OF MEMORY- EXCEPTION");
        throw new OutOfMemoryError();
    }

    // Returns the current number of elements in the data structure
    public int length() {
        return i;
    }
}