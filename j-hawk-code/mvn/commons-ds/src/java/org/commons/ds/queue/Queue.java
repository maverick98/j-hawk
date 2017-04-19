package org.commons.ds.queue;

import java.util.LinkedList;

/**
 *
 * @author manosahu
 */
public class Queue<T> {

    private LinkedList<T> list = new LinkedList<>();

    public void enqueue(T item) {
        list.addLast(item);
    }

    public T dequeue() {
        return list.poll();
    }

    public boolean hasItems() {
        return !list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void addItems(Queue<? extends T> q) {
        while (q.hasItems()) {
            list.addLast(q.dequeue());
        }
    }

}
