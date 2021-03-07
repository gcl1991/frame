
package sample.testng;
import com.smart.domain.User;
import org.testng.annotations.*;
import static org.testng.Assert.*;

// 依赖保证test1和test2在test3之前执行，每个测试方法仅执行一次
public class TestNGDependsTest {
    @BeforeMethod
    public void initial(){
        System.out.println("run object:"+this);
    }

    @Test
    public void testMethod1() {
        System.out.println("run test1");
    }

    @Test
    public void testMethod2() {
        System.out.println("run test2");
        assertNull(new User());
    }

    // 不能保证依赖方法的执行顺序
    @Test(dependsOnMethods = {"testMethod1","testMethod2"},alwaysRun=false)
    public void testMethod3() {
        System.out.println("run test3");
    }

}
