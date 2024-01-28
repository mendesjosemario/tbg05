package Background;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class TerminalHandler {
    private final Terminal terminal;

    public TerminalHandler() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(new TerminalSize(100, 65));
        this.terminal = terminalFactory.createTerminal();
        terminal.enterPrivateMode();
    }

    public KeyStroke getKeyPress() throws IOException {
        return terminal.pollInput(); // Non-blocking input read
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
