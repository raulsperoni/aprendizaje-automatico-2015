/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practico4.E6;

/**
 *
 * @author santiago
 */
public class JugadorP1 {
    
    public Tablero tablero;
    public Coeficientes coeficientes;
    public Tablero.Marca marca;

    
    public JugadorP1(Tablero tablero, Tablero.Marca marca) {
        this.tablero = tablero;
        this.marca = marca;
        this.coeficientes = new Coeficientes();
    }
       
    public EstadoTablero setMarca(int i, int j, Tablero.Marca m, boolean esDeMentira, boolean analizar_rival) throws Exception {
        /**
         * Jugadas prohibidas.
         */
        if (tablero.grilla[i][j] != Tablero.Marca.N || m == Tablero.Marca.N) throw new Exception("Jugada prohibida");

        /**
         * Asigno X o O
         */
        tablero.grilla[i][j] = marca;
        
        //Marca de mi rival
        Tablero.Marca r;
        if(m == Tablero.Marca.O){
            r = Tablero.Marca.X;
        }
        else{
            r = Tablero.Marca.O;
        }
        
        /*  Busco el mejor vop luego de mi jugada y la de mi rival,
            en caso de que solo haya un casillero libre no analizo la jugada del rival 
        */
        if (analizar_rival){
            EstadoTablero t;
            EstadoTablero mejor_t = null;
            int mejorFilaR = 0;
            int mejorColumnaR = 0;
            for(int k= 0; k<tablero.SIZE; k++){
                for(int l = 0; l<tablero.SIZE; l++){
                    if(tablero.grilla[k][l] == Tablero.Marca.N){
                        t = setMarca(k, l, r, true, false);
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
                tablero.grilla[mejorFilaR][mejorColumnaR] = r;
                double t2 = getVopTablero();
                tablero.grilla[mejorFilaR][mejorColumnaR] = Tablero.Marca.N;
                tablero.grilla[i][j] = Tablero.Marca.N;
                return new EstadoTablero(t2, false, false, null);
            }else{
                tablero.grilla[i][j] = Tablero.Marca.N;
                return setMarca(i, j, m, true, false);
            }
        }
        /*Analizo el tablero sin preocuparme por el siguiente movimiento */
        else{
        
            int cantLineasInutilesM = 0;
            int minimoParaGanarM = tablero.SIZE;
            int cantLineasInutilesR = 0;
            int minimoParaGanarR = tablero.SIZE;
            int cantFichasM = 0;
            int cantFichasR = 0;

            //Analizo las filas para el jugador M
            for (int k = 0; k<tablero.SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = tablero.SIZE;
                for (int l = 0; l<tablero.SIZE; l++){
                    if(esPosibleGanar){
                        if(tablero.grilla[k][l] == m){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(tablero.grilla[k][l] != Tablero.Marca.N){
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
            for (int k = 0; k<tablero.SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = tablero.SIZE;
                for (int l = 0; l<tablero.SIZE; l++){
                    if(esPosibleGanar){
                        if(tablero.grilla[k][l] == m){
                            cantLineasInutilesR += 1;
                            esPosibleGanar = false;
                        }
                        else if(tablero.grilla[k][l] != Tablero.Marca.N){
                            cantRestantesParaGanar -= 1;
                        }
                    }
                }
                if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
                }
            }


            //Analizo las columnas para el jugador M
            for (int k = 0; k<tablero.SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = tablero.SIZE;
                for (int l = 0; l<tablero.SIZE; l++){
                    if(esPosibleGanar){
                        if(tablero.grilla[l][k] == m){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(tablero.grilla[l][k] != Tablero.Marca.N){
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
            for (int k = 0; k<tablero.SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = tablero.SIZE;
                for (int l = 0; l<tablero.SIZE; l++){
                    if(esPosibleGanar){
                        if(tablero.grilla[l][k] == m){
                            cantLineasInutilesR += 1;
                            esPosibleGanar = false;
                        }
                        else if(tablero.grilla[l][k] != Tablero.Marca.N){
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
            int cantRestantesParaGanar = tablero.SIZE;
            for (int k = 0; k<tablero.SIZE; k++){
                if(esPosibleGanar){
                    if(tablero.grilla[k][k] == m){
                           cantRestantesParaGanar -= 1; 
                        }
                        else if(tablero.grilla[k][k] != Tablero.Marca.N){
                            cantLineasInutilesM += 1;
                            esPosibleGanar = false;
                        }
                }
            }
            if(minimoParaGanarM > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarM = cantRestantesParaGanar;
            }

            esPosibleGanar = true;
            cantRestantesParaGanar = tablero.SIZE;
            for (int k = 0; k<tablero.SIZE; k++){
                if(esPosibleGanar){
                    if(tablero.grilla[k][tablero.SIZE-k-1] == m){
                           cantRestantesParaGanar -= 1; 
                        }
                        else if(tablero.grilla[k][k] != Tablero.Marca.N){
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
            cantRestantesParaGanar = tablero.SIZE;
            for (int k = 0; k<tablero.SIZE; k++){
                if(esPosibleGanar){
                    if(tablero.grilla[k][k] == m){
                        cantLineasInutilesR += 1;
                        esPosibleGanar = false;   

                        }
                        else if(tablero.grilla[k][k] != Tablero.Marca.N){
                            cantRestantesParaGanar -= 1; 
                        }
                }
            }
            if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
            }

            esPosibleGanar = true;
            cantRestantesParaGanar = tablero.SIZE;
            for (int k = 0; k<tablero.SIZE; k++){
                if(esPosibleGanar){
                    if(tablero.grilla[k][tablero.SIZE-k-1] == m){
                        cantLineasInutilesR += 1;
                        esPosibleGanar = false;   

                        }
                        else if(tablero.grilla[k][k] != Tablero.Marca.N){
                            cantRestantesParaGanar -= 1; 
                        }
                }
            }
            if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
            }

            //Cuento la cantidad de fichas
            for(int k = 0; k<tablero.SIZE; k++){
                for(int l = 0; l<tablero.SIZE; l++){
                    if(tablero.grilla[k][l] == m){
                        cantFichasM += 1;
                    }
                    else if(tablero.grilla[k][l] != Tablero.Marca.N){
                        cantFichasR += 1;
                    }
                }
            }

            //Si es marca posta, actualizo atributos.
            if (!esDeMentira) {

                /**
                 * Actualizo todo
                 */
                if (m == Tablero.Marca.X){
                    tablero.cantFichasX++;
                    tablero.cantMinimaRestanteParaGanarX = minimoParaGanarM;
                    tablero.cantMinimaRestanteParaGanarO = minimoParaGanarR;
                    tablero.cantLineasInutilesParaX = cantLineasInutilesM;
                    tablero.cantLineasInutilesParaO = cantLineasInutilesR;
                }    
                else {
                    tablero.cantFichasO++;
                    tablero.cantMinimaRestanteParaGanarO = minimoParaGanarM;
                    tablero.cantMinimaRestanteParaGanarX = minimoParaGanarR;
                    tablero.cantLineasInutilesParaO = cantLineasInutilesM;
                    tablero.cantLineasInutilesParaX = cantLineasInutilesR;
                }
            }
            //Si no es marca posta, si solo esta "probando" saco la marca.
            else {

                /**
                 * Vuelvo atrás X o O
                 */
                tablero.grilla[i][j] = Tablero.Marca.N;
            }

            //TODO: revisar esta condicion de ganar.
            boolean empate = ((cantFichasM + cantFichasR == tablero.SIZE * tablero.SIZE) && (minimoParaGanarM != 0) && (minimoParaGanarR != 0));
            boolean findejuego = ((minimoParaGanarM == 0) || (minimoParaGanarR == 0) || empate);
            Tablero.Marca ganador = null;
            if (findejuego && !empate) {
                Tablero.Marca jugador = m;
                Tablero.Marca oponente = (m == Tablero.Marca.X) ? Tablero.Marca.O : Tablero.Marca.X;
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

    
    public double getVopTablero() {
            int cantLineasInutilesM = 0;
            int minimoParaGanarM = tablero.SIZE;
            int cantLineasInutilesR = 0;
            int minimoParaGanarR = tablero.SIZE;
            int cantFichasM = 0;
            int cantFichasR = 0;

            //Analizo las filas para el jugador M
            for (int k = 0; k<tablero.SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = tablero.SIZE;
                for (int l = 0; l<tablero.SIZE; l++){
                    if(esPosibleGanar){
                        if(tablero.grilla[k][l] == marca){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(tablero.grilla[k][l] != Tablero.Marca.N){
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
            for (int k = 0; k<tablero.SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = tablero.SIZE;
                for (int l = 0; l<tablero.SIZE; l++){
                    if(esPosibleGanar){
                        if(tablero.grilla[k][l] == marca){
                            cantLineasInutilesR += 1;
                            esPosibleGanar = false;
                        }
                        else if(tablero.grilla[k][l] != Tablero.Marca.N){
                            cantRestantesParaGanar -= 1;
                        }
                    }
                }
                if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
                }
            }


            //Analizo las columnas para el jugador M
            for (int k = 0; k<tablero.SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = tablero.SIZE;
                for (int l = 0; l<tablero.SIZE; l++){
                    if(esPosibleGanar){
                        if(tablero.grilla[l][k] == marca){
                            cantRestantesParaGanar -= 1;
                        }
                        else if(tablero.grilla[l][k] != Tablero.Marca.N){
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
            for (int k = 0; k<tablero.SIZE; k++){
                boolean esPosibleGanar = true;
                int cantRestantesParaGanar = tablero.SIZE;
                for (int l = 0; l<tablero.SIZE; l++){
                    if(esPosibleGanar){
                        if(tablero.grilla[l][k] == marca){
                            cantLineasInutilesR += 1;
                            esPosibleGanar = false;
                        }
                        else if(tablero.grilla[l][k] != Tablero.Marca.N){
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
            int cantRestantesParaGanar = tablero.SIZE;
            for (int k = 0; k<tablero.SIZE; k++){
                if(esPosibleGanar){
                    if(tablero.grilla[k][k] == marca){
                           cantRestantesParaGanar -= 1; 
                        }
                        else if(tablero.grilla[k][k] != Tablero.Marca.N){
                            cantLineasInutilesM += 1;
                            esPosibleGanar = false;
                        }
                }
            }
            if(minimoParaGanarM > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarM = cantRestantesParaGanar;
            }

            esPosibleGanar = true;
            cantRestantesParaGanar = tablero.SIZE;
            for (int k = 0; k<tablero.SIZE; k++){
                if(esPosibleGanar){
                    if(tablero.grilla[k][tablero.SIZE-k-1] == marca){
                           cantRestantesParaGanar -= 1; 
                        }
                        else if(tablero.grilla[k][k] != Tablero.Marca.N){
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
            cantRestantesParaGanar = tablero.SIZE;
            for (int k = 0; k<tablero.SIZE; k++){
                if(esPosibleGanar){
                    if(tablero.grilla[k][k] == marca){
                        cantLineasInutilesR += 1;
                        esPosibleGanar = false;   

                        }
                        else if(tablero.grilla[k][k] != Tablero.Marca.N){
                            cantRestantesParaGanar -= 1; 
                        }
                }
            }
            if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
            }

            esPosibleGanar = true;
            cantRestantesParaGanar = tablero.SIZE;
            for (int k = 0; k<tablero.SIZE; k++){
                if(esPosibleGanar){
                    if(tablero.grilla[k][tablero.SIZE-k-1] == marca){
                        cantLineasInutilesR += 1;
                        esPosibleGanar = false;   

                        }
                        else if(tablero.grilla[k][k] != Tablero.Marca.N){
                            cantRestantesParaGanar -= 1; 
                        }
                }
            }
            if(minimoParaGanarR > cantRestantesParaGanar && esPosibleGanar){
                    minimoParaGanarR = cantRestantesParaGanar;
            }

            //Cuento la cantidad de fichas
            for(int k = 0; k<tablero.SIZE; k++){
                for(int l = 0; l<tablero.SIZE; l++){
                    if(tablero.grilla[k][l] == marca){
                        cantFichasM += 1;
                    }
                    else if(tablero.grilla[k][l] != Tablero.Marca.N){
                        cantFichasR += 1;
                    }
                }
            }
            if (minimoParaGanarM == 0) return 100;
            else if (minimoParaGanarR == 0){ return -100;
            }else if (cantFichasM+cantFichasR == tablero.SIZE*tablero.SIZE){ return 0;
            }else{
                return (coeficientes.w0 * cantLineasInutilesM +
                    coeficientes.w1 * cantLineasInutilesR +
                    coeficientes.w2 * minimoParaGanarM +
                    coeficientes.w3 * minimoParaGanarR +
                    coeficientes.w4 * cantFichasM +
                    coeficientes.w5 * cantFichasR + coeficientes.indep);
            }
    }
    
}
