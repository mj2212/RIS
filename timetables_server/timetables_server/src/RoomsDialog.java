import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RoomsDialog extends AbstractEntityDialog {
    public RoomsDialog(JFrame parent) {
        super(parent, "Кабінети");
    }

    void before() {
        this.tableModel = new RoomsTableModel();
        this.table = new JTable(this.tableModel);
    }

    void after() {
        this.newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeEntityDialog(Server.mainWindow, new Room());
                RoomsDialog.this.tableModel.fireTableDataChanged();
            }

        });
        this.changeButton.setIcon(new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/room_16.png")).getImage()));
        this.changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeEntityDialog(Server.mainWindow, RoomsDialog.this.tableModel.getEntity(RoomsDialog.this.table.getSelectedRow()));
                RoomsDialog.this.tableModel.fireTableDataChanged();
            }
        });
    }

    public static class RoomsTableModel extends AbstractEntityDialog.AbstractEntityTableModel {
        public int getRowCount() {
            return Server.data.getRooms().size();
        }

        public AbstractEntity getEntity(int row) {
            if (Server.data.getRooms().size() > 0)
                return ((AbstractEntity) Server.data.getRooms().get(row));

            return null;
        }
    }
}