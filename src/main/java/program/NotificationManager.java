package program;

import model.Task;
import model.TaskManagerModel;
import org.apache.log4j.Logger;
import view.NotificationView;
import view.TaskManagerView;

import java.util.Date;
import java.util.Set;
import java.util.SortedMap;

/**
 * Class that provides notifications of task, that perform in current moment.
 * @author Vinogradov Max
 */
public class NotificationManager implements Runnable{
    private static Logger log = Logger.getLogger(TaskManager.class.getName());
    TaskManagerModel model;
    TaskManagerView view;

    /**
     * Launching the notification part of manager.
     * @param model
     * @param view
     */
    public NotificationManager(TaskManagerModel model, TaskManagerView view) {
        this.model = model;
        this.view = view;
    }


    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run() {
        long sleepTime = 10000;
        try {
            NotificationView notification = new NotificationView(view);
            while (! Thread.currentThread().isInterrupted()) {
                Thread.sleep(sleepTime);
                Date currentDate = new Date();
                Date millisBefore = new Date(currentDate.getTime() - sleepTime);
                model.setCurrentCalendarMap();
                SortedMap<Date, Set<Task>> subMap =  model.getCurrentCalendarMap().subMap(millisBefore, currentDate);
                if (subMap.size() != 0) {
                    notification.notifyUser(subMap);
                }
            }
        } catch (InterruptedException e) {
            log.warn("Catch InterruptedException: \n" + e);
            Thread.currentThread().interrupt();
        }
    }
}
