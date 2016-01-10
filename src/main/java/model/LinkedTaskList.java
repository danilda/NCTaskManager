package model;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Storage class of tasks.
 * It's based on linked list
 * @author Vinogradov Max
 */
public class LinkedTaskList extends TaskList{
    private Node firstItem = new Node();
    private Node lastItem = firstItem;
    private int length;
    private Node iterNode = firstItem;

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Tried to add null!");
        } else {
            Node tmp = lastItem;
            lastItem.data = task;
            lastItem.next = new Node();
            lastItem  = lastItem.next;
            lastItem.prev = tmp;
            length++;
        }
    }

    @Override
    public boolean remove(Task task) {
        Node listIterator = firstItem;
        for (int i = 0; i < length; i++) {
            if (listIterator.data.equals(task)) {
                Node prev = listIterator.prev;
                Node next = listIterator.next;
                if (Objects.nonNull(prev)) {
                    prev.next = next;
                } else {
                    firstItem = firstItem.next;
                    firstItem.prev = null;
                }
                if (Objects.nonNull(next)) {
                    next.prev = prev;
                } else {
                    lastItem = lastItem.prev;
                    lastItem.next = null;
                }
                length--;
                return true;
            }
            listIterator = listIterator.next;
        }
        return false;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public Task getTask(int index) {
        Node listIterator = firstItem;
        for (int i = 0; i < index; i++) {
            listIterator = listIterator.next;
        }
        return listIterator.data;
    }

    private class Node {
        public Task data;
        public Node prev;
        public Node next;
    }

    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int cursor;       // index of next element to return
            private int lastRet = -1; // index of last element returned; -1 if no such
            private Node iterNode = firstItem;

            public boolean hasNext() {
                return cursor < size();
            }

            public Task next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    lastRet = cursor;
                    iterNode = iterNode.next;
                    cursor++;
                    return iterNode.prev.data;
                }
            }

            public void remove() {
                if (lastRet < 0)
                    throw new IllegalStateException();
                try {
                    LinkedTaskList.this.remove(getTask(lastRet));
                    cursor = lastRet;
                    lastRet = -1;
                } catch (IndexOutOfBoundsException ex) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (this.getClass() == obj.getClass()) {
            TaskList tmp = (LinkedTaskList)obj;
            if (this.size() != tmp.size()) {
                return false;
            }
            Node thisList = this.firstItem;
            Node thatList = this.firstItem;
            for (int i = 0; i < size(); i++) {
                if (!thatList.data.equals(thisList.data)) {
                    return false;
                }
                thisList = thisList.next;
                thatList = thatList.next;
            }
        } else {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result = 200;
        if (this.size() == 0) {
            return result;
        } else {
            for(Task tmp : this) {
                result += tmp.hashCode();
            }
            return result;
        }
    }

    @Override
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList clone = (LinkedTaskList)super.clone();
        clone.lastItem = firstItem;
        clone.length = 0;
        clone.iterNode = firstItem;
        for (Task item: this) {
            clone.add((Task)item.clone());
        }
        return clone;
    }

    /**
     * Return a string representation of this TaskList
     * @return  a string representation of this TaskList.
     */
    @Override
    public String toString() {
        return getClass().getName()
                + "["
                + (iterNode.data == null ?
                    "null" : iterNode.data + ", ")
                + (iterNode.next == null ?
                    "null" : iterNode.next.toString())
                + "]";
    }
}