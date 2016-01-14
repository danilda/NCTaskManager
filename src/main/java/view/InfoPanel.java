package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Class, that encapsulates info-panel.
 * @author Vinogradov Max
 */
public class InfoPanel extends Panel{
    private JTextField taskText;
    private JCheckBox activityCB = new JCheckBox();
    private JCheckBox repeatCB = new JCheckBox();
    private JLabel nextTimeLabel;
    private JButton applyBtn;
    private JButton deleteBtn;

    public InfoPanel() {
        createPanel("Full info\u25BC", new Rectangle(10, 420, 775, 140));
        createElements();
    }

    @Override
    protected void createElements() {
        createLabel("Title:", new Rectangle(15, 20, 200, 25));
        taskText = new JTextField();
        taskText.setEnabled(false);
        add(taskText);
        taskText.setBounds(60, 20, 700, 25);
        createLabel("Repeat:", new Rectangle(15, 50, 200, 25));
        repeatCB.setBounds(80, 50, 50, 25);
        repeatCB.setBackground(background);
        repeatCB.setEnabled(false);
        repeatCB.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                boolean isSelected = repeatCB.isSelected();
                changeViewOnClick(isSelected);
            }
        });
        add(repeatCB);
        createLabel("Time:", new Rectangle(130, 50, 200, 25));
        setTimeSpinner(createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(180, 50, 140, 25)));
        getTimeSpinner().setEnabled(false);
        createLabel("Start:", new Rectangle(355, 50, 200, 25));
        setStartSpinner(createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(400, 50, 140, 25)));
        getStartSpinner().setEnabled(false);
        createLabel("End:", new Rectangle(580, 50, 200, 25));
        setEndSpinner(createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(620, 50, 140, 25)));
        getEndSpinner().setEnabled(false);
        createIntervalPart();
        createLabel("Next time of task:", new Rectangle(15, 110, 300, 25));
        nextTimeLabel = createLabel("-- / -- / -- --:--", new Rectangle(160, 110, 300, 25));
        createLabel("Activity:", new Rectangle(360, 80, 200, 25));
        activityCB.setBounds(435, 80, 50, 25);
        activityCB.setBackground(background);
        add(activityCB);
        activityCB.setEnabled(false);
        applyBtn = createButton("Apply", new Rectangle(660, 100, 100, 25));
        applyBtn.setEnabled(false);
        deleteBtn = createButton("Delete", new Rectangle(540, 100, 100, 25));
        deleteBtn.setEnabled(false);
    }

    private void createIntervalPart() {
        createLabel("Interval:", new Rectangle(15, 80, 200, 25));
        setDaySpinner(createNumberSpinner(new Rectangle(87, 80, 40, 25), 99));
        getDaySpinner().setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        getDaySpinner().setEnabled(false);
        createLabel("d", new Rectangle(130, 80, 80, 25));
        setHourSpinner(createNumberSpinner(new Rectangle(147, 80, 40, 25), 24));
        getHourSpinner().setEnabled(false);
        createLabel("h", new Rectangle(189, 80, 80, 25));
        setMinuteSpinner(createNumberSpinner(new Rectangle(205, 80, 40, 25), 60));
        getMinuteSpinner().setEnabled(false);
        createLabel("m", new Rectangle(248, 80, 80, 25));
        setSecondSpinner(createNumberSpinner(new Rectangle(268, 80, 40, 25), 60));
        getSecondSpinner().setEnabled(false);
        createLabel("s", new Rectangle(313, 80, 80, 25));
    }

    public JButton getDeleteBtn() {
        return deleteBtn;
    }

    public JButton getApplyBtn() {
        return applyBtn;
    }

    public JLabel getNextTimeLabel() {
        return nextTimeLabel;
    }

    public JCheckBox getRepeatCB() {
        return repeatCB;
    }

    public JCheckBox getActivityCB() {
        return activityCB;
    }

    public JTextField getTaskText() {
        return taskText;
    }
}
