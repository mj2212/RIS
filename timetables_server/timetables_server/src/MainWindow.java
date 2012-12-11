import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class MainWindow extends JFrame {
    public JTable generalTable;
    public JTable teachersTable;
    public JTable roomsTable;
    public ToolBar toolBar;
    private JList generalRowHeader;
    private JList teachersRowHeader;
    private JList roomsRowHeader;
    public JPanel bottomPanel;
    public LessonsList list;
    private JScrollPane generalScroll;
    private Group selectedGroup = null;
    private Teacher selectedTeacher = null;
    private Room selectedRoom = null;
    private int actualRowIndex = -1;
    private int actualColumnIndex = -1;
    private JTabbedPane tabbedPane;
    private JLabel subjectLabel;
    private JLabel groupLabel;
    private JLabel teacherLabel;
    private JLabel roomLabel;
    private Cursor dc;
    private Lesson draggedLesson;
    private JButton weeksButton;
    private boolean isCreated = false;
    private JPanel infoPanel;

    public MainWindow() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setDefaultCloseOperation(3);
        setLayout(new BorderLayout());
        setTitle("Розклад занять");

        tabbedPane = new JTabbedPane(1, 1);
        JPanel generalPanel = new JPanel(new BorderLayout());
        JPanel teachersPanel = new JPanel(new BorderLayout());
        JPanel roomsPanel = new JPanel(new BorderLayout());

        generalTable = createTable();
        generalTable.setModel(new GeneralTimetableModel());
        generalScroll = new JScrollPane(generalTable);
        generalRowHeader = createGeneralRowHeader();
        generalScroll.setRowHeaderView(generalRowHeader);
        generalPanel.add(generalScroll, "Center");
        tabbedPane.add("Загальний", generalPanel);

        teachersTable = createTable();
        teachersTable.setModel(new TeachersTimetableModel());
        JScrollPane teachersScroll = new JScrollPane(teachersTable);
        teachersRowHeader = createTeachersRowHeader();
        teachersScroll.setRowHeaderView(teachersRowHeader);
        teachersPanel.add(teachersScroll, "Center");
        tabbedPane.add("Вчителі", teachersPanel);

        roomsTable = createTable();
        roomsTable.setModel(new RoomsTimetableModel());
        JScrollPane roomsScroll = new JScrollPane(roomsTable);
        roomsRowHeader = createRoomsRowHeader();
        roomsScroll.setRowHeaderView(roomsRowHeader);
        roomsPanel.add(roomsScroll, "Center");
        tabbedPane.add("Кабінети", roomsPanel);

        bottomPanel = new JPanel(new BorderLayout());
        JPanel westPanel = new JPanel(null);
        westPanel.setLayout(null);
        westPanel.setPreferredSize(new Dimension(300, 200));
        westPanel.setBorder(BorderFactory.createEtchedBorder());
        westPanel.setBackground(new Color(162, 163, 208));
        infoPanel = new JPanel(null);
        infoPanel.setVisible(false);
        subjectLabel = new JLabel("");
        subjectLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("images/subject_16.png")).getImage()));
        subjectLabel.setBounds(5, 0, 190, 20);
        infoPanel.add(subjectLabel);
        groupLabel = new JLabel("");
        groupLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("images/class_16.png")).getImage()));
        groupLabel.setBounds(5, 20, 190, 20);
        infoPanel.add(groupLabel);
        teacherLabel = new JLabel("");
        teacherLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("images/teacher_16.png")).getImage()));
        teacherLabel.setBounds(5, 40, 190, 20);
        infoPanel.add(teacherLabel);
        roomLabel = new JLabel("");
        roomLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("images/room_16.png")).getImage()));
        roomLabel.setBounds(5, 60, 190, 20);
        infoPanel.add(roomLabel);
        infoPanel.setBounds(10, 10, 200, 80);
        infoPanel.setBackground(new Color(201, 201, 221));
        infoPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        westPanel.add(infoPanel);
        bottomPanel.add(westPanel, "West");

        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());

        list = new LessonsList();
        list.setCellRenderer(new LessonsListCellRenderer());
        list.setSelectionMode(1);
        list.setLayoutOrientation(2);
        list.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (draggedLesson == null) {
                    takeLesson(draggedLesson = ((Lesson) list.getSelectedValue()));
                    list.setEnabled(false);
                } else {
                    draggedLesson.setPosition(null);
                    giveLesson(e);
                }
            }

            public void mouseReleased(MouseEvent e) {
                giveLesson(e);
            }

            public void mouseExited(MouseEvent e) {
                infoPanel.setVisible(false);
                subjectLabel.setText("");
                groupLabel.setText("");
                teacherLabel.setText("");
                roomLabel.setText("");
            }
        });
        list.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                infoPanel.setVisible(true);
                if (draggedLesson != null) {
                    subjectLabel.setText(draggedLesson.getSubject().getName());
                    groupLabel.setText(draggedLesson.getGroup().getName() + " - " + draggedLesson.getSubGroup().getName());
                    teacherLabel.setText(draggedLesson.getTeacher().getName());
                    roomLabel.setText(draggedLesson.getRoom().getName());
                }
            }

            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                if ((p.x > 0) && (p.y > 0) && (p.x < list.getWidth()) && (p.y < list.getHeight())) {
                    try {
                        Lesson lesson = (Lesson) list.getListData().get(list.locationToIndex(e.getPoint()));
                        if (lesson != null) {
                            infoPanel.setVisible(true);
                            subjectLabel.setText(lesson.getSubject().getName());
                            groupLabel.setText(lesson.getGroup().getName() + " - " + lesson.getSubGroup().getName());
                            teacherLabel.setText(lesson.getTeacher().getName());
                            roomLabel.setText(lesson.getRoom().getName());
                        } else {
                            infoPanel.setVisible(false);
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                }
            }
        });
        list.setVisibleRowCount(-1);
        eastPanel.add(new JScrollPane(list), "Center");

        eastPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.add(eastPanel, "Center");

        JSplitPane splitPane = new JSplitPane(0, tabbedPane, bottomPanel);
        splitPane.setDividerLocation(screen.height / 10 * 6);
        add(splitPane, "Center");

        JPanel glassPanel = (JPanel) getGlassPane();
        glassPanel.setVisible(true);
        glassPanel.setLayout(null);
        weeksButton = new JButton("1-ий");
        weeksButton.setBounds(3, 110, 51, 31);
        weeksButton.setMargin(new Insets(0, 0, 0, 0));
        weeksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < generalTable.getRowCount(); ++i) {
                    ((DefaultTableModel) generalTable.getModel()).fireTableRowsUpdated(i, i);
                }

            }

        });
        weeksButton.setVisible(false);
        glassPanel.add(weeksButton);
        setGlassPane(glassPanel);

        toolBar = new ToolBar();
        add(toolBar, "North");

        int width = screen.width / 10 * 9;
        int height = screen.height / 10 * 9;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        setIconImage(Toolkit.getDefaultToolkit().createImage("cancel.jpeg"));
        setVisible(true);
    }

    private JTable createTable() {
        final JTable table = new JTable() {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };
        table.setAutoResizeMode(0);
        table.setRowHeight(32);
        table.addMouseListener(new MouseAdapter() {
            int row;
            int column;

            public void mousePressed(MouseEvent e) {
                row = table.rowAtPoint(e.getPoint());
                column = table.columnAtPoint(e.getPoint());

                if(e.getButton() == MouseEvent.BUTTON1) {
                    if (draggedLesson == null) {
                        draggedLesson = ((Lesson) table.getValueAt(row, column));
                        table.setValueAt(null, row, column);
                        takeLesson(draggedLesson);
                    } else {
                        Lesson previousLesson = ((Lesson) table.getValueAt(row, column));
                        try {
                            if (previousLesson.getGroup().equals(draggedLesson.getGroup())) {
                                giveLesson(e);
                                draggedLesson = previousLesson;
                            }
                        } catch (NullPointerException ignored) {
                        }
                    }
                } else {
//                    JPopupMenu popup = new JPopupMenu();
//                    JMenuItem oMenuItem = new JMenuItem("Показати журнал оцінок");
//        //            oMenuItem.addActionListener(this);
//                    JMenuItem vMenuItem = new JMenuItem("Показати журнал відвідування");
//        //            vMenuItem.addActionListener(this);
//                    popup.add(oMenuItem);
//                    popup.add(vMenuItem);
//
//                    ((Lesson) table.getValueAt(row, column)).add
                }
            }

            public void mouseReleased(MouseEvent e) {
                Point p = e.getLocationOnScreen();
                if ((p.x > table.getLocationOnScreen().x) && (p.y > table.getLocationOnScreen().y) && (p.x < table.getLocationOnScreen().x + table.getWidth()) && (p.y < table.getLocationOnScreen().y + table.getHeight())) {
                    giveLesson(e);
                } else if ((p.x > list.getLocationOnScreen().x) && (p.y > list.getLocationOnScreen().y) && (p.x < list.getLocationOnScreen().x + list.getWidth()) && (p.y < list.getLocationOnScreen().y + list.getHeight())) {
                    draggedLesson.setPosition(null);
                    table.setValueAt(null, row, column);
                    list.setListData(Server.data.getLessons());
                    giveLesson(e);
                }
            }

            public void mouseExited(MouseEvent e) {
                actualRowIndex = -1;
                actualColumnIndex = -1;
                generalRowHeader.repaint();
                teachersRowHeader.repaint();
                roomsRowHeader.repaint();
                table.getTableHeader().repaint();
                infoPanel.setVisible(false);
            }

        });
        table.addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                infoPanel.setVisible(true);
                if (draggedLesson != null) {
                    subjectLabel.setText(draggedLesson.getSubject().getName());
                    groupLabel.setText(draggedLesson.getGroup().getName() + " - " + draggedLesson.getSubGroup().getName());
                    teacherLabel.setText(draggedLesson.getTeacher().getName());
                    roomLabel.setText(draggedLesson.getRoom().getName());
                }
            }

            public void mouseMoved(MouseEvent e) {
                Point p = e.getPoint();
                actualRowIndex = table.rowAtPoint(p);
                actualColumnIndex = table.columnAtPoint(p);
                generalRowHeader.repaint();
                teachersRowHeader.repaint();
                roomsRowHeader.repaint();
                table.getTableHeader().repaint();

                if ((p.x > 0) && (p.y > 0) && (p.x < table.getWidth()) && (p.y < table.getHeight()))
                    try {
                        int row = table.rowAtPoint(e.getPoint());
                        int column = table.columnAtPoint(e.getPoint());
                        Lesson lesson = (Lesson) table.getValueAt(row, column);
                        if (lesson != null) {
                            infoPanel.setVisible(true);
                            subjectLabel.setText(lesson.getSubject().getName());
                            groupLabel.setText(lesson.getGroup().getName() + " - " + lesson.getSubGroup().getName());
                            teacherLabel.setText(lesson.getTeacher().getName());
                            roomLabel.setText(lesson.getRoom().getName());
                        } else {
                            infoPanel.setVisible(false);
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException ignored) {
                    }
            }
        });
        return table;
    }

    private void takeLesson(Lesson draggedLesson) {
        if (draggedLesson != null) {
            Cursor customCursor = null;
            JTextArea cursorComponent = new JTextArea();
            cursorComponent.setText(draggedLesson.getSubject().getShortName());
            Color backGround = draggedLesson.getTeacher().getColor();
            cursorComponent.setBackground(backGround);
            if ((backGround.getBlue() < 75 && backGround.getRed() < 75)
                    || (backGround.getRed() < 75 && backGround.getGreen() < 75)
                    || (backGround.getBlue() < 75 && backGround.getGreen() < 75)) {
                cursorComponent.setForeground(Color.white);
            } else {
                cursorComponent.setForeground(Color.black);
            }
            int i = 0;
            int size = 0;
            for (ArrayList<SubGroup> subGroups : draggedLesson.getGroup().getSubGroups()) {
                for (SubGroup sb : subGroups) {
                    if (sb == draggedLesson.getSubGroup()) {
                        size = subGroups.size();
                        i = subGroups.indexOf(sb);
                        break;
                    }
                }
            }
            cursorComponent.setBorder(BorderFactory.createMatteBorder(0 + ((32 / size) * i), 0, 32 - ((0 + (32 / size) * i) + 32 / size), 0, Color.white));
            cursorComponent.setPreferredSize(new Dimension(32, 32));
            cursorComponent.setLineWrap(true);
            cursorComponent.setAlignmentY(0.5F);
            try {
                customCursor = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.createImage(cursorComponent), new Point(0, 0), "Pencil");
            } catch (IOException e) {
                e.printStackTrace();
            }
            setCursor(customCursor);

            selectedGroup = draggedLesson.getGroup();
            selectedTeacher = draggedLesson.getTeacher();
            selectedRoom = draggedLesson.getRoom();

            generalRowHeader.repaint();
        } else {
            setCursor(dc);
        }
    }

    private void giveLesson(MouseEvent e) {
        if (draggedLesson != null) {
            Point p = e.getLocationOnScreen();
            switch (tabbedPane.getSelectedIndex()) {
                case 0:
                    if ((p.x > generalTable.getLocationOnScreen().x) && (p.y > generalTable.getLocationOnScreen().y) && (p.x < generalTable.getLocationOnScreen().x + generalTable.getWidth()) && (p.y < generalTable.getLocationOnScreen().y + generalTable.getHeight())) {
                        SwingUtilities.convertPointFromScreen(p, generalTable);
                        int row = generalTable.rowAtPoint(p);
                        int column = generalTable.columnAtPoint(p);
                        if (((GeneralTimetableModel) generalTable.getModel()).getGroup(row).equals(draggedLesson.getGroup())) {
                            try {
                                if (draggedLesson.getTeacher().getLessons()[column].getTeacher() != draggedLesson.getTeacher()) {
                                    addLessonToGeneralTable(row, column);
                                }
                            } catch (NullPointerException npe) {
                            }
                            try {
                                if (draggedLesson.getRoom().getLessons()[column].getRoom() != draggedLesson.getRoom()) {
                                    addLessonToGeneralTable(row, column);
                                }
                            } catch (NullPointerException npe) {
                            }
                            if (draggedLesson.getTeacher().getLessons()[column] == null && draggedLesson.getRoom().getLessons()[column] == null) {
                                addLessonToGeneralTable(row, column);
                            }
                        }
                    }
                    break;
                case 1:
                    if ((p.x > teachersTable.getLocationOnScreen().x) && (p.y > teachersTable.getLocationOnScreen().y) && (p.x < teachersTable.getLocationOnScreen().x + teachersTable.getWidth()) && (p.y < teachersTable.getLocationOnScreen().y + teachersTable.getHeight())) {
                        SwingUtilities.convertPointFromScreen(p, teachersTable);
                        int row = teachersTable.rowAtPoint(p);
                        int column = teachersTable.columnAtPoint(p);
                        if (((TeachersTimetableModel) teachersTable.getModel()).getTeacher(row).equals(draggedLesson.getTeacher())) {
                            try {
                                if (draggedLesson.getGroup().getLessons()[column].getGroup() != draggedLesson.getGroup()) {
                                    addLessonToTeachersTable(row, column);
                                }
                            } catch (NullPointerException npe) {
                            }
                            try {
                                if (draggedLesson.getRoom().getLessons()[column].getRoom() != draggedLesson.getRoom()) {
                                    addLessonToTeachersTable(row, column);
                                }
                            } catch (NullPointerException npe) {
                            }
                            if (draggedLesson.getGroup().getLessons()[column] == null && draggedLesson.getRoom().getLessons()[column] == null) {
                                addLessonToTeachersTable(row, column);
                            }
                        }
                    }
                    break;
                case 2:
                    if ((p.x > roomsTable.getLocationOnScreen().x) && (p.y > roomsTable.getLocationOnScreen().y) && (p.x < roomsTable.getLocationOnScreen().x + roomsTable.getWidth()) && (p.y < roomsTable.getLocationOnScreen().y + roomsTable.getHeight())) {
                        SwingUtilities.convertPointFromScreen(p, roomsTable);
                        int row = roomsTable.rowAtPoint(p);
                        int column = roomsTable.columnAtPoint(p);
                        if (((RoomsTimetableModel) roomsTable.getModel()).getRoom(row).equals(draggedLesson.getRoom())) {
                            try {
                                if (draggedLesson.getGroup().getLessons()[column].getGroup() != draggedLesson.getGroup()) {
                                    addLessonToRoomTable(row, column);
                                }
                            } catch (NullPointerException npe) {
                            }
                            try {
                                if (draggedLesson.getTeacher().getLessons()[column].getTeacher() != draggedLesson.getTeacher()) {
                                    addLessonToRoomTable(row, column);
                                }
                            } catch (NullPointerException npe) {
                            }
                            if (draggedLesson.getGroup().getLessons()[column] == null && draggedLesson.getTeacher().getLessons()[column] == null) {
                                addLessonToRoomTable(row, column);
                            }
                        }
                    }
                    break;
            }
            if ((p.x > list.getLocationOnScreen().x) && (p.y > list.getLocationOnScreen().y) && (p.x < list.getLocationOnScreen().x + list.getWidth()) && (p.y < list.getLocationOnScreen().y + list.getHeight())) {
                if (list.isEnabled()) {
                    setCursor(dc);
                    draggedLesson = null;
                    selectedGroup = null;
                    selectedTeacher = null;
                    selectedRoom = null;
                    list.setListData(Server.data.getLessons());
                    generalRowHeader.repaint();
                }
            }
        }
    }

    private void addLessonToRoomTable(int row, int column) {
        if (roomsTable.getValueAt(row, column) == null) {
            roomsTable.setValueAt(draggedLesson, row, column);
            list.setEnabled(true);
            setListData(Server.data.getLessons());
            setCursor(dc);
            draggedLesson = null;
            selectedGroup = null;
            selectedTeacher = null;
            selectedRoom = null;
            roomsRowHeader.repaint();
        } else {
            Lesson previousValue = (Lesson) roomsTable.getValueAt(row, column);
            roomsTable.setValueAt(draggedLesson, row, column);
            draggedLesson = previousValue;
            list.setEnabled(true);
            setListData(Server.data.getLessons());
            setCursor(dc);
            //draggedLesson = null;
            selectedGroup = null;
            selectedTeacher = null;
            selectedRoom = null;
            roomsRowHeader.repaint();
            takeLesson(previousValue);
        }
    }

    private void addLessonToTeachersTable(int row, int column) {
        if (teachersTable.getValueAt(row, column) == null) {
            teachersTable.setValueAt(draggedLesson, row, column);
            list.setEnabled(true);
            setListData(Server.data.getLessons());
            setCursor(dc);
            draggedLesson = null;
            selectedGroup = null;
            selectedTeacher = null;
            selectedRoom = null;
            teachersRowHeader.repaint();
        } else {
            Lesson previousValue = (Lesson) teachersTable.getValueAt(row, column);
            teachersTable.setValueAt(draggedLesson, row, column);
            draggedLesson = previousValue;
            list.setEnabled(true);
            setListData(Server.data.getLessons());
            setCursor(dc);
            //draggedLesson = null;
            selectedGroup = null;
            selectedTeacher = null;
            selectedRoom = null;
            teachersRowHeader.repaint();
            takeLesson(previousValue);
        }
    }

    private void addLessonToGeneralTable(int row, int column) {
        if (generalTable.getValueAt(row, column) == null) {
            generalTable.setValueAt(draggedLesson, row, column);
            list.setEnabled(true);
            setListData(Server.data.getLessons());
            setCursor(dc);
            draggedLesson = null;
            selectedGroup = null;
            selectedTeacher = null;
            selectedRoom = null;
            generalRowHeader.repaint();
        } else {
            Lesson previousValue = (Lesson) generalTable.getValueAt(row, column);
            generalTable.setValueAt(draggedLesson, row, column);
            draggedLesson = previousValue;
            list.setEnabled(true);
            setListData(Server.data.getLessons());
            setCursor(dc);
            //draggedLesson = null;
            selectedGroup = null;
            selectedTeacher = null;
            selectedRoom = null;
            generalRowHeader.repaint();
            takeLesson(previousValue);
        }
    }

    public JList createLessonsList() {
        return null;
    }

    public JList createGeneralRowHeader() {
        JList rowHeader = new JList();
        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(generalTable.getRowHeight());
        rowHeader.setCellRenderer(new RowHeaderRenderer(generalTable));
        rowHeader.setBackground(UIManager.getColor("control"));
        return rowHeader;
    }

    public JList createTeachersRowHeader() {
        JList rowHeader = new JList();
        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(teachersTable.getRowHeight());
        rowHeader.setCellRenderer(new RowHeaderRenderer(teachersTable));
        rowHeader.setBackground(UIManager.getColor("control"));
        return rowHeader;
    }

    public JList createRoomsRowHeader() {
        JList rowHeader = new JList();
        rowHeader.setFixedCellWidth(50);
        rowHeader.setFixedCellHeight(roomsTable.getRowHeight());
        rowHeader.setCellRenderer(new RowHeaderRenderer(roomsTable));
        rowHeader.setBackground(UIManager.getColor("control"));
        return rowHeader;
    }

    public void setView(int view) {
        switch (view) {
            case 0:
        }
    }

    public void refresh() {
        Vector generalRowHeaders = new Vector();
        for (Group g : Server.data.getGroups())
            generalRowHeaders.add(g.getShortName());
        getGeneralRowHeader().setListData(generalRowHeaders);
        Vector roomsRowHeaders = new Vector();
        for (Room r : Server.data.getRooms())
            roomsRowHeaders.add(r.getShortName());
        getRoomsRowHeader().setListData(roomsRowHeaders);
        Vector teachersRowHeaders = new Vector();
        for (Teacher t : Server.data.getTeachers())
            teachersRowHeaders.add(t.getShortName());
        getTeachersRowHeader().setListData(teachersRowHeaders);
        setTitle("Розклад занять - " + Server.data.getName());
        if (!isCreated) {
            generalTable.setColumnModel(new GeneralTimetableColumnModel((GroupableTableHeader) generalTable.getTableHeader()));
            teachersTable.setColumnModel(new TeachersTimetableColumnModel((GroupableTableHeader) teachersTable.getTableHeader()));
            roomsTable.setColumnModel(new RoomsTimetableColumnModel((GroupableTableHeader) roomsTable.getTableHeader()));
            isCreated = true;
        }
        list.setListData(Server.data.getLessons());
        weeksButton.setVisible(true);
    }

    public static BufferedImage createImage(JComponent component) throws IOException {
        Dimension d = component.getSize();

        if (d.width == 0) {
            d = component.getPreferredSize();
            component.setSize(d);
        }

        Rectangle region = new Rectangle(0, 0, d.width, d.height);
        return createImage(component, region);
    }

    public static BufferedImage createImage(JComponent component, Rectangle region) throws IOException {
        boolean opaqueValue = component.isOpaque();
        component.setOpaque(true);
        BufferedImage image = new BufferedImage(region.width, region.height, 1);
        Graphics2D g2d = image.createGraphics();
        g2d.setClip(region);
        component.paint(g2d);
        g2d.dispose();
        component.setOpaque(opaqueValue);

        return image;
    }

    public JList getRoomsRowHeader() {
        return roomsRowHeader;
    }

    public JList getTeachersRowHeader() {
        return teachersRowHeader;
    }

    public JList getGeneralRowHeader() {
        return generalRowHeader;
    }

    public void setListData(ArrayList<Lesson> listData) {
        list.setListData(listData);
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(Room selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    public Group getSelectedGroup() {
        return selectedGroup;
    }

    public void setSelectedGroup(Group selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    public Teacher getSelectedTeacher() {
        return selectedTeacher;
    }

    public void setSelectedTeacher(Teacher selectedTeacher) {
        this.selectedTeacher = selectedTeacher;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public int getActualRowIndex() {
        return actualRowIndex;
    }

    public int getActualColumnIndex() {
        return actualColumnIndex;
    }

    public static class RowHeaderRenderer extends JLabel
            implements ListCellRenderer {
        private JTableHeader header;

        public RowHeaderRenderer(JTable table) {
            header = table.getTableHeader();
            setOpaque(true);
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setHorizontalAlignment(0);
            setForeground(header.getForeground());
            setBackground(header.getBackground());
            setFont(header.getFont());
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            String name = (String) value;
            String nameOfGroup = null;
            String nameOfTeacher = null;
            String nameOfRoom = null;
            try {
                nameOfGroup = Server.mainWindow.getSelectedGroup().getName();
                nameOfTeacher = Server.mainWindow.getSelectedTeacher().getName();
                nameOfRoom = Server.mainWindow.getSelectedRoom().getName();
            } catch (NullPointerException ignored) {
            }
            setText((value == null) ? "" : name);

            if ((Server.mainWindow.getTabbedPane().getSelectedIndex() == 0) && (name.equals(nameOfGroup))) {
                setBackground(Color.green.brighter());
                //setBorder(BorderFactory.createLineBorder(Color.green.brighter(), 1));
            } else if ((Server.mainWindow.getTabbedPane().getSelectedIndex() == 0) && (index == Server.mainWindow.getActualRowIndex())) {
                setBackground(Color.yellow);
                //setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
            } else if ((Server.mainWindow.getTabbedPane().getSelectedIndex() == 1) && (name.equals(nameOfTeacher))) {
                setBackground(Color.green.brighter());
                //setBorder(BorderFactory.createLineBorder(Color.green.brighter(), 1));
            } else if ((Server.mainWindow.getTabbedPane().getSelectedIndex() == 1) && (index == Server.mainWindow.getActualRowIndex())) {
                setBackground(Color.yellow);
                //setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
            } else if ((Server.mainWindow.getTabbedPane().getSelectedIndex() == 2) && (name.equals(nameOfRoom))) {
                setBackground(Color.green.brighter());
                //setBorder(BorderFactory.createLineBorder(Color.green.brighter(), 1));
            } else if ((Server.mainWindow.getTabbedPane().getSelectedIndex() == 2) && (index == Server.mainWindow.getActualRowIndex())) {
                setBackground(Color.yellow);
                //setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
            } else {
                setBackground(header.getBackground());
                setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            }
            return this;
        }
    }

    private class LessonsListCellRenderer implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                JPanel panel = new JPanel();
                panel.setLayout(null);
                Lesson lesson = (Lesson) value;
                JTextArea textArea = new JTextArea();
                textArea.setText(lesson.getSubject().getShortName());
                Color backGround = lesson.getTeacher().getColor();
                textArea.setBackground(backGround);
                if ((backGround.getBlue() < 75 && backGround.getRed() < 75)
                        || (backGround.getRed() < 75 && backGround.getGreen() < 75)
                        || (backGround.getBlue() < 75 && backGround.getGreen() < 75)) {
                    textArea.setForeground(Color.white);
                } else {
                    textArea.setForeground(Color.black);
                }
                int size = 0;
                int i = 0;
                for (ArrayList<SubGroup> subGroups : lesson.getGroup().getSubGroups()) {
                    for (SubGroup sb : subGroups) {
                        if (sb == lesson.getSubGroup()) {
                            size = subGroups.size();
                            i = subGroups.indexOf(sb);
                            break;
                        }
                    }
                }
                panel.setPreferredSize(new Dimension(38, 38));
                panel.setBackground(Color.white);
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setAlignmentY(0.5F);
                textArea.setBounds(3, 3 + (i > 0 ? 32 / size * i : 0), 32, 32 / size);
                panel.add(textArea);
                if (isSelected)
                    panel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(0, 0, 128)));
                else
                    panel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.white));
                return panel;
            }
            return null;
        }
    }

    private class LessonsList extends JList {
        public void setListData(ArrayList<Lesson> listData) {
            Vector v = new Vector();
            for (Lesson l : listData)
                if (l.getPosition() == null)
                    v.add(l);

            super.setListData(v);
        }

        public ArrayList getListData() {
            ArrayList lessons = new ArrayList();
            try {
                for (Lesson l : Server.data.getLessons())
                    if (l.getPosition() == null)
                        lessons.add(l);
            }
            catch (NullPointerException ignored) {
            }
            return lessons;
        }
    }

    public static class LessonsTableCellRenderer implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                JPanel panel = new JPanel();
                panel.setLayout(null);
                Lesson lesson = (Lesson) value;
                JTextArea textArea = new JTextArea();
                textArea.setText(lesson.getSubject().getShortName());
                Color backGround = lesson.getTeacher().getColor();
                textArea.setBackground(backGround);
                if ((backGround.getBlue() < 75 && backGround.getRed() < 75)
                        || (backGround.getRed() < 75 && backGround.getGreen() < 75)
                        || (backGround.getBlue() < 75 && backGround.getGreen() < 75)) {
                    textArea.setForeground(Color.white);
                } else {
                    textArea.setForeground(Color.black);
                }
                int size = 0;
                int i = 0;
                for (ArrayList<SubGroup> subGroups : lesson.getGroup().getSubGroups()) {
                    for (SubGroup sb : subGroups) {
                        if (sb == lesson.getSubGroup()) {
                            size = subGroups.size();
                            i = subGroups.indexOf(sb);
                            break;
                        }
                    }
                }
                panel.setPreferredSize(new Dimension(32, 32));
                panel.setBackground(Color.white);
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setAlignmentY(0.5F);
                textArea.setBounds(0, 0 + (i > 0 ? 32 / size * i : 0), 32, 32 / size);
                panel.add(textArea);
                return panel;
            }
            return null;
        }
    }
}