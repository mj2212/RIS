import javax.swing.*;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class GroupableTableHeaderUI extends BasicTableHeaderUI {
    public void paint(Graphics g, JComponent c) {
        Rectangle clipBounds = g.getClipBounds();
        if (this.header.getColumnModel() == null) return;

        int column = 0;
        Dimension size = this.header.getSize();
        Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
        Hashtable h = new Hashtable();

        Enumeration enumeration = this.header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            cellRect.height = size.height;
            cellRect.y = 0;
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            Enumeration cGroups = ((GroupableTableHeader) this.header).getColumnGroups(aColumn);
            if (cGroups != null) {
                int groupHeight = 0;
                while (cGroups.hasMoreElements()) {
                    ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
                    Rectangle groupRect = (Rectangle) h.get(cGroup);
                    if (groupRect == null) {
                        groupRect = new Rectangle(cellRect);
                        Dimension d = cGroup.getSize(this.header.getTable());
                        groupRect.width = d.width;
                        groupRect.height = d.height;
                        h.put(cGroup, groupRect);
                    }
                    paintCell(g, groupRect, cGroup);
                    groupHeight += groupRect.height;
                    cellRect.height = (size.height - groupHeight);
                    cellRect.y = groupHeight;
                }
            }
            cellRect.width = aColumn.getWidth();
            if (cellRect.intersects(clipBounds))
                paintCell(g, cellRect, column);

            cellRect.x += cellRect.width;
            ++column;
        }
    }

    private void paintCell(Graphics g, Rectangle cellRect, final int columnIndex) {
        TableColumn aColumn = this.header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer renderer = aColumn.getHeaderRenderer();

        renderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel header = new JLabel();
                header.setForeground(table.getTableHeader().getForeground());
                if (columnIndex == Server.mainWindow.getActualColumnIndex())
                    header.setBackground(Color.yellow);
                else
                    header.setBackground(table.getTableHeader().getBackground());

                header.setFont(table.getTableHeader().getFont());
                header.setOpaque(true);
                header.setHorizontalAlignment(0);
                header.setText(value.toString());
                header.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                return header;
            }

        };
        Component c = renderer.getTableCellRendererComponent(this.header.getTable(), aColumn.getHeaderValue(), false, false, -1, columnIndex);

        this.rendererPane.add(c);
        this.rendererPane.paintComponent(g, c, this.header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
    }

    private void paintCell(Graphics g, Rectangle cellRect, ColumnGroup cGroup) {
        TableCellRenderer renderer = cGroup.getHeaderRenderer();
        Component component = renderer.getTableCellRendererComponent(this.header.getTable(), cGroup.getHeaderValue(), false, false, -1, -1);

        this.rendererPane.add(component);
        this.rendererPane.paintComponent(g, component, this.header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
    }

    private int getHeaderHeight() {
        int height = 0;
        TableColumnModel columnModel = this.header.getColumnModel();
        for (int column = 0; column < columnModel.getColumnCount(); ++column) {
            TableColumn aColumn = columnModel.getColumn(column);
            TableCellRenderer renderer = aColumn.getHeaderRenderer();
            if (renderer == null) {
                return 30;
            }

            Component comp = renderer.getTableCellRendererComponent(this.header.getTable(), aColumn.getHeaderValue(), false, false, -1, column);

            int cHeight = comp.getPreferredSize().height;
            Enumeration e = ((GroupableTableHeader) this.header).getColumnGroups(aColumn);
            while ((e != null) &&
                    (e.hasMoreElements())) {
                ColumnGroup cGroup = (ColumnGroup) e.nextElement();
                cHeight += cGroup.getSize(this.header.getTable()).height;
            }

            height = Math.max(height, cHeight);
        }
        return height;
    }

    private Dimension createHeaderSize(long width) {
        TableColumnModel columnModel = this.header.getColumnModel();

        if (width > 2147483647L)
            width = 2147483647L;

        return new Dimension((int) width, getHeaderHeight());
    }

    public Dimension getPreferredSize(JComponent c) {
        long width = 0L;
        Enumeration enumeration = this.header.getColumnModel().getColumns();
        while (enumeration.hasMoreElements()) {
            TableColumn aColumn = (TableColumn) enumeration.nextElement();
            width += aColumn.getPreferredWidth();
        }
        return createHeaderSize(width);
    }
}