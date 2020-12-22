package com.game.ai;

import com.game.Logic;

public interface AiPlayer {
    static char char_jugador = '+';
    static char char_ai = '0';

    public int[] getAiMove(char player, Logic logic);
    //public static int[] posibleGanar(char player, Logic logic);

}
