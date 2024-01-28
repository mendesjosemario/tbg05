package Background;

import Background.TerminalHandler;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.Terminal;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TerminalHandlerTest {

    @Test
    public void testGetKeyPress() throws IOException {
        // Criar um stub de Terminal
        TerminalStub stubTerminal = new TerminalStub();

        // Criar uma instância de TerminalHandler com o stub
        TerminalHandler terminalHandler = new TerminalHandler();

        // Definir o comportamento esperado do stub
        stubTerminal.setKeyStroke(new KeyStroke('a', false, false, false));

        // Verificar se getKeyPress retorna o KeyStroke esperado
        KeyStroke keyStroke = terminalHandler.getKeyPress();
        assertEquals('a', keyStroke.getCharacter());
    }

    // Classe stub para Terminal
    private static class TerminalStub  {
        private KeyStroke keyStroke;

        // Método para definir o KeyStroke a ser retornado
        public void setKeyStroke(KeyStroke keyStroke) {
            this.keyStroke = keyStroke;
        }


        public KeyStroke pollInput() throws IOException {
            return keyStroke;
        }

        // Implementar outros métodos de Terminal conforme necessário...
    }
}
