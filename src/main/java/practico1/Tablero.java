package practico1;

/**
 * Created by RSperoni on 17/08/2015.
 */
public class Tablero {

    public enum Marca {
        X, O, N
    }

    public final int SIZE;
    public final Marca[][] grilla;
    public int cantFichasX = 0;
    public int cantFichasO = 0;

    /**
     * Inicializo con el tamaño N que quiera.
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
     * Sólo permito marcar si no hay X o O en esa posición.
     * @param i
     * @param j
     * @param m
     * @return
     */
    public boolean setMarca(int i, int j, Marca m) throws Exception {
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

        /**
         * Verifico si alguien ganó.
         * La nueva ficha está en (i,j)
         */
        boolean winHorizontal = true;
        boolean winVertical = true;
        boolean winDiagonal = true;

        for (int k = 0; k < SIZE; k++) {
           if (this.grilla[k][j] != m)
               winHorizontal = false;
           if (this.grilla[i][k] != m)
               winVertical = false;
           if (this.grilla[i+k % SIZE][j+k % SIZE] != m)
               winDiagonal = false;

           //Ya no hay chance.
           if (!winDiagonal && !winHorizontal && !winVertical) break;

        }

        //Ganó M!
        if (winDiagonal || winHorizontal || winVertical) return true;
        //No ganó.
        else return false;


    }

    public Marca getMarca(int i, int j){
         return this.grilla[i][j];
    }

    /**
     * Aproximación a la función objetivo.
     * @return
     */
    public int getAproximacionVOP() {
             return 0;
    }
}
