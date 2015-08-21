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
                    if(this.grilla[k][l] == m){
                        cantRestantesParaGanar -= 1;
                    }
                    else if(this.grilla[k][l] != Marca.N){
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
                    if(this.grilla[k][l] == m){
                        cantLineasInutilesR += 1;
                        esPosibleGanar = false;
                    }
                    else if(this.grilla[k][l] != Marca.N){
                        cantRestantesParaGanar -= 1;
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
                    if(this.grilla[l][k] == m){
                        cantRestantesParaGanar -= 1;
                    }
                    else if(this.grilla[l][k] != Marca.N){
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
                    if(this.grilla[l][k] == m){
                        cantLineasInutilesR += 1;
                        esPosibleGanar = false;
                    }
                    else if(this.grilla[l][k] != Marca.N){
                        cantRestantesParaGanar -= 1;
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
                if(this.grilla[k][k] == m){
                       cantRestantesParaGanar -= 1; 
                    }
                    else if(this.grilla[k][k] != Marca.N){
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
                if(this.grilla[k][SIZE-k-1] == m){
                       cantRestantesParaGanar -= 1; 
                    }
                    else if(this.grilla[k][k] != Marca.N){
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
                if(this.grilla[k][k] == m){
                    cantLineasInutilesR += 1;
                    esPosibleGanar = false;   
                    
                    }
                    else if(this.grilla[k][k] != Marca.N){
                        cantRestantesParaGanar -= 1; 
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
                if(this.grilla[k][SIZE-k-1] == m){
                    cantLineasInutilesR += 1;
                    esPosibleGanar = false;   
                    
                    }
                    else if(this.grilla[k][k] != Marca.N){
                        cantRestantesParaGanar -= 1; 
                    }
            }
        }
        if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                minimoParaGanarR = cantRestantesParaGanar;
        }
        
        //Cuento la cantidad de fichas
        for(int k = 0; k<SIZE; k++){
            for(int l = 0; l<SIZE; l++){
                if(this.grilla[k][l] == m){
                    cantFichasM += 1;
                }
                else if(this.grilla[k][l] != Marca.N){
                    cantFichasR += 1;
                }
            }
        }

        //Si es marca posta, actualizo atributos.
        if (!esDeMentira) {

            /**
             * Actualizo todo
             */
            if (m == Marca.X){
                cantFichasX++;
                this.cantMinimaRestanteParaGanarX = minimoParaGanarM;
                this.cantMinimaRestanteParaGanarO = minimoParaGanarR;
                this.cantLineasInutilesParaX = cantLineasInutilesM;
                this.cantLineasInutilesParaO = cantLineasInutilesR;
            }    
            else {
                cantFichasO++;
                this.cantMinimaRestanteParaGanarO = minimoParaGanarM;
                this.cantMinimaRestanteParaGanarX = minimoParaGanarR;
                this.cantLineasInutilesParaO = cantLineasInutilesM;
                this.cantLineasInutilesParaX = cantLineasInutilesR;
            }
        }
        //Si no es marca posta, si solo esta "probando" saco la marca.
        else {

            /**
             * Vuelvo atrás X o O
             */
            grilla[i][j] = Marca.N;
        }

        //TODO: revisar esta condicion de ganar.
        boolean JugadorGano = ((minimoParaGanarM == 0) || (minimoParaGanarR == 0) || (cantFichasM+cantFichasR == SIZE*SIZE));

        //Seteo los resultados
        return new EstadoTablero(coeficientes.w0 * cantLineasInutilesM +
            coeficientes.w1 * cantLineasInutilesR +
            coeficientes.w2 * minimoParaGanarM +
            coeficientes.w3 * minimoParaGanarR +
            coeficientes.w4 * cantFichasM +
            coeficientes.w5 * cantFichasR + coeficientes.indep, JugadorGano);
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
        boolean JugadorGano = ((cantMinimaRestanteParaGanarX == 0) || (cantMinimaRestanteParaGanarO == 0) || (cantFichasX+cantFichasO == SIZE*SIZE));


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
