import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class AddOrChangeLessonDialog extends JDialog {
    private JComboBox groupComboBox;
    private JComboBox subGroupComboBox;
    private JComboBox roomComboBox;
    private JComboBox cycleComboBox;
    private JSpinner durationSpinner;
    private JSpinner countSpinner;
    private JComboBox subjectComboBox;
    private JComboBox teacherComboBox;

    public AddOrChangeLessonDialog(JFrame parent, final Lesson lesson) {
        super(parent, "Уроки");
        setLayout(null);

        JPanel teacherPanel = new JPanel(null);
        JLabel teacherIconLabel = new JLabel(new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/teacher_48.png")).getImage()));
        teacherIconLabel.setBounds(9, 9, 48, 48);
        teacherPanel.add(teacherIconLabel);
        JLabel teacherLabel = new JLabel("Вчитель:");
        teacherLabel.setBounds(80, 5, 100, 25);
        teacherPanel.add(teacherLabel);
        this.teacherComboBox = new JComboBox(Server.data.getTeachers().toArray());
        this.teacherComboBox.setRenderer(new EntityCellRenderer());
        this.teacherComboBox.setBounds(80, 30, 150, 25);
        teacherPanel.add(this.teacherComboBox);
        teacherPanel.setBounds(10, 10, 420, 65);
        teacherPanel.setBorder(BorderFactory.createEtchedBorder());
        add(teacherPanel);

        JPanel subjectPanel = new JPanel(null);
        JLabel subjectIconLabel = new JLabel(new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/subject_48.png")).getImage()));
        subjectIconLabel.setBounds(9, 9, 48, 48);
        subjectPanel.add(subjectIconLabel);
        JLabel subjectLabel = new JLabel("Предмет:");
        subjectLabel.setBounds(80, 5, 100, 25);
        subjectPanel.add(subjectLabel);
        this.subjectComboBox = new JComboBox(Server.data.getSubjects().toArray());
        this.subjectComboBox.setRenderer(new EntityCellRenderer());
        this.subjectComboBox.setBounds(80, 30, 150, 25);
        subjectPanel.add(this.subjectComboBox);
        subjectPanel.setBounds(10, 80, 420, 65);
        subjectPanel.setBorder(BorderFactory.createEtchedBorder());
        add(subjectPanel);

        JPanel groupPanel = new JPanel(null);
        JLabel groupIconLabel = new JLabel(new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/class_48.png")).getImage()));
        groupIconLabel.setBounds(9, 9, 48, 48);
        groupPanel.add(groupIconLabel);
        JLabel groupLabel = new JLabel("Клас:");
        groupLabel.setBounds(80, 5, 100, 25);
        groupPanel.add(groupLabel);
        this.groupComboBox = new JComboBox(Server.data.getGroups().toArray());
        this.groupComboBox.setRenderer(new EntityCellRenderer());
        this.groupComboBox.setBounds(80, 30, 150, 25);
        this.groupComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddOrChangeLessonDialog.this.subGroupComboBox.removeAllItems();
                for (ArrayList<SubGroup> l : ((Group) groupComboBox.getSelectedItem()).getSubGroups())
                    for (SubGroup sb : l)
                        subGroupComboBox.addItem(sb);


            }

        });
        groupPanel.add(this.groupComboBox);
        JLabel subGroupLabel = new JLabel("Підгрупа:");
        subGroupLabel.setBounds(260, 5, 100, 25);
        groupPanel.add(subGroupLabel);
        this.subGroupComboBox = new JComboBox();
        for (ArrayList<SubGroup> l : ((Group) groupComboBox.getSelectedItem()).getSubGroups())
            for (SubGroup sb : l)
                this.subGroupComboBox.addItem(sb);


        this.subGroupComboBox.setRenderer(new SubGroupComboBoxCellRenderer());
        this.subGroupComboBox.setBounds(260, 30, 150, 25);
        groupPanel.add(this.subGroupComboBox);
        groupPanel.setBounds(10, 150, 420, 65);
        groupPanel.setBorder(BorderFactory.createEtchedBorder());
        add(groupPanel);

        JPanel countPanel = new JPanel(null);
        JLabel countIconLabel = new JLabel(new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/count_48.png")).getImage()));
        countIconLabel.setBounds(9, 9, 48, 48);
        countPanel.add(countIconLabel);
        JLabel countLabel = new JLabel("Кількість:");
        countLabel.setBounds(80, 5, 70, 25);
        countPanel.add(countLabel);
        this.countSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        this.countSpinner.setBounds(80, 30, 70, 25);
        countPanel.add(this.countSpinner);
        JLabel cycleLabel = new JLabel("Цикл:");
        cycleLabel.setBounds(310, 5, 100, 25);
        countPanel.add(cycleLabel);
        Vector cycleNames = new Vector();
        cycleNames.add("Щотижня");
        cycleNames.add("По 1-му тижню");
        cycleNames.add("По 2-му тижню");
        this.cycleComboBox = new JComboBox(cycleNames);
        this.cycleComboBox.setBounds(310, 30, 100, 25);
        countPanel.add(this.cycleComboBox);
        countPanel.setBounds(10, 220, 420, 65);
        countPanel.setBorder(BorderFactory.createEtchedBorder());
        add(countPanel);

        JPanel roomPanel = new JPanel(null);
        JLabel roomIconLabel = new JLabel(new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/room_48.png")).getImage()));
        roomIconLabel.setBounds(9, 9, 48, 48);
        roomPanel.add(roomIconLabel);
        JLabel roomLabel = new JLabel("Кабінет:");
        roomLabel.setBounds(80, 5, 100, 25);
        roomPanel.add(roomLabel);
        this.roomComboBox = new JComboBox(Server.data.getRooms().toArray());
        this.roomComboBox.setRenderer(new EntityCellRenderer());
        this.roomComboBox.setBounds(80, 30, 150, 25);
        roomPanel.add(this.roomComboBox);
        roomPanel.setBounds(10, 290, 420, 65);
        roomPanel.setBorder(BorderFactory.createEtchedBorder());
        add(roomPanel);

        JButton okButton = new JButton("OK");
        okButton.setBounds(215, 365, 100, 25);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lesson.setGroup((Group) AddOrChangeLessonDialog.this.groupComboBox.getSelectedItem());
                lesson.setSubject((Subject) AddOrChangeLessonDialog.this.subjectComboBox.getSelectedItem());
                lesson.setTeacher((Teacher) AddOrChangeLessonDialog.this.teacherComboBox.getSelectedItem());
                lesson.setRoom((Room) roomComboBox.getSelectedItem());
                lesson.setCycle(cycleComboBox.getSelectedIndex());
                lesson.setSubGroup((SubGroup) subGroupComboBox.getSelectedItem());
                lesson.setCount(((Integer) AddOrChangeLessonDialog.this.countSpinner.getValue()).intValue());
//                lesson.setDuration(((Integer) AddOrChangeLessonDialog.this.durationSpinner.getValue()).intValue());
                Server.data.getLessons().remove(lesson);
                Server.data.getLessons().add(lesson);
                Server.mainWindow.setListData(Server.data.getLessons());
                AddOrChangeLessonDialog.this.dispose();
            }

        });
        add(okButton);

        JButton cancelButton = new JButton("Відмінити");
        cancelButton.setBounds(325, 365, 100, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddOrChangeLessonDialog.this.dispose();
            }

        });
        add(cancelButton);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 445;
        int height = 430;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }

    public class SubGroupComboBoxCellRenderer extends JLabel
            implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setBackground(Color.white);
            try {
                setText(((SubGroup) value).getName());
            }
            catch (NullPointerException e) {
            }
            return this;
        }
    }

    public class EntityCellRenderer extends JLabel
            implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setBackground(Color.white);
            try {
                setText(((AbstractEntity) value).getName());
            }
            catch (NullPointerException e) {
            }
            return this;
        }
    }
}