package tk.mybatis.simple.mapper;

import java.util.List;
import tk.mybatis.simple.model.SysUser;

public interface UserMapper {
	SysUser selectUserAndRoleById(Long id);
	SysUser selectUserAndRoleLikeName(String userName);
	List<SysUser> selectAllUserAndRoles();
	SysUser selectAllUserAndRolesSelect(Long id);
}
