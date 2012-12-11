import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentsDialog extends JDialog {
    private JTable table;
    private StudentsTableModel tableModel;
    private Group group;

    private int count = 0;

    public StudentsDialog(JFrame parent, final Group group) {
        super(parent, "Студенти");

        this.group = group;

        setLayout(new BorderLayout());

        JPanel westPanel = new JPanel(new BorderLayout(20, 20));
        this.tableModel = new StudentsTableModel();
        this.table = new JTable(this.tableModel);
        this.table.setColumnModel(new StudentsTableColumnModel());
        westPanel.add(new JScrollPane(this.table), "Center");
        add(westPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setPreferredSize(new Dimension(150, 10));

        JButton newButton = new JButton("Додати", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/add_16.png")).getImage()));
        newButton.setBounds(10, 20, 130, 25);
        newButton.setHorizontalAlignment(2);
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Student newStudent = new Student();
                newStudent.setGroup(group);

                new AddOrChangeStudentDialog(Server.mainWindow, newStudent);
                StudentsDialog.this.tableModel.fireTableDataChanged();
            }

        });
        controlPanel.add(newButton);

        JButton changeButton = new JButton("Змінити", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/lessons_16.png")).getImage()));
        changeButton.setBounds(10, 50, 130, 25);
        changeButton.setHorizontalAlignment(2);
        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeStudentDialog(Server.mainWindow, StudentsDialog.this.tableModel.getStudent(StudentsDialog.this.table.getSelectedRow()));
                StudentsDialog.this.tableModel.fireTableDataChanged();
            }

        });
        controlPanel.add(changeButton);

        JButton removeButton = new JButton("Видалити", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/del_16.png")).getImage()));
        removeButton.setBounds(10, 80, 130, 25);
        removeButton.setHorizontalAlignment(2);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                group.removeStudent(tableModel.getStudent(table.getSelectedRow()));
                tableModel.fireTableDataChanged();
            }

        });
        controlPanel.add(removeButton);

        add(controlPanel, "East");

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen.width / 10 * 3;
        int height = screen.height / 5 * 3;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }

    private class StudentsTableColumnModel extends DefaultTableColumnModel {
        public StudentsTableColumnModel() {
            TableColumn column1 = new TableColumn();
            column1.setModelIndex(0);
            column1.setMaxWidth(30);
            addColumn(column1);

            TableColumn column2 = new TableColumn();
            column2.setModelIndex(1);
            column2.setPreferredWidth(100);
            addColumn(column2);
        }
    }

    private class StudentsTableModel extends DefaultTableModel {
        public static final int ID_COLUMN = 0;
        public static final int NAME_COLUMN = 1;
        public static final int COLUMN_COUNT = 2;

        private void add(ArrayList<Student> newStudents) {
            int first = group.getStudents().size();
            int last = first + newStudents.size() - 1;
            group.getStudents().addAll(newStudents);
            fireTableRowsInserted(first, last);
        }

        private void add(Student student) {
            int index = group.getStudents().size();
            group.getStudents().add(student);
            fireTableRowsInserted(index, index);
        }

        public int getRowCount() {
            return group.getStudents().size();
        }

        public int getColumnCount() {
            return COLUMN_COUNT;
        }

        public Student getStudent(int row) {
            if (group.getStudents().size() > 0)
                return ((Student) group.getStudents().get(row));

            return null;
        }

        public Class getColumnClass(int column) {
            try {
                return getValueAt(0, column).getClass();
            }
            catch (NullPointerException e) {
            }
            return new Object().getClass();
        }

        public Object getValueAt(int row, int column) {
            Student student = getStudent(row);
            switch (column) {
                case 0:
                    return ++count/2;
                case 1:
                    return student.getName();
            }
            return null;
        }

        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }

        public void fireTableDataChanged() {
            super.fireTableDataChanged();
            count = 0;
        }
    }
}