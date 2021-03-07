package tk.mybatis.simple.mapper;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class UserMapperTest extends BaseMapperTest {

	@Test
	public void testSelectUserAndRoleById(){
		try (SqlSession sqlSession = getSqlSession();){
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getRole());
			System.out.println(user);
		}
	}

	@Test
	public void testSelectUserAndRoleByIdSelect(){
		try (SqlSession sqlSession = getSqlSession()){
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleLikeName("test");
			Assert.assertNotNull(user);
			System.out.println("调用 user.equals(null)");
			user.equals(null);
			System.out.println("调用 user.hashCode()");
			user.hashCode();
			System.out.println("调用 user.getRole()");
			Assert.assertNotNull(user.getRole());
		}
	}

	@Test
	public void testSelectAllUserAndRoles(){
		try (SqlSession sqlSession = getSqlSession()) {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAllUserAndRoles();
			System.out.println("用户数：" + userList.size());
			for (SysUser user : userList) {
				System.out.println("用户名：" + user.getUserName());
				for (SysRole role : user.getRoleList()) {
					System.out.println(role);
				}
			}
		}
	}

	@Test
	public void testSelectAllUserAndRolesSelect(){
		try (SqlSession sqlSession = getSqlSession()){
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectAllUserAndRolesSelect(1L);
			System.out.println("用户名：" + user.getUserName());
			for (SysRole role : user.getRoleList()) {
				System.out.println("角色名：" + role.getRoleName());
			}
		}
	}
}
