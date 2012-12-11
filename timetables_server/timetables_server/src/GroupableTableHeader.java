import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.Enumeration;
import java.util.Vector;

public class GroupableTableHeader extends JTableHeader {
    private static final String uiClassID = "GroupableTableHeaderUI";
    protected Vector columnGroups = null;

    public GroupableTableHeader(TableColumnModel model) {
        super(model);
        setUI(new GroupableTableHeaderUI());
        setReorderingAllowed(false);
    }

    public void updateUI() {
        setUI(new GroupableTableHeaderUI());
    }

    public void setReorderingAllowed(boolean b) {
        this.reorderingAllowed = false;
    }

    public void addColumnGroup(ColumnGroup g) {
        if (this.columnGroups == null)
            this.columnGroups = new Vector();

        this.columnGroups.addElement(g);
    }

    public Enumeration getColumnGroups(TableColumn col) {
        if (this.columnGroups == null) return null;
        Enumeration e = this.columnGroups.elements();
        while (e.hasMoreElements()) {
            ColumnGroup cGroup = (ColumnGroup) e.nextElement();
            Vector v_ret = cGroup.getColumnGroups(col, new Vector());
            if (v_ret != null)
                return v_ret.elements();
        }

        return null;
    }

    public void setColumnMargin() {
        ColumnGroup cGroup;
        if (this.columnGroups == null) return;
        int columnMargin = getColumnModel().getColumnMargin();
        Enumeration e = this.columnGroups.elements();
        while (e.hasMoreElements())
            cGroup = (ColumnGroup) e.nextElement();
    }
}