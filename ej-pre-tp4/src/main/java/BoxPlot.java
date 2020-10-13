import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

public class BoxPlot {

    private static final String ROW_KEY = "Countries";

    public static void display(ArrayList<Double> areas, ArrayList<Double> gdps, ArrayList<Double> inflations, ArrayList<Double> lifeExpects, ArrayList<Double> militaries, ArrayList<Double> popGrowths, ArrayList<Double> unemployments) {
        JFrame f = new JFrame("BoxPlot");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DefaultBoxAndWhiskerCategoryDataset data = new DefaultBoxAndWhiskerCategoryDataset();
        data.add(areas, "Area", "Areas");
        data.add(gdps, "GDP", "GDP");
        data.add(inflations, "Inflation", "Inflation");
        data.add(lifeExpects, "Life Expect", "Life Expect");
        data.add(militaries, "Military", "Military");
        data.add(popGrowths, "Pop Growth", "Pop Growth");
        data.add(unemployments, "Unemployment", "Unemployment");

        final CategoryAxis xAxis = new CategoryAxis("Columns");
        final NumberAxis yAxis = new NumberAxis("Values");
        yAxis.setAutoRangeIncludesZero(false);
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(true);
        renderer.setSeriesToolTipGenerator(1, new BoxAndWhiskerToolTipGenerator());
        renderer.setMeanVisible(false);
        final CategoryPlot plot = new CategoryPlot(data, xAxis, yAxis, renderer);
        final JFreeChart chart = new JFreeChart(
                "Countries",
                plot
        );
        f.add(new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(1000, 600);
            }
        });
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }
}