package tacos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller            // <1>
@RequestMapping("/")
public class HomeController {

  @GetMapping     // <2>
  public String home() {
    return "home";     // <3>
  }

}
