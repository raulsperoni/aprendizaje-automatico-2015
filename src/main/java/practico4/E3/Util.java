package practico4.E3;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by raul on 26/09/15.
 */
public class Util {

    public static double randDouble(double min, double max, Random rand) {

        double result = min + (rand.nextDouble() * (max - min));

        return result;
    }


    public static void Plot(HashMap<Integer, Double> errores, String nom, Integer indiceInicio, Integer indiceFin) throws Exception {
        final XYSeries err = new XYSeries("Errores");
        for (int i = indiceInicio; i < indiceFin; i++) {
            err.add((double) i, errores.get(i));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(err);


        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "E -" + nom,
                "Iteracion",
                "Error",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        int width = 1024; /* Width of the image */
        int height = 768; /* Height of the image */
        File XYChart = new File("ErrPlot" + nom + ".jpeg");
        ChartUtilities.saveChartAsJPEG(XYChart, xylineChart, width, height);
    }

    public static void Plot(List<Double> ejex, List<Double> ejeyexacto, List<Double> ejeyRed, String nom, int iteraciones, Class cls) throws Exception {
        final XYSeries exacto = new XYSeries("Exacto");
        for (int i = 0; i < ejex.size(); i++) {
            exacto.add(ejex.get(i), ejeyexacto.get(i));
        }

        final XYSeries red = new XYSeries("Red");
        for (int i = 0; i < ejex.size(); i++) {
            red.add(ejex.get(i), ejeyRed.get(i));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(exacto);
        dataset.addSeries(red);


        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "E -" + nom,
                "Iteracion",
                "Error",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        int width = 1024; /* Width of the image */
        int height = 768; /* Height of the image */
        File XYChart = new File("Func" + nom + iteraciones + cls.getName() + ".jpeg");
        ChartUtilities.saveChartAsJPEG(XYChart, xylineChart, width, height);
    }

}
