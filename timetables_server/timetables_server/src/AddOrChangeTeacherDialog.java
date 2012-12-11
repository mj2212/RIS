import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Random;
import java.util.Vector;

public class AddOrChangeTeacherDialog extends JDialog {
    private JTextField shortNameTextField;
    private JTextField nameTextField;
    private JPanel colorPanel;
    private boolean isEdited = false;

    public AddOrChangeTeacherDialog(JFrame parent, final Teacher teacher) {
        super(parent, "");
        setLayout(null);

        JLabel nameLabel = new JLabel("Назва:");
        nameLabel.setBounds(20, 20, 90, 25);
        add(nameLabel);

        JLabel shortNameLabel = new JLabel("Скорочення:");
        shortNameLabel.setBounds(20, 50, 90, 25);
        shortNameLabel.setBackground(Color.blue);
        add(shortNameLabel);

        this.nameTextField = new JTextField();
        this.nameTextField.setBounds(100, 20, 130, 25);
        this.nameTextField.setText(teacher.getName());
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
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
                if (!isEdited) {
                    shortNameTextField.setText(nameTextField.getText());
                }
            }
        });
        add(this.nameTextField);

        this.shortNameTextField = new JTextField();
        this.shortNameTextField.setBounds(100, 50, 130, 25);
        this.shortNameTextField.setText(teacher.getShortName());
        shortNameTextField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                isEdited = true;
            }
        });
        add(this.shortNameTextField);

        this.colorPanel = new JPanel();
        this.colorPanel.setBounds(20, 80, 70, 25);
        Random r = new Random();
        this.colorPanel.setBackground(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
        add(this.colorPanel);

        JButton colorButton = new JButton("Вибрати колір");
        colorButton.setBounds(100, 80, 130, 25);
        colorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(Server.mainWindow, "Choose a color...", AddOrChangeTeacherDialog.this.colorPanel.getBackground());

                if (c != null)
                    AddOrChangeTeacherDialog.this.colorPanel.setBackground(c);
            }

        });
        add(colorButton);

        JButton okButton = new JButton("OK");
        okButton.setBounds(35, 120, 70, 25);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ((AddOrChangeTeacherDialog.this.nameTextField.getText().equals("")) || (AddOrChangeTeacherDialog.this.shortNameTextField.getText().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Заповніть всі поля!");
                    return;
                }
                teacher.setName(AddOrChangeTeacherDialog.this.nameTextField.getText());
                teacher.setShortName(AddOrChangeTeacherDialog.this.shortNameTextField.getText());
                teacher.setColor(AddOrChangeTeacherDialog.this.colorPanel.getBackground());
                ((TeachersTimetableModel) Server.mainWindow.teachersTable.getModel()).remove(teacher);
                ((TeachersTimetableModel) Server.mainWindow.teachersTable.getModel()).add(teacher);
                Vector rowHeaders = new Vector();
                for (Teacher t : Server.data.getTeachers())
                    rowHeaders.add(t.getShortName());

                Server.mainWindow.getTeachersRowHeader().setListData(rowHeaders);
                AddOrChangeTeacherDialog.this.dispose();
            }

        });
        add(okButton);

        JButton cancelButton = new JButton("Відмінити");
        cancelButton.setBounds(115, 120, 100, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddOrChangeTeacherDialog.this.dispose();
            }

        });
        add(cancelButton);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 260;
        int height = 190;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }
}