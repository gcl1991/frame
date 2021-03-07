package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.*;

public class UserMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals("admin", user.getUserName());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAll();
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);
        }
    }

    @Test
    public void testSelectRolesByUserId() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> roleList = userMapper.selectRolesByUserId(1L);
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
        }
    }

    @Test
    public void testSelectExtendRolesByUserId() {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> roleList = userMapper.selectExtendRolesByUserId(1L);
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
        }
    }
    @Test
    public void testSelectRolesByUserIdAndRoleEnabled(){
        try (SqlSession sqlSession = getSqlSession()){
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<SysRole> users = userMapper.selectRolesByUserIdAndRoleEnabled(1L,1);
            Assert.assertNotNull(users);
            Assert.assertTrue(users.size()>0);
        }
    }

    @Test
    public void testInsert(){
        SqlSession sqlSession = getSqlSession();
        try  {
            SysUser user = new SysUser(null, "test1", "123456", "test@mybatis.tk", "test info", new byte[]{1, 2, 3}, new Date());
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            int result = userMapper.insert(user);
            Assert.assertEquals(1, result);
            Assert.assertNull(user.getId());
        }finally{
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testInsertIncrement(){
        SqlSession sqlSession = getSqlSession();
        try  {
            SysUser user = new SysUser(null, "test1", "123456", "test@mybatis.tk", "test info", new byte[]{1, 2, 3}, new Date());
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            int result = userMapper.insertIncrement(user);
            Assert.assertEquals(1, result);
            Assert.assertNotNull(user.getId());
        }finally{
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testInsertSelectKey(){
        SqlSession sqlSession = getSqlSession();
        try  {
            SysUser user = new SysUser(null, "test1", "123456", "test@mybatis.tk", "test info", new byte[]{1, 2, 3}, new Date());
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            int result = userMapper.insertSelectKey(user);
            Assert.assertEquals(1, result);
            Assert.assertNotNull(user.getId());
        }finally{
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById(){
        SqlSession sqlSession = getSqlSession();
        try  {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("admin",user.getUserName());
            user.setUserEmail("test@change.tk");
            int result = userMapper.updateById(user);
            Assert.assertEquals(1, result);
            user = userMapper.selectById(1L);
            Assert.assertEquals("test@change.tk",user.getUserEmail());
        }finally{
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteById(){
        SqlSession sqlSession = getSqlSession();
        try  {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals(1,userMapper.deleteById(1L));
            Assert.assertNull(userMapper.selectById(1L));
        }finally{
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}
