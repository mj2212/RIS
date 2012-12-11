import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class AddOrChangeEntityDialog extends JDialog {
    private JTextField shortNameTextField;
    private JTextField nameTextField;
    private boolean isEdited = false;

    public AddOrChangeEntityDialog(JFrame parent, final AbstractEntity entity) {
        super(parent, "");
        setLayout(null);

        JLabel nameLabel = new JLabel("Назва:");
        nameLabel.setBounds(20, 20, 90, 25);
        add(nameLabel);

        JLabel shortNameLabel = new JLabel("Скорочення:");
        shortNameLabel.setBounds(20, 50, 90, 25);
        add(shortNameLabel);

        this.nameTextField = new JTextField();
        this.nameTextField.setBounds(100, 20, 130, 25);
        this.nameTextField.setText(entity.getName());
        nameTextField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e) {
                autoWrite();
            }

            public void removeUpdate(DocumentEvent e) {
                autoWrite();
            }

            public void changedUpdate(DocumentEvent e) {
                autoWrite();
            }

            private void autoWrite() {
                if(!isEdited) {
                    shortNameTextField.setText(nameTextField.getText());
                }
            }
        });
        add(this.nameTextField);

        this.shortNameTextField = new JTextField();
        this.shortNameTextField.setBounds(100, 50, 130, 25);
        this.shortNameTextField.setText(entity.getShortName());
        shortNameTextField.addFocusListener(new FocusAdapter(){
            public void focusGained(FocusEvent e) {
                isEdited = true;
            }
        });
        add(this.shortNameTextField);

        JButton okButton = new JButton("OK");
        okButton.setBounds(35, 90, 70, 25);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Vector rowHeaders;
                if ((AddOrChangeEntityDialog.this.nameTextField.getText().equals("")) || (AddOrChangeEntityDialog.this.shortNameTextField.getText().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Заповніть всі поля!");
                    return;
                }
                entity.setName(AddOrChangeEntityDialog.this.nameTextField.getText());
                entity.setShortName(AddOrChangeEntityDialog.this.shortNameTextField.getText());
                if (entity instanceof Group) {
                    ((GeneralTimetableModel) Server.mainWindow.generalTable.getModel()).remove((Group) entity);
                    ((GeneralTimetableModel) Server.mainWindow.generalTable.getModel()).add((Group) entity);
                    rowHeaders = new Vector();
                    for (Group g : Server.data.getGroups())
                        rowHeaders.add(g.getShortName());

                    Server.mainWindow.getGeneralRowHeader().setListData(rowHeaders);
                } else if (entity instanceof Room) {
                    ((RoomsTimetableModel) Server.mainWindow.roomsTable.getModel()).remove((Room) entity);
                    ((RoomsTimetableModel) Server.mainWindow.roomsTable.getModel()).add((Room) entity);
                    rowHeaders = new Vector();
                    for (Room r : Server.data.getRooms())
                        rowHeaders.add(r.getShortName());

                    Server.mainWindow.getRoomsRowHeader().setListData(rowHeaders);
                } else if (entity instanceof Subject) {
                    Server.data.getSubjects().remove((Subject) entity);
                    Server.data.getSubjects().add((Subject) entity);
                } else if (entity instanceof Teacher) {
                    ((TeachersTimetableModel) Server.mainWindow.teachersTable.getModel()).remove((Teacher) entity);
                    ((TeachersTimetableModel) Server.mainWindow.teachersTable.getModel()).add((Teacher) entity);
                    rowHeaders = new Vector();
                    for (Teacher t : Server.data.getTeachers())
                        rowHeaders.add(t.getShortName());

                    Server.mainWindow.getTeachersRowHeader().setListData(rowHeaders);
                }
                AddOrChangeEntityDialog.this.dispose();
            }

        });
        add(okButton);

        JButton cancelButton = new JButton("Відмінити");
        cancelButton.setBounds(115, 90, 100, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddOrChangeEntityDialog.this.dispose();
            }

        });
        add(cancelButton);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 260;
        int height = 160;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }
}