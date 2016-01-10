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
}
