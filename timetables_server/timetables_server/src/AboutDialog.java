import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
    public AboutDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("About");
        initComponents();
        setSize(380,  300);
        setResizable(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screen.width/2 - getWidth()/2, screen.height/2 - getHeight()/2);
        jTextArea1.setBackground(jPanel2.getBackground());
        setVisible(true);
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

//        jPanel1.setLayout(new java.awt.BorderLayout());
//
//        jTextArea1.setEditable(false);
//        jTextArea1.setFont(new java.awt.Font("Dialog", 1, 12));
//        jTextArea1.setText("");
//        jTextArea1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
//        jPanel1.add(jTextArea1, java.awt.BorderLayout.CENTER);
//
//        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
//
//        jButton1.setText("Close");
//        jButton1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton1ActionPerformed(evt);
//            }
//        });
//
//        jPanel2.add(jButton1);
//
//        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        closeDialog(null);
    }

    private void closeDialog(java.awt.event.WindowEvent evt) {
        setVisible(false);
        dispose();
    }

    private JButton jButton1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JTextArea jTextArea1;
}
