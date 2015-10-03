package practico4.E6;

import practico1.*;


public class Tablero {

    public final int SIZE;
    public final Marca[][] grilla;
    public int cantLineasInutilesParaX;
    public int cantLineasInutilesParaO;
    public int cantMinimaRestanteParaGanarX;
    public int cantMinimaRestanteParaGanarO;
    public int cantFichasX;
    public int cantFichasO;

    /**
     * Inicializo con el tama�o N que quiera.
     *
     * @param size
     */
    public Tablero(int size) {
        this.SIZE = size;
        this.grilla = new Marca[SIZE][SIZE];
        this.cantLineasInutilesParaX = SIZE;
        this.cantLineasInutilesParaO = SIZE;
        this.cantMinimaRestanteParaGanarO = SIZE;
        this.cantMinimaRestanteParaGanarX = SIZE;
        this.cantFichasX = 0;
        this.cantFichasO = 0;

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                this.grilla[i][j] = Marca.N;
            }


    }

    public Marca getMarca(int i, int j) {
        return this.grilla[i][j];
    }

    public void imprimir() {
        System.out.println("___________________________________________________");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(String.format("%10s", this.grilla[i][j].name()));
            }
            System.out.println("");
        }
        System.out.println("___________________________________________________");
    }

    /**
     * m es pto de vista.
     *
     * @param m
     * @param coeficientes
     * @return
     */
    public EstadoTablero getEstadoTablero(Marca m, Coeficientes coeficientes) {
        boolean empate = ((cantFichasX + cantFichasO == SIZE * SIZE) && (cantMinimaRestanteParaGanarX != 0) && (cantMinimaRestanteParaGanarO != 0));
        boolean juegofin = ((cantMinimaRestanteParaGanarX == 0) || (cantMinimaRestanteParaGanarO == 0) || (cantFichasX + cantFichasO == SIZE * SIZE));

        Marca ganador = null;
        if (juegofin && !empate){
            ganador = m;
        }

        //Seteo los resultados
        return new EstadoTablero(
                coeficientes.w0 * cantLineasInutilesParaO +
                coeficientes.w1 * cantLineasInutilesParaX +
                coeficientes.w2 * cantMinimaRestanteParaGanarO +
                coeficientes.w3 * cantMinimaRestanteParaGanarX +
                coeficientes.w4 * cantFichasO +
                coeficientes.w5 * cantFichasX + coeficientes.indep, juegofin, empate, ganador);

    }

    public enum Marca {
        X, O, N
    }
}
