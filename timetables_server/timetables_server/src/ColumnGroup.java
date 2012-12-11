import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Enumeration;
import java.util.Vector;

public class ColumnGroup {
    protected TableCellRenderer renderer;
    protected Vector v;
    protected String text;
    protected int margin;

    public ColumnGroup(String text) {
        this(null, text);
    }

    public ColumnGroup(TableCellRenderer renderer, String text) {
        this.margin = 0;

        if (renderer == null)
            this.renderer = new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JTableHeader header = table.getTableHeader();
                    if (header != null) {
                        setForeground(header.getForeground());
                        setBackground(header.getBackground());
                        setFont(header.getFont());
                    }
                    setHorizontalAlignment(0);
                    setText((value == null) ? "ihg" : value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    return this;
                }
            };
        else
            this.renderer = renderer;

        this.text = text;
        this.v = new Vector();
    }

    public void add(Object obj) {
        if (obj == null)
            return;

        this.v.addElement(obj);
    }

    public Vector getColumnGroups(TableColumn c, Vector g) {
        g.addElement(this);
        if (this.v.contains(c)) return g;
        Enumeration e = this.v.elements();
        while (e.hasMoreElements()) {
            Object obj = e.nextElement();
            if (obj instanceof ColumnGroup) {
                Vector groups = ((ColumnGroup) obj).getColumnGroups(c, (Vector) g.clone());

                if (groups != null) return groups;
            }
        }
        return null;
    }

    public TableCellRenderer getHeaderRenderer() {
        return this.renderer;
    }

    public void setHeaderRenderer(TableCellRenderer renderer) {
        if (renderer != null)
            this.renderer = renderer;
    }

    public Object getHeaderValue() {
        return this.text;
    }

    public Dimension getSize(JTable table) {
        Component comp = this.renderer.getTableCellRendererComponent(table, getHeaderValue(), false, false, -1, -1);

        int height = comp.getPreferredSize().height;
        int width = 0;
        Enumeration e = this.v.elements();
        while (e.hasMoreElements()) {
            Object obj = e.nextElement();
            if (obj instanceof TableColumn) {
                TableColumn aColumn = (TableColumn) obj;
                width += aColumn.getWidth();
            } else {
                width += ((ColumnGroup) obj).getSize(table).width;
            }
        }
        return new Dimension(width, height);
    }
}