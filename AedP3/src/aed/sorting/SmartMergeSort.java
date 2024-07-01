package aed.sorting;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SmartMergeSort extends Sort {

    private static final int MAX_INTERVAL = 64;

    //creates a random generator with a specific seed
    //this is useful for testing methods that are supposed to generate random elements
    //because we can always repeat the same tests by using the same seed
    private static final Random pseudoRandom = new Random(3729);



    //sort an array of elements (using InsertionSort) from low to high (including)
    //assuming that the first n elements are already sorted between themselves
    //this variation implies that we can start with a bigger hand immediately
    //this method is very useful for the SmartMergeSort in order to extend a natural
    //ascending run with additional elements to make a run with minimum size
    public static <T extends Comparable<T>> void insertionSortWithInitialSortedHand(T[] a, int low, int n, int high)
    {
        assert(low <= high);
        assert(n > 0);
        assert(low + n <= high);
        //TODO: implement
        for(int i = n+low; i <= high; i++){
            for(int j = i; j > low; j--){
                    if(less(a[j], a[j-1]))
                        exchange(a,j,j-1);
                    else
                        break ;
                }
            }

    }

    public static <T extends Comparable<T>> Run getNaturalRun(T[] a, int low, int high)
    {
        assert(low <= high);
        int runSize = 1;
        //TODO: implement
        for(int i = low; i < high; i++){
            if(less(a[i], a[i+1]) || a[i].compareTo(a[i+1]) == 0)
                runSize++;
            else
                break;
        }
        Run run = new Run(low,runSize);
        return run;

    }

    public static <T extends Comparable<T>> Run getNaturalOrMakeAscendingRun(T[] a, int low, int high)
    {
        assert(low <= high);
        //TODO: implement
        int runSize = 0;
        int runSizeDescent = 1;
        int sortUntil = low;
        if(a.length == 1) {
            runSize = 1;
        }
        else{
            runSize = 1;
            for(int i = low; i < high; i++){
                if(less(a[i], a[i+1]) || a[i].compareTo(a[i+1]) == 0)
                    runSize++;
                else if(!less(a[i], a[i+1]) && runSize == 1){
                    runSizeDescent++;
                    sortUntil++;
                }
                else
                    break;
            }
        }
        if(runSizeDescent != 1){
            runSize = runSizeDescent;
            MergeInsertionSort.insertionSort(a,low,sortUntil);
        }

        return new Run(low,runSize);
    }

    public static <T extends Comparable<T>> Run getNextRunWithMinimumSize(T[] a, int low, int high, int minRunSize)
    {
        assert(low < high);
        assert(minRunSize > 0);
        //TODO: implement
        int size = getNaturalRun(a,low,high).length;
        int sortUntil = low + minRunSize - 1;
        if(size >= minRunSize)
            return getNaturalRun(a,low,high);
        else{
            if(sortUntil > high)
                insertionSortWithInitialSortedHand(a,low,size,high);
            else
                insertionSortWithInitialSortedHand(a,low,size,sortUntil);
            return getNaturalRun(a,low,high);
        }
    }

    public static <T extends Comparable<T>> void merge(T[] a, T[] aux, Run leftRun, Run rightRun)
    {
        ///TODO: implement
        assert(rightRun.start == leftRun.start + leftRun.length);
        int low = leftRun.start;
        int mid = leftRun.start+ leftRun.length-1;
        int high = rightRun.start + rightRun.length-1;
        int left = low;
        int right = mid+1;

        if (high + 1 - low >= 0) System.arraycopy(a, low, aux, low, high + 1 - low);

        for(int i = low; i <= high; i++){
            if(left > mid)
                a[i] = aux[right++];
            else if(right > high)
                a[i] = aux[left++];
            else if(less(aux[right], aux[left]))
                a[i] = aux[right++];
            else
                a[i] = aux[left++];
        }

    }

    public static <T extends Comparable<T>> void mergeCollapse(MergeStack stack, T[] a, T[] aux)
    {
        //TODO: implement

    }
    

    public static <T extends Comparable<T>> void sort(T[] a)
    {
        //TODO: implement
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Comparable[a.length];
    }

    private static void printArray(Object[] a, int low, int high)
    {
        if(a == null || a.length == 0)
        {
            System.out.println("Array: []");
            return;
        }

        if(low > 0)
        {
            System.out.print("Array: [...");
        }
        else
        {
            System.out.print("Array: [");
        }

        for(int i = low; i <= high; i++)
        {
            System.out.print(a[i]+",");
        }
        if(high < a.length-1)
        {
            System.out.print(a[a.length-1]+"...]");
        }
        else
        {
            System.out.print(a[a.length-1]+"]");
        }

        System.out.println();
    }

    public static Integer[] generateLargeNaturalRunsExample(Random r, int n)
    {
        //todo: implement
        return null;
    }


    public static void main(String[] args)
    {
        //TODO: implement tests
    }
}
