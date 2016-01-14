package program;

import control.TaskManagerController;
import model.TaskManagerModel;
import org.apache.log4j.Logger;
import view.TaskManagerView;

import java.io.IOException;

/**
 * Main application class, that contains main-function.
 * @author Vinogradov Max
 */
public class TaskManager {
    private static Logger log = Logger.getLogger(TaskManager.class.getName());

    /**
     * Launching the application.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        log.info("Task Manager start work.");
        TaskManagerView taskManagerView = new TaskManagerView();
        TaskManagerModel taskManagerModel = new TaskManagerModel();
        TaskManagerController taskManagerController = new TaskManagerController(
                        taskManagerModel,
                        taskManagerView
        );
    }

}
