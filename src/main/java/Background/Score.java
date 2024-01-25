package Background;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Score {
    private int score;


    public Score(){
        this.score = 0;
    }

    public void update(){
        this.score = getScore();
    }

    public void draw(TextGraphics screenGraphics){
        update();
        screenGraphics.setBackgroundColor(TextColor.Factory.fromString("#3A3A3A"));
        //screenGraphics.putString(42, 18, "  ___  ___ ___  ___ ___");
        //screenGraphics.putString(42, 19, " / __|/ __/ _ \\| _ \\ __|");
        //screenGraphics.putString(42, 20, " \\__ \\ (_| (_) |   / _|");
        //screenGraphics.putString(42, 21, " |___/\\___\\___/|_|_\\___|");

        drawText(screenGraphics,52,21,"SCORE:","#FFFFFF");

        screenGraphics.putString(54, 24,Integer.toString(score), SGR.BOLD);
    }

    private void drawText(TextGraphics textGraphics, int col, int row, String text, String color) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString(color));
        textGraphics.enableModifiers(SGR.BOLD);
        textGraphics.putString(col,row,text);
    }

    public void addToScore(int linesCompleted) {
        if(linesCompleted == 1){
            score += 40;
        }
        else if(linesCompleted == 2){
            score += 100;
        }
        else if(linesCompleted == 3){
            score += 300;
        }
        else if(linesCompleted >= 4){
            score += 1200;
        }
    }

    public int getScore(){
        return this.score;
    }
}