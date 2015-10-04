/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practico4.E6;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author santiago
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            float MIN_MU = 0.0000001f;
            int MAX_IT = 500;
            float STEP_MU = 0.00001f;  //TODO: ver esto
            int SIZE = 3;

            //Estadisticas
            int countGanoX = 0;
            int countGanoO = 0;
            int countEmpate = 0;

            //declaro los jugadores
            JugadorP1 jugador1 = new JugadorP1(null, Tablero.Marca.X);
            JugadorRefuerzo jugador2 = new JugadorRefuerzo(null, Tablero.Marca.O, 0.8);
            //Partidas
            float mu = 0.01f;
            int cantIteraciones = 0;
            
            while (mu > MIN_MU && cantIteraciones < MAX_IT) {

                //Inicializo
                Tablero tablero = new Tablero(SIZE);
                jugador1.tablero = tablero;
                jugador2.tablero = tablero;
                EstadoTablero estadoTablero = tablero.getEstadoTablero(Tablero.Marca.X, jugador1.coeficientes);
                EstadoTablero estadoTableroPrueba;
                Map<Integer, Double> ejemplosVent = new HashMap();
                Map<Integer, Double> ejemplosVop = new HashMap();


                //Movimientos
                double VEnt;
                int cant_movimientos = 0;
                while (!estadoTablero.finalizado) {


                    /**
                     * JUGADOR PRACTICO 1
                     */
                    //System.out.println("TURNO DE: " + jugador);


                    //Mejor pos.
                    double mejorVop = -1;
                    int mejori = -1;
                    int mejorj = -1;


                    //2- Calcular posicion para movida probando.
                    for (int i = 0; i < tablero.SIZE; i++) {
                        for (int j = 0; j < tablero.SIZE; j++) {
                            try {
                                estadoTableroPrueba = jugador1.setMarca(i, j, Tablero.Marca.X, true, true);
                                if (mejorVop == -1 || estadoTableroPrueba.VOp > mejorVop) {
                                    mejorVop = estadoTableroPrueba.VOp;
                                    mejori = i;
                                    mejorj = j;
                                }
                            } catch (Exception jugadaProhibidaIgnored) {
                            }
                        }
                    }

                    //3- Mover
                    try{
                        estadoTablero = jugador1.setMarca(mejori, mejorj, Tablero.Marca.X, false, false);
                    } catch(Exception jugadaProhibidaIgnore) {

                    }

                    //imprimir ta-te-ti
                    //tablero.imprimir();

                    //Si soy X actualizo VopUltimoTurno
                    double VOpUltimoTurno = estadoTablero.VOp;
                    ejemplosVop.put(cant_movimientos, VOpUltimoTurno);


                    if (!estadoTablero.finalizado) {

                        /**
                         * JUGADOR PRACTICO 4
                         */
                        //System.out.println("TURNO DE: " + oponente);


                        //Mejor pos.
                        mejorVop = -1;
                        mejori = -1;
                        mejorj = -1;


                        //2- Calcular posicion para movida probando.
                        

                        //3- Mover
                        

                        //imprimir ta-te-ti
                        //tablero.imprimir();


                    }


                    if (estadoTablero.finalizado && !estadoTablero.empate) {
                        if (estadoTablero.ganador == Tablero.Marca.X) {

                            VEnt = 100;
                            countGanoX++;

                        } else {

                            VEnt = -100;
                            countGanoO++;
                        }

                        System.out.println("GANO: " + estadoTablero.ganador);

                    } else if (estadoTablero.empate) {

                        VEnt = 0;
                        countEmpate++;
                        System.out.println("EMPATE!!! ");

                    } else {

                        //Calculo VEnt desde el punto de vista de X usando el Vop del ultimo turno.
                        EstadoTablero trucho = tablero.getEstadoTablero(Tablero.Marca.X, jugador1.coeficientes);
                        VEnt = trucho.VOp;
                    }
                    ejemplosVent.put(cant_movimientos, VEnt);
                    //actualizo wi's con minimos cuadrados
                    
                    
                    //imprimo datos de jugada.
                    //coeficientes.imprimir();
                    //Actualizar MU
                    mu -= STEP_MU;
                    cant_movimientos++;

                }
                for(int i=0; i<=cant_movimientos; i++){
                    jugador1.coeficientes.actualizarCoeficientes(tablero, mu, ejemplosVent.get(i), ejemplosVop.get(i), Tablero.Marca.X);
                }
                cantIteraciones++;

            }

            System.out.println("########################");
            int cant = countGanoO + countGanoX + countEmpate;
            System.out.println("Cantidad de juegos: " + cant);
            System.out.println("El jugador del practico 1 gano " + countGanoX + " veces.");
            System.out.println("El jugador del practico 4 gano " + countGanoO + " veces.");
            System.out.println("Empataron " + countEmpate + " veces.");
            System.out.println("Coeficientes del jugador del practico 1: ");
            jugador1.coeficientes.imprimir();
            System.out.println("########################");



        }catch(Exception e){
    
        }
    }
    
    
}
