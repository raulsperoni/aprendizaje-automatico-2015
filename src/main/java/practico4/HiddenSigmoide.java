package practico4;

import java.util.List;

/**
 * Created by raul on 27/09/15.
 */
public class HiddenSigmoide extends Sigmoide {

    public HiddenSigmoide(Integer id, Integer cantEntradas) {
        super(id, cantEntradas);
    }

    public double getError(List<Double> erroresOutput) {
        //TODO: error de los hidden
        return 0d;

    }


}
