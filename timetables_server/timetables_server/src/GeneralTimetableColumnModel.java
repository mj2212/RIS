import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;

public class GeneralTimetableColumnModel extends DefaultTableColumnModel {
    //public static final int COLUMN_COUNT = 7;
    public static final ColumnGroup[] COLUMN_GROUPS = {
            new ColumnGroup("Понеділок"),
            new ColumnGroup("Вівторок"),
            new ColumnGroup("Середа"),
            new ColumnGroup("Четвер"),
            new ColumnGroup("Пятниця"),
            new ColumnGroup("Субота"),
            new ColumnGroup("Неділя")};

    public GeneralTimetableColumnModel(GroupableTableHeader header) {
        for (int i = 0; i < Server.data.getSchool().getDaysOfWeek(); i++) {
            for (int j = 0; j < Server.data.getSchool().getLessonsByDay(); j++) {
                TableColumn column = new TableColumn();
                column.setModelIndex(i * Server.data.getSchool().getLessonsByDay() + j);
                column.setHeaderValue(String.valueOf(j + 1));
                column.setPreferredWidth(32);
                column.setCellRenderer(new MainWindow.LessonsTableCellRenderer());

                addColumn(column);
                COLUMN_GROUPS[i].add(column);
            }
            header.addColumnGroup(COLUMN_GROUPS[i]);
        }
    }

    public class LessonsTableHeaderRenderer extends JLabel implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                setText((String) value);
                setBackground(Color.cyan);
                setPreferredSize(new Dimension(32, 32));
                return this;
            }
            return null;
        }
    }
}