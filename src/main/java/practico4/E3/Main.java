package practico4.E3;

import practico4.RedNeuronal;
import practico4.Sigmoid;
import practico4.Tanh;

import java.util.*;

/**
 * Created by RSperoni on 23/09/2015.
 */
public class Main {

    public static void main(String[] args) {

        //valores por defecto
        int funciones = 4;
        int iteraciones = 10000;
        int cantHidden = 10;
        if (args.length > 2) {
            funciones = Integer.valueOf(args[0]);
            iteraciones = Integer.valueOf(args[1]);
            cantHidden = Integer.valueOf(args[2]);
        } else if (args.length > 1) {
            funciones = Integer.valueOf(args[0]);
            iteraciones = Integer.valueOf(args[1]);
        } else if (args.length > 0) {
            funciones = Integer.valueOf(args[0]);
        }
        String fu = funciones < 4 ? String.valueOf(funciones) : "TODAS";
        System.out.println("Comienzo, Iteraciones=" + iteraciones + " Unidades Hidden=" + cantHidden + " Funcion(es): " + fu);

        //Ejercicio 3
        try {
            //f(x) = x
            if (funciones == 1 || funciones > 3) {
                System.out.println("... Entrenando para Identidad ...");
                funcionIdentidad(iteraciones, Tanh.class, cantHidden);
            }

            //f(x) = x^4
            if (funciones == 2 || funciones > 3) {
                System.out.println("... Entrenando para Potencia ...");
                funcionPotencia4(iteraciones, Sigmoid.class, cantHidden);
            }

            //f(x) = cos(7/2*pi*x)
            if (funciones >= 3) {
                System.out.println("... Entrenando para Coseno ...");
                funcionCoseno(iteraciones, Sigmoid.class, cantHidden);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void funcionIdentidad(int iteraciones, Class cls, int cantHidden) throws Exception {
        List<List<Double>> entradas_funcion1 = new ArrayList<>();
        List<Double> salidas_esperadas_funcion1 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            double x = Util.randDouble(-1, 1, r);
            entradas_funcion1.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion1.add(i, x);
        }
        RedNeuronal redNeuronal = new RedNeuronal<>(cantHidden, 1, 1, 0.1, iteraciones, cls, 0d);
        HashMap<Integer, Double> err = redNeuronal.backpropagation(entradas_funcion1, salidas_esperadas_funcion1);
        Double minErr = err.get(err.size() - 1);
        System.out.println("... Menor error: " + String.format("%.3f", minErr));
        Util.Plot(err, "Id-" + cls.getName() + "-It: " + iteraciones + "-Hid: " + cantHidden + "-Min: " + String.format("%.3f", minErr), 0, err.size());

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
            List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(x)));
            salidasExactasEval.add(i, x);
            double res = resultado.get(0);
            salidasRedEval.add(i, res);
        }
        Util.Plot(entradasEval, salidasExactasEval, salidasRedEval, "Id-" + cls.getName() + "-It: " + iteraciones + "-Hid: " + cantHidden, iteraciones, cls);


    }

    public static void funcionPotencia4(int iteraciones, Class cls, int cantHidden) throws Exception {

        List<List<Double>> entradas_funcion = new ArrayList<>();
        List<Double> salidas_esperadas_funcion = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            double x = Util.randDouble(0, 1, r);
            entradas_funcion.add(i, new ArrayList<Double>(Arrays.asList(Math.abs(x))));
            salidas_esperadas_funcion.add(i, Math.pow(Math.abs(x), 4));
        }
        RedNeuronal redNeuronal = new RedNeuronal(cantHidden, 1, 1, 0.1, iteraciones, cls, 0d);
        HashMap<Integer, Double> err = redNeuronal.backpropagation(entradas_funcion, salidas_esperadas_funcion);
        Double minErr = err.get(err.size() - 1);
        System.out.println("... Menor error: " + String.format("%.3f", minErr));
        Util.Plot(err, "Pot-" + cls.getName() + "-It: " + iteraciones + "-Hid: " + cantHidden + "-Min: " + String.format("%.3f", minErr), 0, err.size());


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
        Util.Plot(entradasEval, salidasExactasEval, salidasRedEval, "Pot-" + cls.getName() + "-It: " + iteraciones + "-Hid: " + cantHidden, iteraciones, cls);

    }

    public static void funcionCoseno(int iteraciones, Class cls, int cantHidden) throws Exception {
        List<List<Double>> entradas_funcion = new ArrayList<>();
        List<Double> salidas_esperadas_funcion = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            double x = Util.randDouble(0, 4d / 7d, r);
            entradas_funcion.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion.add(i, (coseno(x)));
        }
        RedNeuronal redNeuronal = new RedNeuronal(cantHidden, 1, 1, 0.1, iteraciones, cls, 0d);
        HashMap<Integer, Double> err = redNeuronal.backpropagation(entradas_funcion, salidas_esperadas_funcion);
        Double minErr = err.get(err.size() - 1);
        System.out.println("... Menor error: " + String.format("%.3f", minErr));
        Util.Plot(err, "Cos-" + cls.getName() + "-It: " + iteraciones + "-Hid: " + cantHidden + "-Min: " + String.format("%.3f", minErr), 0, err.size());


        /**
         * Evaluacion
         * Pude entrenar desde 0 a 4/7.
         * Lo demas lo deduzco por periodicidad.
         */

        List<Double> entradasEval = new ArrayList<>();
        List<Double> salidasExactasEval = new ArrayList<>();
        List<Double> salidasRedEval = new ArrayList<>();
        r = new Random();
        for (int i = 0; i < 100; i++) {
            double x = Util.randDouble(-1, 1, r);
            entradasEval.add(i, x);
            List<Double> resultado = new ArrayList<>();
            //Dentro de lo que entrene
            if (x >= 0d && x < 4d / 7d) {
                resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(x)));
            }
            //Espejo de lo que entrene
            else if (x < 0 && x >= -4d / 7d) {
                resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(-x)));
            }
            //Cola izquierda
            else if (x < -4d / 7d && x >= -1) {
                resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(x + 7d / 7d)));
            }
            //Cola derecha
            else {
                resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(x - 4d / 7d)));
            }
            double res = (resultado.get(0) * 2) - 1;
            salidasRedEval.add(i, res);
            salidasExactasEval.add(i, (coseno(x) * 2) - 1);

        }
        Util.Plot(entradasEval, salidasExactasEval, salidasRedEval, "Cos-" + cls.getName() + "-It: " + iteraciones + "-Hid: " + cantHidden, iteraciones, cls);


    }

    private static double coseno(double x) {
        x = (x * Math.PI * 7) / 2;
        return (Math.cos(x) + 1) / 2;
    }
}
