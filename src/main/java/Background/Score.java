package Background;

public class Score {
    private int score;

    public Score(){score=0;}

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