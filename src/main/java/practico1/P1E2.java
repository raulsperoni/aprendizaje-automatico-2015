package practico1;

/**
 * Created by RSperoni on 17/08/2015.
 * Logica para TA-TE-TI
 */
public class P1E2 {

    static float MIN_MU = 0.01f;
    static int MAX_IT = 10;
    static float STEP_MU = 0.001f;  //TODO: ver esto

    public void run(final int SIZE) throws Exception {

        //declaro wi's
        Coeficientes coeficientes = new Coeficientes();

        //Partidas
        float mu = 0.1f;
        int cantIteraciones = 0;
        while (mu > 0.0001f || cantIteraciones < MAX_IT) {

            //Inicializo
            Tablero tablero = new Tablero(SIZE);

            //variable jugador;
            Tablero.Marca jugador = Tablero.Marca.X;
            //variable oponente
            Tablero.Marca oponente = Tablero.Marca.O;

            //Movimientos
            boolean juegoFinalizado = false;
            double VEnt = 0.0;
            double VOp = 0.0;
            double VOpUltimoTurno = -1;
            while (!juegoFinalizado) {

                //Calculo VEnt desde el punto de vista de X usando el Vop del ultimo turno.
                if (jugador == Tablero.Marca.X && VOpUltimoTurno != -1) {
                    VEnt = tablero.getEstadoTablero(Tablero.Marca.X, coeficientes).VOp;

                    //actualizo wi's con minimos cuadrados
                    coeficientes.actualizarCoeficientes(tablero, mu, VEnt, VOpUltimoTurno, jugador);

                    //imprimo datos de jugada.
                    //coeficientes.imprimir();

                    //Actualizar MU
                    //mu -= STEP_MU;

                }


                //Mejor pos.
                double mejorVop = -1;
                int mejori = -1;
                int mejorj = -1;


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
                        } catch (Exception jugadaProhibidaIgnored) {
                        }
                    }
                }

                //3- Mover
                EstadoTablero estadoTablero = tablero.setMarca(mejori, mejorj, jugador, false, coeficientes);

                //Si soy X actualizo VopUltimoTurno
                if (jugador == Tablero.Marca.X) VOpUltimoTurno = estadoTablero.VOp;

                //imprimir ta-te-ti
                System.out.println("TURNO DE: " + jugador.name());
                tablero.imprimir();

                //Gano jugador?
                juegoFinalizado = estadoTablero.finalizado;
                VOp = estadoTablero.VOp;
                if (juegoFinalizado && !estadoTablero.empate) {
                    if (jugador == Tablero.Marca.X) {

                        VEnt = 100;

                        //actualizo wi's con minimos cuadrados
                        coeficientes.actualizarCoeficientes(tablero, mu, VEnt, VOpUltimoTurno, jugador);

                        //imprimo datos de jugada.
                        coeficientes.imprimir();

                        //Actualizar MU
                        //mu -= STEP_MU;

                        System.out.println("GANO: " + Tablero.Marca.X.name());

                    } else {

                        VEnt = -100;

                        //actualizo wi's con minimos cuadrados
                        coeficientes.actualizarCoeficientes(tablero, mu, VEnt, VOpUltimoTurno, Tablero.Marca.X);

                        //imprimo datos de jugada.
                        coeficientes.imprimir();

                        //Actualizar MU
                        //mu -= STEP_MU;

                        System.out.println("GANO: " + Tablero.Marca.O.name());


                    }
                } else if (estadoTablero.empate) {

                    VEnt = 0;

                    //actualizo wi's con minimos cuadrados
                    coeficientes.actualizarCoeficientes(tablero, mu, VEnt, VOpUltimoTurno, Tablero.Marca.X);

                    //imprimo datos de jugada.
                    coeficientes.imprimir();

                    //Actualizar MU
                    //mu -= STEP_MU;

                    System.out.println("EMPATE!!! ");

                }


                //cambiarJugador
                if (jugador == Tablero.Marca.X) jugador = Tablero.Marca.O;
                else jugador = Tablero.Marca.X;


            }
            cantIteraciones++;

        }

    }
}

