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
                boolean isSelected = repeat.isSelected();
                changeViewOnClick(isSelected);
            }
        });
        createLabel("Time:", new Rectangle(17, 110, 200, 25));
        setTimeSpinner(createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(75, 110, 180, 25)));
        createLabel("Start:", new Rectangle(17, 150, 200, 25));
        setStartSpinner(createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(75, 150, 180, 25)));
        getStartSpinner().setEnabled(false);
        createLabel("End:", new Rectangle(17, 190, 200, 25));
        setEndSpinner(createDateSpinner("yyyy-MM-dd HH:mm:ss", new Rectangle(75, 190, 180, 25)));
        getEndSpinner().setEnabled(false);
        createIntervalPart();
        createLabel("Activity:", new Rectangle(15, 274, 200, 25));
        createRadio(active, group2, new Rectangle(80, 274, 78, 25));
        createRadio(notActive, group2, new Rectangle(155, 274, 105, 25));
        createBtn = createButton("Create task", new Rectangle(60, 310, 150, 25));
    }

    private void createIntervalPart() {
        createLabel("Interval:", new Rectangle(17, 223, 200, 25));
        setDaySpinner(createNumberSpinner(new Rectangle(15, 247, 40, 25), 999));
        getDaySpinner().setEnabled(false);
        createLabel("d", new Rectangle(57, 247, 80, 25));
        setHourSpinner(createNumberSpinner(new Rectangle(76, 247, 40, 25), 24));
        getHourSpinner().setEnabled(false);
        createLabel("h", new Rectangle(121, 247, 80, 25));
        setMinuteSpinner(createNumberSpinner(new Rectangle(136, 247, 40, 25), 60));
        getMinuteSpinner().setEnabled(false);
        createLabel("m", new Rectangle(180, 247, 80, 25));
        setSecondSpinner(createNumberSpinner(new Rectangle(200, 247, 40, 25), 60));
        getSecondSpinner().setEnabled(false);
        createLabel("s", new Rectangle(245, 247, 80, 25));
    }

    private void createRadio(JRadioButton btn, ButtonGroup group, Rectangle bounds) {
        btn.setBounds(bounds);
        btn.setFont(font);
        btn.setBackground(background);
        group.add(btn);
        add(btn);
    }

    public JButton getCreateBtn() {
        return createBtn;
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

    public JTextField getTaskText() {
        return taskText;
    }
}