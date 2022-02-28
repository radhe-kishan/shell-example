package rk.shell.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class ShellExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShellExampleApplication.class, args);
	}

}
