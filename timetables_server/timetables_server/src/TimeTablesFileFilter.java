import javax.swing.filechooser.FileFilter;
import java.io.File;

public class TimeTablesFileFilter extends FileFilter {
    public boolean accept(File file) {
        if (file.getName().endsWith(".tmt")) return true;
        return (file.isDirectory());
    }

    public String getDescription() {
        return "Файли з розкладом занять";
    }
}