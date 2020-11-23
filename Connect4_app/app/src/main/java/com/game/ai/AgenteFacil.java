package com.game.ai;

import com.game.Logic;

public class AgenteFacil implements AiPlayer {

    public AgenteFacil(){

    }

    @Override
    public int[] getAiMove(char jugador, Logic logic) {
        int jugada[] = new int[3];
        int posicionJugada[] = new int[2];
        int columnaEscogida;
        int colAux;
        char oponente;
        char[][] tablero = logic.getTablero();

        // Comprueba si él puede hacer 4 en raya
        jugada = AgenteFacil.posibleGanar(jugador, tablero);
        if (jugada[0] == 1) {
            // Puede ganar escogiendo la columna almacenada en jugarGanar[1]
            columnaEscogida = jugada[1];
            logic.introducirFicha(jugador, columnaEscogida);
            colAux = columnaEscogida + 1;
            System.out.println("El Agente puede ganar si introduce en la columna " + colAux);
            logic.actualizarTablero(tablero);

            posicionJugada[0] = columnaEscogida;
            posicionJugada[1] = jugada[2];
            return posicionJugada;
        } else {
            // Si no puede ganar él, comprueba si oponente puede hacer 4 en raya para
            // evitarlo
            if (jugador == '0') {
                oponente = '+';
            } else {
                oponente = '0';
            }
            jugada = AgenteFacil.posibleGanar(oponente, tablero);
            if (jugada[0] == 1) {
                // Su oponente puede ganar escogiendo la columna almacenada en
                // jugarEvitarPerder[1]
                // Debe poner ahi su ficha para evitar que su oponente gane en el siguiente
                // turno
                columnaEscogida = jugada[1];
                logic.introducirFicha(jugador, columnaEscogida);
                colAux = columnaEscogida + 1;
                System.out.println("El Usuario podria ganar. El Agente lo evita introduciendo en la columna " + colAux);
                logic.actualizarTablero(tablero);

                posicionJugada[0] = columnaEscogida;
                posicionJugada[1] = jugada[2];
                return posicionJugada;
            } else {
                // Ni él puede ganar ni tampoco el oponente en su siguiente turno --> Juega
                // aleatoriamente
                System.out.println("Ni el Agente ni el usuario pueden ganar aún... Agente juega aleatorio");
                columnaEscogida = generar_columna(logic);
                posicionJugada[1] = logic.introducirFicha(jugador, columnaEscogida);
                logic.actualizarTablero(tablero);
                posicionJugada[0] = columnaEscogida;
                return posicionJugada;
            }
        }
    }


    public static int generar_columna(Logic logic) {
        int columna_escogida = 0;
        int min = 0;
        int max = 6;
        int colAux;
        boolean columna_valida = false;
        while (!columna_valida) {
            // Genera un numero aleatorio entre min y max --> De 0 a 6
            columna_escogida = (int) (Math.random() * (max - min + 1) + min);
            colAux = columna_escogida + 1;
            System.out.println("La columna escogida ha sido: " + colAux);
            columna_valida = logic.validarColumna(columna_escogida);
        }

        return columna_escogida;
    }

    /*
     * Busca posibilidad de hacer 4 en raya del jugador recibido como parámetro.
     * Devuelve 3 valores: jugarGanar[0]: 1 si existe posibilidad de 4 en raya, 0 si
     * no la hay, jugarGanar[1] y jugarGanar[2]: en caso de ser posible hacer 4 en
     * raya, son la columna y la fila del hueco ganador
     */

    public static int[] posibleGanar(char jugador, char[][] tablero) {
        int jugarGanar[] = new int[3];
        jugarGanar[0] = 0;
        jugarGanar[1] = 0;
        jugarGanar[2] = 0;

        // Busca en horizontal
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 5; columna++) {

                // Busca 3 fichas seguidas...
                if (tablero[fila][columna] == jugador && tablero[fila][columna + 1] == jugador
                        && tablero[fila][columna + 2] == jugador) {

                    if (fila == 5) {
                        // Si es la fila inferior del tablero, comprueba si el hueco de al lado esta
                        // libre
                        if (columna == 4) {
                            // En este caso, el hueco tendria que estar a la izqda
                            if (tablero[fila][columna - 1] == ' ') {
                                jugarGanar[0] = 1;
                                jugarGanar[1] = columna - 1;
                                jugarGanar[2] = fila;
                                return jugarGanar;
                            }
                        } else {
                            if (columna == 0) {
                                // En este caso, el hueco tendria que estar a la derecha
                                if (tablero[fila][columna + 3] == ' ') {
                                    jugarGanar[0] = 1;
                                    jugarGanar[1] = columna + 3;
                                    jugarGanar[2] = fila;
                                    return jugarGanar;
                                }
                            } else {
                                // En el resto de los casos, el hueco podria estar a la derecha o a la izqda
                                if (tablero[fila][columna - 1] == ' ') {
                                    jugarGanar[0] = 1;
                                    jugarGanar[1] = columna - 1;
                                    jugarGanar[2] = fila;
                                    return jugarGanar;
                                } else {
                                    if (tablero[fila][columna + 3] == ' ') {
                                        jugarGanar[0] = 1;
                                        jugarGanar[1] = columna + 3;
                                        jugarGanar[2] = fila;
                                        return jugarGanar;
                                    }
                                }
                            }

                        }

                    } else {
                        // Si es cualquiera de las otras filas, ademas de tener que haber un hueco al
                        // lado, la fila anterior de esa misma columna debe estar ocupada para poder
                        // poner una ficha encima
                        if (columna == 4) {
                            // En este caso, el hueco tendria que estar a la izqda
                            if ((tablero[fila + 1][columna - 1] != ' ') && tablero[fila][columna - 1] == ' ') {
                                jugarGanar[0] = 1;
                                jugarGanar[1] = columna - 1;
                                jugarGanar[2] = fila;
                                return jugarGanar;
                            }
                        } else {
                            if (columna == 0) {
                                // En este caso, el hueco tendria que estar a la derecha
                                if ((tablero[fila + 1][columna + 3] != ' ') && tablero[fila][columna + 3] == ' ') {
                                    jugarGanar[0] = 1;
                                    jugarGanar[1] = columna + 3;
                                    jugarGanar[2] = fila;
                                    return jugarGanar;
                                }
                            } else {
                                // En el resto de los casos, el hueco podria estar a la izqda o a la dcha
                                if ((tablero[fila + 1][columna - 1] != ' ') && tablero[fila][columna - 1] == ' ') {
                                    jugarGanar[0] = 1;
                                    jugarGanar[1] = columna - 1;
                                    jugarGanar[2] = fila;
                                    return jugarGanar;
                                } else {
                                    if ((tablero[fila + 1][columna + 3] != ' ') && tablero[fila][columna + 3] == ' ') {
                                        jugarGanar[0] = 1;
                                        jugarGanar[1] = columna + 3;
                                        jugarGanar[2] = fila;
                                        return jugarGanar;
                                    }
                                }
                            }

                        }
                    }
                } else {
                    if (columna < 4) {
                        // Busca 2 fichas suyas seguidas + hueco + ficha suya
                        if (tablero[fila][columna] == jugador && tablero[fila][columna + 1] == jugador
                                && tablero[fila][columna + 2] == ' ' && tablero[fila][columna + 3] == jugador) {

                            if (fila == 5) {
                                // Si es la fila inferior del tablero: posible poner en el hueco
                                jugarGanar[0] = 1;
                                jugarGanar[1] = columna + 2;
                                jugarGanar[2] = fila;
                                return jugarGanar;
                            } else {
                                // Si es cualquiera de las otras filas, se necesita que la posicion debajo del
                                // hueco este ocupada para poner encima
                                if (tablero[fila + 1][columna + 2] != ' ') {
                                    jugarGanar[0] = 1;
                                    jugarGanar[1] = columna + 2;
                                    jugarGanar[2] = fila;
                                    return jugarGanar;
                                }
                            }
                        } else {
                            // Busca 1 ficha suya + hueco + 2 fichas suyas seguidas
                            if (tablero[fila][columna] == jugador && tablero[fila][columna + 1] == ' '
                                    && tablero[fila][columna + 2] == jugador && tablero[fila][columna + 3] == jugador) {
                                if (fila == 5) {
                                    // Si es la fila inferior del tablero: posible poner en el hueco
                                    jugarGanar[0] = 1;
                                    jugarGanar[1] = columna + 1;
                                    jugarGanar[2] = fila;
                                    return jugarGanar;
                                } else {
                                    // Si es cualquiera de las otras filas, se necesita que la posicion debajo del
                                    // hueco este ocupada para poner encima
                                    if (tablero[fila + 1][columna + 1] != ' ') {
                                        jugarGanar[0] = 1;
                                        jugarGanar[1] = columna + 1;
                                        jugarGanar[2] = fila;
                                        return jugarGanar;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Busca en Vertical
        for (int fila = 1; fila < 4; fila++) {
            for (int columna = 0; columna < 7; columna++) {
                // Busca 3 fichas suyas seguidas en vertical
                if (tablero[fila][columna] == jugador && tablero[fila + 1][columna] == jugador
                        && tablero[fila + 2][columna] == jugador) {
                    // Necesario que el hueco superior este libre
                    if (tablero[fila - 1][columna] == ' ') {
                        jugarGanar[0] = 1;
                        jugarGanar[1] = columna;
                        jugarGanar[2] = fila - 1;
                        return jugarGanar;
                    }
                }
            }
        }

        // Busca en diagonales ascendentes hacia la derecha (diagonales descendentes
        // hacia la izquierda)
        for (int fila = 3; fila < 6; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                // Comprueba si tiene 3 fichas suyas seguidas en posiciones en las que
                // sea posible hacer 4 en raya en diagonal
                if (tablero[fila][columna] == jugador && tablero[fila - 1][columna + 1] == jugador
                        && tablero[fila - 2][columna + 2] == jugador) {
                    // Para poder colocar en la posicion deseada debe haber ahi un hueco y la
                    // posicion de la misma columna y fila inferior debe estar ocupada
                    if (tablero[fila - 2][columna + 3] != ' ' && tablero[fila - 3][columna + 3] == ' ') {
                        jugarGanar[0] = 1;
                        jugarGanar[1] = columna + 3;
                        jugarGanar[2] = fila - 3;
                        return jugarGanar;
                    }
                } else {
                    // Comprueba si tiene 2 fichas seguidas + hueco + ficha suya en la diagonal
                    if (tablero[fila][columna] == jugador && tablero[fila - 1][columna + 1] == jugador
                            && tablero[fila - 2][columna + 2] == ' ' && tablero[fila - 3][columna + 3] == jugador) {
                        // Para poder colocar en la posicion deseada, la posicion de la misma columna y
                        // fila inferior debe estar ocupada
                        if (tablero[fila - 1][columna + 2] != ' ') {
                            jugarGanar[0] = 1;
                            jugarGanar[1] = columna + 2;
                            jugarGanar[2] = fila - 2;
                            return jugarGanar;
                        }
                    } else {
                        // Comprueba si tiene 1 ficha suya + hueco + 2 fichas suyas seguidas en la
                        // diagonal
                        if (tablero[fila][columna] == jugador && tablero[fila - 1][columna + 1] == ' '
                                && tablero[fila - 2][columna + 2] == jugador
                                && tablero[fila - 3][columna + 3] == jugador) {
                            // Para poder colocar en la posicion deseada, la posicion de la misma columna y
                            // fila inferior debe estar ocupada
                            if (tablero[fila][columna + 1] != ' ') {
                                jugarGanar[0] = 1;
                                jugarGanar[1] = columna + 1;
                                jugarGanar[2] = fila - 1;
                                return jugarGanar;
                            }
                        }
                    }
                }
            }
        }

        // Busca en diagonales descendentes hacia la derecha (diagonales ascendentes
        // hacia la izquierda)
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                // Comprueba si tiene 3 fichas suyas seguidas en posiciones en las que
                // sea posible hacer 4 en raya en diagonal
                if (tablero[fila][columna] == jugador && tablero[fila + 1][columna + 1] == jugador
                        && tablero[fila + 2][columna + 2] == jugador) {
                    // Si la fila del bucle es la 2, solo es necesario que haya un hueco en la
                    // posicion deseada
                    if (fila == 2) {
                        if (tablero[fila + 3][columna + 3] == ' ') {
                            jugarGanar[0] = 1;
                            jugarGanar[1] = columna + 3;
                            jugarGanar[2] = fila + 3;
                            return jugarGanar;
                        }
                    } else {
                        // En otro caso, debe haber un hueco en la posicion deseada y ademas la fila
                        // inferior de la misma columna debe estar ocupada (para poder colocar encima)
                        if (tablero[fila + 3][columna + 3] == ' ' && tablero[fila + 4][columna + 3] != ' ') {
                            jugarGanar[0] = 1;
                            jugarGanar[1] = columna + 3;
                            jugarGanar[2] = fila + 3;
                            return jugarGanar;
                        }

                    }
                } else {
                    // Comprueba si tiene 2 fichas seguidas + hueco + ficha suya en la diagonal
                    if (tablero[fila][columna] == jugador && tablero[fila + 1][columna + 1] == jugador
                            && tablero[fila + 2][columna + 2] == ' ' && tablero[fila + 3][columna + 3] == jugador) {
                        // Para poder colocar en la posicion deseada, la posicion de la misma columna y
                        // fila inferior debe estar ocupada
                        if (tablero[fila + 3][columna + 2] != ' ') {
                            jugarGanar[0] = 1;
                            jugarGanar[1] = columna + 2;
                            jugarGanar[2] = fila + 2;
                            return jugarGanar;
                        }
                    } else {
                        // Comprueba si tiene 1 ficha suya + hueco + 2 fichas suyas seguidas en la
                        // diagonal
                        if (tablero[fila][columna] == jugador && tablero[fila + 1][columna + 1] == ' '
                                && tablero[fila + 2][columna + 2] == jugador
                                && tablero[fila + 3][columna + 3] == jugador) {
                            // Para poder colocar en la posicion deseada, la posicion de la misma columna y
                            // fila inferior debe estar ocupada
                            if (tablero[fila + 2][columna + 1] != ' ') {
                                jugarGanar[0] = 1;
                                jugarGanar[1] = columna + 1;
                                jugarGanar[2] = fila + 1;
                                return jugarGanar;
                            }
                        }
                    }
                }
            }
        }

        return jugarGanar;
    }
}
