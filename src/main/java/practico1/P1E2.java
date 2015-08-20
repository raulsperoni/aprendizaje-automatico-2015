package practico1;

import java.util.Random;

/**
 * Created by RSperoni on 17/08/2015.
 * Logica para TA-TE-TI
 */
public class P1E2 {

    static float MIN_MU = 0.1f;
    static int MAX_IT = 500;
    static float STEP_MU = 0.0001f;  //TODO: ver esto

    public void run(final int SIZE) throws Exception {

        //declaro wi's
        Coeficientes coeficientes = new Coeficientes();

        //Partidas
        float mu = 0.0f;
        int cantIteraciones = 0;
        while (mu < MIN_MU || cantIteraciones > MAX_IT) {

            //Inicializo
            Tablero tablero = new Tablero(SIZE);

            //variable jugador;
            Tablero.Marca jugador;
            //variable oponente
            Tablero.Marca oponente;

            //Sortear quien empieza,
            Random r = new Random();
            if (r.nextBoolean()) {
                jugador = Tablero.Marca.X;
            } else jugador = Tablero.Marca.O;


            //Movimientos
            boolean juegoFinalizado = false;
            int VEnt = 0;
            double VOp = 0.0;
            while (!juegoFinalizado) {

                //Mejor pos.
                double mejorVop = -1;
                int mejori = -1;
                int mejorj = -1;
                
                //Peor pos(mejor posición del oponente)
                double menorVop = 101;

                //2- Calcular posicion para movida probando.
                for (int i = 0; i < tablero.SIZE; i++) {
                    for (int j = 0; j < tablero.SIZE; j++) {
                        try {
                            EstadoTablero estadoTableroPrueba = tablero.setMarca(i, j, jugador, true, coeficientes);
                            if (mejorVop == -1 || estadoTableroPrueba.VOp > mejorVop) {
                                mejorVop = estadoTableroPrueba.VOp;
                                mejori = i;
                                mejorj = j;
                            }
                        } catch (Exception jugadaProhibidaIgnored) {}
                    }


                    //3- Mover
                    EstadoTablero estadoTablero = tablero.setMarca(mejori, mejorj, jugador, false, coeficientes);

                    //imprimir ta-te-ti
                    tablero.imprimir();

                    //Gano jugador?
                    juegoFinalizado = estadoTablero.finalizado;
                    VOp = estadoTablero.VOp;
                    if (juegoFinalizado) {
                        if (jugador == Tablero.Marca.X) {
                            //TODO: asignar VEnt segun estado del tablero...
                            //VEnt = tablero.getVEnt();  //TODO:
                        } else {
                            //TODO: asignar VEnt segun estado del tablero...	
                        }
                    }
                    else
                    {//Calcular Vent como Vop(miproximoturno)
					//Vent=Calcular jugada oponente
					
					//Obtengo oponente
					if (jugador == Tablero.Marca.X) oponente = Tablero.Marca.O;
					else oponente = Tablero.Marca.X;
					
					//Busco el minimo Vop(mejor jugada del oponente)
					for (int i = 0; i < tablero.SIZE; i++) {
						for (int j = 0; j < tablero.SIZE; j++) {
							try {
								EstadoTablero estadoTableroPrueba = tablero.setMarca(i, j, oponente, true, coeficientes);
								if (menorVop == 101 || estadoTableroPrueba.VOp < menorVop) {
									menorVop = estadoTableroPrueba.VOp;
								}
							} catch (Exception jugadaProhibidaIgnored) {}
						}
					}
					VEnt = menorVop;
					//actualizo wi's con minimos cuadrados		
					coeficientes.actualizarCoeficientes(tablero, mu, VEnt, VOp);

					//imprimo datos de jugada.
					coeficientes.imprimir();

					//Actualizar MU
					mu -= STEP_MU;
					}
					
                    //cambiarJugador
                    if (jugador == Tablero.Marca.X) jugador = Tablero.Marca.O;
                    else jugador = Tablero.Marca.X;


                }
                cantIteraciones++;

            }

        }
    }
}
