import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GroupsDialog extends AbstractEntityDialog {
    public SubGroupsDialog subGroupsDialog;
    public StudentsDialog studentsDialog;

    public GroupsDialog(JFrame parent) {
        super(parent, "Класи");
    }

    void before() {
        this.tableModel = new GroupsTableModel();
        this.table = new JTable(this.tableModel);
    }

    void after() {
        this.newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeEntityDialog(Server.mainWindow, new Group());
                GroupsDialog.this.tableModel.fireTableDataChanged();
            }

        });
        this.changeButton.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/class_16.png")).getImage()));
        this.changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeEntityDialog(Server.mainWindow, GroupsDialog.this.tableModel.getEntity(GroupsDialog.this.table.getSelectedRow()));
                GroupsDialog.this.tableModel.fireTableDataChanged();
            }

        });
        JButton subGroupsButton = new JButton("Підгрупи", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/division_16.png")).getImage()));
        subGroupsButton.setBounds(10, 210, 130, 25);
        subGroupsButton.setHorizontalAlignment(2);
        subGroupsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GroupsDialog.this.subGroupsDialog = new SubGroupsDialog(Server.mainWindow, (Group) GroupsDialog.this.tableModel.getEntity(GroupsDialog.this.table.getSelectedRow()));
            }

        });
        this.controlPanel.add(subGroupsButton);

        JButton studentsButton = new JButton("Студенти", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/division_16.png")).getImage()));
        studentsButton.setBounds(10, 240, 130, 25);
        studentsButton.setHorizontalAlignment(2);
        studentsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentsDialog = new StudentsDialog(Server.mainWindow, (Group) tableModel.getEntity(table.getSelectedRow()));
            }

        });
        this.controlPanel.add(studentsButton);

        add(this.controlPanel, "East");
    }

    public static class GroupsTableModel extends AbstractEntityDialog.AbstractEntityTableModel {
        public int getRowCount() {
            return Server.data.getGroups().size();
        }

        public AbstractEntity getEntity(int row) {
            if (Server.data.getGroups().size() > 0)
                return ((AbstractEntity) Server.data.getGroups().get(row));

            return null;
        }
    }
}