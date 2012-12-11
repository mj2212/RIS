import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubjectsDialog extends AbstractEntityDialog {
    public SubjectsDialog(JFrame parent) {
        super(parent, "Предмети");
    }

    void before() {
        this.tableModel = new SubjectsTableModel();
        this.table = new JTable(this.tableModel);
    }

    void after() {
        this.newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeEntityDialog(Server.mainWindow, new Subject());
                SubjectsDialog.this.tableModel.fireTableDataChanged();
            }

        });
        this.changeButton.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/subject_16.png")).getImage()));
        this.changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeEntityDialog(Server.mainWindow, SubjectsDialog.this.tableModel.getEntity(SubjectsDialog.this.table.getSelectedRow()));
                SubjectsDialog.this.tableModel.fireTableDataChanged();
            }
        });
    }

    public static class SubjectsTableModel extends AbstractEntityDialog.AbstractEntityTableModel {
        public int getRowCount() {
            return Server.data.getSubjects().size();
        }

        public AbstractEntity getEntity(int row) {
            if (Server.data.getSubjects().size() > 0)
                return ((AbstractEntity) Server.data.getSubjects().get(row));

            return null;
        }
    }
}