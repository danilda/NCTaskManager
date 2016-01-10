package model;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;

/**
 * The abstract class stores the list of tasks
 * @author Vinogradov Max
 */
public abstract class TaskList implements Iterable<Task>, Cloneable, Serializable {

    /**
     * Add task to task list
     * @param task task to adding
     */
    public abstract void add(Task task);

    /**
     * Remove task to task list
     * @param task task to removing
     * @return has task removed
     */
    public abstract boolean remove(Task task);

    /**
     * Getting size of list
     * @return count of tasks
     */
    public abstract int size();

    /**
     * Getting task by index
     * @param index
     * @return instance of Task
     */
    public abstract Task getTask(int index);

    /**
     * Returns iterator of collection
     * @return iterator
     */
    public abstract Iterator<Task> iterator();

    /**
     * Scan an object obj for equivalence with instance of this class
     * @param obj
     * @return equivalence of objects
     */
    public abstract boolean equals(Object obj);

    /**
     * Calculate hash-code of task object
     * @return hash-code
     */
    public abstract int hashCode();

    /**
     * Create TaskList that will be performed between from and to
     * @param from
     * @param to
     * @return
     */
    public TaskList incoming(Date from, Date to) {
        TaskList incomingTask;
        if (this instanceof LinkedTaskList) {
            incomingTask = new LinkedTaskList();
        } else {
            incomingTask = new ArrayTaskList();
        }
        for (int i = 0; i < size(); i++) {
            if (getTask(i).nextTimeAfter(from) != null && getTask(i).nextTimeAfter(from).before(to)) {
                incomingTask.add(getTask(i));
            }
        }
        return incomingTask;
    }
}