package rk.shell.example.command;

import org.jline.reader.Parser;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.FileInputProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Script;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicBoolean;

@ShellComponent
public class CustomScript implements Script.Command {

    public static final AtomicBoolean EXIT_ON_ERROR = new AtomicBoolean(Boolean.FALSE);
    private final Shell shell;
    private final Parser parser;
    
    public CustomScript(@Lazy Shell shell, @Lazy Parser parser) {
        this.shell = shell;
        this.parser = parser;
    }

    @ShellMethod(value = "Read and execute commands from a file.")
    public void script(File file) throws IOException {
        EXIT_ON_ERROR.set(true);
        Reader reader = new FileReader(file);
        try (FileInputProvider inputProvider = new FileInputProvider(reader, parser)) {
            shell.run(inputProvider);
        }
        EXIT_ON_ERROR.set(false);
    }
}
