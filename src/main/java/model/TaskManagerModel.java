package model;

import model.exception.InvalidIntervalException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class-model, that encapsulates all model needful function.
 * @author Vinogradov Max
 */
public class TaskManagerModel {
    private static Logger log = Logger.getLogger(TaskManagerModel.class.getName());
    private File dataFile = new File(System.getProperty("user.dir") + "\\data.txt");
    private ArrayTaskList taskList = new ArrayTaskList();
    private ArrayTaskList todayList = new ArrayTaskList();
    private ArrayTaskList weekList = new ArrayTaskList();
    private ArrayTaskList monthList = new ArrayTaskList();
    private ArrayTaskList yearList = new ArrayTaskList();
    private ArrayTaskList incomingList = new ArrayTaskList();
    private SortedMap<Date, Set<Task>> currentCalendarMap;

    /**
     * Initialize model part in MVC.
     * @throws IOException
     */
    public TaskManagerModel() throws IOException {
        if (!dataFile.exists()) {
            dataFile.createNewFile();
            log.debug("New data-file was created.");
        }
        try {
            TaskIO.readText(taskList, dataFile);
        } catch (InvalidIntervalException e) {
            log.log(Level.WARN, "Invalid interval. Exception: ", e);
        } finally {
            currentCalendarMap = new TreeMap<Date, Set<Task>>();
            setCurrentCalendarMap();
            log.debug("Model part created.");
            return;
        }
    }

    /**
     * Set map of current tasks.
     */
    public void setCurrentCalendarMap() {
        Date dt = new Date();
        this.currentCalendarMap = Tasks.calendar(taskList,
                new Date(dt.getYear(), dt.getMonth(), dt.getDate(), dt.getHours(), dt.getMinutes()),
                new Date(dt.getYear(), dt.getMonth(), dt.getDate(), dt.getHours(), dt.getMinutes() + 1));
    }

    /**
     * @return returns map of current tasks.
     */
    public SortedMap<Date, Set<Task>> getCurrentCalendarMap() {
        return currentCalendarMap;
    }

    /**
     * Convert ArrayTaskList to array of Strings.
     * @param list
     * @return array of Strings
     */
    public Object[] convertListToArray(ArrayTaskList list) {
        Object array[] = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.getTask(i).toString();
        }
        return array;
    }

    /**
     * Rewrite file with tasks.
     * @throws IOException
     */
    public void refreshModel() throws IOException {
        TaskIO.writeText(taskList, dataFile);
    }

    /**
     * @param index index of selected task.
     * @return return selected task.
     * @throws ArrayIndexOutOfBoundsException
     */
    public Task getSelectedTask(int index) throws ArrayIndexOutOfBoundsException {
        return getTaskList().getTask(index);
    }

    /**
     * @return list of incoming tasks.
     */
    public ArrayTaskList getIncomingList() {
        return incomingList;
    }

    /**
     * Set list of incoming tasks.
     * @param incomingList
     */
    public void setIncomingList(ArrayTaskList incomingList) {
        this.incomingList = incomingList;
    }

    /**
     * @return task list of current year tasks.
     */
    public ArrayTaskList getYearList() {
        return yearList;
    }

    /**
     * Set task list of current year tasks.
     * @param yearList
     */
    public void setYearList(ArrayTaskList yearList) {
        this.yearList = yearList;
    }

    /**
     * @return list of all tasks.
     */
    public ArrayTaskList getTaskList() {
        return taskList;
    }

    /**
     * Set list of all tasks.
     * @param taskList list of all tasks.
     */
    public void setTaskList(ArrayTaskList taskList) {
        this.taskList = taskList;
    }

    /**
     * @return list of current day tasks.
     */
    public ArrayTaskList getTodayList() {
        return todayList;
    }

    /**
     * Set list of current day tasks.
     * @param todayList list of current day tasks.
     */
    public void setTodayList(ArrayTaskList todayList) {
        this.todayList = todayList;
    }

    /**
     * @return list of current week tasks.
     */
    public ArrayTaskList getWeekList() {
        return weekList;
    }

    /**
     * Set list of current week tasks.
     * @param weekList list of current week tasks.
     */
    public void setWeekList(ArrayTaskList weekList) {
        this.weekList = weekList;
    }

    /**
     * @return list of current month tasks.
     */
    public ArrayTaskList getMonthList() {
        return monthList;
    }

    /**
     * Set list of current month.
     * @param monthList list of current month tasks.
     */
    public void setMonthList(ArrayTaskList monthList) {
        this.monthList = monthList;
    }

}
