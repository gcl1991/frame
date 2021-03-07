package sample.testng;

import com.smart.domain.User;
import org.testng.annotations.*;
import static org.testng.Assert.*;

public class TestNGExceptionTest {
	private User user;
    @BeforeMethod
    public void init() { 
    	user = null;
    }
    // enabled是否运行该Case
    @Test(enabled = true, expectedExceptions =NullPointerException.class)
    public void testUser(){
        System.out.println("run");
        assertNotNull(user.getUserName());
    } 
}
