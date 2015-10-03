package practico4.E6;

import practico1.*;

/**
 * Created by RSperoni on 19/08/2015.
 */
public class EstadoTablero {

    public double VOp;
    public boolean finalizado;
    public boolean empate;
    public Tablero.Marca ganador;

    public EstadoTablero(double VOp, boolean finalizado, boolean empate, Tablero.Marca ganador) {
        this.VOp = VOp;
        this.finalizado = finalizado;
        this.empate = empate;
        this.ganador = ganador;
    }
}
