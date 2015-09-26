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
public class Sigmoide {
    final Integer id;
    final TipoSigmoide tipo;
    final List<Double> pesos;

    public Sigmoide(Integer id, Integer cantEntradas, TipoSigmoide tipo) {
        this.id = id;
        this.pesos = new ArrayList<>(cantEntradas);
        this.tipo = tipo;
        Random r = new Random();
        for (int i = 0; i < cantEntradas; i++) {
            pesos.add(i, r.nextDouble());
        }
    }

    public double salida(List<Double> entradas) {
        return 1 / (1 + Math.exp(getSumaConPesos(entradas)));
    }

    private double getSumaConPesos(List<Double> entradas) {
        double sum = 0d;
        for (int i = 0; i < entradas.size(); i++) {
            sum += entradas.get(i) * pesos.get(i);
        }
        return sum;
    }
}
