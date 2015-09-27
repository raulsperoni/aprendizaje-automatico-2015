package practico4;

/**
 * Created by raul on 27/09/15.
 */
public class HiddenSigmoide extends Sigmoide {

    public HiddenSigmoide(Integer id, Integer cantEntradas) {
        super(id, cantEntradas);
    }

    public double getError(Double salidaReal, Double terminoOutput) {
        return salidaReal * (1 - salidaReal) * terminoOutput;

    }


}
