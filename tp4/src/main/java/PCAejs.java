import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;

public class PCAejs {
    public static void showPCA(RealMatrix mx) {
        SparkConf conf = new SparkConf().setAppName("PCA").setMaster("local[2]").set("spark.executor.memory","1g");
        SparkContext sc = new SparkContext(conf);
        JavaSparkContext jsc = JavaSparkContext.fromSparkContext(sc);

        List<Vector> dataAux = new ArrayList<>();
        for (int i = 0; i < mx.getColumnDimension(); i++) {
            double[] aux = mx.getColumn(i);
            dataAux.add(Vectors.dense(aux));
        }

        /*List<Vector> data = Arrays.asList(
                Vectors.dense(2.0, 0.0, 3.0, 4.0, 5.0),
                Vectors.dense(4.0, 0.0, 0.0, 6.0, 7.0)
        );*/

        JavaRDD<Vector> rows = jsc.parallelize(dataAux);

        // Create a RowMatrix from JavaRDD<Vector>.
        RowMatrix mat = new RowMatrix(rows.rdd());

        // Compute the top 4 principal components.
        // Principal components are stored in a local dense matrix.
        Matrix pc = mat.computePrincipalComponents(1);

        // Project the rows to the linear space spanned by the top 4 principal components.
        RowMatrix projected = mat.multiply(pc);
        // $example off$
        Vector[] collectPartitions = (Vector[])projected.rows().collect();
        System.out.println("Projected vector of principal component:");
        for (Vector vector : collectPartitions) {
            System.out.println("\t" + vector.toArray()[0]/10);
        }
        jsc.stop();
    }

    private static void printRealMatrix(RealMatrix mx, List<Vector> data){
        for (int i = 0; i < mx.getRowDimension(); i++) {
            for (int j = 0; j < mx.getColumnDimension(); j++) {
                mx.getEntry(i,j);
            }
        }
    }
}
