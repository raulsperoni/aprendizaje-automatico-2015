package practico1;

/**
 * Created by RSperoni on 19/08/2015.
 */
public class EstadoTablero {

    public double VOp;
    public boolean finalizado;
    public boolean empate;

    public EstadoTablero(double VOp, boolean finalizado, boolean empate) {
        this.VOp = VOp;
        this.finalizado = finalizado;
        this.empate = empate;
    }
}
