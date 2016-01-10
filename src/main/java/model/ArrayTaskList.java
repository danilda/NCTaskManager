package model;

import java.util.*;

/**
 * Storage class of tasks.
 * It's based on array.
 * @author Vinogradov Max
 */
public class ArrayTaskList extends TaskList {
    private Task[] list = new Task[10];
    private int currentPos;
    private int listLength = 10;

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Tried to add null!");
        } else if (currentPos < listLength) {
            list[currentPos] = task;
            currentPos++;
        } else {
            Task[] tempList = new Task[listLength + 10];
            for (int i = 0; i < listLength; i++) {
                tempList[i] = list[i];
            }
            list = tempList;
            listLength += 10;
            list[currentPos] = task;
            currentPos++;
        }
    }

    @Override
    public boolean remove(Task task) {
        for (int i = 0; i < currentPos; i++) {
            if (list[i].equals(task)) {
                System.arraycopy(list, i + 1, list, i, listLength - 1 - i);
                currentPos--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return currentPos;
    }

    @Override
    public Task getTask(int index) {
        return list[index];
    }

    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            private int cursor;       // index of next element to return
            private int lastRet = -1; // index of last element returned; -1 if no such

            public boolean hasNext() {
                return cursor < size(); 
            }

            public Task next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                } else {
                    lastRet = cursor;
                    return getTask(cursor++);
                }
            }

            public void remove() {
                if (lastRet < 0)
                    throw new IllegalStateException();
                try {
                    ArrayTaskList.this.remove(getTask(lastRet));
                    cursor = lastRet;
                    lastRet = -1;
                } catch (IndexOutOfBoundsException ex) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (this.getClass() == obj.getClass()) {
            TaskList tmp = (ArrayTaskList)obj;
            if (this.size() != tmp.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i++) {
                if (!this.list[i].equals(tmp.getTask(i))){
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 100;
        if (this.size() == 0) {
            return result;
        } else {
            for (int i = 0; i < this.size(); i++) {
                result += this.list[i].hashCode();
            }
            return result;
        }
    }

    /**
     * Return a string representation of this TaskList
     * @return  a string representation of this TaskList.
     */
    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "list=" + Arrays.toString(list) +
                ", currentPos=" + currentPos +
                ", listLength=" + listLength +
                '}';
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList clone = (ArrayTaskList) super.clone();
        clone.currentPos = 0;
        clone.list = new Task[10];
        clone.listLength = 10;
        for (int i = 0; i < this.size(); i++) {
            /*clone.list[i] = (Task) this.list[i].clone();*/
            clone.add(this.getTask(i));
        }
        return clone;
    }
}