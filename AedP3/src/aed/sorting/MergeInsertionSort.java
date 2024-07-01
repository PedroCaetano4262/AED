package aed.sorting;

import aed.utils.TemporalAnalysisUtils;
import java.util.Random;
import java.util.stream.IntStream;

public class MergeInsertionSort extends Sort {

    private static final int MAX_INTERVAL = 64;

    //creates a random generator with a specific seed
    //this is useful for testing methods that are supposed to generate random elements
    //because we can always repeat the same tests by using the same seed
    private static final Random pseudoRandom = new Random(4582);

    //sort an array of elements (using MergeSort Bottom Up)
    //this method uses extra memory of O(n) to perform the sort
    public static <T extends Comparable<T>> void traditionalBottomUpSort(T[] a)
    {
        //TODO: implement

        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Comparable[a.length];
        int n = aux.length;
        for(int i = 1; i < n; i *= 2){
            for(int j = 0; j < n - i; j += 2*i){
                merge(a,aux,j,j+i-1,Math.min(j+2*i-1,n-1));
            }
        }
    }

    //sort an array of elements (using InsertionSort) from low to high (including)
    //In a conventional implementation, this method should be private.
    //However, for testing purposes in Mooshak, we need to make it public
    public static <T extends Comparable<T>> void insertionSort(T[] a, int low, int high)
    {
        //TODO: implement
        for(int i = low; i <= high; i++){
            for(int j = i; j > low; j--){
                if(less(a[j], a[j-1])){
                    exchange(a,j,j-1);
                }
                else
                    break;
            }
        }
    }

    //In a conventional implementation, this method should be private.
    //However, for testing purposes in Mooshak, we need to make it public
    public static int determineRunSize(int n)
    {
        //TODO: implement
        if(checkPower(n))
            return 32;
        int k = n;
        int rest = 0;
        while(k > 64) {
            if(k%2 != 0)
                rest++;
            k = k/2;
        }
        if(rest != 0)
            return k + 1;
        else
            return k;
    }

    public static boolean checkPower(int n){
        while(n%2 == 0)
            n = n/2;
        return n == 1;
    }



    protected static <T extends Comparable<T>> void merge(T[] a, T[] aux, int low, int mid, int high)
    {
        //TODO: implement
        int left = low;
        int right = mid+1;

        if (high + 1 - low >= 0) System.arraycopy(a, low, aux, low, high + 1 - low);

        for(int i = low; i <= high; i++){
            if(left > mid)
                a[i] = aux[right++];
            else if (right > high)
                a[i] = aux[left++];
            else if(less(aux[right], aux[left]))
                a[i] = aux[right++];
            else
                a[i] = aux[left++];
        }
    }

    public static <T extends Comparable<T>> void sort(T[] a)
    {

        ///TODO: implement

        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Comparable[a.length];
        int n = a.length;
        int result = 0;
        int limitLow = 0;
        if( n < 64)
            insertionSort(a,0,n-1);
        else{
            result = determineRunSize(n);
            int limitHigh = result;
            while(limitHigh<n){
                insertionSort(a,limitLow,limitHigh-1);
                limitLow += result;
                limitHigh+= result;
            }
            insertionSort(a,limitLow,n-1);
            for(int i = result; i < n; i *= 2){
                for(int j = 0; j < n - i; j += 2*i){
                    merge(a,aux,j,j+i-1,Math.min(j+2*i-1,n-1));
                }
            }
        }
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

    public static Integer[] generateRandomExample(int n)
    {
        return generateRandomExample(pseudoRandom,n);
    }

    public static Integer[] generateRandomExample(Random randomGenerator, int n)
    {
        ///TODO: implement
        Integer[] arr = new Integer[n];
        for(int i = 0; i < n; i++)
            arr[i] = randomGenerator.nextInt(n);
        return arr;
    }

    public static Integer[] generateMostlySortedExample(int n)
    {
        return generateMostlySortedExample(pseudoRandom,n);
    }

    public static Integer[] generateMostlySortedExample(Random r, int n)
    {
        //TODO: implement
        Integer[] arr = new Integer[n];
        int tenPercent = (int) (n * 0.1);
        int ninetyPercent = n - tenPercent;
        int check = (int) (n - n*0.1);
        for(int i = 0; i < ninetyPercent; i++){
            int j = i;
            arr[i] = r.nextInt(n);
            if(i != 0 && arr[i] < arr[i-1]){
                while(j != 0 && arr[j] < arr[j-1]){
                    Integer temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                    j--;
                }
            }
        }
        int[] array = r.ints(0,n).distinct().limit((long) (n*0.1)).sorted().toArray();
        Integer[] a = IntStream.of(array).boxed().toArray(Integer[]::new);
        int j = array.length - 1;
        for (int i = ninetyPercent; i < n; i++) {
            arr[i] = a[j];
            j--;
        }
        return arr;
    }

    public static Integer[] generateAlmostSortedExample(Random r, int n)
    {
        //TODO: implement
        Integer[] arr = new Integer[n];
        int onePercent = (int) (n * 0.01);
        int ninetyNinePercent = n - onePercent;
        for(int i = 0; i < ninetyNinePercent; i++){
            int j = i;
            arr[i] = r.nextInt(n);
            if(i != 0 && arr[i] < arr[i-1]){
                while(j != 0 && arr[j] < arr[j-1]){
                    Integer temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                    j--;
                }
            }
        }
        for(int i = ninetyNinePercent; i < n; i++)
            arr[i] = r.nextInt(n);
        return arr;
    }

    public static Integer[] generateAlmostSortedExample(int n)
    {
        return generateAlmostSortedExample(pseudoRandom,n);
    }

    public static Integer[] generateAscendingExample(Random r, int n)
    {
        //TODO: implement
        Integer[] arr = new Integer[n];
        for(int i = 0; i < n; i++){
            int j = i;
            arr[i] = r.nextInt(n);
            if(i != 0 && arr[i] < arr[i-1]){
                while(j != 0 && arr[j] < arr[j-1]){
                    Integer temp = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] = temp;
                    j--;
                }
            }
        }
        return arr;
    }

    public static Integer[] generateAscendingExample(int n)
    {
        return generateAscendingExample(pseudoRandom,n);
    }

    public static Integer[] generateDescendingExample(Random r, int n)
    {
        //TODO: implement
        int[] arr = r.ints(0,n).distinct().limit(n).sorted().toArray();
        Integer[] a = IntStream.of(arr).boxed().toArray(Integer[]::new);
        Integer[] finalArray = new Integer[n];
        int j = n-1;
        for (int i = 0; i < n; i++) {
            finalArray[i] = a[j];
            j--;
        }

        return finalArray;
    }

    public static Integer[] generateDescendingExample(int n)
    {
       return generateDescendingExample(pseudoRandom,n);
    }

    public static void main(String[] args)
    {
        //TODO: implement tests
        TemporalAnalysisUtils.runDoublingRatioTest("tests.tsv","Merge/Random",MergeInsertionSort::generateRandomExample,MergeInsertionSort::sort,100,15,30);
        TemporalAnalysisUtils.runDoublingRatioTest("tests.tsv","Merge/Mostly",MergeInsertionSort::generateMostlySortedExample,MergeInsertionSort::sort,100,15,30);
        TemporalAnalysisUtils.runDoublingRatioTest("tests.tsv","Merge/Almost",MergeInsertionSort::generateAlmostSortedExample,MergeInsertionSort::sort,100,15,30);
    }
}
