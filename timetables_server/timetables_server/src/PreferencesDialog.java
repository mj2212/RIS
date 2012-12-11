import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PreferencesDialog extends JDialog {
//    private JSpinner daysOfWeekSpinner;
//    private JSpinner lessonsByDaySpinner;
//    private JSpinner portSpinner;

    public PreferencesDialog(JFrame parent) {
        super(parent, "");
        setLayout(null);

//        JLabel daysOfWeekLabel = new JLabel("Днів на тиждень:");
//        daysOfWeekLabel.setBounds(20, 20, 110, 25);
//        add(daysOfWeekLabel);
//
//        JLabel lessonsByDayLabel = new JLabel("Уроків в день:");
//        lessonsByDayLabel.setBounds(20, 50, 90, 25);
//        lessonsByDayLabel.setBackground(Color.blue);
//        add(lessonsByDayLabel);
//
//        JLabel portLabel = new JLabel("Порт:");
//        portLabel.setBounds(20, 80, 90, 25);
//        portLabel.setBackground(Color.blue);
//        add(portLabel);
//
//        daysOfWeekSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 7, 1));
//        daysOfWeekSpinner.setBounds(120, 20, 130, 25);
//        daysOfWeekSpinner.setValue(Server.data.getSchool().getDaysOfWeek());
//        add(daysOfWeekSpinner);
//
//        lessonsByDaySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 12, 1));
//        lessonsByDaySpinner.setBounds(120, 50, 130, 25);
//        lessonsByDaySpinner.setValue(Server.data.getSchool().getLessonsByDay());
//        add(lessonsByDaySpinner);
//
//        portSpinner = new JSpinner(new SpinnerNumberModel(7001, 1, 32000, 1));
//        portSpinner.setBounds(120, 80, 130, 25);
//        portSpinner.setValue(Server.port);
//        add(portSpinner);



//        JButton okButton = new JButton("OK");
//        okButton.setBounds(35, 120, 70, 25);
//        okButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                Server.data.getSchool().setDaysOfWeek((Integer) daysOfWeekSpinner.getValue());
//                Server.data.getSchool().setLessonsByDay((Integer) lessonsByDaySpinner.getValue());
//                Server.port = ((Integer) portSpinner.getValue());
//                JOptionPane.showMessageDialog(null, "Зміни вступлять в дію після перезапуску програми");
//                dispose();
//            }
//
//        });
//        add(okButton);
//
//        JButton cancelButton = new JButton("Відмінити");
//        cancelButton.setBounds(115, 120, 100, 25);
//        cancelButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//            }
//
//        });
//        add(cancelButton);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 260;
        int height = 190;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }
}
