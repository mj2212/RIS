import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class EntityTableColumnModel extends DefaultTableColumnModel {
    public EntityTableColumnModel() {
        TableColumn column = new TableColumn();
        column.setModelIndex(0);
        column.setHeaderValue("Назва");
        column.setPreferredWidth(10);
        addColumn(column);

        column = new TableColumn();
        column.setModelIndex(1);
        column.setHeaderValue("Скорочення");
        column.setPreferredWidth(10);
        addColumn(column);

        column = new TableColumn();
        column.setModelIndex(2);
        column.setHeaderValue("Кількість уроків на тиждень");
        column.setPreferredWidth(10);
        addColumn(column);
    }
}