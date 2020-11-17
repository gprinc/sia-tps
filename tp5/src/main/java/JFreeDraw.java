import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.*;

public class JFreeDraw extends JFrame {

    private static double[] empty = {0};

    public JFreeDraw(double[] data1, double[] data2) {
        initUI(data1, data2);
    }

    public JFreeDraw(double[][] data) {
        /*for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }*/
        if (data.length > 1) initUI(data[0], data[1]);
        else initUI(empty, empty);
    }

    private void initUI(double[] data1, double[] data2) {
        XYDataset dataset = createDataset(data1, data2);
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);

        pack();
        setTitle("Neurons");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private XYDataset createDataset(double[] data1, double[] data2) {

        XYSeries series = new XYSeries("Layer");

        for (int i = 0; i < data1.length && i < data2.length; i++) {
            series.add(data1[i], data2[i]);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createScatterPlot(
                "TP5",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("TP5",
                        new Font("Serif", java.awt.Font.BOLD, 18)
                )
        );

        return chart;
    }

    /*public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            JFreeDraw ex = new JFreeDraw();
            ex.setVisible(true);
        });
    }*/
}