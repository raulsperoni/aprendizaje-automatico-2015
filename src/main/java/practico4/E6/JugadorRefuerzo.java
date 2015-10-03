/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practico4.E6;

import practico4.RedNeuronal;
import practico4.Neurona;
import practico4.Sigmoid;
import practico4.Tanh;

/**
 *
 * @author santiago
 */
public class JugadorRefuerzo {
    
    public Tablero tablero;
    public Tablero.Marca marca;
    public RedNeuronal red;

    public JugadorRefuerzo(Tablero tablero, Tablero.Marca marca) throws Exception{
        this.tablero = tablero;
        this.marca = marca;
        this.red = new RedNeuronal(6, 2, 6, 0.1, 50000, Sigmoid.class, 0d);
    }
    
}
