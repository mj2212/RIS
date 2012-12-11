import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private static ServerSocket ss;
    public static int port = 7001;
    public static MainWindow mainWindow;
    public static Data data = null;

    public static void main(String[] args) throws IOException {
//        LoginDialog loginDialog = new LoginDialog();
        mainWindow = new MainWindow();
        new Server().run();
    }

    public Server() throws IOException {
        ss = new ServerSocket(port);
    }

    void run() {
        while (true) {
            try {
                Socket s = ss.accept();
                new ServerThread(s).start();;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ServerThread extends Thread {
        Socket s;
        ObjectOutputStream oos;

        ServerThread(Socket socketParam) {
            s = socketParam;
            try {
                OutputStream os = s.getOutputStream();
                oos = new ObjectOutputStream(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                oos.writeObject(data);
                oos.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save(File file) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            data.setName(file.getName());
            oos.writeObject(data);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static File saveAs() {
        JFileChooser fileChooser = new JFileChooser();
        File file = null;
        int result = fileChooser.showSaveDialog(null);
        if (result == 0) {
            file = fileChooser.getSelectedFile();
            save(file);
        }
        return file;
    }

    public static int close() {
        if (data != null) {
            int result = JOptionPane.showConfirmDialog(mainWindow, "Зберегти зміни?", "Закриття документа", 1);
            if (result == 0) {
                saveAs();
                data = null;
                return 1;
            }
            if (result == 1) {
                data = null;
                return -1;
            }
        }
        return 0;
    }

    public static void exit() {
        if (close() != 0)
            System.exit(0);
    }
}