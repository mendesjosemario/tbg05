package Background;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    @Test
    public void testAddToScoreAndgetScore() {
        Score score = new Score();

        // Testar com 1 linha completada
        score.addToScore(1);
        assertEquals(40, score.getScore(), "A pontuação deveria ser 40 para 1 linha completada");

        // Testar com 2 linhas completadas
        score.addToScore(2);
        assertEquals(140, score.getScore(), "A pontuação deveria ser 140 para 2 linhas completadas (40 + 100)");

        // Testar com 3 linhas completadas
        score.addToScore(3);
        assertEquals(440, score.getScore(), "A pontuação deveria ser 440 para 3 linhas completadas (140 + 300)");

        // Testar com 4 ou mais linhas completadas
        score.addToScore(4);
        assertEquals(1640, score.getScore(), "A pontuação deveria ser 1640 para 4 linhas completadas (440 + 1200)");
    }
}
