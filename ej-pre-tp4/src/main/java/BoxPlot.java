import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
        data.add(areas, ROW_KEY, "Areas");
        data.add(gdps, ROW_KEY, "GDP");
        data.add(inflations, ROW_KEY, "Inflation");
        data.add(lifeExpects, ROW_KEY, "Life Expect");
        data.add(militaries, ROW_KEY, "Military");
        data.add(popGrowths, ROW_KEY, "Pop Growth");
        data.add(unemployments, ROW_KEY, "Unemployment");

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