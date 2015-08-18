package practico1;

/**
 * Created by RSperoni on 17/08/2015.
 */
public class Tablero {

    public final int SIZE;
    public final Marca[][] grilla;
    public int cantLineasInutilesParaX = 0;
    public int cantLineasInutilesParaO = 0;
    public int cantMinimaRestanteParaGanarX = 0;
    public int cantMinimaRestanteParaGanarO = 0;
    public int cantFichasX = 0;
    public int cantFichasO = 0;

    /**
     * Inicializo con el tama�o N que quiera.
     *
     * @param size
     */
    public Tablero(int size) {
        this.SIZE = size;
        this.grilla = new Marca[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                this.grilla[i][j] = Marca.N;
            }


    }

    /**
     * S�lo permito marcar si no hay X o O en esa posici�n.
     * @param i
     * @param j
     * @param m
     * @return
     */
    public boolean setMarca(int i, int j, Marca m, boolean esDeMentira) throws Exception {
        /**
         * Jugadas prohibidas.
         */
        if (grilla[i][j] != Marca.N || m == Marca.N) throw new Exception("Jugada prohibida");

        /**
         * Asigno X o O
         */
        grilla[i][j] = m;

        /**
         * Actualizo Contadores
         */
        if (m == Marca.X)
            cantFichasX++;
        else cantFichasO++;

        //Actualizo los atributos aca
        for (int k = 0; k < SIZE; k++) {
            int cantFilasX = 0;
            int cantFilasO = 0;
            for (int l = 0; l < SIZE; k++) {


            }

        }

        /**
         * Verifico si alguien gan�.
         * La nueva ficha est� en (i,j)
         */
        boolean winHorizontal = true;
        boolean winVertical = true;
        boolean winDiagonal = true;

        for (int k = 0; k < SIZE; k++) {
           if (this.grilla[k][j] != m)
               winHorizontal = false;
           if (this.grilla[i][k] != m)
               winVertical = false;
            if (i == j && this.grilla[i + k % SIZE][j + k % SIZE] != m) //TODO: ojo con las diagonales prohibis
               winDiagonal = false;

           //Ya no hay chance.
           if (!winDiagonal && !winHorizontal && !winVertical) break;

        }

        //Gan� M!
        if (winDiagonal || winHorizontal || winVertical) return true;
        //No gan�.
        else return false;


    }

    public Marca getMarca(int i, int j){
         return this.grilla[i][j];
    }

    /**
     * Aproximaci�n a la funci�n objetivo.
     * @return
     */
    public double getAproximacionVOP(Marca marcaJugador, Coeficientes coeficientes) {
             return 0;
    }

    public enum Marca {
        X, O, N
    }
}
