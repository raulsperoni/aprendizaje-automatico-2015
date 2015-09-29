package practico4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by RSperoni on 23/09/2015.
 */
public class Main {

    public static void main(String[] args) {

        //Ejercicio 3

        //f(x) = x
        try {
            funcionIdentidad();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //f(x) = x^4
        //funcionPotencia4();

        //f(x) = cos(7/2*pi*x)
        //funcionCoseno();


    }

    public static void funcionIdentidad() throws Exception {
        List<List<Double>> entradas_funcion1 = new ArrayList<>();
        List<Double> salidas_esperadas_funcion1 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 200; i++) {
            double x = Util.randDouble(0, 1, r);
            entradas_funcion1.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion1.add(i, x);
        }
        RedNeuronal redNeuronal = new RedNeuronal(2, 1, 1, 0.1, 20000, Sigmoid.class);
        redNeuronal.backpropagation(entradas_funcion1, salidas_esperadas_funcion1);
        double ejemploAEvaluar = Util.randDouble(0, 1, r);
        List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(ejemploAEvaluar)));
        System.out.println("Ejemplo: " + ejemploAEvaluar + "\t" + "Resultado: " + resultado.get(0));
    }

    public static void funcionPotencia4() throws Exception {

        List<List<Double>> entradas_funcion = new ArrayList<>();
        List<Double> salidas_esperadas_funcion = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 500; i++) {
            double x = Util.randDouble(0, 1, r);
            entradas_funcion.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion.add(i, Math.pow(x, 4d));
        }
        RedNeuronal redNeuronal = new RedNeuronal(2, 1, 1, 0.1, 10000, Sigmoid.class);
        redNeuronal.backpropagation(entradas_funcion, salidas_esperadas_funcion);
        double ejemploAEvaluar = Util.randDouble(0, 1, r);
        List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(ejemploAEvaluar)));
        System.out.println("Ejemplo: " + ejemploAEvaluar + "\t" + "Resultado: " + Math.sqrt(Math.sqrt(resultado.get(0))));

    }

    public static void funcionCoseno() {
        List<Double> entradas_funcion3 = new ArrayList<>();
        List<Double> salidas_esperadas_funcion3 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            entradas_funcion3.add(i, Util.randDouble(-1, 1, r));
            salidas_esperadas_funcion3.add(i, Math.cos((7 / 2) * Math.PI * entradas_funcion3.get(i)));
        }
    }
}
