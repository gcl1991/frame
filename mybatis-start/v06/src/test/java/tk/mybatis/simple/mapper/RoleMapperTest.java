package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.type.Enabled;

public class RoleMapperTest extends BaseMapperTest {

	@Test
	public void testSelectRoleByUserId(){
		try(SqlSession sqlSession = getSqlSession()) {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<SysRole> role = roleMapper.selectRoleByUserId(1L);
			Assert.assertFalse(role.isEmpty());
		}
	}

	@Test
	public void testSelectRoleByUserIdChoose(){
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			//由于数据库数据 enable 都是 1，所以我们给其中一个角色的 enable 赋值为 0
			SysRole role = roleMapper.selectById(2L);
			role.setEnabled(Enabled.disabled);
			roleMapper.updateById(role);
			//获取用户 1 的角色
			List<SysRole> roleList = roleMapper.selectRoleByUserIdChoose(1L);
			for(SysRole r: roleList){
				System.out.println("角色名：" + r.getRoleName());
				if(r.getId().equals(1L)){
					//第一个角色存在权限信息
					Assert.assertNotNull(r.getPrivilegeList());
				} else if(r.getId().equals(2L)){
					//第二个角色的权限为 null
					Assert.assertNull(r.getPrivilegeList());
					continue;
				}
				for(SysPrivilege privilege : r.getPrivilegeList()){
					System.out.println("权限名：" + privilege.getPrivilegeName());
				}
			}
		} finally {
			sqlSession.rollback();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}


}
