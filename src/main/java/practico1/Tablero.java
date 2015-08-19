package practico1;

import java.util.*;

/**
 * Created by RSperoni on 17/08/2015.
 */
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

    /**
     * S�lo permito marcar si no hay X o O en esa posici�n.
     *
     * @param i
     * @param j
     * @param m
     * @return
     */
    public EstadoTablero setMarca(int i, int j, Marca m, boolean esDeMentira, Coeficientes coeficientes) throws Exception {
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

            //Variables Para Evaluar X
            boolean esPosibleGanarFilaX = true;
            boolean esPosibleGanarColumnaX = true;
            boolean esPosibleGanarDiagonalX = true;
            int cantRestanteParaGanarFilaX = SIZE;
            int cantRestanteParaGanarColumnaX = SIZE;
            int cantRestanteParaGanarDiagonalX = SIZE;

            //Variables Para Evaluar O
            boolean esPosibleGanarFilaO = true;
            boolean esPosibleGanarColumnaO = true;
            boolean esPosibleGanarDiagonalO = true;
            int cantRestanteParaGanarFilaO = SIZE;
            int cantRestanteParaGanarColumnaO = SIZE;
            int cantRestanteParaGanarDiagonalO = SIZE;

            for (int l = 0; l < SIZE; l++) {

                /**
                 * Evaluo X
                 */

                //MIRO LA FILA
                if (esPosibleGanarFilaX) {
                    int fila = k;
                    int columna = l;
                    Marca marcaDelContrario = Marca.O;
                    Marca marcaEnPos = this.grilla[fila][columna];
                    if (marcaEnPos == marcaDelContrario) {
                        esPosibleGanarFilaX = false;
                    } else {
                        cantRestanteParaGanarFilaX -= 1;
                    }
                }

                //MIRO LA Columna
                if (esPosibleGanarColumnaX) {
                    int fila = l;
                    int columna = k;
                    Marca marcaDelContrario = Marca.O;
                    Marca marcaEnPos = this.grilla[fila][columna];
                    if (marcaEnPos == marcaDelContrario) {
                        esPosibleGanarColumnaX = false;
                    } else {
                        cantRestanteParaGanarColumnaX -= 1;
                    }
                }

                //MIRO LA Diagonal
                if (esPosibleGanarDiagonalX) {
                    int fila = l;
                    int columna = k;
                    //Solo miro la diagonal posta.
                    if (fila == columna) {
                        Marca marcaDelContrario = Marca.O;
                        Marca marcaEnPos = this.grilla[fila][columna];
                        if (marcaEnPos == marcaDelContrario) {
                            esPosibleGanarDiagonalX = false;
                        } else {
                            cantRestanteParaGanarDiagonalX -= 1;
                        }
                    }
                }

                /**
                 * Evaluo O
                 */

                //MIRO LA FILA
                if (esPosibleGanarFilaO) {
                    int fila = k;
                    int columna = l;
                    Marca marcaDelContrario = Marca.X;
                    Marca marcaEnPos = this.grilla[fila][columna];
                    if (marcaEnPos == marcaDelContrario) {
                        esPosibleGanarFilaO = false;
                    } else {
                        cantRestanteParaGanarFilaO -= 1;
                    }
                }

                //MIRO LA Columna
                if (esPosibleGanarColumnaO) {
                    int fila = l;
                    int columna = k;
                    Marca marcaDelContrario = Marca.X;
                    Marca marcaEnPos = this.grilla[fila][columna];
                    if (marcaEnPos == marcaDelContrario) {
                        esPosibleGanarColumnaO = false;
                    } else {
                        cantRestanteParaGanarColumnaO -= 1;
                    }
                }

                //MIRO LA Diagonal
                if (esPosibleGanarDiagonalO) {
                    int fila = l;
                    int columna = k;
                    //Solo miro la diagonal posta.
                    if (fila == columna) {
                        Marca marcaDelContrario = Marca.X;
                        Marca marcaEnPos = this.grilla[fila][columna];
                        if (marcaEnPos == marcaDelContrario) {
                            esPosibleGanarDiagonalO = false;
                        } else {
                            cantRestanteParaGanarDiagonalO -= 1;
                        }
                    }
                }


                //No hay chance
                if (!esPosibleGanarColumnaX && !esPosibleGanarFilaX && !esPosibleGanarDiagonalX
                        && !esPosibleGanarFilaO && !esPosibleGanarColumnaO && !esPosibleGanarDiagonalO) break;

            }

            if (esPosibleGanarFilaX || esPosibleGanarColumnaX || esPosibleGanarDiagonalX) {
                List<Integer> s = Arrays.asList(cantRestanteParaGanarColumnaX,cantRestanteParaGanarFilaX,cantRestanteParaGanarDiagonalX,cantMinimaRestanteParaGanarX);
                this.cantMinimaRestanteParaGanarX = Collections.min(s);
            }

            if (esPosibleGanarFilaO || esPosibleGanarColumnaO || esPosibleGanarDiagonalO) {
                List<Integer> s = Arrays.asList(cantRestanteParaGanarColumnaO,cantRestanteParaGanarFilaO,cantRestanteParaGanarDiagonalO,cantMinimaRestanteParaGanarO);
                this.cantMinimaRestanteParaGanarO = Collections.min(s);
            }



        }

        /**
         * Verifico si alguien gano.
         * La nueva ficha esta en (i,j)
         */
        boolean winHorizontal = true;
        boolean winVertical = true;
        boolean winDiagonal = true;

        //No es la diagonal posta, no hay chance diagonal.
        if (i != j) winDiagonal = false;

        for (int k = 0; k < SIZE; k++) {
            if (this.grilla[k][j] != m)
                winHorizontal = false;
            if (this.grilla[i][k] != m)
                winVertical = false;
            if (winDiagonal && this.grilla[i + k % SIZE][j + k % SIZE] != m) //TODO: revisar.
                winDiagonal = false;

            //Ya no hay chance.
            if (!winDiagonal && !winHorizontal && !winVertical) break;

        }

        //Seteo los resultados
        return new EstadoTablero(coeficientes.w0 * cantLineasInutilesParaO +
                coeficientes.w1 * cantLineasInutilesParaX +
                coeficientes.w2 * cantMinimaRestanteParaGanarO +
                coeficientes.w3 * cantMinimaRestanteParaGanarX +
                coeficientes.w4 * cantFichasO +
                coeficientes.w5 * cantFichasX, (winDiagonal || winHorizontal || winVertical));


    }

    public Marca getMarca(int i, int j) {
        return this.grilla[i][j];
    }

    public void imprimir() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(String.format("%20s", this.grilla[i][j].name()));
            }
            System.out.println("");
        }
    }


    public enum Marca {
        X, O, N
    }
}
