package view;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Class, that encapsulates main frame of Task Manager.
 * @author Vinogradov Max
 */
public class TaskManagerFrame extends JFrame {
    private static Logger log = Logger.getLogger(TaskManagerFrame.class.getName());
    protected InputPanel inputPanel = new InputPanel();
    protected InfoPanel infoPanel = new InfoPanel();
    protected TabsPanel tabsPanel = new TabsPanel();
    protected TaskMenu taskMenu = new TaskMenu();

    /**
     * Initialize main frame object.
     */
    protected TaskManagerFrame () {
        createMainFrame();
        add(inputPanel);
        add(infoPanel);
        add(tabsPanel);
        add(taskMenu);
        repaint();
        setVisible(true);
        log.debug("Main frame created.");
    }

    /**
     * Create main frame.
     */
    private void createMainFrame() {
        setName("Task Manager");
        setTitle("Task Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(calcFormBounds());
        setBackground(new Color(224, 224, 224));
        ImageIcon icon = new ImageIcon("src/main/java/resources/icon.png");
        setIconImage(icon.getImage());
        setLayout(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Calculate start position of main frame.
     * @return Rectangle instance with proper coordinates.
     */
    private Rectangle calcFormBounds() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight();
        double width = screenSize.getWidth();
        Rectangle bounds = new Rectangle((int)width / 2 - 400, (int)height/2 - 300, 800, 600);
        return bounds;
    }

    /**
     * This method makes simple generating of warning alert.
     * @param text text of warning alert.
     * @param caption caption of warning alert.
     */
    public static void getWarningAlert(String text, String caption) {
        JOptionPane.showMessageDialog(new Frame(), text, caption, JOptionPane.WARNING_MESSAGE);
        log.debug("User error: " + text);
    }

    /**
     * Set default info-panel data.
     */
    public void setDefaultInfo() {
        tabsPanel.getAllTasks().clearSelection();
        infoPanel.getTaskText().setText("");
        infoPanel.getTaskText().setEnabled(false);
        infoPanel.getRepeatCB().setSelected(false);
        infoPanel.getRepeatCB().setEnabled(false);
        infoPanel.getActivityCB().setSelected(false);
        infoPanel.getActivityCB().setEnabled(false);
        infoPanel.getTimeSpinner().setEnabled(false);
        infoPanel.getStartSpinner().setEnabled(false);
        infoPanel.getEndSpinner().setEnabled(false);
        setDefaultInfoData();
        infoPanel.getDaySpinner().setEnabled(false);
        infoPanel.getHourSpinner().setEnabled(false);
        infoPanel.getMinuteSpinner().setEnabled(false);
        infoPanel.getSecondSpinner().setEnabled(false);
        infoPanel.getApplyBtn().setEnabled(false);
        infoPanel.getDeleteBtn().setEnabled(false);
        infoPanel.getNextTimeLabel().setText("-- / -- / -- --:--");
    }

    /**
     * Set default data in spinners in info panel.
     */
    public void setDefaultInfoData() {
        infoPanel.getStartSpinner().setValue(new Date());
        infoPanel.getEndSpinner().setValue(new Date());
        infoPanel.getTimeSpinner().setValue(new Date());
        infoPanel.getDaySpinner().setValue(0);
        infoPanel.getHourSpinner().setValue(0);
        infoPanel.getMinuteSpinner().setValue(0);
        infoPanel.getSecondSpinner().setValue(0);
    }

    /**
     * Set default input-panel data.
     */
    public void setDefaultInputData() {
        inputPanel.getTaskText().setText("");
        inputPanel.getNotRepeat().setSelected(true);
        inputPanel.getTimeSpinner().setValue(new Date());
        inputPanel.getStartSpinner().setValue(new Date());
        inputPanel.getEndSpinner().setValue(new Date());
        inputPanel.getSecondSpinner().setValue(0);
        inputPanel.getMinuteSpinner().setValue(0);
        inputPanel.getHourSpinner().setValue(0);
        inputPanel.getDaySpinner().setValue(0);
        inputPanel.getActive().setSelected(true);
    }

    /**
     * @return input-panel object.
    */
    public InputPanel getInputPanel() {
        return inputPanel;
    }

    /**
     * @return info-panel object.
     */
    public InfoPanel getInfoPanel() {
        return infoPanel;
    }

    /**
     * @return tabs-panel object.
     */
    public TabsPanel getTabsPanel() {
        return tabsPanel;
    }

    /**
     * @return menu object.
     */
    public TaskMenu getTaskMenu() {
        return taskMenu;
    }
}
