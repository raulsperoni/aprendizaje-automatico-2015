package practico1;


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
    public EstadoTablero setMarca(int i, int j, Marca m, boolean esDeMentira, boolean analizar_rival, Coeficientes coeficientes) throws Exception {
        /**
         * Jugadas prohibidas.
         */
        if (grilla[i][j] != Marca.N || m == Marca.N) throw new Exception("Jugada prohibida");

        /**
         * Asigno X o O
         */
        grilla[i][j] = m;
        
        //Marca de mi rival
        Marca r;
        if(m == Marca.O){
            r = Marca.X;
        }
        else{
            r = Marca.O;
        }
        
        /*  Busco el mejor vop luego de mi jugada y la de mi rival,
            en caso de que solo haya un casillero libre no analizo la jugada del rival 
        */
        if (analizar_rival){
            EstadoTablero t;
            EstadoTablero mejor_t = null;
            int mejorFilaR = 0;
            int mejorColumnaR = 0;
            for(int k= 0; k<SIZE; k++){
                for(int l = 0; l<SIZE; l++){
                    if(grilla[k][l] == Marca.N){
                        t = setMarca(k, l, r, true, false, coeficientes);
                        if(mejor_t == null){
                            mejor_t = t;
                            mejorFilaR = k;
                            mejorColumnaR = l;  
                        }else{
                            if(mejor_t.VOp < t.VOp){
                                mejor_t = t;
                                mejorFilaR = k;
                                mejorColumnaR = l;                                
                            }
                        }
                    }
                }
            }
            
            //Si el rival tiene una jugada para hacer devuelvo el mejor vop considerando esa jugada
            if(mejor_t != null){
                grilla[mejorFilaR][mejorColumnaR] = r;
                double t2 = getVopTablero(m, coeficientes);
                grilla[mejorFilaR][mejorColumnaR] = Marca.N;
                grilla[i][j] = Marca.N;
                return new EstadoTablero(t2, false, false, null);
            }else{
                grilla[i][j] = Marca.N;
                return setMarca(i, j, m, true, false, coeficientes);
            }
        }
        /*Analizo el tablero sin preocuparme por el siguiente movimiento */
        else{
        
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
                        if(this.grilla[k][l] == r){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(this.grilla[k][l] != Marca.N){
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
                        if(this.grilla[l][k] == r){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(this.grilla[l][k] != Marca.N){
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
                    if(this.grilla[k][k] == r){
                          cantRestantesParaGanar -= 1;
                        }
                        else if(this.grilla[k][k] != Marca.N){ 
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
                    if(this.grilla[k][SIZE-k-1] == r){
                          cantRestantesParaGanar -= 1;
                        }
                        else if(this.grilla[k][k] != Marca.N){   
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
            boolean empate = ((cantFichasM + cantFichasR == SIZE * SIZE) && (minimoParaGanarM != 0) && (minimoParaGanarR != 0));
            boolean findejuego = ((minimoParaGanarM == 0) || (minimoParaGanarR == 0) || empate);
            Marca ganador = null;
            if (findejuego && !empate) {
                Marca jugador = m;
                Marca oponente = (m == Marca.X) ? Marca.O : Marca.X;
                if (minimoParaGanarM == 0) ganador = jugador;
                if (minimoParaGanarR == 0) ganador = oponente;
            }

            //Seteo los resultados
            return new EstadoTablero(coeficientes.w0 * cantLineasInutilesM +
                coeficientes.w1 * cantLineasInutilesR +
                coeficientes.w2 * minimoParaGanarM +
                coeficientes.w3 * minimoParaGanarR +
                coeficientes.w4 * cantFichasM +
                coeficientes.w5 * cantFichasR + coeficientes.indep, findejuego, empate, ganador);
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

    public double getVopTablero(Marca m, Coeficientes coeficientes) {
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
            if (minimoParaGanarM == 0) return 100;
            else if (minimoParaGanarR == 0){ return -100;
            }else if (cantFichasM+cantFichasR == SIZE*SIZE){ return 0;
            }else{
                return (coeficientes.w0 * cantLineasInutilesM +
                    coeficientes.w1 * cantLineasInutilesR +
                    coeficientes.w2 * minimoParaGanarM +
                    coeficientes.w3 * minimoParaGanarR +
                    coeficientes.w4 * cantFichasM +
                    coeficientes.w5 * cantFichasR + coeficientes.indep);
            }
    }
    public enum Marca {
        X, O, N
    }
}
