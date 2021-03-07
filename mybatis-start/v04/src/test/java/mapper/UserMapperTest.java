package mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.mapper.UserMapper;
import tk.mybatis.simple.model.SysUser;

import java.util.*;

public class UserMapperTest extends BaseMapperTest {
    @Test
    public void testSelectByIdList() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<Long> ids = new ArrayList<>();
            ids.add(1L);
            // 业务逻辑中必须校验ids.size()>0
            List<SysUser> userList = userMapper.selectByIds(ids);
            Assert.assertEquals(1, userList.size());
        }
    }

    @Test
    public void testInsertUsers() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 创建一个user 对象
            List<SysUser> userList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                SysUser user = new SysUser();
                user.setUserName("test" + i);
                user.setUserPassword("123456");
                user.setUserEmail("test@mybatis.tk");
                userList.add(user);
            }
            //将新建的对象批量插入数据库中
            //特别注意，这里的返回值result 是执行SQL 影响的行数
            int result = userMapper.insertUsers(userList);
            // 批量新增回写主键
            userList.forEach(System.out::println);
            Assert.assertEquals(2, result);
        } finally {
            //为了不影响其他测试，这里选择回滚
            sqlSession.rollback();
            //不妥忘记关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByMap() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            // 查询条件，同样也是更新字段，必须保证该值存在
            map.put("id", 1L);
            // 要更新的其他字段
            map.put("user_email", "test@mybatis.tk");
            map.put("user_password", "12345678");
            // 更新数据
            userMapper.updateByMap(map);
            // 根据当前id 查询修改后的数据
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("test@mybatis.tk",user.getUserEmail() );
        } finally {
            // 为了不影响其他测试，这里选择回滚
            sqlSession.rollback();
            // 不要忘记关闭sqlSession
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByNameAndEmail() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> users = userMapper.selectByNameAndEmail("ad",null);
            Assert.assertFalse(users.isEmpty());
            users = userMapper.selectByNameAndEmail("","test@mybatis.tk");
            Assert.assertFalse(users.isEmpty());
            users = userMapper.selectByNameAndEmail("ad","test@mybatis.tk");
            Assert.assertTrue(users.isEmpty());
            users = userMapper.selectByNameAndEmail("","");
            Assert.assertTrue(users.isEmpty());
        } finally {
            // 为了不影响其他测试，这里选择回滚
            sqlSession.rollback();
            // 不要忘记关闭sqlSession
            sqlSession.close();
        }
    }
    @Test
    public void testUpdateById() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // 更新数据
            SysUser user = new SysUser();
            user.setUserName("test");
            user.setId(1L);
            userMapper.updateById(user);
        } finally {
            // 为了不影响其他测试，这里选择回滚
            sqlSession.rollback();
            // 不要忘记关闭sqlSession
            sqlSession.close();
        }
    }
}
