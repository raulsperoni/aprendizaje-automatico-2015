package practico4;

import java.util.List;

/**
 * Created by raul on 29/09/15.
 */
public class Tanh extends Neurona {

    public Tanh(Integer id, Integer cantEntradas, TipoNeurona tipo) {
        super(id, cantEntradas, tipo);
    }

    @Override
    public double getSalida(List<Double> entradas) {
        double x = getSumaConPesos(entradas);
        return Math.cos(x) / Math.sin(x);
    }

    @Override
    public double getError(Double salidaReal, Double termino) {
        double error = 0d;
        if (tipo == TipoNeurona.HIDDEN)
            error = (1 - salidaReal) * (1 - salidaReal) * termino;
        else if (tipo == TipoNeurona.OUTPUT)
            error = (1 - salidaReal) * (1 - salidaReal) * (termino - salidaReal);
        return error;

    }
}
