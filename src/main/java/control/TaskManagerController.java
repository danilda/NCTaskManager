package control;

import model.Task;
import model.TaskManagerModel;
import org.apache.log4j.Logger;
import program.NotificationManager;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * Class-controller, that encapsulates all events-listeners.
 * @author Vinogradov Max
 */
public class TaskManagerController {
    private static Logger log = Logger.getLogger(TaskManagerController.class.getName());
    TaskManagerModel model;
    TaskManagerView view;
    MenuController menuController;
    InfoPanel infoPanel;
    InputPanel inputPanel;
    TabsPanel tabsPanel;
    TaskMenu menuPanel;

    /**
     * Initialize controller part in MVC.
     * @param model TaskManager model.
     * @param view TaskManager view.
     */
    public TaskManagerController(TaskManagerModel model, TaskManagerView view) {
        this.model = model;
        this.view = view;
        infoPanel = view.getInfoPanel();
        inputPanel = view.getInputPanel();
        tabsPanel = view.getTabsPanel();
        menuPanel = view.getTaskMenu();
        linkMV();
        view.createAditionalLists(model);
        createTaskEvent();
        deleteTaskEvent();
        saveChangeEvent();
        showIncomingTaskEvent();
        chooseListItemIvent();
        createNotification();
        menuController = new MenuController(view, model, this);
        log.debug("Controller created.");
    }

    /**
     * Initial linking model and view.
     */
    private void linkMV() {
        tabsPanel.getAllTasks().setListData(model.convertListToArray(model.getTaskList()));
    }

    /**
     * Refresh view-part and model-part after changes in view.
     * @throws IOException
     */
    protected void refreshMV() throws IOException {
        model.refreshModel();
        linkMV();
        view.createAditionalLists(model);
        view.getIncoming(model);
        view.setDefaultInfo();
        System.gc();
        log.debug("Model and View update.");
    }

    private void createNotification() {
        NotificationManager notificationManager = new NotificationManager(model, view);
        Thread notificationThread = new Thread(notificationManager);
        notificationThread.setDaemon(true);
        notificationThread.start();
        log.info("Notification thread start work.");
    }

    private void createTaskEvent() {
        inputPanel.getCreateBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Task task = view.createTask();
                if (Objects.isNull(task)) {
                    return;
                } else {
                    model.getTaskList().add(task);
                }
                try {
                    refreshMV();
                } catch (IOException e1) {
                    log.warn("Model and View didn't update.");
                }
                view.setDefaultInputData();
                log.debug("Task: " + task.toString() + " created.");
            }
        });
    }

    private void showIncomingTaskEvent() {
        tabsPanel.getShowIncomingBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.getIncoming(model);
            }
        });
    }

    private void deleteTaskEvent() {
        infoPanel.getDeleteBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = tabsPanel.getAllTasks().getSelectedIndex();
                model.getTaskList().remove(model.getSelectedTask(index));
                try {
                    refreshMV();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                log.debug("Task #" + index + " deleted.");
            }
        });
    }

    private void saveChangeEvent() {
        infoPanel.getApplyBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = tabsPanel.getAllTasks().getSelectedIndex();
                if (view.changeSelectedItem(model.getSelectedTask(index))) {
                    try {
                        refreshMV();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                log.debug("Task #" + index + " changed after pressing apply-button.");
            }
        });
    }

    private void chooseListItemIvent() {
        tabsPanel.getAllTasks().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = tabsPanel.getAllTasks().getSelectedIndex();
                try {
                    view.selectingListItem(model.getSelectedTask(index));
                } catch (ArrayIndexOutOfBoundsException e1) {
                    log.debug("User clicked on empty list.");
                    return;
                }
                log.debug("List item #" + index + " chosen and full-info panel-refreshed.");
            }
        });
    }

}