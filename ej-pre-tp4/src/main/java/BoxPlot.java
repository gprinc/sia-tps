import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
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

        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart(
                "BoxPlot Chart", ROW_KEY, "Data", data, false);

        f.add(new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(640, 480);
            }
        });
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}