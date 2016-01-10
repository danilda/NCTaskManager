package view;

import model.ArrayTaskList;
import model.Task;
import model.TaskManagerModel;
import model.exception.InvalidIntervalException;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * Class, that encapsulates view part of Task Manager.
 * @author Vinogradov Max
 */
public class TaskManagerView extends TaskManagerFrame {
    private static Logger log = Logger.getLogger(TaskManagerView.class.getName());

    /**
     * Initialize view part in MVC.
     */
    public TaskManagerView() {
        super();
        log.debug("View part created.");
    }

    /**
     * Make incoming list.
     * @param model model part of MVC.
     */
    public void getIncoming(TaskManagerModel model) {
        Date from = (Date) tabsPanel.getFromInput().getValue();
        Date to = (Date) tabsPanel.getToInput().getValue();
        if (to.before(from)) {
            getWarningAlert("Illegal dates: to is lover than from!!!", "Date error");
        }
        model.setIncomingList((ArrayTaskList) model.getTaskList().incoming(from, to));
        tabsPanel.getIncomingTasks().setListData(model.convertListToArray(model.getIncomingList()));
        log.debug("Incoming from: " + from.toString() +
                ", to: " + to.toString() + " got");
    }

    /**
     * Create new task.
     * @return new task.
     */
    public Task createTask() {
        String title = inputPanel.getTaskText().getText();
        if (title.length() == 0) {
            getWarningAlert("Enter title of task!!!", "Title error");
            return null;
        }
        boolean activity = false;
        if (inputPanel.getActive().isSelected()) {
            activity = true;
        }
        boolean repeated = false;
        Date time = null, start = null, end = null;
        int interval = 0;
        if (inputPanel.getRepeat().isSelected()) {
            repeated = true;
            start = (Date) inputPanel.getStartSpinner().getValue();
            end = (Date) inputPanel.getEndSpinner().getValue();
            if (start.after(end)) {
                Date temp = start;
                start = end;
                end = temp;
                inputPanel.getStartSpinner().setValue(start);
                inputPanel.getEndSpinner().setValue(end);
                getWarningAlert("Start of task was installed after its finish time. " +
                                "We changed start and end positions.",
                                "Time error");
                return null;
            }
            interval += (Integer) inputPanel.getSecondSpinner().getValue();
            interval += 60 * (Integer) inputPanel.getMinuteSpinner().getValue();
            interval += 60 * 60 * (Integer) inputPanel.getHourSpinner().getValue();
            interval += 24 * 60 * 60 * (Integer) inputPanel.getDaySpinner().getValue();
        } else {
            time = (Date) inputPanel.getTimeSpinner().getValue();
        }
        Task task = null;
        if (repeated) {
            try {
                task = new Task(title, start, end, interval);
            } catch (InvalidIntervalException e1) {
                getWarningAlert("Set the correct interval!!!", "Interval error");
                return null;
            }
        } else {
            task = new Task(title, time);
        }
        task.setActive(activity);
        return task;
    }

    /**
     * Create aditional lists: today-list, week-list, month-list, tyear-list.
     * @param model model part of MVC.
     */
    public void createAditionalLists(TaskManagerModel model) {
        Date dt = new Date();
        Date from = new Date(dt.getYear(), dt.getMonth(), dt.getDate(), 0, 0);
        Date to = new Date(dt.getYear(), dt.getMonth(), dt.getDate() + 1, 0, 0);
        model.setTodayList((ArrayTaskList) model.getTaskList().incoming(from, to));
        tabsPanel.getTodayTasks().setListData(model.convertListToArray(model.getTodayList()));
        from = new Date(dt.getYear(), dt.getMonth(), dt.getDate() - dt.getDay(), 0, 0);
        to = new Date(dt.getYear(), dt.getMonth(), dt.getDate() - dt.getDay() + 7, 0, 0);
        model.setWeekList((ArrayTaskList) model.getTaskList().incoming(from, to));
        tabsPanel.getWeekTasks().setListData(model.convertListToArray(model.getWeekList()));
        from = new Date(dt.getYear(), dt.getMonth(), 0, 0, 0);
        to = new Date(dt.getYear(), dt.getMonth() + 1, 0, 0, 0);
        model.setMonthList((ArrayTaskList) model.getTaskList().incoming(from, to));
        tabsPanel.getMonthTasks().setListData(model.convertListToArray(model.getMonthList()));
        from = new Date(dt.getYear(), 0, 0, 0, 0);
        to = new Date(dt.getYear() + 1, 0, 0, 0, 0);
        model.setYearList((ArrayTaskList) model.getTaskList().incoming(from, to));
        tabsPanel.getYearTasks().setListData(model.convertListToArray(model.getYearList()));
        log.debug("Additional lists updated.");
    }

    /**
     * Save changing in task after clicking save-button.
     * @param task changed task.
     * @return true if manager has changed task, otherwise - return false
     */
    public boolean changeSelectedItem(Task task) {
        task.setTitle(infoPanel.getTaskText().getText());
        task.setActive(infoPanel.getActivityCB().isSelected());
        Date start, end;
        int interval = 0;
        boolean isRepeated = infoPanel.getRepeatCB().isSelected();
        if (isRepeated) {
            interval += (Integer) infoPanel.getSecondSpinner().getValue();
            interval += 60 * (Integer) infoPanel.getMinuteSpinner().getValue();
            interval += 60 * 60 * (Integer) infoPanel.getHourSpinner().getValue();
            interval += 24 * 60 * 60 * (Integer) infoPanel.getDaySpinner().getValue();
            start = (Date)infoPanel.getStartSpinner().getValue();
            end = (Date)infoPanel.getEndSpinner().getValue();
            if (start.after(end)) {
                Date temp = start;
                start = end;
                end = temp;
                infoPanel.getStartSpinner().setValue(start);
                infoPanel.getEndSpinner().setValue(end);
                getWarningAlert("Start of task was installed after its finish time. " +
                                "We changed our start and end positions.",
                        "Time error");
                log.debug("Did not enter correct interval.");
                return false;
            }
            try {
                task.setTime(start, end, interval);
            } catch (InvalidIntervalException e1) {
                getWarningAlert("Set the correct interval !!!", "Interval error");
                log.warn("Did not enter correct interval in try block!");
                return false;
            }
        } else {
            task.setTime((Date)infoPanel.getTimeSpinner().getValue());
        }
        return true;
    }

    /**
     * This method change view, especially info-panel, when user choose item.
     * @param task selected task
     */
    public void selectingListItem(Task task) {
        setDefaultInfoData();
        infoPanel.getTaskText().setEnabled(true);
        infoPanel.getTaskText().setText(task.getTitle());
        infoPanel.getActivityCB().setEnabled(true);
        if (task.isActive()) {
            infoPanel.getActivityCB().setSelected(true);
        } else {
            infoPanel.getActivityCB().setSelected(false);
        }
        infoPanel.getRepeatCB().setEnabled(true);
        if (task.isRepeated()) {
            int temp = task.getRepeatInterval();
            infoPanel.getRepeatCB().setSelected(true);
            infoPanel.getTimeSpinner().setEnabled(false);
            infoPanel.getStartSpinner().setEnabled(true);
            infoPanel.getStartSpinner().setValue(task.getStartTime());
            infoPanel.getEndSpinner().setEnabled(true);
            infoPanel.getEndSpinner().setValue(task.getEndTime());
            infoPanel.getDaySpinner().setEnabled(true);
            infoPanel.getHourSpinner().setEnabled(true);
            infoPanel.getMinuteSpinner().setEnabled(true);
            infoPanel.getSecondSpinner().setEnabled(true);
            infoPanel.getSecondSpinner().setValue(temp % 60);
            temp /= 60;
            infoPanel.getMinuteSpinner().setValue(temp % 60);
            temp /= 60;
            infoPanel.getHourSpinner().setValue(temp % 24);
            temp /= 24;
            infoPanel.getDaySpinner().setValue(temp);
        } else {
            infoPanel.getRepeatCB().setSelected(false);
            infoPanel.getTimeSpinner().setEnabled(true);
            infoPanel.getStartSpinner().setEnabled(false);
            infoPanel.getEndSpinner().setEnabled(false);
            infoPanel.getTimeSpinner().setValue(task.getTime());
            infoPanel.getDaySpinner().setEnabled(false);
            infoPanel.getHourSpinner().setEnabled(false);
            infoPanel.getMinuteSpinner().setEnabled(false);
            infoPanel.getSecondSpinner().setEnabled(false);
        }
        infoPanel.getApplyBtn().setEnabled(true);
        infoPanel.getDeleteBtn().setEnabled(true);
        Date temp = task.nextTimeAfter(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!Objects.isNull(temp)) {
            infoPanel.getNextTimeLabel().setText(sdf.format(temp));
        } else {
            infoPanel.getNextTimeLabel().setText("-- / -- / -- --:--");
        }
    }

}
