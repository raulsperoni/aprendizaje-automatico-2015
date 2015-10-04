package practico4.E6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
            int MAX_IT = 100;
            float STEP_MU = 0.00001f;  //TODO: ver esto
            int SIZE = 3;

            //Estadisticas
            int countGanoX = 0;
            int countGanoO = 0;
            int countEmpate = 0;

            //declaro los jugadores, el jugador1 va a tener coeficientes predefinidos por los resultados del practico 1
            JugadorP1 jugador1 = new JugadorP1(null, Tablero.Marca.X);
            jugador1.coeficientes.w0 = 2.9353569f;
            jugador1.coeficientes.w1 = -0.6056756f;
            jugador1.coeficientes.w2 = 1.9616599f;
            jugador1.coeficientes.w3 = 1.2146791f;
            jugador1.coeficientes.w4 = -1.3071471f;
            jugador1.coeficientes.w5 = -4.862419f;
            jugador1.coeficientes.indep = 0.46656638f;
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
                List<List<Double>> ejemplos = new ArrayList();
                List<Double> ejemplosVop = new ArrayList();


                //Movimientos
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
                        jugador1.setMarca(mejori, mejorj, Tablero.Marca.X, false, false);
                        estadoTablero = tablero.getEstadoTablero(Tablero.Marca.X, jugador1.coeficientes);
                    } catch(Exception jugadaProhibidaIgnore) {
                        jugadaProhibidaIgnore.printStackTrace();
                    }

                    //imprimir ta-te-ti
                    //tablero.imprimir();

                    //Guardo un ejemplo para que el jugador con red neuronal entrene al terminar la partida
                    List<Double> inputs = new ArrayList();
                    inputs.add((double) jugador1.tablero.cantFichasO);
                    inputs.add((double) jugador1.tablero.cantFichasX);
                    inputs.add((double) jugador1.tablero.cantLineasInutilesParaO);
                    inputs.add((double) jugador1.tablero.cantLineasInutilesParaX);
                    inputs.add((double) jugador1.tablero.cantMinimaRestanteParaGanarO);
                    inputs.add((double) jugador1.tablero.cantMinimaRestanteParaGanarX);
                    
                    ejemplos.add(inputs);
                    ejemplosVop.add(estadoTablero.VOp);


                    if (!estadoTablero.finalizado) {

                        /**
                         * JUGADOR PRACTICO 4
                         */
                        //System.out.println("TURNO DE: " + oponente);

                        //2- Calculo la probabilidad ACUMULADA de realizar cada movida probando
                        Map<Integer, Double> probabilidades = new HashMap();
                        probabilidades.put(0, 0d);
                        Map<Integer, Integer> posicion = new HashMap();
                        posicion.put(0, 0);
                        
                        double total = 0;
                        int contador = 1;
                        for(int i=0; i<tablero.SIZE; i++){
                            for(int j=0; j<tablero.SIZE; j++){
                                if(tablero.grilla[i][j] == Tablero.Marca.N){
                                    double aux = jugador2.setMarca(i, j,jugador2.marca, true, true);
                                    probabilidades.put(contador, probabilidades.get(contador-1) + Math.exp(aux));
                                    total=probabilidades.get(contador);
                                    posicion.put(contador, i*10 + j);
                                    contador++;
                                }
                            }
                        }
                        

                        //3- Muevo con cierto margen de azar para balancear explotacion y exploracion
                        Random r = new Random();
                        double p = r.nextDouble();
                        boolean bandera = false;
                        int contador2 = -1;
                        while((!bandera) && (probabilidades.get(contador2+1)!=null)){
                            contador2++;
                            bandera = p<(probabilidades.get(contador2))/total;
                        }
                        /*System.out.println(String.format("%10s", p));
                        System.out.println(String.format("%10s", contador2));
                        System.out.println(String.format("%10s", posicion.size()));
                        System.out.println(String.format("%10s", probabilidades.size()));*/
                        double posicionJ2 = posicion.get(contador2)%10d;
                        int posicionJ = (int) posicionJ2;
                        double posicionI2 = posicion.get(contador2)/10d;
                        int posicionI = (int) posicionI2;
                        if(contador2 != 0){
                            jugador2.setMarca(posicionI, posicionJ, jugador2.marca, false, false);
                            estadoTablero = tablero.getEstadoTablero(Tablero.Marca.O, jugador1.coeficientes);
                        }

                        //imprimir ta-te-ti
                        //tablero.imprimir();


                    }


                    if (estadoTablero.finalizado && !estadoTablero.empate) {
                        if (estadoTablero.ganador == Tablero.Marca.X) {
                            countGanoX++;
                            //tablero.imprimir();
                        } else {
                            countGanoO++;
                            //tablero.imprimir();
                        }
                        System.out.println("GANO: " + estadoTablero.ganador);
                    } else if (estadoTablero.empate) {
                        countEmpate++;
                        System.out.println("EMPATE!!! ");
                        //tablero.imprimir();
                    } else {
                        //Calculo VEnt desde el punto de vista de X usando el Vop del ultimo turno.
                        EstadoTablero trucho = tablero.getEstadoTablero(Tablero.Marca.X, jugador1.coeficientes);
                    }                    
                    
                    //imprimo datos de jugada.
                    //coeficientes.imprimir();
                    //Actualizar MU
                    mu -= STEP_MU;

                }
                //Actualizo los coeficientes de la red neuronal con backpropagation
                jugador2.red.backpropagation(ejemplos, ejemplosVop);
                
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
            e.printStackTrace();
        }
    }
       
}
