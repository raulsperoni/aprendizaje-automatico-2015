package practico1;

import java.util.Random;

/**
 * Created by raul on 18/08/15.
 */
public class Coeficientes {

    //W pares Jugador, impares Oponente.
    public float w0;
    public float w1;
    public float w2;
    public float w3;
    public float w4;
    public float w5;
    public float indep;

    public Coeficientes() {
        Random r = new Random();
        this.w0 = r.nextFloat();
        this.w1 = r.nextFloat();
        this.w2 = r.nextFloat();
        this.w3 = r.nextFloat();
        this.w4 = r.nextFloat();
        this.w5 = r.nextFloat();
        this.indep = r.nextFloat();
    }

    //TODO: ojo con los pts de vista.
    public void actualizarCoeficientes(Tablero t, double mu, double VEnt, double VOp, Tablero.Marca m) throws Exception {
        if (m == Tablero.Marca.X) {
            this.w0 += mu * (VEnt - VOp) * t.cantLineasInutilesParaO;
            this.w1 += mu * (VEnt - VOp) * t.cantLineasInutilesParaX;
            this.w2 += mu * (VEnt - VOp) * t.cantMinimaRestanteParaGanarO;
            this.w3 += mu * (VEnt - VOp) * t.cantMinimaRestanteParaGanarX;
            this.w4 += mu * (VEnt - VOp) * t.cantFichasO;
            this.w5 += mu * (VEnt - VOp) * t.cantFichasX;
        } else {

            throw new Exception("Solo debe aprender el jugador inicial");
        }
    }

    public void imprimir() {
        System.out.println("W0: " + this.w0 + " W1: " + this.w1 + " W2: " + this.w2 + " W3: " + this.w3 + " W4: " + this.w4 + " W5: " + this.w5 + " indep: " + this.indep);
    }
}
