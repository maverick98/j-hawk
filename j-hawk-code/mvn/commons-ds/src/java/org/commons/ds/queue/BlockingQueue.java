package org.commons.ds.queue;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author manosahu
 */
public class BlockingQueue<T> {

    private List<T> queue = new LinkedList<>();
    private int limit = 3;

    public BlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void enqueue(T item)
            throws InterruptedException {
        while (this.isFull()) {
            System.out.println("" + Thread.currentThread() + " is going to wait as it is full!");

            wait();
        }

        System.out.println("" + Thread.currentThread() + " is going to notify as this is empty!");
        notifyAll();

        this.queue.add(item);
    }

    public synchronized boolean isFull() {
        return this.queue.size() == this.limit;
    }

    public synchronized boolean isEmpty() {
        return this.queue.isEmpty();
    }

    public synchronized T dequeue()
            throws InterruptedException {
        while (this.isEmpty()) {
            System.out.println("" + Thread.currentThread() + " is going to wait as it is empty!");
            wait();
        }

        System.out.println("" + Thread.currentThread() + " is going to wait as it is full!");
        notifyAll();

        return this.queue.remove(0);
    }

}
