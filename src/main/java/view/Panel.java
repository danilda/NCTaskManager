package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Abstract class, that encapsulates main properties and methods of all panel Task Manager.
 * @author Vinogradov Max
 */
public abstract class Panel extends JPanel {
    protected Color background = new Color(211,211,211);
    protected Font font = new Font("Arial", Font.BOLD, 16);
    private JSpinner timeSpinner;
    private JSpinner startSpinner;
    private JSpinner endSpinner;
    private JSpinner daySpinner;
    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner secondSpinner;

    /**
     * Create panel.
     * @param title title of panel.
     * @param bounds coordinates of panel.
     */
    protected void createPanel(String title, Rectangle bounds) {
        setLayout(null);
        setBounds(bounds);
        TitledBorder titledBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.black),
                title,
                TitledBorder.LEFT,
                TitledBorder.DEFAULT_POSITION,
                font
        );
        setBorder(titledBorder);
        setBackground(background);
    }

    /**
     * Create elements of panel.
     */
    protected abstract void createElements();

    /**
     * Create label in current panel.
     * @param text text of label.
     * @param bounds coordinates of label.
     * @return object of created label.
     */
    protected JLabel createLabel(String text, Rectangle bounds) {
        JLabel label = new JLabel(text);
        label.setBounds(bounds);
        label.setFont(font);
        add(label);
        return label;
    }

    /**
     * Create button in current panel.
     * @param title title of panel.
     * @param bounds coordinates of button.
     * @return object of created button.
     */
    protected JButton createButton(String title, Rectangle bounds) {
        JButton button = new JButton(title);
        button.setBounds(bounds);
        button.setFont(font);
        add(button);
        return button;
    }

    /**
     * Create Date Spinner in current panel.
     * @param pattern pattern of spinner.
     * @param bounds coordinates of spinner.
     * @return object of created spinner.
     */
    protected JSpinner createDateSpinner(String pattern, Rectangle bounds) {
        Date date = new Date();
        SpinnerDateModel sm =
                new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        JSpinner jSpinner = new JSpinner(sm);
        JSpinner.DateEditor de = new JSpinner.DateEditor(jSpinner, pattern);
        jSpinner.setEditor(de);
        jSpinner.setBounds(bounds);
        add(jSpinner);
        jSpinner.setVisible(true);
        return jSpinner;
    }

    /**
     * Create Number Spinner in current panel.
     * @param bounds coordinates of spinner.
     * @param max max value of spinner.
     * @return object of created spinner.
     */
    protected JSpinner createNumberSpinner(Rectangle bounds, int max) {
        SpinnerNumberModel sm =
                new SpinnerNumberModel(0, 0, max, 1);
        JSpinner jSpinner = new JSpinner(sm);
        JSpinner.NumberEditor ne = new JSpinner.NumberEditor(jSpinner);
        jSpinner.setEditor(ne);
        jSpinner.setBounds(bounds);
        add(jSpinner);
        jSpinner.setVisible(true);
        return jSpinner;
    }

    protected void changeViewOnClick(boolean isSelected) {
        if (isSelected) {
            getTimeSpinner().setEnabled(false);
            getStartSpinner().setEnabled(true);
            getEndSpinner().setEnabled(true);
            getDaySpinner().setEnabled(true);
            getHourSpinner().setEnabled(true);
            getMinuteSpinner().setEnabled(true);
            getSecondSpinner().setEnabled(true);
        } else {
            getTimeSpinner().setEnabled(true);
            getStartSpinner().setEnabled(false);
            getEndSpinner().setEnabled(false);
            getDaySpinner().setEnabled(false);
            getHourSpinner().setEnabled(false);
            getMinuteSpinner().setEnabled(false);
            getSecondSpinner().setEnabled(false);
        }
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

    public JSpinner getStartSpinner() {
        return startSpinner;
    }

    public JSpinner getEndSpinner() {
        return endSpinner;
    }

    public JSpinner getTimeSpinner() {
        return timeSpinner;
    }

    public void setTimeSpinner(JSpinner timeSpinner) {
        this.timeSpinner = timeSpinner;
    }

    public void setStartSpinner(JSpinner startSpinner) {
        this.startSpinner = startSpinner;
    }

    public void setDaySpinner(JSpinner daySpinner) {
        this.daySpinner = daySpinner;
    }

    public void setEndSpinner(JSpinner endSpinner) {
        this.endSpinner = endSpinner;
    }

    public void setHourSpinner(JSpinner hourSpinner) {
        this.hourSpinner = hourSpinner;
    }

    public void setMinuteSpinner(JSpinner minuteSpinner) {
        this.minuteSpinner = minuteSpinner;
    }

    public void setSecondSpinner(JSpinner secondSpinner) {
        this.secondSpinner = secondSpinner;
    }
}
