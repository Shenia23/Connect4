package com.game.ai;

import com.game.Logic;

public class MinMax implements AiPlayer{
    private int depth;
    private static int alpha = -100000;
    private static int beta = 100000;
    private static char minmax_char = '0';
    private static char player_char = '+';

    public static final int MAX_WINNING_SCORE = 999999;
    public static final int MIN_WINNING_SCORE = -999999;

    public MinMax(int depth){
        this.depth = depth;
    }

    @Override
    public int[] getAiMove(char player, Logic logic) {
        int columnaEscogida = -1;
        boolean columnaValida = false;
        do {
            columnaEscogida = maxPlay(logic.getReverseBoardCopy(),0,Integer.MIN_VALUE, Integer.MAX_VALUE)[0];
            columnaValida = logic.validarColumna(columnaEscogida);
        } while (columnaValida == false);
        System.out.println("Columna escogida:" + columnaEscogida);

        int[] posicionJugada = new int[2];
        posicionJugada[1] = logic.introducirFicha(player, columnaEscogida);
        posicionJugada[0] = columnaEscogida;

        return posicionJugada;
    }


    public boolean isDone(int depth, char[][] board, int score) {
        return depth >= this.depth || score >= MAX_WINNING_SCORE || score <= MIN_WINNING_SCORE;
    }

    private int[] maxPlay(char[][] board, int depth, int alpha, int beta) {
        int score = calcScore('0',board);

        if (isDone(depth, board, score))
            return new int[]{-1, score};

        int[] max = new int[]{-1, 0};

        for (int column = 0; column < 7; column++) {

            char [][] new_board=new char[7][6];
            for(int col = 0; col < 7; col++) {
                for(int fil=0;fil<6;fil++) {
                    new_board[col][fil]=board[col][fil];
                }
            }
            if (place(column, true,new_board)) {
                int[] next = minPlay(new_board, depth + 1, alpha, beta);

                if (max[0] == -1 || next[1] > max[1]) {
                    max[0] = column;
                    max[1] = next[1];
                    alpha = next[1];
                }

                if(beta <= alpha)
                    return max;
            }
        }

        return max;
    }

    private int[] minPlay(char[][] board, int depth, int alpha, int beta)
    {
        int score = calcScore('+',board);

        if (isDone(depth, board, score))
            return new int[]{-1, score};

        int[] min = new int[]{-1, 0};

        for (int column = 0; column < 7; column++) {

            char [][] new_board=new char[7][6];
            for(int col = 0; col < 7; col++) {
                for(int fil=0;fil<6;fil++) {
                    new_board[col][fil]=board[col][fil];
                }
            }
            if (place(column, false,new_board)) {
                int[] next = maxPlay(new_board, depth + 1, alpha, beta);

                if (min[0] == -1 || next[1] < min[1]) {
                    min[0] = column;
                    min[1] = next[1];
                    beta = next[1];
                }

                if(beta <= alpha)
                    return min;
            }
        }

        return min;
    }

    public static boolean place(int col, boolean isMax, char[][] board)
    {
        if (board[col][0] == ' ' && col >= 0 && col < 7) {
            for (int y = 6 - 1; y >= 0; y--) {
                if (board[col][y] == ' ') {
                    board[col][y] = isMax ? '0' : '+';
                    break;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static int calcScore(char minMax, char[][] board)
    {
        int vertical_points=0, horizontal_points=0, descDiagonal_points=0, ascDiagonal_points=0, total_points=0;

        for (int row = 0; row < 6 - 3; row++) {
            for (int column = 0; column < 7; column++) {
                int tempScore = calcScorePosition(row, column, 1, 0,minMax,board);
                vertical_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 0; row < 6 ; row++) {
            for (int column = 0; column < 7 - 3; column++) {
                int tempScore = calcScorePosition(row, column, 0, 1,minMax,board);
                horizontal_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 0; row < 6 - 3 ; row++) {
            for (int column = 0; column < 7 - 3; column++) {
                int tempScore = calcScorePosition(row, column, 1, 1,minMax,board);
                descDiagonal_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        for (int row = 3; row < 6  ; row++) {
            for (int column = 0; column < 7 - 4; column++) {
                int tempScore = calcScorePosition(row, column, -1, 1,minMax,board);
                ascDiagonal_points += tempScore;
                if(tempScore >= MAX_WINNING_SCORE || tempScore <= MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        total_points = vertical_points + horizontal_points + descDiagonal_points + ascDiagonal_points;
        return total_points;
    }


    private static int calcScorePosition(int row, int column, int increment_row, int increment_col, char minMax,char[][] board)
    {
        int ai_points = 0, player_points = 0;

        for (int i = 0; i < 4; i++) //connect "4"
        {
            if(board[column][row] == '0')
            {
                ai_points++;
            }
            else if (board[column][row] == '+')
            {
                player_points++;
            }

            row += increment_row;
            column += increment_col;
        }

        if(player_points == 4)
            return MIN_WINNING_SCORE;
        else if(ai_points == 4)
            return MAX_WINNING_SCORE;
        else
            return ai_points;
    }




}