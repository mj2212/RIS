import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public abstract class AbstractEntityDialog extends JDialog {
    protected AbstractEntityTableModel tableModel;
    protected JTable table;
    protected JButton newButton;
    protected JButton changeButton;
    protected JPanel controlPanel;

    public AbstractEntityDialog(JFrame parent, String title) {
        super(parent, title);
        setLayout(new BorderLayout());

        JPanel westPanel = new JPanel(new BorderLayout(20, 20));
        before();
        this.table.setColumnModel(new EntityTableColumnModel());
        this.table.setSelectionMode(0);
        westPanel.add(new JScrollPane(this.table), "Center");
        if (this.table.getRowCount() > 0) this.table.getSelectionModel().setSelectionInterval(0, 0);
        add(westPanel);

        this.controlPanel = new JPanel();
        this.controlPanel.setLayout(null);
        this.controlPanel.setPreferredSize(new Dimension(150, 10));

        this.newButton = new JButton("Додати", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/add_16.png")).getImage()));
        this.newButton.setBounds(10, 20, 130, 25);
        this.newButton.setHorizontalAlignment(2);
        this.controlPanel.add(this.newButton);

        this.changeButton = new JButton("Змінити");
        this.changeButton.setBounds(10, 50, 130, 25);
        this.changeButton.setHorizontalAlignment(2);
        this.controlPanel.add(this.changeButton);

        JButton removeButton = new JButton("Видалити", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/del_16.png")).getImage()));
        removeButton.setBounds(10, 80, 130, 25);
        removeButton.setHorizontalAlignment(2);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Vector rowHeaders;
                AbstractEntity entity = AbstractEntityDialog.this.tableModel.getEntity(AbstractEntityDialog.this.table.getSelectedRow());
                if (entity instanceof Group) {
                    ((GeneralTimetableModel) Server.mainWindow.generalTable.getModel()).remove((Group) entity);
                    rowHeaders = new Vector();
                    for (Group g : Server.data.getGroups())
                        rowHeaders.add(g.getShortName());
                    Server.mainWindow.getGeneralRowHeader().setListData(rowHeaders);
                } else if (entity instanceof Room) {
                    ((RoomsTimetableModel) Server.mainWindow.roomsTable.getModel()).remove((Room) entity);
                    rowHeaders = new Vector();
                    for (Room r : Server.data.getRooms())
                        rowHeaders.add(r.getShortName());

                    Server.mainWindow.getRoomsRowHeader().setListData(rowHeaders);
                } else if (entity instanceof Subject) {
                    Server.data.getSubjects().remove((Subject) entity);
                } else if (entity instanceof Teacher) {
                    ((TeachersTimetableModel) Server.mainWindow.teachersTable.getModel()).remove((Teacher) entity);
                    rowHeaders = new Vector();
                    for (Teacher t : Server.data.getTeachers())
                        rowHeaders.add(t.getShortName());
                    Server.mainWindow.getTeachersRowHeader().setListData(rowHeaders);
                }
                AbstractEntityDialog.this.tableModel.fireTableDataChanged();
            }
        });
        this.controlPanel.add(removeButton);

        JButton lessonsButton = new JButton("Уроки", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/lessons_16.png")).getImage()));
        lessonsButton.setBounds(10, 130, 130, 25);
        lessonsButton.setHorizontalAlignment(2);
        lessonsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LessonsDialog(Server.mainWindow);
            }

        });
        this.controlPanel.add(lessonsButton);

        add(this.controlPanel, "East");

        after();

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen.width / 2;
        int height = screen.height / 2;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }

    abstract void before();

    abstract void after();

    public static abstract class AbstractEntityTableModel extends DefaultTableModel {
        public static final int NAME_COLUMN = 0;
        public static final int SHORT_COLUMN = 1;
        public static final int COUNT_COLUMN = 2;
        public static final int COLUMN_COUNT = 3;

        public int getColumnCount() {
            return 3;
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
            AbstractEntity entity = getEntity(row);
            switch (column) {
                case 0:
                    return entity.getName();
                case 1:
                    return entity.getShortName();
                case 2:
                    return entity.getCountOfLessons();
            }
            return null;
        }

        abstract AbstractEntity getEntity(int paramInt);

        public boolean isCellEditable(int rowIndex, int colIndex) {
            return false;
        }
    }
}