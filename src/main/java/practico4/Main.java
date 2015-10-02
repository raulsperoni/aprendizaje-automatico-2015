package practico4;

import java.util.*;

/**
 * Created by RSperoni on 23/09/2015.
 */
public class Main {

    public static void main(String[] args) {

        //Ejercicio 3


        try {
            //f(x) = x
            //funcionIdentidad();

            //f(x) = x^4
            //funcionPotencia4();

            //f(x) = cos(7/2*pi*x)
            funcionCoseno();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void funcionIdentidad() throws Exception {
        List<List<Double>> entradas_funcion1 = new ArrayList<>();
        List<Double> salidas_esperadas_funcion1 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            double x = Util.randDouble(-1, 1, r);
            entradas_funcion1.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion1.add(i, x);
        }
        int iteraciones = 50000;
        Class cls = Tanh.class;
        RedNeuronal redNeuronal = new RedNeuronal<>(6, 1, 1, 0.1, iteraciones, cls, 0d);
        HashMap<Integer, Double> err = redNeuronal.backpropagation(entradas_funcion1, salidas_esperadas_funcion1);
        Double minErr = err.get(err.size() - 1);
        Util.Plot(err, "Indentidad: min:" + String.format("%.3f", minErr), 0, err.size());

        /**
         * Evaluacion
         */

        List<Double> entradasEval = new ArrayList<>();
        List<Double> salidasExactasEval = new ArrayList<>();
        List<Double> salidasRedEval = new ArrayList<>();
        r = new Random();
        for (int i = 0; i < 100; i++) {
            double x = Util.randDouble(-1, 1, r);
            entradasEval.add(i, x);
            //if (x < 0) x = -x;
            List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(x)));
            salidasExactasEval.add(i, x);
            double res = resultado.get(0);
            //if (x < 0)
            //    salidasRedEval.add(i,-res);
            //else
            salidasRedEval.add(i, res);
        }
        Util.Plot(entradasEval, salidasExactasEval, salidasRedEval, "Identidad", iteraciones, cls);


    }

    public static void funcionPotencia4() throws Exception {

        List<List<Double>> entradas_funcion = new ArrayList<>();
        List<Double> salidas_esperadas_funcion = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            double x = Util.randDouble(0, 1, r);
            entradas_funcion.add(i, new ArrayList<Double>(Arrays.asList(Math.abs(x))));
            salidas_esperadas_funcion.add(i, Math.pow(Math.abs(x), 4));
        }
        int iteraciones = 50000;
        Class cls = Sigmoid.class;
        RedNeuronal redNeuronal = new RedNeuronal(6, 1, 1, 0.1, iteraciones, cls, 0d);
        HashMap<Integer, Double> err = redNeuronal.backpropagation(entradas_funcion, salidas_esperadas_funcion);
        Double minErr = err.get(err.size() - 1);
        Util.Plot(err, "Potencia: min:" + String.format("%.3f", minErr), 0, err.size());


        /**
         * Evaluacion
         */

        List<Double> entradasEval = new ArrayList<>();
        List<Double> salidasExactasEval = new ArrayList<>();
        List<Double> salidasRedEval = new ArrayList<>();
        r = new Random();
        for (int i = 0; i < 100; i++) {
            double x = Util.randDouble(0, 1, r);
            entradasEval.add(i, x);
            List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(x)));
            salidasExactasEval.add(i, Math.pow(x, 4));
            double res = resultado.get(0);
            salidasRedEval.add(i, res);
        }
        Util.Plot(entradasEval, salidasExactasEval, salidasRedEval, "Potencia", iteraciones, cls);

    }

    public static void funcionCoseno() throws Exception {
        List<List<Double>> entradas_funcion = new ArrayList<>();
        List<Double> salidas_esperadas_funcion = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            double x = Util.randDouble(0, 3d / 7d, r);
            entradas_funcion.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion.add(i, (coseno(x)));
        }
        int iteraciones = 80000;
        Class cls = Sigmoid.class;
        RedNeuronal redNeuronal = new RedNeuronal(6, 1, 1, 0.1, iteraciones, cls, 0d);
        HashMap<Integer, Double> err = redNeuronal.backpropagation(entradas_funcion, salidas_esperadas_funcion);
        Double minErr = err.get(err.size() - 1);
        Util.Plot(err, "Coseno: min:" + String.format("%.3f", minErr), 0, err.size());


        //1 - (1/2!)x2 + (1/4!)x4 - (1/6!)x6
        /**
         * Evaluacion
         */

        List<Double> entradasEval = new ArrayList<>();
        List<Double> salidasExactasEval = new ArrayList<>();
        List<Double> salidasRedEval = new ArrayList<>();
        r = new Random();
        for (int i = 0; i < 100; i++) {
            double x = Util.randDouble(0, 1, r);
            entradasEval.add(i, x);
            List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(x)));
            salidasExactasEval.add(i, (coseno(x) * 2) - 1);
            double res = (resultado.get(0) * 2) - 1;
            salidasRedEval.add(i, res);
        }
        Util.Plot(entradasEval, salidasExactasEval, salidasRedEval, "Coseno", iteraciones, cls);



    }

    private static double coseno(double x) {
        x = (x * Math.PI * 7) / 2;
        return (Math.cos(x) + 1) / 2;
    }
}
