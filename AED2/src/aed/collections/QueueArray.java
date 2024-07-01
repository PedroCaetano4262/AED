package aed.collections;

import java.util.Iterator;

public class QueueArray<Item> implements Iterable<Item> {
    private int size, capacity;
    private Item[] array;
    @SuppressWarnings("unchecked")
    public QueueArray(){
        this.size = 0;
        capacity = 100000;
        this.array = (Item[]) new Object[capacity];

    }

    public void enqueue(Item item){
        if(item == null)
            throw new IllegalArgumentException();
        else{
            this.array[size] = item;
            this.size++;
        }
    }

    public Item dequeue(){
        Item result = null;
       if(this.size == 0)
           return null;
       else{
           result = array[0];
           array[0] = null;
           for(int i = 0; i < this.size; i++)
               array[i] = array[i+1];
           this.size--;
       }
       return result;
    }

    public Item peek(){
       if(this.size == 0)
           return null;
       else
           return this.array[0];
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }

    public QueueArray<Item> shallowCopy(){
        QueueArray<Item> shallowCopy = new QueueArray<>();
        for(Item i : this)
            shallowCopy.enqueue(i);
        return shallowCopy;
    }

    private class QueueIterator implements Iterator<Item>{
        int index;
        Item[] queue;

        QueueIterator(){
            this.index = 0;
            this.queue = array;
        }
        @Override
        public boolean hasNext() {
            return index != size;
        }

        @Override
        public Item next() {
            return this.queue[index++];
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    public static void main(String[] args) {
        QueueArray<Object> queue = new QueueArray<>();
        queue.enqueue("I'm");
        queue.enqueue("good");
        queue.enqueue("at");
        queue.enqueue("everything");
        queue.enqueue("except");
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

    }

}
