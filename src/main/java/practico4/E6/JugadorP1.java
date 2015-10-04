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
        /* Jugadas prohibidas */
        if (tablero.grilla[i][j] != Tablero.Marca.N || m == Tablero.Marca.N) throw new Exception("Jugada prohibida");

        /* Asigno X o O */
        tablero.setMarca(i, j, marca);
        
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
                tablero.setMarca(mejorFilaR, mejorColumnaR, r);
                double t2 = getVopTablero(m);
                tablero.setMarca(mejorFilaR, mejorColumnaR, Tablero.Marca.N);
                tablero.setMarca(i, j, Tablero.Marca.N);
                return new EstadoTablero(t2, false, false, null);
            }else{
                tablero.setMarca(i, j, Tablero.Marca.N);
                return setMarca(i, j, m, true, false);
            }
        }
        /*Analizo el tablero sin preocuparme por el siguiente movimiento */
        else{
            //Si es de mentira, vuelvo el movimiento para atras.
            if (esDeMentira) {
                tablero.setMarca(i, j, Tablero.Marca.N);
            }
            EstadoTablero estado = tablero.getEstadoTablero(m, coeficientes);
            return estado;
        }
    }

    
    public double getVopTablero(Tablero.Marca m) {
        if(m == Tablero.Marca.X){
            if(tablero.cantMinimaRestanteParaGanarX==0){
                return 100;
            }else if(tablero.cantMinimaRestanteParaGanarO==0){
                return -100;
            }else if(tablero.cantFichasO+tablero.cantFichasX == tablero.SIZE*tablero.SIZE){
                return 0;
            }else{
                return (coeficientes.w0 * tablero.cantLineasInutilesParaX +
                    coeficientes.w1 * tablero.cantLineasInutilesParaO +
                    coeficientes.w2 * tablero.cantMinimaRestanteParaGanarX +
                    coeficientes.w3 * tablero.cantMinimaRestanteParaGanarO +
                    coeficientes.w4 * tablero.cantFichasX +
                    coeficientes.w5 * tablero.cantFichasO + coeficientes.indep);
            }
        }else{
            if(tablero.cantMinimaRestanteParaGanarO==0){
                return 100;
            }else if(tablero.cantMinimaRestanteParaGanarX==0){
                return -100;
            }else if(tablero.cantFichasX+tablero.cantFichasO == tablero.SIZE*tablero.SIZE){
                return 0;
            }else{
                return (coeficientes.w0 * tablero.cantLineasInutilesParaO +
                    coeficientes.w1 * tablero.cantLineasInutilesParaX +
                    coeficientes.w2 * tablero.cantMinimaRestanteParaGanarO +
                    coeficientes.w3 * tablero.cantMinimaRestanteParaGanarX +
                    coeficientes.w4 * tablero.cantFichasO +
                    coeficientes.w5 * tablero.cantFichasX + coeficientes.indep);
            }
        }
    }    
}
