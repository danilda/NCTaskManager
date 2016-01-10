package model;

import java.lang.Iterable;
import java.util.*;

/**
 * Class that provides and encapsulates service methods for working with task lists.
 * @author Vinogradov Max
 */
public class Tasks {

    /**
     * Create TaskList (more precisely - ArrayTaskList) with tasks that will be performed
     * in the interval from <code>start</code> to <code>end</code>
     * @param tasks task list where you search incoming tasks
     * @param start start time of task
     * @param end end time of task
     * @return incoming TaskList
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) {
        ArrayList<Task> result = new ArrayList<Task>();
        for (Task t : tasks) {
            Date nextTime = t.nextTimeAfter(start);
            if (nextTime != null && nextTime.compareTo(end) <= 0) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     *Creates calendar of tasks in the specified period from <code>start</code> to <code>end</code>.
     * @param tasks task list  calendar
     * @param start start time of task
     * @param end end time of task
     * @return calendar of tasks
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        TreeMap<Date, Set<Task>> calendar = new TreeMap<Date, Set<Task>>();
        Iterable<Task> inc = incoming(tasks, start, end);
        for (Task task : inc) {
            Date tmp = task.nextTimeAfter(start);
            while(tmp != null && tmp.compareTo(end) <= 0) {
                if (calendar.containsKey(tmp)) {
                    calendar.get(tmp).add(task);
                } else {
                    Set<Task> setOfTasks = new HashSet<Task>();
                    setOfTasks.add(task);
                    calendar.put(tmp, setOfTasks);
                }
                tmp = task.nextTimeAfter(tmp);
            }
        }
        return calendar;
    }

}