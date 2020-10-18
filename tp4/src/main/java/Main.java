import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class Main {
    static Hopfield trainingPattern;

    private final static String DEFAULT_EJ = "Kohonen";
    public final static String[] possibleEjs = {"Kohonen", "Oja", "Hopfield"};

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        JSONObject jsonData = null;
        try {
            jsonData = (JSONObject) parser.parse(new FileReader("config.json"));
            boolean correctEj = false;
            String ej = (String) jsonData.get("ej");
            for (String s: possibleEjs) {
                if (s.equals(ej)) {
                    correctEj = true;
                    break;
                }
            }
            if (ej == null || !correctEj)
                ej = DEFAULT_EJ;
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String country;
        Double area;
        Double gdp;
        Double inflation;
        Double lifeExpect;
        Double military;
        Double popGrowth;
        Double unemployment;
        /*
        for (int trainingPatterns = 0; trainingPatterns < sp.length; trainingPatterns++) {
            float[] storedPattern = Hopfield.getPattern(sp[trainingPatterns]);

            // Get training pattern and run the learning function.
            trainingPattern = new Hopfield(storedPattern.length);
            trainingPattern.learn(storedPattern);
        }

        for (int i = 0; i < ip.length; i++) {
            // Get the patterns into int array format
            float[] incompletePattern = Hopfield.getPattern(ip[i]);

            // Generate the network output.
            Hopfield.generateOutput(trainingPattern, incompletePattern);
        }
        */
        File csvFile = new File("europe.csv");
        BufferedReader csvReader;
        String[] data;
        ArrayList<Country> countries = new ArrayList<>();
        if (csvFile.isFile()) {
            try {
                csvReader = new BufferedReader(new FileReader("europe.csv"));
                String row;
                boolean isFirstLine = true;
                while ((row = csvReader.readLine()) != null) {
                    data = row.split(",");
                    if (isFirstLine) {
                        isFirstLine = false;
                    } else {
                        data = row.split(",");
                        country = data[0];
                        area = Double.parseDouble(data[1]);
                        gdp = Double.parseDouble(data[2]);
                        inflation = Double.parseDouble(data[3]);
                        lifeExpect = Double.parseDouble(data[4]);
                        military = Double.parseDouble(data[5]);
                        popGrowth = Double.parseDouble(data[6]);
                        unemployment = Double.parseDouble(data[7]);
                        countries.add(new Country(country, area, gdp, inflation, lifeExpect, military, popGrowth, unemployment));
                    }
                }
                csvReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Country media = new Country();
            for (Country c : countries) {
                media.setArea(media.getArea() + (c.getArea() / countries.size()));
                media.setGdp(media.getGdp() + (c.getGdp() / countries.size()));
                media.setInflation(media.getInflation() + (c.getInflation() / countries.size()));
                media.setLifeExpect(media.getLifeExpect() + (c.getLifeExpect() / countries.size()));
                media.setMilitary(media.getMilitary() + (c.getMilitary() / countries.size()));
                media.setPopGrowth(media.getPopGrowth() + (c.getPopGrowth() / countries.size()));
                media.setUnemployment(media.getUnemployment() + (c.getUnemployment() / countries.size()));
            }

            Country covarianza = new Country();
            for (Country c: countries) {
                covarianza.setArea(covarianza.getArea() + (Math.pow((c.getArea() - media.getArea()) , 2 ) / (countries.size() - 1)));
                covarianza.setGdp(covarianza.getGdp() + (Math.pow((c.getGdp() - media.getGdp()) , 2 ) / (countries.size() - 1)));
                covarianza.setInflation(covarianza.getInflation() + (Math.pow((c.getInflation() - media.getInflation()) , 2 ) / (countries.size() - 1)));
                covarianza.setLifeExpect(covarianza.getLifeExpect() + (Math.pow((c.getLifeExpect() - media.getLifeExpect()) , 2 ) / (countries.size() - 1)));
                covarianza.setMilitary(covarianza.getMilitary() + (Math.pow((c.getMilitary() - media.getMilitary()) , 2 ) / (countries.size() - 1)));
                covarianza.setPopGrowth(covarianza.getPopGrowth() + (Math.pow((c.getPopGrowth() - media.getPopGrowth()) , 2 ) / (countries.size() - 1)));
                covarianza.setUnemployment(covarianza.getUnemployment() + (Math.pow((c.getUnemployment() - media.getUnemployment()) , 2 ) / (countries.size() - 1)));
            }

            ArrayList<Country> normalizeCountries = new ArrayList<>();
            for (Country c : countries) {
                area = (c.getArea() - media.getArea()) / Math.sqrt(covarianza.getArea());
                gdp = (c.getGdp() - media.getGdp()) / Math.sqrt(covarianza.getGdp());
                inflation = (c.getInflation() - media.getInflation()) / Math.sqrt(covarianza.getInflation());
                lifeExpect = (c.getLifeExpect() - media.getLifeExpect()) / Math.sqrt(covarianza.getLifeExpect());
                military = (c.getMilitary() - media.getMilitary()) / Math.sqrt(covarianza.getMilitary());
                popGrowth = (c.getPopGrowth() - media.getPopGrowth()) / Math.sqrt(covarianza.getPopGrowth());
                unemployment = (c.getUnemployment() - media.getUnemployment()) / Math.sqrt(covarianza.getUnemployment());
                normalizeCountries.add(new Country(c.getCountry(),area,gdp,inflation,lifeExpect,military,popGrowth,unemployment));
            }

            double[][] countriesMatrix = new double[countries.size()][7];
            for (int i = 0; i < countries.size(); i++) {
                Country aux = countries.get(i);
                countriesMatrix[i][0] = aux.getArea();
                countriesMatrix[i][1] = aux.getGdp();
                countriesMatrix[i][2] = aux.getInflation();
                countriesMatrix[i][3] = aux.getLifeExpect();
                countriesMatrix[i][4] = aux.getMilitary();
                countriesMatrix[i][5] = aux.getPopGrowth();
                countriesMatrix[i][6] = aux.getUnemployment();
            }

            double[][] normalizedMatrix = new double[countries.size()][7];
            for (int i = 0; i < normalizeCountries.size(); i++) {
                Country aux = normalizeCountries.get(i);
                normalizedMatrix[i][0] = aux.getArea();
                normalizedMatrix[i][1] = aux.getGdp();
                normalizedMatrix[i][2] = aux.getInflation();
                normalizedMatrix[i][3] = aux.getLifeExpect();
                normalizedMatrix[i][4] = aux.getMilitary();
                normalizedMatrix[i][5] = aux.getPopGrowth();
                normalizedMatrix[i][6] = aux.getUnemployment();
            }

            RealMatrix mx = MatrixUtils.createRealMatrix(countriesMatrix);
            RealMatrix cov = new Covariance(mx).getCovarianceMatrix();
            RealMatrix corr  = new PearsonsCorrelation(countriesMatrix).getCorrelationMatrix();


            // Autovaloes y Autovectores
            EigenDecomposition eigenDecompositionCov = new EigenDecomposition(cov);
            double[] eigenValuesCov = eigenDecompositionCov.getRealEigenvalues();
            RealMatrix eigenVectorsCov = eigenDecompositionCov.getV();

            EigenDecomposition eigenDecompositionCorr = new EigenDecomposition(corr);
            double[] eigenValuesCorr = eigenDecompositionCorr.getRealEigenvalues();
            RealMatrix eigenVectorsCorr = eigenDecompositionCorr.getV();

            // Componentes Principales
            //PCAejs.showPCA(MatrixUtils.createRealMatrix(normalizedMatrix));

            double[][] normalizedMatrixT = MatrixUtils.createRealMatrix(normalizedMatrix).transpose().getData();

            LinealPerceptronOja oja = new LinealPerceptronOja(normalizedMatrix, 0.001);
            oja.train(1000);
        }

        return;

    }

    private static void printRealMatrix(RealMatrix mx){
        for (int i = 0; i < mx.getRowDimension(); i++) {
            for (int j = 0; j < mx.getColumnDimension(); j++) {
                System.out.print(mx.getEntry(i,j) + " ");
            }
            System.out.println();
        }
    }

    private static double fillMatrix(int i, int k, double[][] matrix, Country media, int size) {
        double aux = 0;
        for (int j = 0; j < size; j++) {
            aux += (matrix[i][j] - media.getByNumber(i)) * (matrix[k][j] - media.getByNumber(k));
        }
        return aux / 7;
    }
}
