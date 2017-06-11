package ch.laessker.jonathan.airhockey.util;

/**
 * Created by Roman Lenov on 11.06.2017.
 */

public class StatisticsValues {
    private int player1Points;
    private int player2Points;
    private Long duration;


    public StatisticsValues(int player1Points, int player2Points, Long duration){
        this.player1Points = player1Points;
        this.player2Points = player2Points;
        this.duration = duration;
    }

    public int getPlayer1Points(){
        return player1Points;
    }

    public int getPlayer2Points(){
        return player2Points;
    }

    public Long getDuration(){
        return duration;
    }
}
