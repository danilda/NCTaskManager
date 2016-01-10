package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Class, that encapsulates input-panel.
 * @author Vinogradov Max
 */
public class InputPanel extends Panel {
    private JTextField taskText;
    private ButtonGroup group1 = new ButtonGroup();
    private ButtonGroup group2 = new ButtonGroup();
    private JRadioButton repeat = new JRadioButton("Yes", true);
    private JRadioButton notRepeat = new JRadioButton("No", false);
    private JRadioButton active = new JRadioButton("Active", true);
    private JRadioButton notActive = new JRadioButton("Not active", false);
    private JButton createBtn;
    private JSpinner timeSpinner;
    private JSpinner startSpinner;
    private JSpinner endSpinner;
    private JSpinner daySpinner;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner secondSpinner;


    public InputPanel() {
        createPanel("Add task\u25BC", new Rectangle(515, 50, 270, 350));
        createElements();
    }

    @Override
    protected void createElements() {
        createLabel("Title:", new Rectangle(17, 25, 200, 25));
        taskText = new JTextField();
        add(taskText);
        taskText.setBounds(15, 50, 240, 25);
        createLabel("Is repeated:", new Rectangle(15, 80, 110, 25));
        createRadio(repeat, group1, new Rectangle(110, 80, 70, 25));
        createRadio(notRepeat, group1, new Rectangle(183, 80, 70, 25));
        notRepeat.setSelected(true);
        repeat.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (repeat.isSelected()) {
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
        createLabel("Time:", new Rectangle(17, 110, 200, 25));
        timeSpinner = createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(75, 110, 180, 25));
        createLabel("Start:", new Rectangle(17, 150, 200, 25));
        startSpinner = createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(75, 150, 180, 25));
        startSpinner.setEnabled(false);
        createLabel("End:", new Rectangle(17, 190, 200, 25));
        endSpinner = createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(75, 190, 180, 25));
        endSpinner.setEnabled(false);
        createIntervalPart();
        createLabel("Activity:", new Rectangle(15, 274, 200, 25));
        createRadio(active, group2, new Rectangle(80, 274, 78, 25));
        createRadio(notActive, group2, new Rectangle(155, 274, 105, 25));
        createBtn = createButton("Create task", new Rectangle(60, 310, 150, 25));
    }

    private void createIntervalPart() {
        createLabel("Interval:", new Rectangle(17, 223, 200, 25));
        daySpinner = createNumberSpinner(new Rectangle(15, 247, 40, 25), 999);
        daySpinner.setEnabled(false);
        createLabel("d", new Rectangle(57, 247, 80, 25));
        hourSpinner = createNumberSpinner(new Rectangle(76, 247, 40, 25), 24);
        hourSpinner.setEnabled(false);
        createLabel("h", new Rectangle(121, 247, 80, 25));
        minuteSpinner = createNumberSpinner(new Rectangle(136, 247, 40, 25), 60);
        minuteSpinner.setEnabled(false);
        createLabel("m", new Rectangle(180, 247, 80, 25));
        secondSpinner = createNumberSpinner(new Rectangle(200, 247, 40, 25), 60);
        secondSpinner.setEnabled(false);
        createLabel("s", new Rectangle(245, 247, 80, 25));
    }


    private void createRadio(JRadioButton btn, ButtonGroup group, Rectangle bounds) {
        btn.setBounds(bounds);
        btn.setFont(font);
        btn.setBackground(background);
        group.add(btn);
        add(btn);
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

    public JSpinner getStartSpinner() {
        return startSpinner;
    }

    public JSpinner getTimeSpinner() {
        return timeSpinner;
    }

    public JButton getCreateBtn() {
        return createBtn;
    }

    public JRadioButton getNotActive() {
        return notActive;
    }

    public JRadioButton getActive() {
        return active;
    }

    public JRadioButton getNotRepeat() {
        return notRepeat;
    }

    public JRadioButton getRepeat() {
        return repeat;
    }

    public ButtonGroup getGroup2() {
        return group2;
    }

    public ButtonGroup getGroup1() {
        return group1;
    }

    public JTextField getTaskText() {
        return taskText;
    }
}