package view;

import javax.swing.*;
import java.awt.*;

/**
 * Class, that encapsulates tabs-panel.
 * @author Vinogradov Max
 */
public class TabsPanel extends Panel {
    private JTabbedPane tabbedPane;
    private JList allTasks;
    private JList todayTasks;
    private JList weekTasks;
    private JList monthTasks;
    private JList yearTasks;
    private JList incomingTasks;
    private JButton showIncomingBtn;
    private JSpinner fromInput;
    private JSpinner toInput;

    public TabsPanel() {
        createPanel("Tasks\u25BC", new Rectangle(10, 50, 480, 350));
        createElements();
    }

    @Override
    protected void createElements() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(4, 15, 473, 332);
        allTasks = createTab("All tasks");
        todayTasks = createTab("Today");
        weekTasks = createTab("This week");
        monthTasks = createTab("This month");
        yearTasks = createTab("This year");
        DefaultListModel dm = new DefaultListModel();
        incomingTasks = new JList(dm);
        JScrollPane scrollPane = new JScrollPane(incomingTasks);
        scrollPane.setBounds(5, 50, 460, 250);
        JPanel last = new JPanel();
        last.setLayout(null);
        showIncomingBtn = createButton("Show", new Rectangle(375, 10, 80, 25));
        last.add(showIncomingBtn);
        fromInput = createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(58, 10, 140, 25));
        toInput = createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(230, 10, 140, 25));
        last.add(fromInput);
        last.add(toInput);
        last.add(createLabel("From:", new Rectangle(10, 10, 55, 25)));
        last.add(createLabel("To:", new Rectangle(203, 10, 30, 25)));
        last.add(scrollPane);
        tabbedPane.add("Your interval", last);
        add(tabbedPane);
    }

    private JList createTab(String title) {
        DefaultListModel listModel = new DefaultListModel();
        JList list = new JList(listModel);
        JScrollPane scrollPane = new JScrollPane(list);
        tabbedPane.add(title, scrollPane);
        return list;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JList getAllTasks() {
        return allTasks;
    }

    public JList getTodayTasks() {
        return todayTasks;
    }

    public JList getWeekTasks() {
        return weekTasks;
    }

    public JList getMonthTasks() {
        return monthTasks;
    }

    public JList getYearTasks() {
        return yearTasks;
    }

    public JList getIncomingTasks() {
        return incomingTasks;
    }

    public JButton getShowIncomingBtn() {
        return showIncomingBtn;
    }

    public JSpinner getFromInput() {
        return fromInput;
    }

    public JSpinner getToInput() {
        return toInput;
    }
}
