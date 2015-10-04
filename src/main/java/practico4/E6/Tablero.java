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
        System.out.println("empate: "+empate);
        System.out.println("juegofin: "+juegofin);
        System.out.println("cX"+cantFichasX +"cX"+cantFichasO+"cantminX"+cantMinimaRestanteParaGanarX+"cantminO"+cantMinimaRestanteParaGanarO);
        Marca ganador = null;
        if (juegofin && !empate){
            if(cantMinimaRestanteParaGanarX == 0){
                ganador = Marca.X;
            }else {
                ganador = Marca.O;
            }
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
    
    public void setMarca(int i, int j, Marca m){
            grilla[i][j] = m;
            actualizarEstado();
    }
    
    public void actualizarEstado(){
        Marca m = Marca.X;
        Marca r = Marca.O;
        int cantLineasInutilesM = 0;
            int minimoParaGanarM = SIZE;
            int cantLineasInutilesR = 0;
            int minimoParaGanarR = SIZE;
            int cantFichasM = 0;
            int cantFichasR = 0;

            //Analizo las filas para el jugador M
            for (int k = 0; k<SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = SIZE;
                for (int l = 0; l<SIZE; l++){
                    if(esPosibleGanar){
                        if(grilla[k][l] == m){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(grilla[k][l] != Tablero.Marca.N){
                            cantLineasInutilesM += 1;
                            esPosibleGanar = false;
                        }
                    }
                }
                if(minimoParaGanarM > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarM = cantRestantesParaGanar;
                }
            }

            //Analizo las filas para el jugador Rival
            for (int k = 0; k<SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = SIZE;
                for (int l = 0; l<SIZE; l++){
                    if(esPosibleGanar){
                        if(grilla[k][l] == r){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(grilla[k][l] != Tablero.Marca.N){
                            cantLineasInutilesR += 1;
                            esPosibleGanar = false;
                        }
                    }
                }
                if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
                }
            }


            //Analizo las columnas para el jugador M
            for (int k = 0; k<SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = SIZE;
                for (int l = 0; l<SIZE; l++){
                    if(esPosibleGanar){
                        if(grilla[l][k] == m){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(grilla[l][k] != Tablero.Marca.N){
                            cantLineasInutilesM += 1;
                            esPosibleGanar = false;
                        }
                    }
                }
                if(minimoParaGanarM > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarM = cantRestantesParaGanar;
                }
            }

            //Analizo las columnas para el jugador Rival
            for (int k = 0; k<SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = SIZE;
                for (int l = 0; l<SIZE; l++){
                    if(esPosibleGanar){
                        if(grilla[l][k] == r){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(grilla[l][k] != Tablero.Marca.N){
                            cantLineasInutilesR += 1;
                            esPosibleGanar = false;
                        }
                    }
                }
                if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
                }
            }

            //Analizo las diagonales para el jugador M
            boolean esPosibleGanar = true;
            int cantRestantesParaGanar = SIZE;
            for (int k = 0; k<SIZE; k++){
                if(esPosibleGanar){
                    if(grilla[k][k] == m){
                           cantRestantesParaGanar -= 1; 
                        }
                        else if(grilla[k][k] != Tablero.Marca.N){
                            cantLineasInutilesM += 1;
                            esPosibleGanar = false;
                        }
                }
            }
            if(minimoParaGanarM > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarM = cantRestantesParaGanar;
            }

            esPosibleGanar = true;
            cantRestantesParaGanar = SIZE;
            for (int k = 0; k<SIZE; k++){
                if(esPosibleGanar){
                    if(grilla[k][SIZE-k-1] == m){
                           cantRestantesParaGanar -= 1; 
                        }
                        else if(grilla[k][k] != Tablero.Marca.N){
                            cantLineasInutilesM += 1;
                            esPosibleGanar = false;
                        }
                }
            }
            if(minimoParaGanarM > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarM = cantRestantesParaGanar;
            }


            //Analizo las diagonales para el jugador Rival
            esPosibleGanar = true;
            cantRestantesParaGanar = SIZE;
            for (int k = 0; k<SIZE; k++){
                if(esPosibleGanar){
                    if(grilla[k][k] == r){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(grilla[k][k] != Tablero.Marca.N){
                            cantLineasInutilesR += 1;
                            esPosibleGanar = false;
                        }
                }
            }
            if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
            }

            esPosibleGanar = true;
            cantRestantesParaGanar = SIZE;
            for (int k = 0; k<SIZE; k++){
                if(esPosibleGanar){
                    if(grilla[k][SIZE-k-1] == r){
                        cantRestantesParaGanar -= 1; 
                    } else if(grilla[k][k] != Tablero.Marca.N){
                        cantLineasInutilesR += 1;
                        esPosibleGanar = false;  
                    }
                }
            }
            if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
            }

            //Cuento la cantidad de fichas
            for(int k = 0; k<SIZE; k++){
                for(int l = 0; l<SIZE; l++){
                    if(grilla[k][l] == m){
                        cantFichasM += 1;
                    }
                    else if(grilla[k][l] != Tablero.Marca.N){
                        cantFichasR += 1;
                    }
                }
            }

            /**
            * Actualizo todo
            */
            if (m == Tablero.Marca.X){
                cantFichasX++;
                cantMinimaRestanteParaGanarX = minimoParaGanarM;
                cantMinimaRestanteParaGanarO = minimoParaGanarR;
                cantLineasInutilesParaX = cantLineasInutilesM;
                cantLineasInutilesParaO = cantLineasInutilesR;
            }    
            else {
                cantFichasO++;
                cantMinimaRestanteParaGanarO = minimoParaGanarM;
                cantMinimaRestanteParaGanarX = minimoParaGanarR;
                cantLineasInutilesParaO = cantLineasInutilesM;
                cantLineasInutilesParaX = cantLineasInutilesR;
            }        
    }

    public enum Marca {
        X, O, N
    }
}
