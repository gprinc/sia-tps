import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.linear.EigenDecomposition;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        String country;
        Double area;
        Double gdp;
        Double inflation;
        Double lifeExpect;
        Double military;
        Double popGrowth;
        Double unemployment;

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
                area = (c.getArea() - media.getArea()) / covarianza.getArea();
                gdp = (c.getGdp() - media.getGdp()) / covarianza.getGdp();
                inflation = (c.getInflation() - media.getInflation()) / covarianza.getInflation();
                lifeExpect = (c.getLifeExpect() - media.getLifeExpect()) / covarianza.getLifeExpect();
                military = (c.getMilitary() - media.getMilitary()) / covarianza.getMilitary();
                popGrowth = (c.getPopGrowth() - media.getPopGrowth()) / covarianza.getPopGrowth();
                unemployment = (c.getUnemployment() - media.getUnemployment()) / covarianza.getUnemployment();
                normalizeCountries.add(new Country(c.getCountry(),area,gdp,inflation,lifeExpect,military,popGrowth,unemployment));
            }

            double[][] countriesMatrix = new double[7][countries.size()];
            for (int i = 0; i < countries.size(); i++) {
                Country aux = countries.get(i);
                countriesMatrix[0][i] = aux.getArea();
                countriesMatrix[1][i] = aux.getGdp();
                countriesMatrix[2][i] = aux.getInflation();
                countriesMatrix[3][i] = aux.getLifeExpect();
                countriesMatrix[4][i] = aux.getMilitary();
                countriesMatrix[5][i] = aux.getPopGrowth();
                countriesMatrix[6][i] = aux.getUnemployment();
            }

            RealMatrix mx = MatrixUtils.createRealMatrix(countriesMatrix);
            RealMatrix cov = new Covariance(mx).getCovarianceMatrix();
            RealMatrix corr  = new PearsonsCorrelation(countriesMatrix).getCorrelationMatrix();
            /*double[][] covarianzaMatrix = new double[7][countries.size()];
            for (int i = 0; i < countries.size(); i++) {
                for (int j = 0; j < 7; j++) {
                    covarianzaMatrix[j][i] = fillMatrix(i, j, countriesMatrix, media, countries.size());
                }
            }*/


            plotData(countries, normalizeCountries);


            EigenDecomposition eigenDecomposition = new EigenDecomposition(mx);

            double[] eigenValues = eigenDecomposition.getRealEigenvalues();

            RealMatrix eigenVectors = eigenDecomposition.getV();

        }

        return;

    }

    private static double fillMatrix(int i, int k, double[][] matrix, Country media, int size) {
        double aux = 0;
        for (int j = 0; j < size; j++) {
            aux += (matrix[i][j] - media.getByNumber(i)) * (matrix[k][j] - media.getByNumber(k));
        }
        return aux / 7;
    }

    private static void plotData(ArrayList<Country> common, ArrayList<Country> normalize) {
        ArrayList<Double> areas = new ArrayList<>();
        ArrayList<Double> gdps = new ArrayList<>();
        ArrayList<Double> inflations = new ArrayList<>();
        ArrayList<Double> lifeExpects = new ArrayList<>();
        ArrayList<Double> militaries = new ArrayList<>();
        ArrayList<Double> popGrowths = new ArrayList<>();
        ArrayList<Double> unemployments = new ArrayList<>();
        for (Country c: common) {
            areas.add(c.getArea());
            gdps.add(c.getGdp());
            inflations.add(c.getInflation());
            lifeExpects.add(c.getLifeExpect());
            militaries.add(c.getMilitary());
            popGrowths.add(c.getPopGrowth());
            unemployments.add(c.getUnemployment());
        }

        BoxPlot.display(areas, gdps, inflations, lifeExpects, militaries, popGrowths, unemployments);

        areas = new ArrayList<>();
        gdps = new ArrayList<>();
        inflations = new ArrayList<>();
        lifeExpects = new ArrayList<>();
        militaries = new ArrayList<>();
        popGrowths = new ArrayList<>();
        unemployments = new ArrayList<>();
        for (Country c: normalize) {
            areas.add(c.getArea());
            gdps.add(c.getGdp());
            inflations.add(c.getInflation());
            lifeExpects.add(c.getLifeExpect());
            militaries.add(c.getMilitary());
            popGrowths.add(c.getPopGrowth());
            unemployments.add(c.getUnemployment());
        }

        BoxPlot.display(areas, gdps, inflations, lifeExpects, militaries, popGrowths, unemployments);
    }
}
