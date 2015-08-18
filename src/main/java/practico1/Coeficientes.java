package practico1;

import java.util.Random;

/**
 * Created by raul on 18/08/15.
 */
public class Coeficientes {

    public float w0;
    public float w1;
    public float w2;
    public float w3;
    public float w4;
    public float w5;

    public Coeficientes() {
        Random r = new Random();
        this.w0 = r.nextFloat();
        this.w1 = r.nextFloat();
        this.w2 = r.nextFloat();
        this.w3 = r.nextFloat();
        this.w4 = r.nextFloat();
        this.w5 = r.nextFloat();
    }

    public void actualizarCoeficientes(Tablero t, double mu, int VEnt, double VOp) {
        this.w0 += mu * (VEnt - VOp) * t.cantLineasInutilesParaO;
        this.w1 += mu * (VEnt - VOp) * t.cantLineasInutilesParaX;
        this.w2 += mu * (VEnt - VOp) * t.cantMinimaRestanteParaGanarO;
        this.w3 += mu * (VEnt - VOp) * t.cantMinimaRestanteParaGanarX;
        this.w4 += mu * (VEnt - VOp) * t.cantFichasO;
        this.w5 += mu * (VEnt - VOp) * t.cantFichasX;
    }


}
