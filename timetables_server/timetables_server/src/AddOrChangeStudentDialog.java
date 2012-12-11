import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Vector;

public class AddOrChangeStudentDialog extends JDialog {
    private JTextField nameTextField;

    public AddOrChangeStudentDialog(JFrame parent, final Student student) {
        super(parent, "");
        setLayout(null);

        JLabel nameLabel = new JLabel("Назва:");
        nameLabel.setBounds(20, 20, 90, 25);
        add(nameLabel);

        this.nameTextField = new JTextField();
        this.nameTextField.setBounds(100, 20, 130, 25);
        this.nameTextField.setText(student.getName());
        add(this.nameTextField);

        JButton okButton = new JButton("OK");
        okButton.setBounds(35, 50, 70, 25);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Vector rowHeaders;
                if ((AddOrChangeStudentDialog.this.nameTextField.getText().equals(""))) {
                    JOptionPane.showMessageDialog(null, "Заповніть всі поля!");
                    return;
                }
                student.setName(nameTextField.getText());
                student.getGroup().removeStudent(student);
                student.getGroup().addStudent(student);
                AddOrChangeStudentDialog.this.dispose();
            }

        });
        add(okButton);

        JButton cancelButton = new JButton("Відмінити");
        cancelButton.setBounds(115, 50, 100, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddOrChangeStudentDialog.this.dispose();
            }

        });
        add(cancelButton);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 260;
        int height = 120;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }
}