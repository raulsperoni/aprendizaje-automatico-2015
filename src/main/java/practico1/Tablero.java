package practico1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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


        //Actualizo los atributos aca

        //Variables Para Evaluar X
        boolean esPosibleGanarFilaX = true;
        boolean esPosibleGanarColumnaX = true;
        boolean esPosibleGanarDiagonalX1 = true;
        boolean esPosibleGanarDiagonalX2 = true;
        int cantRestanteParaGanarFilaX = SIZE;
        int cantRestanteParaGanarColumnaX = SIZE;
        int cantRestanteParaGanarDiagonalX1 = SIZE;
        int cantRestanteParaGanarDiagonalX2 = SIZE;

        //Variables Para Evaluar O
        boolean esPosibleGanarFilaO = true;
        boolean esPosibleGanarColumnaO = true;
        boolean esPosibleGanarDiagonalO1 = true;
        boolean esPosibleGanarDiagonalO2 = true;
        int cantRestanteParaGanarFilaO = SIZE;
        int cantRestanteParaGanarColumnaO = SIZE;
        int cantRestanteParaGanarDiagonalO1 = SIZE;
        int cantRestanteParaGanarDiagonalO2 = SIZE;

        for (int k = 0; k < SIZE; k++) {


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
                    } else if (marcaEnPos != Marca.N) {
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
                    } else if (marcaEnPos != Marca.N) {
                        cantRestanteParaGanarColumnaX -= 1;
                    }
                }

                //MIRO LA Diagonal Izq a Der
                if (esPosibleGanarDiagonalX1) {
                    int fila = l;
                    int columna = k;
                    //Solo miro la diagonal posta.
                    if (fila == columna) {
                        Marca marcaDelContrario = Marca.O;
                        Marca marcaEnPos = this.grilla[fila][columna];
                        if (marcaEnPos == marcaDelContrario) {
                            esPosibleGanarDiagonalX1 = false;
                        } else if (marcaEnPos != Marca.N) {
                            cantRestanteParaGanarDiagonalX1 -= 1;
                        }
                    }
                }

                //MIRO LA Diagonal Der a Izq
                if (esPosibleGanarDiagonalX2) {
                    int fila = l;
                    int columna = k;
                    //Solo miro la diagonal otra.
                    if (fila + columna == SIZE - 1) {
                        Marca marcaDelContrario = Marca.O;
                        Marca marcaEnPos = this.grilla[fila][columna];
                        if (marcaEnPos == marcaDelContrario) {
                            esPosibleGanarDiagonalX2 = false;
                        } else if (marcaEnPos != Marca.N) {
                            cantRestanteParaGanarDiagonalX2 -= 1;
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
                    } else if (marcaEnPos != Marca.N) {
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
                    } else if (marcaEnPos != Marca.N) {
                        cantRestanteParaGanarColumnaO -= 1;
                    }
                }

                //MIRO LA Diagonal
                if (esPosibleGanarDiagonalO1) {
                    int fila = l;
                    int columna = k;
                    //Solo miro la diagonal posta.
                    if (fila == columna) {
                        Marca marcaDelContrario = Marca.X;
                        Marca marcaEnPos = this.grilla[fila][columna];
                        if (marcaEnPos == marcaDelContrario) {
                            esPosibleGanarDiagonalO1 = false;
                        } else if (marcaEnPos != Marca.N) {
                            cantRestanteParaGanarDiagonalO1 -= 1;
                        }
                    }
                }

                //MIRO LA Diagonal
                if (esPosibleGanarDiagonalO2) {
                    int fila = l;
                    int columna = k;
                    //Solo miro la diagonal posta.
                    if (fila == columna) {
                        Marca marcaDelContrario = Marca.X;
                        Marca marcaEnPos = this.grilla[fila][columna];
                        if (marcaEnPos == marcaDelContrario) {
                            esPosibleGanarDiagonalO2 = false;
                        } else if (marcaEnPos != Marca.N) {
                            cantRestanteParaGanarDiagonalO2 -= 1;
                        }
                    }
                }


                //No hay chance
                if (!esPosibleGanarColumnaX && !esPosibleGanarFilaX && !esPosibleGanarDiagonalX1 && !esPosibleGanarDiagonalX2
                        && !esPosibleGanarFilaO && !esPosibleGanarColumnaO && !esPosibleGanarDiagonalO1 && !esPosibleGanarDiagonalO2) break;

            }



        }

        //Si es marca posta, actualizo atributos.
        if (!esDeMentira) {

            /**
             * Actualizo cantFichas
             */
            if (m == Marca.X)
                cantFichasX++;
            else cantFichasO++;

            /**
             * Actualizo canMinimaRestanteParaGanar
             */
            if (esPosibleGanarFilaX || esPosibleGanarColumnaX || esPosibleGanarDiagonalX1 || esPosibleGanarDiagonalX2) {
                List<Integer> s = Arrays.asList(cantRestanteParaGanarColumnaX, cantRestanteParaGanarFilaX, cantRestanteParaGanarDiagonalX1, cantRestanteParaGanarDiagonalX2, cantMinimaRestanteParaGanarX);
                this.cantMinimaRestanteParaGanarX = Collections.min(s);
            }

            if (esPosibleGanarFilaO || esPosibleGanarColumnaO || esPosibleGanarDiagonalO1 || esPosibleGanarDiagonalO2) {
                List<Integer> s = Arrays.asList(cantRestanteParaGanarColumnaO, cantRestanteParaGanarFilaO, cantRestanteParaGanarDiagonalO1, cantRestanteParaGanarDiagonalO2, cantMinimaRestanteParaGanarO);
                this.cantMinimaRestanteParaGanarO = Collections.min(s);
            }

            /**
             * Actualizo CantLineasInutiles
             */
            //TODO: Hacer esto arriba y actualizar acá-

        }
        //Si no es marca posta, si solo esta "probando" saco la marca.
        else {

            /**
             * Vuelvo atrás X o O
             */
            grilla[i][j] = Marca.N;
        }

        //TODO: revisar esta condicion de ganar.
        boolean JugadorGano = (m == Marca.X) ? cantMinimaRestanteParaGanarX == 0 : cantMinimaRestanteParaGanarO == 0;


        if (m == Marca.X) {

            //Seteo los resultados
            return new EstadoTablero(coeficientes.w0 * cantLineasInutilesParaO +
                    coeficientes.w1 * cantLineasInutilesParaX +
                    coeficientes.w2 * cantMinimaRestanteParaGanarO +
                    coeficientes.w3 * cantMinimaRestanteParaGanarX +
                    coeficientes.w4 * cantFichasO +
                    coeficientes.w5 * cantFichasX + coeficientes.indep, JugadorGano);

        } else {

            //Seteo los resultados
            return new EstadoTablero(coeficientes.w0 * cantLineasInutilesParaX +
                    coeficientes.w1 * cantLineasInutilesParaO +
                    coeficientes.w2 * cantMinimaRestanteParaGanarX +
                    coeficientes.w3 * cantMinimaRestanteParaGanarO +
                    coeficientes.w4 * cantFichasX +
                    coeficientes.w5 * cantFichasO + coeficientes.indep, JugadorGano);

        }


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

    /**
     * m es pto de vista.
     *
     * @param m
     * @return
     */
    public EstadoTablero getEstadoTablero(Marca m, Coeficientes coeficientes) {
        boolean JugadorGano = (m == Marca.X) ? cantMinimaRestanteParaGanarX == 0 : cantMinimaRestanteParaGanarO == 0;


        if (m == Marca.X) {

            //Seteo los resultados
            return new EstadoTablero(coeficientes.w0 * cantLineasInutilesParaO +
                    coeficientes.w1 * cantLineasInutilesParaX +
                    coeficientes.w2 * cantMinimaRestanteParaGanarO +
                    coeficientes.w3 * cantMinimaRestanteParaGanarX +
                    coeficientes.w4 * cantFichasO +
                    coeficientes.w5 * cantFichasX + coeficientes.indep, JugadorGano);

        } else {

            //Seteo los resultados
            return new EstadoTablero(coeficientes.w0 * cantLineasInutilesParaX +
                    coeficientes.w1 * cantLineasInutilesParaO +
                    coeficientes.w2 * cantMinimaRestanteParaGanarX +
                    coeficientes.w3 * cantMinimaRestanteParaGanarO +
                    coeficientes.w4 * cantFichasX +
                    coeficientes.w5 * cantFichasO + coeficientes.indep, JugadorGano);

        }
    }


    public enum Marca {
        X, O, N
    }
}
