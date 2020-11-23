package com.game;


public class Logic {

    private char[][] tablero;

    public Logic(){
        tablero = new char[6][7];
        initBoard();
    }

    public char[][] getTablero(){
        return this.tablero;
    }

    public void initBoard(){
        for (int fila = 0; fila < tablero.length; fila++) {
            for (int columna = 0; columna < tablero[0].length; columna++) {
                tablero[fila][columna] = ' ';
            }
        }
    }


    public int validarJugada(int columnaEscogida, char player){
        boolean columnaValida;

        columnaValida = validarColumna(columnaEscogida);

        if(!columnaValida){
            return -1;
        }

        int fila = introducirFicha(player, columnaEscogida);
        actualizarTablero(tablero);

        return fila;
    }


    // Introduce Ficha en la Columna Elegida comprobando cual es la posicion libre
    // más baja de la columna
    public int introducirFicha(char jugador, int columnaEscogida) {
        for (int fila = tablero.length - 1; fila >= 0; fila--) {
            if (tablero[fila][columnaEscogida] == ' ') {
                tablero[fila][columnaEscogida] = jugador;
                return fila;
            }
        }
        return -1;
    }

    public static void actualizarTablero(char[][] tablero) {
        System.out.println(" 1 2 3 4 5 6 7");
        System.out.println("###############");
        for (int fila = 0; fila < tablero.length; fila++) {
            System.out.print("|");
            for (int col = 0; col < tablero[0].length; col++) {
                System.out.print(tablero[fila][col]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("###############");
        }
        System.out.println();
    }

    public boolean validarColumna(int columna_seleccionada) {
        // Comprobamos que la columna esta en el tablero
        if (columna_seleccionada < 0 || columna_seleccionada > 6) {
            System.out.println("¡ Vuelve a intentarlo porque la columna no es valida ! ");
            return false;
        }

        // La columna esta llena si el hueco superior no esta vacio
        if (tablero[0][columna_seleccionada] != ' ') {
            System.out.println("¡ Vuelve a intentarlo porque la columna esta llena ! ");
            return false;
        }

        return true;
    }

    public boolean ganador(char jugador) {

        // Comprueba si 4 fichas en horizontal
        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                if (tablero[fila][columna] == jugador && tablero[fila][columna + 1] == jugador
                        && tablero[fila][columna + 2] == jugador && tablero[fila][columna + 3] == jugador) {
                    return true;
                }
            }
        }

        // Comprueba si 4 fichas en vertical
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 7; columna++) {
                if (tablero[fila][columna] == jugador && tablero[fila + 1][columna] == jugador
                        && tablero[fila + 2][columna] == jugador && tablero[fila + 3][columna] == jugador) {
                    return true;
                }
            }
        }

        // Comprueba si 4 fichas en diagonal ascendente hacia la derecha (equivale a
        // diagonal descendente hacia la izquierda)
        for (int fila = 3; fila < 6; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                if (tablero[fila][columna] == jugador && tablero[fila - 1][columna + 1] == jugador
                        && tablero[fila - 2][columna + 2] == jugador && tablero[fila - 3][columna + 3] == jugador) {
                    return true;
                }
            }
        }

        // Comprueba si 4 fichas en diagonal descendente hacia la derecha (equivale a
        // diagonal ascendente hacia la izquierda)
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 4; columna++) {
                if (tablero[fila][columna] == jugador && tablero[fila + 1][columna + 1] == jugador
                        && tablero[fila + 2][columna + 2] == jugador && tablero[fila + 3][columna + 3] == jugador) {
                    return true;
                }
            }
        }
        return false;
    }
}
