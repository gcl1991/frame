package tacos;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data // 目标：类，接口（包括注释类型）或枚举声明，保留级别：源码级别
public class Taco {
  @NotNull // 不能为null，可以为""和" "
  @Size(min=5, message="Name must be at least 5 characters long")
  private String name;

  @NotNull
  @Size(min=1, message="You must choose at least 1 ingredient")
  private List<String> ingredients;
}
