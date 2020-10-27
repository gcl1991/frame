package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication   // <1>
public class TacoCloudApplication {
  public static void main(String[] args) {
    SpringApplication.run(TacoCloudApplication.class, args); // <2>
  }
}


