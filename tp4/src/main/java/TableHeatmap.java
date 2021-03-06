import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableHeatmap {

    public static void showHeatmap(int[][] data) {
        JFrame frame = new JFrame();
        // create a demo table 10 x 10 cells
        int size = data[0].length;
        JTable table = new JTable(size, size);
        frame.setContentPane(table);

        // fill in some random data
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                table.setValueAt(data[row][col], row, col);
            }
        }

        // set our custom TableCellRenderer
        table.setDefaultRenderer(Object.class, (TableCellRenderer) new HeatmapCellRenderer());
        table.setRowHeight(600 / data.length);

        // show the window
        frame.pack();
        frame.setVisible(true);
    }

    private static class HeatmapCellRenderer implements TableCellRenderer {

        private final DotRenderer renderer = new DotRenderer();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Integer) {
                this.renderer.setValue((Integer) value);
                return this.renderer;
            }
            return null;
        }
    }

    private static class DotRenderer extends JComponent {
        private int value;

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.RED);
            int centerX = this.getWidth() / 2;
            int centerY = this.getHeight() / 2;
            g.fillOval(centerX - this.value, centerY - this.value, this.value * 2, this.value * 2);
        }

    }

}