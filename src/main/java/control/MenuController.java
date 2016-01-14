package control;

import model.ArrayTaskList;
import model.TaskManagerModel;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import view.TaskManagerView;
import view.TaskMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Class-controller, that encapsulates menu events-listeners.
 * @author Vinogradov Max
 */
public class MenuController {
    private static Logger log = Logger.getLogger(MenuController.class.getName());
    TaskManagerModel model;
    TaskManagerView view;
    TaskManagerController controller;
    TaskMenu menuPanel;

    /**
     * Initialize menu-controller.
     * @param model TaskManager model
     * @param view TaskManagerview
     * @param controller TaskManager controller
     */
    public MenuController(TaskManagerView view, TaskManagerModel model, TaskManagerController controller) {
        this.model = model;
        this.view = view;
        this.controller = controller;
        menuPanel = view.getTaskMenu();
        closeEvent();
        deleteAllEvent();
        getInfoEvent();
        log.debug("Menu controller created");
    }

    private void closeEvent() {
        menuPanel.getCloseItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                view.setVisible(false);
                view.dispose();
                log.debug("Task Manager finish work");
                System.exit(0);
            }
        });
    }

    private void deleteAllEvent() {
        menuPanel.getDeleteAllItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setTaskList(new ArrayTaskList());
                try {
                    controller.refreshMV();
                } catch (IOException e1) {
                    log.log(Level.WARN, "Model and View didn't update. Exception: ", e1);
                }
                controller.getIncoming();
                log.debug("All tasks was deleted.");
            }
        });
    }

    private void getInfoEvent() {
        menuPanel.getInfoItem().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(new Frame(),
                        "Task Manager created by Vinogradov Maxim. Version 1.0.",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE);
                log.debug("Info-massage was shown.");
            }
        });
    }
}
