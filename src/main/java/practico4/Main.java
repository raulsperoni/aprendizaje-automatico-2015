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
        funcionPotencia4();

        //f(x) = cos(7/2*pi*x)
        funcionCoseno();


    }

    public static void funcionIdentidad() {
        List<Double> entradas_funcion1 = new ArrayList<>();
        List<Double> salidas_esperadas_funcion1 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            entradas_funcion1.add(i, Util.randDouble(-1, 1, r));
            salidas_esperadas_funcion1.add(i, entradas_funcion1.get(i));
        }
        RedNeuronal redNeuronal = new RedNeuronal(2, 1, 1);
        redNeuronal.backpropagation(new ArrayList<List<Double>>(Arrays.asList(entradas_funcion1)));
    }

    public static void funcionPotencia4() {
        List<Double> entradas_funcion2 = new ArrayList<>();
        List<Double> salidas_esperadas_funcion2 = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            entradas_funcion2.add(i, Util.randDouble(-1, 1, r));
            salidas_esperadas_funcion2.add(i, Math.pow(entradas_funcion2.get(i), 4));
        }
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
