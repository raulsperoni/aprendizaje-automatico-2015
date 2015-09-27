/**
 *
 */
package practico4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author emi
 */
public abstract class Sigmoide {
    final Integer id;
    final List<Double> pesos;

    public Sigmoide(Integer id, Integer cantEntradas) {
        this.id = id;
        this.pesos = new ArrayList<>(cantEntradas);
        Random r = new Random();
        for (int i = 0; i < cantEntradas; i++) {
            pesos.add(i, r.nextDouble());
        }
    }

    public double getSalida(List<Double> entradas) {
        return salidaTanh(entradas);
    }

    protected double salidaSigmoid(List<Double> entradas) {
        return 1 / (1 + Math.exp(-getSumaConPesos(entradas)));
    }

    protected double salidaTanh(List<Double> entradas) {
        double x = getSumaConPesos(entradas);
        return Math.cos(x) / Math.sin(x);
    }

    protected double getSumaConPesos(List<Double> entradas) {
        double sum = 0d;
        for (int i = 0; i < entradas.size(); i++) {
            sum += entradas.get(i) * pesos.get(i);
        }
        return sum;
    }
}
