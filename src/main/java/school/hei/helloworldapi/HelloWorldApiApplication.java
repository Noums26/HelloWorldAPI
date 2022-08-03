package school.hei.helloworldapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"school.hei.helloworldapi","controller"})
public class HelloWorldApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApiApplication.class, args);
    }
}