package ch.laessker.jonathan.airhockey.util;

/**
 * Created by Roman Lenov on 06.06.2017.
 */

public class SettingsValues {
    private int effects;
    private int soundtrack;
    private int difficulty;

    public SettingsValues(int effects, int soundtrack, int difficulty) {
        this.effects = effects;
        this.soundtrack = soundtrack;
        this.difficulty = difficulty;
    }

    public void setEffects(int effects) {
        this.effects = effects;
    }

    public void setSoundtrack(int effects) {
        this.soundtrack = soundtrack;
    }

    public void setDifficulty(int effects) {
        this.difficulty = difficulty;
    }

    public int getEffects(){
        return effects;
    }
    public int getSoundtrack(){
        return soundtrack;
    }
    public int getDifficulty(){
        return difficulty;
    }
}
