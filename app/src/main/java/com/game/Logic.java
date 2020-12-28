package com.game;


import java.util.Arrays;

import com.game.ai.MinMax;

public class Logic {

    private char[][] tablero;
    protected int[][] fichasGanador;

    public Logic(){
        tablero = new char[6][7];
        fichasGanador = new int[4][2];
        initBoard();
    }

    public Logic copy(){
        Logic new_logic = new Logic();
        int length = tablero.length;
        char[][] target = new char[length][this.tablero[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(this.tablero[i], 0, target[i], 0, this.tablero[i].length);
        }
        new_logic.tablero = target;
        return new_logic;
    }

    public char[][] getTablero(){
        return this.tablero;
    }

    public void setTablero(char[][] tablero){
        this.tablero=tablero;
        return;
    }

    public char[][] getReverseBoardCopy(){
        char [][] tableroaux=new char[7][6];
        for(int col = 0; col < 7; col++) {
            for(int fil=0;fil<6;fil++) {
                tableroaux[col][fil]=this.tablero[fil][col];
            }
        }
        return tableroaux;
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
            return false;
        }

        // La columna esta llena si el hueco superior no esta vacio
        if (tablero[0][columna_seleccionada] != ' ') {
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
                    setFichasGanador(fila, columna, 0, 1);
                    return true;
                }
            }
        }

        // Comprueba si 4 fichas en vertical
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 7; columna++) {
                if (tablero[fila][columna] == jugador && tablero[fila + 1][columna] == jugador
                        && tablero[fila + 2][columna] == jugador && tablero[fila + 3][columna] == jugador) {
                    setFichasGanador(fila, columna, 1, 0);
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
                    setFichasGanador(fila, columna, -1, 1);
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
                    setFichasGanador(fila, columna, 1, 1);
                    return true;
                }
            }
        }
        return false;
    }

    public void setFichasGanador(int row, int column, int increment_row, int increment_col){
        for (int i = 0; i < 4; i++) {
            fichasGanador[i][0] = row;
            fichasGanador[i][1] = column;
            row += increment_row;
            column += increment_col;
        }

    }



}