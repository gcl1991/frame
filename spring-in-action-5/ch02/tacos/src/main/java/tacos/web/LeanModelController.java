package tacos.web;

import lombok.ToString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 网络请求发起时开始新建模型，模型参数基于类型匹配绑定，每个请求新建一个模型
 可以将GET参数绑定到模型中,每个参数伴有一个错误检查对象

 对于参数注释：@ModelAttribute=@Autowired+@Qualifier
 即它尝试从Spring托管模型中检索具有给定名称的bean，如果未找到命名的bean。
 将使用默认构造函数创建新实例(Bean)并将其添加到模型中,字符串将创建空串（默认构造器）
 基本类型包装类将报错(包装类没有<init>默认构造器)

 对于方法注释：@ModelAttribut=@Bean + @Before
 即它将由用户代码构造的bean放入模型中，并且始终在请求处理方法之前调用它。
 重名Bean时调用顺序不固定，同时设置属性相同的方法只会调用一个，调用哪个不确定

 * */
@Controller
@RequestMapping("/leanModel")
public class LeanModelController {

    @ModelAttribute("repeat")
    String repeat(Model model) {
        System.out.println("call repeat method: "+model);
        return "repeat1";
    }

    @ModelAttribute("repeat")
    // 此方法与上个方法，只调用一个，调用哪个不确定
    String testRepeat(Model model){
        System.out.println("call test repeat method: "+model);
        return "repeat2";
    }

    @GetMapping()
    String pathA(Model model,@ModelAttribute("X") String i) {
        System.out.println(model);
        System.out.println(i);
        return "home";
    }
}
