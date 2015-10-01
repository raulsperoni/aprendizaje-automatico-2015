package practico4;

import java.util.List;

/**
 * Created by raul on 29/09/15.
 */
public class Tanh extends Neurona {

    public Tanh(Integer id, Integer cantEntradas, TipoNeurona tipo, Double momentumTerm) {
        super(id, cantEntradas, tipo, momentumTerm);
    }

    @Override
    public double getSalida(List<Double> entradas) {
        double x = getSumaConPesos(entradas);
        return Math.tanh(x);
    }

    @Override
    protected double derivada(double valorFuncional) {
        return 1 - Math.pow(valorFuncional, 2);
    }


}
