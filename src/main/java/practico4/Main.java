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
        funcionIdentidad();

        //f(x) = x^4
        //funcionPotencia4();

        //f(x) = cos(7/2*pi*x)
        //funcionCoseno();


    }

    public static void funcionIdentidad() {
        List<List<Double>> entradas_funcion1 = new ArrayList<>();
        List<Double> salidas_esperadas_funcion1 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            double x = Util.randDouble(0, 1, r);
            entradas_funcion1.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion1.add(i, x);
        }
        RedNeuronal redNeuronal = new RedNeuronal(2, 1, 1, 0.1, 100);
        redNeuronal.backpropagation(entradas_funcion1, salidas_esperadas_funcion1);
        List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(0.99)));
    }

    public static void funcionPotencia4() {
    	List<List<Double>> entradas_funcion2 = new ArrayList<>();
        List<Double> salidas_esperadas_funcion2 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
        	double x = Util.randDouble(0, 1, r);
            entradas_funcion2.add(i, new ArrayList<Double>(Arrays.asList(x)));
            salidas_esperadas_funcion2.add(i, Math.pow(x, 4));
        }
        RedNeuronal redNeuronal = new RedNeuronal(2, 1, 1, 0.1, 500);
        redNeuronal.backpropagation(entradas_funcion2, salidas_esperadas_funcion2);
        List<Double> resultado = redNeuronal.evaluar(new ArrayList<Double>(Arrays.asList(0.99)));
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
