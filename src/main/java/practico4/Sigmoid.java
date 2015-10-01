package practico4;

import java.util.List;

/**
 * Created by raul on 29/09/15.
 */
public class Sigmoid extends Neurona {

    public Sigmoid(Integer id, Integer cantEntradas, TipoNeurona tipo, Double momentumTerm) {
        super(id, cantEntradas, tipo, momentumTerm);
    }

    @Override
    public double getSalida(List<Double> entradas) {
        return 1 / (1 + Math.exp(-getSumaConPesos(entradas)));
    }


    @Override
    protected double derivada(double valorFuncional) {
        return valorFuncional * (1 - valorFuncional);
    }


}
