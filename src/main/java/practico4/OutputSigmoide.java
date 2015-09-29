package practico4;

/**
 * Created by raul on 27/09/15.
 */
public class OutputSigmoide extends Sigmoide {

    public OutputSigmoide(Integer id, Integer cantEntradas) {
        super(id, cantEntradas);
    }

    public double getError(Double salidaReal, Double salidaEsperada) {
        double error = salidaReal * (1 - salidaReal) * (salidaEsperada - salidaReal);
        errores.add(error);
        return error;
    }
}
