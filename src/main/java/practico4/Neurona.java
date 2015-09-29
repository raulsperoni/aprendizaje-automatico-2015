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
public abstract class Neurona {
    final Integer id;
    final List<Double> pesos;
    final Integer cantidadEntradas;
    final TipoNeurona tipo;

    public Neurona(Integer id, Integer cantEntradas, TipoNeurona tipo) {
        this.tipo = tipo;
        this.id = id;
        this.cantidadEntradas = cantEntradas;
        this.pesos = new ArrayList<>(cantEntradas);
        Random r = new Random();
        for (int i = 0; i < cantEntradas; i++) {
            pesos.add(i, r.nextDouble());
        }
    }

    public abstract double getSalida(List<Double> entradas);

    public abstract double getError(Double salidaReal, Double termino);

    public void actualizarPesos(List<Double> entradas, Double error, Double aprendizaje) {
        for (int i = 0; i < pesos.size(); i++) {
            double incremento = aprendizaje * error * entradas.get(i);
            pesos.set(i, pesos.get(i) + incremento);
        }
    }


    protected double getSumaConPesos(List<Double> entradas) {
        double sum = 0d;
        for (int i = 0; i < entradas.size(); i++) {
            sum += entradas.get(i) * pesos.get(i);
        }
        return sum;
    }


    public enum TipoNeurona {
        OUTPUT, HIDDEN
    }
}
