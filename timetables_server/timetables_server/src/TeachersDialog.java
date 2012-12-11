import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeachersDialog extends AbstractEntityDialog {
    public TeachersDialog(JFrame parent) {
        super(parent, "Â÷èòåë³");
    }

    public void before() {
        this.tableModel = new TeachersTableModel();
        this.table = new JTable(this.tableModel);
    }

    void after() {
        this.newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeTeacherDialog(Server.mainWindow, new Teacher());
                TeachersDialog.this.tableModel.fireTableDataChanged();
            }

        });
        this.changeButton.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/teacher_16.png")).getImage()));
        this.changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeTeacherDialog(Server.mainWindow, (Teacher) TeachersDialog.this.tableModel.getEntity(TeachersDialog.this.table.getSelectedRow()));
                TeachersDialog.this.tableModel.fireTableDataChanged();
            }
        });
    }

    public static class TeachersTableModel extends AbstractEntityDialog.AbstractEntityTableModel {
        public int getRowCount() {
            return Server.data.getTeachers().size();
        }

        public AbstractEntity getEntity(int row) {
            if (Server.data.getTeachers().size() > 0)
                return ((AbstractEntity) Server.data.getTeachers().get(row));
            else
                return null;
        }
    }
}