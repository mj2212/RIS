import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LessonsDialog extends JDialog {
    private JTable table;
    private LessonsTableModel tableModel;

    public LessonsDialog(JFrame parent) {
        super(parent, "Уроки");
        setLayout(new BorderLayout());

        JPanel westPanel = new JPanel(new BorderLayout(20, 20));
        this.tableModel = new LessonsTableModel();
        this.table = new JTable(this.tableModel);
        this.table.setColumnModel(new LessonsTableColumnModel());
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
                new AddOrChangeLessonDialog(Server.mainWindow, new Lesson());
                LessonsDialog.this.tableModel.fireTableDataChanged();
            }

        });
        controlPanel.add(newButton);

        JButton changeButton = new JButton("Змінити", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/lessons_16.png")).getImage()));
        changeButton.setBounds(10, 50, 130, 25);
        changeButton.setHorizontalAlignment(2);
        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeLessonDialog(Server.mainWindow, LessonsDialog.this.tableModel.getLesson(LessonsDialog.this.table.getSelectedRow()));
                LessonsDialog.this.tableModel.fireTableDataChanged();
            }

        });
        controlPanel.add(changeButton);

        JButton removeButton = new JButton("Видалити", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/del_16.png")).getImage()));
        removeButton.setBounds(10, 80, 130, 25);
        removeButton.setHorizontalAlignment(2);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Server.data.getLessons().remove(LessonsDialog.this.tableModel.getLesson(LessonsDialog.this.table.getSelectedRow()));
                LessonsDialog.this.tableModel.fireTableDataChanged();
            }

        });
        controlPanel.add(removeButton);

        add(controlPanel, "East");

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen.width / 4 * 3;
        int height = screen.height / 4 * 3;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }

    private class CycleCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            switch (Integer.parseInt(value.toString())) {
                case 0:
                    setValue("Щотижня");
                    break;
                case 1:
                    setValue("Через тиждень");
            }

            return this;
        }
    }

    private class LessonsTableColumnModel extends DefaultTableColumnModel {
        public LessonsTableColumnModel() {
            TableColumn column = new TableColumn();
            column.setModelIndex(0);
            column.setHeaderValue("Предмет");
            column.setPreferredWidth(100);
            addColumn(column);

            column = new TableColumn();
            column.setModelIndex(1);
            column.setHeaderValue("Вчитель");
            column.setPreferredWidth(100);
            addColumn(column);

            column = new TableColumn();
            column.setModelIndex(2);
            column.setHeaderValue("Клас");
            column.setPreferredWidth(100);
            addColumn(column);

            column = new TableColumn();
            column.setModelIndex(3);
            column.setHeaderValue("Кількість уроків на тиждень");
            column.setPreferredWidth(10);
            addColumn(column);

            column = new TableColumn();
            column.setModelIndex(4);
            column.setHeaderValue("Тривалість");
            column.setPreferredWidth(10);
            addColumn(column);

            column = new TableColumn();
            column.setModelIndex(5);
            column.setHeaderValue("Кабінет");
            column.setPreferredWidth(20);
            addColumn(column);

            column = new TableColumn();
            column.setModelIndex(6);
            column.setHeaderValue("Цикл");
            column.setPreferredWidth(10);
            column.setCellRenderer(new CycleCellRenderer());
            addColumn(column);
        }
    }

    private class LessonsTableModel extends DefaultTableModel {
        public static final int SUBJECT_COLUMN = 0;
        public static final int TEACHER_COLUMN = 1;
        public static final int GROUP_COLUMN = 2;
        public static final int COUNT_COLUMN = 3;
        public static final int DURATION_COLUMN = 4;
        public static final int ROOM_COLUMN = 5;
        public static final int CYCLE_COLUMN = 6;
        public static final int COLUMN_COUNT = 7;

        private void add(ArrayList<Lesson> newLessons) {
            int first = Server.data.getLessons().size();
            int last = first + newLessons.size() - 1;
            Server.data.getLessons().addAll(newLessons);
            fireTableRowsInserted(first, last);
        }

        private void add(Lesson lesson) {
            int index = Server.data.getLessons().size();
            Server.data.getLessons().add(lesson);
            fireTableRowsInserted(index, index);
        }

        public int getRowCount() {
            return Server.data.getLessons().size();
        }

        public int getColumnCount() {
            return 7;
        }

        public Lesson getLesson(int row) {
            if (Server.data.getLessons().size() > 0)
                return ((Lesson) Server.data.getLessons().get(row));

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
            Lesson lesson = getLesson(row);
            switch (column) {
                case 0:
                    return lesson.getSubject().getName();
                case 1:
                    return lesson.getTeacher().getName();
                case 2:
                    return lesson.getGroup().getName() + " (" + lesson.getSubGroup().getName() + ")";
                case 3:
                    return Integer.valueOf(lesson.getCount());
                case 4:
                    return Integer.valueOf(lesson.getDuration());
                case 5:
                    return lesson.getRoom().getName();
                case 6:
                    return Integer.valueOf(lesson.getCycle());
            }
            return null;
        }

        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }
    }
}