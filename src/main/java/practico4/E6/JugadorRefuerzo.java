/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practico4.E6;

import java.util.ArrayList;
import java.util.List;
import practico4.RedNeuronal;
import practico4.Sigmoid;

/**
 *
 * @author santiago
 */
public class JugadorRefuerzo {
    
    public Tablero tablero;
    public Tablero.Marca marca;
    public RedNeuronal red;
    public double gamma;

    public JugadorRefuerzo(Tablero tablero, Tablero.Marca marca, double gamma) throws Exception{
        this.tablero = tablero;
        this.marca = marca;
        this.gamma = gamma;
        //Por ahora puse una input pero solo porque no se que valores poner, esto se puede cambiar
        this.red = new RedNeuronal(6, 1, 6, 0.1, 50000, Sigmoid.class, 0d);
    }
    
    public double setMarca(int i, int j, Tablero.Marca m, boolean esMentira, boolean analizarRival) throws Exception{
        //Si la jugada es valida
        if(tablero.grilla[i][j] == Tablero.Marca.N){
            //preveo la mejor jugada de mi rival en su proximo turno
            tablero.setMarca(i, j, m);
            
            if(analizarRival){
                Tablero.Marca rival;
                if(marca == Tablero.Marca.O){
                    rival = Tablero.Marca.X;
                }else{
                    rival = Tablero.Marca.O;
                }
                double mejorQop = -200;
                for(int k=0; k<tablero.SIZE; k++){
                    for(int l=0; l<tablero.SIZE; l++){
                        if(tablero.grilla[k][l] == Tablero.Marca.N){
                            double aux = setMarca(k, l, rival, true, false);
                            if(aux>mejorQop){
                                mejorQop = aux;
                            }
                        }
                    }
                }
                if(esMentira){
                    tablero.setMarca(i, j, Tablero.Marca.N);
                }
                return -mejorQop;
            
            }else{

                //Estimo el valor de V
                List<Double> ejemplo = new ArrayList();
                ejemplo.add((double) tablero.cantFichasO);
                ejemplo.add((double) tablero.cantFichasX);
                ejemplo.add((double) tablero.cantLineasInutilesParaO);
                ejemplo.add((double) tablero.cantLineasInutilesParaX);
                ejemplo.add((double) tablero.cantMinimaRestanteParaGanarO);
                ejemplo.add((double) tablero.cantMinimaRestanteParaGanarX);
                List<Double> Vop = red.evaluar(ejemplo);

                //Determino la recompensa
                double recompensa;
                if(m == Tablero.Marca.O){
                    if(tablero.cantMinimaRestanteParaGanarO == 0){
                        recompensa = 100;
                    }else if(tablero.cantMinimaRestanteParaGanarX <= 1){
                        recompensa = -100;
                    }else{
                        recompensa = 0;
                    }
                }else{
                    if(tablero.cantMinimaRestanteParaGanarX == 0){
                        recompensa = 100;
                    }else if(tablero.cantMinimaRestanteParaGanarO <= 1){
                        recompensa = -100;
                    }else{
                        recompensa = 0;
                    }
                }

                //Si la jugada era de prueba vuelvo para atras
                if(esMentira){
                    tablero.setMarca(i, j, Tablero.Marca.N);
                }

                return recompensa+gamma*Vop.get(0);
            }
        }else{
            throw new Exception("Jugada prohibida");
        }
    }
}
