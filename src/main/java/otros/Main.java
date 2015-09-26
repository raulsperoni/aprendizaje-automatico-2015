package otros;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by RSperoni on 23/09/2015.
 */
public class Main {

    public static void main(String[] args) {

        //Entreno un Perceptron para la funcion XOR
        //https://es.wikipedia.org/wiki/Perceptr%C3%B3n
        Perceptron p = new Perceptron(0.5f, 0.1f, 3);
        Float[] a = {1f, 0f, 0f};
        Float[] b = {1f, 0f, 1f};
        Float[] c = {1f, 1f, 0f};
        Float[] d = {1f, 1f, 1f};
        List<List<Float>> ejemplosEntrenamiento = new ArrayList<>();
        ejemplosEntrenamiento.add(new ArrayList<Float>(Arrays.asList(a)));
        ejemplosEntrenamiento.add(new ArrayList<Float>(Arrays.asList(b)));
        ejemplosEntrenamiento.add(new ArrayList<Float>(Arrays.asList(c)));
        ejemplosEntrenamiento.add(new ArrayList<Float>(Arrays.asList(d)));
        List<Boolean> resultadosEsperados = new ArrayList<>(Arrays.asList(true, true, true, false));
        try {
            p.entrenar(ejemplosEntrenamiento, resultadosEsperados);
            List<Float> pesos = p.getPesos();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Entreno un Perceptron para la funcion AND
        //https://es.wikipedia.org/wiki/Perceptr%C3%B3n
        Perceptron p2 = new Perceptron(0.5f, 0.1f, 2);
        Float[] a2 = {1f, 0f};
        Float[] b2 = {0f, 1f};
        Float[] c2 = {1f, 1f};
        Float[] d2 = {0f, 0f};
        List<List<Float>> ejemplosEntrenamiento2 = new ArrayList<>();
        ejemplosEntrenamiento2.add(new ArrayList<Float>(Arrays.asList(a2)));
        ejemplosEntrenamiento2.add(new ArrayList<Float>(Arrays.asList(b2)));
        ejemplosEntrenamiento2.add(new ArrayList<Float>(Arrays.asList(c2)));
        ejemplosEntrenamiento2.add(new ArrayList<Float>(Arrays.asList(d2)));
        List<Boolean> resultadosEsperados2 = new ArrayList<>(Arrays.asList(false, false, true, false));
        try {
            p2.entrenar(ejemplosEntrenamiento2, resultadosEsperados2);

            boolean res = p2.evaluar(new ArrayList<Float>(Arrays.asList(1f, 1f)));

            List<Float> pesos = p2.getPesos();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
