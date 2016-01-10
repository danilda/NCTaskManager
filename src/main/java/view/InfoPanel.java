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
    private JSpinner timeSpinner;
    private JSpinner startSpinner;
    private JSpinner endSpinner;
    private JSpinner daySpinner;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner secondSpinner;

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
                if (repeatCB.isSelected()) {
                    timeSpinner.setEnabled(false);
                    startSpinner.setEnabled(true);
                    endSpinner.setEnabled(true);
                    daySpinner.setEnabled(true);
                    hourSpinner.setEnabled(true);
                    minuteSpinner.setEnabled(true);
                    secondSpinner.setEnabled(true);
                } else {
                    timeSpinner.setEnabled(true);
                    startSpinner.setEnabled(false);
                    endSpinner.setEnabled(false);
                    daySpinner.setEnabled(false);
                    hourSpinner.setEnabled(false);
                    minuteSpinner.setEnabled(false);
                    secondSpinner.setEnabled(false);
                }
            }
        });
        add(repeatCB);
        createLabel("Time:", new Rectangle(130, 50, 200, 25));
        timeSpinner = createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(180, 50, 140, 25));
        timeSpinner.setEnabled(false);
        createLabel("Start:", new Rectangle(355, 50, 200, 25));
        startSpinner = createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(400, 50, 140, 25));
        startSpinner.setEnabled(false);
        createLabel("End:", new Rectangle(580, 50, 200, 25));
        endSpinner = createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(620, 50, 140, 25));
        endSpinner.setEnabled(false);
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
        daySpinner = createNumberSpinner(new Rectangle(87, 80, 40, 25), 99);
        daySpinner.setModel(new SpinnerNumberModel(0, 0, 1000, 1));
        daySpinner.setEnabled(false);
        createLabel("d", new Rectangle(130, 80, 80, 25));
        hourSpinner = createNumberSpinner(new Rectangle(147, 80, 40, 25), 24);
        hourSpinner.setEnabled(false);
        createLabel("h", new Rectangle(189, 80, 80, 25));
        minuteSpinner = createNumberSpinner(new Rectangle(205, 80, 40, 25), 60);
        minuteSpinner.setEnabled(false);
        createLabel("m", new Rectangle(248, 80, 80, 25));
        secondSpinner = createNumberSpinner(new Rectangle(268, 80, 40, 25), 60);
        secondSpinner.setEnabled(false);
        createLabel("s", new Rectangle(313, 80, 80, 25));
    }

    public JSpinner getSecondSpinner() {
        return secondSpinner;
    }

    public JSpinner getMinuteSpinner() {
        return minuteSpinner;
    }

    public JSpinner getHourSpinner() {
        return hourSpinner;
    }

    public JSpinner getDaySpinner() {
        return daySpinner;
    }

    public JSpinner getEndSpinner() {
        return endSpinner;
    }

    public JSpinner getTimeSpinner() {
        return timeSpinner;
    }

    public JButton getDeleteBtn() {
        return deleteBtn;
    }

    public JSpinner getStartSpinner() {
        return startSpinner;
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
