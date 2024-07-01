package aed.collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

public class SmartList<Item> implements Iterable<Item> {
    private class Node {
        private Item item;
        private Node next;
        public Node(Item item){

            this.item = item;
        }
    }

    private Node first;
    private Node last;
    private int size;

    public SmartList() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void add(Item item) {
        Node old = this.first;
        if (item == null) {
            throw new IllegalArgumentException();
        }
        this.first = new Node(item);
        this.first.item = item;
        this.first.next = old;

        this.size++;
    }
    public void addLast(Item item) {
        Node old = this.last;
        if(item == null)
            throw new IllegalArgumentException();
        this.last = new Node(item);
        if (old == null)
            first = this.last;
        else
            old.next = this.last;
        this.size++;
    }


    float mtf_amount = 0;
    float mtf_comparisons = 0;

    public Item searchMTF(Item item) {
        mtf_amount++;
        if (this.size == 0)
            return null;
        Node current = this.first;
        Node currentNext = current.next;
        mtf_comparisons++;
        if (current.item.equals(item)) {
            return current.item;
        } else
            while (currentNext != null && !currentNext.item.equals(item)) {
                mtf_comparisons++;
                current = currentNext;
                currentNext = currentNext.next;
            }
        if (currentNext == null)
            return null;
        mtf_comparisons++;
        current.next = currentNext.next;
        currentNext.next = this.first;
        this.first = currentNext;
        return this.first.item;
    }

    float trans_amount = 0;
    float trans_comparisons = 0;

    public Item searchTrans(Item item) {
        trans_amount++;
        if (this.size == 0)
            return null;
        Node current = this.first;
        Node currentNext = current.next;
        trans_comparisons++;
        if (current.item.equals(item)) {
            return current.item;
        } else
            while (currentNext != null && !currentNext.item.equals(item)) {
                trans_comparisons++;
                current = currentNext;
                currentNext = currentNext.next;

            }
        if (currentNext == null)
            return null;
        trans_comparisons++;
        Item temp = current.item;
        current.item = currentNext.item;
        currentNext.item = temp;


        return current.item;
    }

    public Item search(Item item) {
        return searchMTF(item);
    }

    public Item search(Item item, Comparator<Item> c) {
        if (this.size == 0)
            return null;
        Node current = this.first;
        Node currentNext = current.next;
        if (c.compare(current.item, item) == 0)
            return current.item;
        else
            while (currentNext != null && !(c.compare(currentNext.item, item) == 0)) {
                current = currentNext;
                currentNext = currentNext.next;
            }
        if (currentNext == null)
            return null;
        current.next = currentNext.next;
        currentNext.next = this.first;
        this.first = currentNext;

        return this.first.item;
    }

    public Item remove(Item item) {
        Item result = null;
        Node old = this.first;
        if (this.size == 0 || old.next == null)
            return null;
        else if (old.item.equals(item)) {//Begin
            result = old.item;
            this.first = this.first.next;
        } else
            while (old.next != null && !old.next.item.equals(item)) {//Middle
                old = old.next;
            }
        if (old.next == null)
            return null;
        result = old.next.item;
        old.next = old.next.next;
        this.size--;

        return result;
    }

    public float getAvgMTFCompares() {
        if (mtf_amount == 0 || mtf_comparisons == 0)
            return 0.0F;
        return mtf_comparisons / mtf_amount;
    }

    public float getAvgTransCompares() {
        if (trans_amount == 0 || trans_comparisons == 0)
            return 0.0F;
        return trans_comparisons / trans_amount;
    }

    public void clear() {
        Node old = this.first;
        while (old != null && old.next != null) {
            old.item = null;
            old = old.next;
        }
        this.first = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public Object[] toArray() {
        Node old = this.first;
        Object[] result = new Object[size];
        for (int i = 0; i < this.size; i++) {
            result[i] = old.item;
            old = old.next;
        }
        return result;
    }

    public SmartList<Item> shallowCopy() {
        SmartList<Item> list = new SmartList<>();
        for(Item i : this)
            list.addLast(i);
        return list;
    }
    private class SmartListIterator implements Iterator<Item> {
        Node iterator = first;
        @Override
        public boolean hasNext() {

            return iterator != null;
        }

        @Override
        public Item next() {
            Item data = iterator.item;
            iterator = iterator.next;
            return data;
        }
    }
    @Override
    public Iterator<Item> iterator() {

        return new SmartListIterator();
    }

    //Speed Tests
    public static double calculateAverageExecutionTimeMTF(int n)
    {
        Random r = new Random();
        SmartList<Object> list = new SmartList<>();
        int trials = 10;
        double totalTime = 0;
        for(int i = 0; i < trials; i++)
        {
            int[] example = generateExample(n);
            for(int j = 0; j < example.length; j++) {
                list.add(example[j]);
            }
            long time = System.currentTimeMillis();
            for(int k = 0; k < example.length; k++)
                list.searchMTF(example[k]);
            totalTime += System.currentTimeMillis() - time;
        }
        return totalTime/trials;
    }

    public static double calculateAverageExecutionTimeTrans(int n)
    {
        SmartList<Object> list = new SmartList<>();
        int trials = 10;
        double totalTime = 0;
        for(int i = 0; i < trials; i++)
        {
            int[] example = generateExample(n);
            for(int j = 0; j < example.length; j++) {
                list.add(example[j]);
            }
            long time = System.currentTimeMillis();
            for(int k = 0; k < example.length; k++)
                list.searchTrans(example[k]);
            totalTime += System.currentTimeMillis() - time;
        }
        return totalTime/trials;
    }

    public static int[] generateExample(int n)
    {
        Random r = new Random();
        int [] examples = new int[n];
        for(int i = 0; i < n; i++)
        {
            examples[i] = r.nextInt();
        }
        return examples;
    }


    public static void main(String[] args) {
        int n = 125;
        double previousTime = calculateAverageExecutionTimeMTF(n);
        double previousTime2 = calculateAverageExecutionTimeTrans(n);
        double mtfTime;
        double transTime;
        double doublingRatio;
        double ratio;
        for(int i = 250; true; i*=2){
            mtfTime = calculateAverageExecutionTimeMTF(i);
            transTime = calculateAverageExecutionTimeTrans(i);
            if(previousTime2 > 0)
                ratio = transTime / previousTime2;
            else
                ratio = 0;
            if(previousTime > 0)
                doublingRatio = mtfTime / previousTime;

            else
                doublingRatio = 0;

            previousTime = mtfTime;
            previousTime2 = transTime;
            System.out.println("MTF  " + i + "\t" + mtfTime + "\t" + doublingRatio);
            System.out.println("Trans " + i + "\t" + transTime + "\t" + ratio);
        }
    }

}


