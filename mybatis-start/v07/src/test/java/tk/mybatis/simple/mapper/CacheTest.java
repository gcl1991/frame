package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class CacheTest extends BaseMapperTest {
	
	@Test
	public void testL1L2Cache(){
		SysUser user1 = null;
		try (SqlSession sqlSession = getSqlSession()){
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//调用 selectById 方法，查询 id = 1 的用户
			user1 = userMapper.selectById(1L);
			//对当前获取的对象重新赋值
			user1.setUserName("New Name");
			//再次查询获取 id 相同的用户
			SysUser user2 = userMapper.selectById(1L);
			//虽然我们没有更新数据库，但是这个用户名和我们 user1 重新赋值的名字相同了
			Assert.assertEquals("New Name", user2.getUserName());
			//不仅如何，user2 和 user1 完全就是同一个实例
			Assert.assertSame(user1, user2);
			// 会话关闭后，实例才会缓存进L2
		}
		//开始另一个新的 session
		System.out.println("开启新的 sqlSession");
		try (SqlSession sqlSession = getSqlSession()) {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//调用 selectById 方法，查询 id = 1 的用户
			SysUser user2 = userMapper.selectById(1L);
			//第二个 session 获取的用户名是 New Name（关闭二级缓存）,
			// Assert.assertEquals("admin", user2.getUserName());
			//第二个 session 获取的用户名是 New Name（开启二级缓存）,
			Assert.assertEquals("New Name", user2.getUserName());
			//这里的 user2 和 前一个 session 查询的结果是两个不同的实例
			Assert.assertNotSame(user1, user2);
			//执行删除操作,即使数据库中不存在此记录，也会刷新缓存
			userMapper.deleteById(2L);
			//获取 user3
			SysUser user3 = userMapper.selectById(1L);
			//这里的 user2 和 user3 是两个不同的实例
			Assert.assertNotSame(user2, user3);
		}
	}

	@Test
	public void testDirtyData(){
		try (SqlSession sqlSession = getSqlSession()){
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			Assert.assertEquals("普通用户", user.getRole().getRoleName());
			System.out.println("角色名：" + user.getRole().getRoleName());
		}
		//开始另一个新的 session
		System.out.println("开启新的 sqlSession");
		try (SqlSession sqlSession = getSqlSession()){
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(2L);
			role.setRoleName("脏数据");
			roleMapper.updateById(role);
			//提交修改
			sqlSession.commit();
		}
		System.out.println("开启新的 sqlSession");
		//开始另一个新的 session
		try(SqlSession sqlSession = getSqlSession()) {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			SysRole role = roleMapper.selectById(2L);
			// 不开启缓存引用，脏数据
			// Assert.assertEquals("普通用户", user.getRole().getRoleName());
			// 开启缓存引用，不存在脏数据
			Assert.assertEquals("脏数据", user.getRole().getRoleName());
			Assert.assertEquals("脏数据", role.getRoleName());
			System.out.println("角色名：" + user.getRole().getRoleName());
			//还原数据
			role.setRoleName("普通用户");
			roleMapper.updateById(role);
			//提交修改
			sqlSession.commit();
		}
	}
}
