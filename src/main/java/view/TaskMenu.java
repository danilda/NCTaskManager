package view;

import javax.swing.*;

/**
 * Class, that encapsulates menu of application.
 * @author Vinogradov Max
 */
public class TaskMenu extends JMenuBar {
    private JMenuItem closeItem = new JMenuItem("Close");
    private JMenuItem deleteAllItem = new JMenuItem("Delete all tasks");
    private JMenuItem infoItem = new JMenuItem("About");

    public TaskMenu() {
        setLayout(null);
        setBounds(0, 0, 800, 30);
        JMenu file = new JMenu("File");
        file.setBounds(0 ,0, 50, 30);
        file.add(closeItem);
        JMenu edit = new JMenu("Edit");
        edit.setBounds(50, 0, 50, 30);
        edit.add(deleteAllItem);
        JMenu help = new JMenu("Help");
        help.setBounds(100, 0, 50, 30);
        help.add(infoItem);
        add(file);
        add(edit);
        add(help);
    }

    public JMenuItem getCloseItem() {
        return closeItem;
    }

    public JMenuItem getDeleteAllItem() {
        return deleteAllItem;
    }

    public JMenuItem getInfoItem() {
        return infoItem;
    }
}

