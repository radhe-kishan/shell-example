package rk.shell.example.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent("Some Command")
public class SomeCommand {
    
    @ShellMethod("I just throw exception.")
    public String iThrowException(){
        throw new RuntimeException("I just feel like throwing up.");
    }

    @ShellMethod("I make you feel good.")
    public String amIAGoodBoy(){
        return "Yes you are.";
    }
}
