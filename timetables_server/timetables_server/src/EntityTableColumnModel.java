import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class EntityTableColumnModel extends DefaultTableColumnModel {
    public EntityTableColumnModel() {
        TableColumn column = new TableColumn();
        column.setModelIndex(0);
        column.setHeaderValue("�����");
        column.setPreferredWidth(10);
        addColumn(column);

        column = new TableColumn();
        column.setModelIndex(1);
        column.setHeaderValue("����������");
        column.setPreferredWidth(10);
        addColumn(column);

        column = new TableColumn();
        column.setModelIndex(2);
        column.setHeaderValue("ʳ������ ����� �� �������");
        column.setPreferredWidth(10);
        addColumn(column);
    }
}