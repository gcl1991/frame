package sample.mockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.smart.domain.User;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

// junit每个Test Case都是新对象
public class MockAnnotationTest {
	// IOC
    @Mock User mockUser;
    // 相当于BeforeMethod
    @Before 
    public void initMocks() {
		System.out.println(mockUser);
		// 创建mock对象
        MockitoAnnotations.initMocks(this);
		System.out.println(mockUser);
		System.out.println(this);
    }

    @Test
	public void test2(){
		System.out.println("test2");
	}
	@Test
	public void testMockUser() {
		System.out.println("testMockUser");
		when(mockUser.getUserId()).thenReturn(1);
		when(mockUser.getUserName()).thenReturn("tom");
		assertEquals(mockUser.getUserId(),1);
		assertEquals(mockUser.getUserName(), "tom");
	}
}
