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
            funcionIdentidad();

            //f(x) = x^4
            funcionPotencia4();

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
            double x = Util.randDouble(0, 1, r);
            entradas_funcion1.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion1.add(i, x);
        }
        RedNeuronal redNeuronal = new RedNeuronal<>(2, 1, 1, 0.1, 40000, Sigmoid.class, 0d);
        HashMap<Integer, Double> err = redNeuronal.backpropagation(entradas_funcion1, salidas_esperadas_funcion1);
        Double minErr = err.get(err.size() - 1);
        Util.Plot(err, "Indentidad: min:" + String.format("%.3f", minErr), 0, err.size());
        double ejemploAEvaluar = Util.randDouble(-1, 1, r);
        List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(ejemploAEvaluar)));
        System.out.println("Ejemplo Identidad: " + ejemploAEvaluar + "\t" + "Resultado: " + resultado.get(0));
    }

    public static void funcionPotencia4() throws Exception {

        List<List<Double>> entradas_funcion = new ArrayList<>();
        List<Double> salidas_esperadas_funcion = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            double x = Util.randDouble(-1, 1, r);
            entradas_funcion.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion.add(i, Math.pow(x, 4d));
        }
        RedNeuronal redNeuronal = new RedNeuronal(2, 1, 1, 0.1, 100000, Tanh.class, 0d);
        HashMap<Integer, Double> err = redNeuronal.backpropagation(entradas_funcion, salidas_esperadas_funcion);
        Double minErr = err.get(err.size() - 1);
        Util.Plot(err, "Potencia: min:" + String.format("%.3f", minErr), 70000, err.size());
        double ejemploAEvaluar = Util.randDouble(-1, 1, r);
        List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(ejemploAEvaluar)));
        System.out.println("Ejemplo Potencia: " + ejemploAEvaluar + "\t" + "Resultado: " + Math.sqrt(Math.sqrt(resultado.get(0))));

    }

    public static void funcionCoseno() throws Exception {
        List<List<Double>> entradas_funcion = new ArrayList<>();
        List<Double> salidas_esperadas_funcion = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            double x = Util.randDouble(-1, 1, r);
            entradas_funcion.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion.add(i, Math.cos((7 / 2) * Math.PI * x));
        }
        RedNeuronal redNeuronal = new RedNeuronal(2, 1, 1, 0.1, 50000, Tanh.class, 0d);
        HashMap<Integer, Double> err = redNeuronal.backpropagation(entradas_funcion, salidas_esperadas_funcion);
        Double minErr = err.get(err.size() - 1);
        Util.Plot(err, "Coseno: min:" + String.format("%.3f", minErr), 0, err.size());
        double ejemploAEvaluar = Util.randDouble(-1, 1, r);
        List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(ejemploAEvaluar)));
        System.out.println("Ejemplo Coseno: " + ejemploAEvaluar + "\t" + "Resultado: " + Math.acos((resultado.get(0) / Math.PI) * (2 / 7)));


    }
}
