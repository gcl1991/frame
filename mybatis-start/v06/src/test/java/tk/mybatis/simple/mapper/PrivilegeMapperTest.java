package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import tk.mybatis.simple.model.SysPrivilege;

import java.util.List;

public class PrivilegeMapperTest extends BaseMapperTest{

    @Test
    public void testSelectRoleByUserId(){
        try(SqlSession sqlSession = getSqlSession()) {
            PrivilegeMapper roleMapper = sqlSession.getMapper(PrivilegeMapper.class);
            List<SysPrivilege> privileges = roleMapper.selectPrivilegeByRoleId(1L);
            System.out.println(privileges);
        }
    }
}
