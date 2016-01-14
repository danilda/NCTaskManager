package control;

import model.ArrayTaskList;
import model.Task;
import model.TaskManagerModel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import program.NotificationManager;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Date;
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
        createAditionalLists(model);
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
        createAditionalLists(model);
        getIncoming();
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
                    log.log(Level.WARN, "Model and View didn't update. Exception: ", e1);
                }
                view.setDefaultInputData();
                log.debug("Task: " + task.toString() + " created.");
            }
        });
    }

    private void showIncomingTaskEvent() {
        tabsPanel.getShowIncomingBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getIncoming();
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
                    log.log(Level.WARN, "Model and View didn't update. Exception: ", e1);
                }
                log.debug("Task #" + index + " deleted.");
            }
        });
    }

    private void saveChangeEvent() {
        infoPanel.getApplyBtn().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = tabsPanel.getAllTasks().getSelectedIndex();
                if (changeSelectedItem(model.getSelectedTask(index))) {
                    try {
                        refreshMV();
                    } catch (IOException e1) {
                        log.log(Level.WARN, "Model and View didn't update. Exception: ", e1);
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
                if (index == -1) {
                    return;
                } else {
                    view.selectingListItem(model.getSelectedTask(index));
                    log.debug("List item #" + index + " chosen and full-info panel-refreshed.");
                }
            }
        });
    }

    /**
     * Create aditional lists: today-list, week-list, month-list, tyear-list.
     * @param model model part of MVC.
     */
    private void createAditionalLists(TaskManagerModel model) {
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

    /*Help-methods*/
    /**
     * Make incoming list.
     */
    protected void getIncoming() {
        Date from = (Date) tabsPanel.getFromInput().getValue();
        Date to = (Date) tabsPanel.getToInput().getValue();
        if (to.before(from)) {
            view.getWarningAlert("Illegal dates: to is lover than from!!!", "Date error");
        }
        model.setIncomingList((ArrayTaskList) model.getTaskList().incoming(from, to));
        tabsPanel.getIncomingTasks().setListData(model.convertListToArray(model.getIncomingList()));
        log.debug("Incoming from: " + from.toString() +
                ", to: " + to.toString() + " got");
    }

    /**
     * Save changing in task after clicking save-button.
     * @param first changed task.
     * @return true if manager has changed task, otherwise - return false
     */
    private boolean changeSelectedItem(Task first) {
        Task task = view.changeSelectedItemView();
        if (task != null) {
            first = task;
            return true;
        } else {
            return false;
        }
    }

}