import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SubGroupsDialog extends JDialog {
    public SubGroupsDialog(JFrame parent, final Group group) {
        super(parent, "Підгрупи");
        setLayout(new BorderLayout());

        final JList list = new JList(group.getSubGroups().toArray());
        list.setCellRenderer(new SubGroupsCellRenderer());
        list.setSelectionMode(0);
        JScrollPane scrollPane = new JScrollPane(list);
        list.setSelectedIndex(0);
        add(scrollPane, "Center");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(null);
        controlPanel.setPreferredSize(new Dimension(140, 10));

        JButton newButton = new JButton("Додати", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/add_16.png")).getImage()));
        newButton.setBounds(10, 20, 120, 25);
        newButton.setHorizontalAlignment(2);
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeSubGroupDialog(Server.mainWindow, group, new ArrayList<SubGroup>());
                list.setListData(group.getSubGroups().toArray());
            }

        });
        controlPanel.add(newButton);

        JButton changeButton = new JButton("Змінити", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/class_16.png")).getImage()));
        changeButton.setBounds(10, 50, 120, 25);
        changeButton.setHorizontalAlignment(2);
        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AddOrChangeSubGroupDialog(Server.mainWindow, group, (ArrayList<SubGroup>) list.getSelectedValue());
                list.setListData(group.getSubGroups().toArray());
            }
        });
        controlPanel.add(changeButton);

        JButton removeButton = new JButton("Видалити", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/del_16.png")).getImage()));
        removeButton.setBounds(10, 80, 120, 25);
        removeButton.setHorizontalAlignment(2);
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                group.getSubGroups().remove(list.getSelectedValue());
                list.setListData(group.getSubGroups().toArray());
            }
        });
        controlPanel.add(removeButton);

        add(controlPanel, "East");

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 450;
        int height = 250;
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);

        setDefaultCloseOperation(2);
        setModal(true);
        setVisible(true);
    }

    public static class SubGroupsCellRenderer extends JPanel implements ListCellRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            ArrayList<SubGroup> subGroups = (ArrayList<SubGroup>) value;
            setLayout(new BorderLayout());
            JPanel chiefPanel = new JPanel(new GridLayout(1, subGroups.size() + 2, 5, 5));
            if (isSelected)
                chiefPanel.setBackground(new Color(0, 0, 128));
            else
                chiefPanel.setBackground(Color.white);

            removeAll();
            for (SubGroup subGroup : subGroups) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setPreferredSize(new Dimension(getWidth() - 10, 40));
                panel.setBackground(UIManager.getColor("control"));
                JLabel label = new JLabel(subGroup.getName());
                label.setHorizontalAlignment(0);
                panel.add(label);
                chiefPanel.add(panel);
            }
            add(chiefPanel, "Center");
            add(createSeparator(isSelected), "North");
            add(createSeparator(isSelected), "South");
            add(createSeparator(isSelected), "East");
            add(createSeparator(isSelected), "West");
            return this;
        }

        private JPanel createSeparator(boolean isSelected) {
            JPanel separator = new JPanel();
            separator.setPreferredSize(new Dimension(5, 5));

            if (isSelected)
                separator.setBackground(new Color(0, 0, 128));
            else
                separator.setBackground(Color.white);

            return separator;
        }
    }
}