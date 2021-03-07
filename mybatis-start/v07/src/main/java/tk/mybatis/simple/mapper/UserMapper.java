package tk.mybatis.simple.mapper;

import tk.mybatis.simple.model.SysUser;

public interface UserMapper {
	SysUser selectById(Long id);
	int deleteById(Long id);
	SysUser selectUserAndRoleById(Long id);
}
