package practico4;

import java.util.Random;

/**
 * Created by raul on 26/09/15.
 */
public class Util {

    public static double randDouble(double min, double max, Random rand) {

        double result = min + (rand.nextDouble() * (max - min));

        return result;
    }
}
