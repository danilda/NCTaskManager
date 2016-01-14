package view;

import model.Task;
import model.exception.InvalidIntervalException;
import org.apache.log4j.Level;
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
            interval += (Integer) inputPanel.getSecondSpinner().getValue();
            interval += 60 * (Integer) inputPanel.getMinuteSpinner().getValue();
            interval += 60 * 60 * (Integer) inputPanel.getHourSpinner().getValue();
            interval += 24 * 60 * 60 * (Integer) inputPanel.getDaySpinner().getValue();
            start = (Date) inputPanel.getStartSpinner().getValue();
            end = (Date) inputPanel.getEndSpinner().getValue();
            if (start.after(end)) {
                getWarningAlert("Start of task was installed after its finish time.",
                                "Time error");
                return null;
            }
        } else {
            time = (Date) inputPanel.getTimeSpinner().getValue();
        }
        Task task = null;
        if (repeated) {
            try {
                task = new Task(title, start, end, interval);
            } catch (InvalidIntervalException e1) {
                getWarningAlert("Set the correct interval!!!", "Interval error");
                log.log(Level.WARN, "Did not enter correct interval. Exception: ", e1);
                return null;
            }
        } else {
            task = new Task(title, time);
        }
        task.setActive(activity);
        return task;
    }

    /**
     * Save changing in task after clicking save-button in new task instance.
     * @return true if manager has changed task, otherwise - return false
     */
    public Task changeSelectedItemView() {
        Task task;
        String title = infoPanel.getTaskText().getText();
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
                getWarningAlert("Start of task was installed after its finish time.",
                        "Time error");
                log.debug("Did not enter correct interval.");
                return null;
            }
            try {
                task = new Task(title, start, end, interval);
            } catch (InvalidIntervalException e1) {
                getWarningAlert("Set the correct interval !!!", "Interval error");
                log.log(Level.WARN, "Did not enter correct interval in try block. Exception: ", e1);
                return null;
            }
        } else {
            task = new Task(title,(Date)infoPanel.getTimeSpinner().getValue());
        }
        task.setActive(infoPanel.getActivityCB().isSelected());
        return task;
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
            setDefaultInfoTimeSpinners(true);
            infoPanel.getStartSpinner().setValue(task.getStartTime());
            infoPanel.getEndSpinner().setValue(task.getEndTime());
            setDefaultInfoInterval(true);
            infoPanel.getSecondSpinner().setValue(temp % 60);
            temp /= 60;
            infoPanel.getMinuteSpinner().setValue(temp % 60);
            temp /= 60;
            infoPanel.getHourSpinner().setValue(temp % 24);
            temp /= 24;
            infoPanel.getDaySpinner().setValue(temp);
        } else {
            setDefaultInfoTimeSpinners(false);
            infoPanel.getTimeSpinner().setValue(task.getTime());
            setDefaultInfoInterval(false);
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
