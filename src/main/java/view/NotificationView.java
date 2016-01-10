package view;

import model.Task;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.SortedMap;

/**
 * Class, that encapsulates view part of notification.
 * @author Vinogradov Max
 */
public class NotificationView {
    private static Logger log = Logger.getLogger(NotificationView.class.getName());
    public TaskManagerView view;
    private static final SimpleDateFormat sdf
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Initialize view part in MVC.
     * @param view TaskManager view.
     */
    public NotificationView(TaskManagerView view) {
        this.view = view;
    }

    /**
     * Generate text message that will be show to user in modal frame.
     * @param map of current tasks
     * @return sting of current tasks
     */
    public String buildStringMassage(SortedMap<Date, Set<Task>> map) {
        StringBuilder sb = new StringBuilder();
        for (Date date : map.keySet()) {
            Set<Task> tasks = map.get(date);
            sb.append(sdf.format(date));
            sb.append(" : ");
            int i = 1;
            for (Task task : tasks) {
                sb.append(task.getTitle());
                if(i == tasks.size()) {
                    sb.append(".");
                } else {
                    sb.append("; ");
                }
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Initialize notification.
     * @param map map of tasks that perform in current time.
     */
    public void notifyUser(SortedMap<Date, Set<Task>> map) {
        JOptionPane.showMessageDialog(view,
                buildStringMassage(map),
                "Task manager",
                JOptionPane.INFORMATION_MESSAGE);
        log.debug("User notified.");
    }
}

