import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddOrChangeSubGroupDialog extends JDialog {
    private JTextField[] textFields;

    public AddOrChangeSubGroupDialog(JFrame parent, final Group group, final ArrayList<SubGroup> subGroups) {
        super(parent, "");
        setLayout(null);

        int x = 20;
        int y = 20;
        JLabel[] labels = new JLabel[6];
        for (int i = 0; i < labels.length;) {
            labels[i] = new JLabel("Підгрупа " + (i + 1) + ":");
            labels[i].setBounds(x, y, 90, 25);
            add(labels[i]);

            ++i;
            y += 30;
        }

        x = 100;
        y = 20;
        textFields = new JTextField[6];
        for (int i = 0; i < textFields.length;) {
            textFields[i] = new JTextField();
            textFields[i].setBounds(x, y, 130, 25);
            textFields[i].setText((subGroups.size() > i) ? ((SubGroup) subGroups.get(i)).getName() : "");
            add(textFields[i]);

            ++i;
            y += 30;
        }

        JButton okButton = new JButton("OK");
        okButton.setBounds(35, 210, 70, 25);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                subGroups.clear();
                for (int i = 0; i < textFields.length; ++i)
                    if ((textFields[i].getText() != null) && (textFields[i].getText().trim().length() > 0)) {
                        SubGroup newSubGroup = new SubGroup(textFields[i].getText());
                        newSubGroup.setIndex(i);
                        subGroups.add(newSubGroup);
                    }

                group.getSubGroups().remove(subGroups);
                group.getSubGroups().add(subGroups);
                AddOrChangeSubGroupDialog.this.dispose();
            }

        });
        add(okButton);

        JButton cancelButton = new JButton("Відмінити");
        cancelButton.setBounds(115, 210, 100, 25);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddOrChangeSubGroupDialog.this.dispose();
            }

        });
        add(cancelButton);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 260;
        int height = 290;
        x = (screen.width - width) / 2;
        y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }
}