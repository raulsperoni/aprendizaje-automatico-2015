package practico1;

/**
 * Created by RSperoni on 17/08/2015.
 * Logica para TA-TE-TI
 */
public class P1E2 {

    static float MIN_MU = 0.0000001f;
    static int MAX_IT = 5000;
    static float STEP_MU = 0.00001f;  //TODO: ver esto


    public void run(final int SIZE) throws Exception {

        //Estadisticas
        int countGanoX = 0;
        int countGanoO = 0;
        int countEmpate = 0;

        //declaro wi's
        Coeficientes coeficientes = new Coeficientes();

        //Partidas
        float mu = 0.01f;
        int cantIteraciones = 0;
        while (mu > MIN_MU && cantIteraciones < MAX_IT) {

            //Inicializo
            Tablero tablero = new Tablero(SIZE);

            //variable jugador;
            Tablero.Marca jugador = Tablero.Marca.X;
            //variable oponente
            Tablero.Marca oponente = Tablero.Marca.O;
            Tablero.Marca ultimoJugador = null;

            EstadoTablero estadoTablero = tablero.getEstadoTablero(Tablero.Marca.X, coeficientes);
            EstadoTablero estadoTableroPrueba = null;


            //Movimientos
            double VEnt = 0.0;
            while (!estadoTablero.finalizado) {


                /**
                 * JUGADOR
                 */
                ultimoJugador = jugador;
                //System.out.println("TURNO DE: " + jugador);


                //Mejor pos.
                double mejorVop = -1;
                int mejori = -1;
                int mejorj = -1;


                //2- Calcular posicion para movida probando.
                for (int i = 0; i < tablero.SIZE; i++) {
                    for (int j = 0; j < tablero.SIZE; j++) {
                        try {
                            estadoTableroPrueba = tablero.setMarca(i, j, jugador, true, true, coeficientes);
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
                estadoTablero = tablero.setMarca(mejori, mejorj, jugador, false, false, coeficientes);

                //imprimir ta-te-ti
                //tablero.imprimir();

                //Si soy X actualizo VopUltimoTurno
                double VOpUltimoTurno = estadoTablero.VOp;


                if (!estadoTablero.finalizado) {

                    /**
                     * OPONENTE
                     */
                    ultimoJugador = oponente;
                    //System.out.println("TURNO DE: " + oponente);


                    //Mejor pos.
                    mejorVop = -1;
                    mejori = -1;
                    mejorj = -1;


                    //2- Calcular posicion para movida probando.
                    for (int i = 0; i < tablero.SIZE; i++) {
                        for (int j = 0; j < tablero.SIZE; j++) {
                            try {
                                estadoTableroPrueba = tablero.setMarca(i, j, oponente, true, true, coeficientes);
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
                    estadoTablero = tablero.setMarca(mejori, mejorj, oponente, false, false, coeficientes);

                    //imprimir ta-te-ti
                    //tablero.imprimir();


                }


                if (estadoTablero.finalizado && !estadoTablero.empate) {
                    if (estadoTablero.ganador == Tablero.Marca.X) {

                        VEnt = 1;
                        countGanoX++;

                    } else {

                        VEnt = -1;
                        countGanoO++;
                    }

                    System.out.println("GANO: " + estadoTablero.ganador);

                } else if (estadoTablero.empate) {

                    VEnt = 0;
                    countEmpate++;
                    System.out.println("EMPATE!!! ");

                } else {

                    //Calculo VEnt desde el punto de vista de X usando el Vop del ultimo turno.
                    EstadoTablero trucho = tablero.getEstadoTablero(jugador, coeficientes);
                    VEnt = trucho.VOp;



                }
                //actualizo wi's con minimos cuadrados
                coeficientes.actualizarCoeficientes(tablero, mu, VEnt, VOpUltimoTurno, jugador);
                //imprimo datos de jugada.
                //coeficientes.imprimir();
                //Actualizar MU
                mu -= STEP_MU;


            }

            cantIteraciones++;

        }

        System.out.println("########################");
        int cant = countGanoO + countGanoX + countEmpate;
        System.out.println("Cantidad de juegos: " + cant);
        System.out.println("X gano " + countGanoX + " veces.");
        System.out.println("O gano " + countGanoO + " veces.");
        System.out.println("Empate " + countEmpate + " veces.");
        System.out.println("Coeficientes: ");
        coeficientes.imprimir();
        System.out.println("########################");



    }
}

