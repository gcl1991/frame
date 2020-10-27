package tacos.web;

import lombok.ToString;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.model.IModel;


@ToString
class MyBean{
    private String name;
}

@Controller
@RequestMapping("/leanModel")
public class LeanModelController {


    @ModelAttribute("attrib1")
    String createAttrib1(Model model) {
        System.out.println("attrib1: "+model);
        return "1";
    }

    @ModelAttribute("attrib1") // 没有被调用,此方法与上个方法，只调用一个，调用哪个不确定
    String createAttrib2(Model model){
        System.out.println("attrib2: "+model);
        return "2";
    }

    @ModelAttribute
    String string1(Model model){
        System.out.println("string1"+ model);
        return "my is string1"; // 创建于方法同名的属性
    }

    @ModelAttribute
    String string2(Model model){
        System.out.println("string2"+ model);
        return "my is string2"; // 创建于方法同名的属性
    }

    @ModelAttribute
    void createSomeBean1(Model model){
        System.out.println("bean1"+model);
        model.addAttribute("bean1","my is bean1");
    }
    @ModelAttribute
    void createSomeBean2(Model model){
        System.out.println("bean2"+model);
        model.addAttribute("bean2","my is bean2");
    }

    @ModelAttribute
    void createSomeBean3(Model model){
        System.out.println("bean3"+model);
        model.addAttribute("bean3","my is bean3");
    }

//    @ModelAttribute
//    public void createSomeBean4(Model model){
//        model.addAttribute("bean4","my is bean4"); // 将引发错误，说String无法转换为tacos.web.MyBean,因为在下文中申请了一个bean5
//    }

    @ModelAttribute
    public void createSomeBean5(Model model){
        System.out.println("bean5"+model);
        model.addAttribute("bean5","my is bean5");
    }

    @ModelAttribute()
    public void createSomeBean6(Model model){
        System.out.println("bean6"+model);
        model.addAttribute("bean6","my is bean6");
    }

    // 不会被调用
    void createSomeBean7(Model model){
        System.out.println("Bean7"+model);
        model.addAttribute("bean7","my is bean7");
    }

    @GetMapping()
        // 0 网络请求发起时开始新建模型，模型参数基于类型匹配绑定，每个请求新建一个模型
        // 1 类型可以自动兼容转换，
        // 2 可以绑定到多个变量
        // 3 可以将GET参数绑定到模型中,每个参数伴有一个错误检查对象
        // 4 模型可以通过方法创建，并初始化
        // 5 方法中绑定的参数不会被GET中参数覆盖
        // 6 如果模型中不存在此属性，则将新建参数为null的对象，字符串创建空串,基本类型包装类初始化为null
        // 7 调用顺序不固定，同时设置属性相同的方法只会调用一个，调用哪个不确定
        // 8 方法调用先于形参
    String pathA(@ModelAttribute("attrib1") String attrib1,@ModelAttribute("attrib1") int attrib1_int,
                 @ModelAttribute("bean1") String bean1,@ModelAttribute("bean2") String bean2,
                 @ModelAttribute("bean3") String bean3,@ModelAttribute("bean4") MyBean bean4,//MyBean(name=null)
                 @ModelAttribute("bean5") String bean5,@ModelAttribute("bean6") String bean6,
                 @ModelAttribute("get1") String get1,@ModelAttribute("get1") int get1_int,
                 @ModelAttribute("get2") String get2,
                 String string,Integer integer,
                 Model model) {
        System.out.println(model);
        System.out.println(attrib1);
        System.out.println(attrib1_int);
        System.out.println(bean1);
        System.out.println(bean2);
        System.out.println(bean3);
        System.out.println(bean4);
        System.out.println(bean5);
        System.out.println(bean6);
        System.out.println(get1);
        System.out.println(get1_int);
        System.out.println(get2);
        System.out.println(string);
        System.out.println(integer);
        System.out.println("end");
        return "home";
    }
}
