import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.MessageFormat;

public class ToolBar extends JPanel {
//    private JButton journalButton;
    private JButton teachersButton;
    private JButton roomsButton;
    private JButton groupsButton;
    private JButton subjectsButton;
    private JButton printButton;
    private JButton saveButton;
    private JButton saveAsButton;
    private JButton openButton;
    private JButton newButton;
    private JFileChooser fileChooser;
    private File file = null;
//    private JButton preferencesButton;

    public ToolBar() {
        setLayout(new FlowLayout(0, 5, 4));

        JPanel chiefPanel = new JPanel();
        chiefPanel.setBorder(BorderFactory.createEtchedBorder());

        this.newButton = new JButton("Новий", new ImageIcon(new ImageIcon(getClass().getResource("images/new.png")).getImage()));
        this.newButton.setVerticalTextPosition(3);
        this.newButton.setHorizontalTextPosition(0);
        this.newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Server.data = new Data();
                Server.data.setName("untitled");
                ToolBar.this.enablingControls();
                Server.mainWindow.refresh();
            }

        });
        this.newButton.setMargin(new Insets(2, 5, 2, 5));
        chiefPanel.add(this.newButton);

        this.fileChooser = new JFileChooser();
        this.fileChooser.setFileFilter(new TimeTablesFileFilter());

        this.openButton = new JButton("Відкрити", new ImageIcon(new ImageIcon(getClass().getResource("images/open_32.png")).getImage()));
        this.openButton.setVerticalTextPosition(3);
        this.openButton.setHorizontalTextPosition(0);
        this.openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = fileChooser.showOpenDialog(null);
                if (result == 0) {
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
                        Server.data = (Data) ois.readObject();
                        file = fileChooser.getSelectedFile();
                        enablingControls();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Помилка при відкритті файлу: " + ex.getMessage());
                        return;
                    }
                    Server.mainWindow.refresh();
                    //(ToolBar.this, ToolBar.this.fileChooser.getSelectedFile());
                }
            }

        });
        this.openButton.setMargin(new Insets(2, 5, 2, 5));
        chiefPanel.add(this.openButton);

        this.saveButton = new JButton("Зберегти", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/save_32.png")).getImage()));
        this.saveButton.setVerticalTextPosition(3);
        this.saveButton.setHorizontalTextPosition(0);
        this.saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (file != null)
                    Server.save(file);
                else
                    Server.saveAs();
                Server.mainWindow.refresh();
            }

        });
        this.saveButton.setMargin(new Insets(2, 5, 2, 5));
        this.saveButton.setEnabled(false);
        chiefPanel.add(this.saveButton);

        this.saveAsButton = new JButton("Зберегти Як", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/saveas_32.png")).getImage()));
        this.saveAsButton.setVerticalTextPosition(3);
        this.saveAsButton.setHorizontalTextPosition(0);
        this.saveAsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                file = Server.saveAs();
                Server.mainWindow.refresh();
            }

        });
        this.saveAsButton.setMargin(new Insets(2, 5, 2, 5));
        this.saveAsButton.setEnabled(false);
        chiefPanel.add(this.saveAsButton);

        this.printButton = new JButton("Друк", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/printer.png")).getImage()));
        this.printButton.setVerticalTextPosition(3);
        this.printButton.setHorizontalTextPosition(0);
        this.printButton.setMargin(new Insets(2, 5, 2, 5));
        this.printButton.setEnabled(false);
        printButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    MessageFormat headerFormat = new MessageFormat("Page {0}");
                    MessageFormat footerFormat = new MessageFormat("- {0} -");
                    Server.mainWindow.generalTable.print(JTable.PrintMode.FIT_WIDTH, null, footerFormat);
                } catch (PrinterException pe) {
                    JOptionPane.showMessageDialog(null, "Error printing: " + pe.getMessage());
                }
            }
        });
        chiefPanel.add(this.printButton);

        add(chiefPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createEtchedBorder());

        this.subjectsButton = new JButton("Предмети", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/subject.png")).getImage()));
        this.subjectsButton.setVerticalTextPosition(3);
        this.subjectsButton.setHorizontalTextPosition(0);
        this.subjectsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SubjectsDialog(Server.mainWindow);
            }

        });
        this.subjectsButton.setMargin(new Insets(2, 5, 2, 5));
        this.subjectsButton.setEnabled(false);
        controlPanel.add(this.subjectsButton);

        this.groupsButton = new JButton("Групи", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/class.png")).getImage()));
        this.groupsButton.setVerticalTextPosition(3);
        this.groupsButton.setHorizontalTextPosition(0);
        this.groupsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new GroupsDialog(Server.mainWindow);
            }

        });
        this.groupsButton.setMargin(new Insets(2, 5, 2, 5));
        this.groupsButton.setEnabled(false);
        controlPanel.add(this.groupsButton);

        this.roomsButton = new JButton("Кабінети", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/room.png")).getImage()));
        this.roomsButton.setVerticalTextPosition(3);
        this.roomsButton.setHorizontalTextPosition(0);
        this.roomsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RoomsDialog(Server.mainWindow);
            }

        });
        this.roomsButton.setMargin(new Insets(2, 5, 2, 5));
        this.roomsButton.setEnabled(false);
        controlPanel.add(this.roomsButton);

        this.teachersButton = new JButton("Вчителі", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/teacher.png")).getImage()));
        this.teachersButton.setVerticalTextPosition(3);
        this.teachersButton.setHorizontalTextPosition(0);
        this.teachersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TeachersDialog(Server.mainWindow);
            }
        });
        this.teachersButton.setMargin(new Insets(2, 5, 2, 5));
        this.teachersButton.setEnabled(false);
        controlPanel.add(this.teachersButton);

//        this.journalButton = new JButton("Журнал", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/text-x-log.png")).getImage()));
//        this.journalButton.setVerticalTextPosition(3);
//        this.journalButton.setHorizontalTextPosition(0);
//        this.journalButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
////                new JournalDialog(Server.mainWindow);
//            }
//        });
//        this.journalButton.setMargin(new Insets(2, 5, 2, 5));
//        this.journalButton.setEnabled(false);
//        controlPanel.add(this.journalButton);

        add(controlPanel);

//        preferencesButton = new JButton("Налаштування", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/settings_32.png")).getImage()));
//        preferencesButton.setVerticalTextPosition(3);
//        preferencesButton.setHorizontalTextPosition(0);
//        preferencesButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
////                new PreferencesDialog(Server.mainWindow);
//            }
//        });
//        preferencesButton.setMargin(new Insets(2, 5, 2, 5));
//        preferencesButton.setEnabled(false);
//        controlPanel.add(preferencesButton);
//
//        add(controlPanel);

        /*JPanel selectWiewPanel = new JPanel(new FlowLayout(0, 5, 7));
        selectWiewPanel.setBorder(BorderFactory.createEtchedBorder());
        selectWiewPanel.setPreferredSize(new Dimension(114, 76));

        this.viewComboBox = new JComboBox(new String[]{"Загальний", "Вчителі", "Кабінети"});
        this.viewComboBox.setPreferredSize(new Dimension(99, 25));
        this.viewComboBox.setEnabled(false);
        selectWiewPanel.add(this.viewComboBox);

        this.weeksComboBox = new JComboBox(new String[]{"Всі тижні", "1-й тиждень", "2-й тиждень"});
        this.weeksComboBox.setEnabled(false);
        selectWiewPanel.add(this.weeksComboBox);

        add(selectWiewPanel);*/

//        JPanel aboutPanel = new JPanel();
//        aboutPanel.setBorder(BorderFactory.createEtchedBorder());
//
//        JButton aboutButton = new JButton("Про програму", new ImageIcon(new ImageIcon(MainWindow.class.getResource("images/about.png")).getImage()));
//        aboutButton.setVerticalTextPosition(3);
//        aboutButton.setHorizontalTextPosition(0);
//        aboutButton.setMargin(new Insets(2, 5, 2, 5));
//        aboutButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                new AboutDialog(Server.mainWindow, true);
//            }
//        });
//        aboutPanel.add(aboutButton);
//
//        add(aboutPanel);
    }

    private void enablingControls() {
//        this.journalButton.setEnabled(true);
        this.teachersButton.setEnabled(true);
        this.roomsButton.setEnabled(true);
        this.groupsButton.setEnabled(true);
        this.subjectsButton.setEnabled(true);
        this.printButton.setEnabled(true);
        this.saveButton.setEnabled(true);
        this.saveAsButton.setEnabled(true);
        this.openButton.setEnabled(true);
        this.newButton.setEnabled(true);
//        preferencesButton.setEnabled(true);
//        this.viewComboBox.setEnabled(true);
//        this.weeksComboBox.setEnabled(true);
    }
}