package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tk.mybatis.simple.model.SysRole;

public interface RoleMapper {
	
	@Select({"select id,role_name roleName, enabled, create_by createBy, create_time createTime",
			 "from sys_role",
			 "where id = #{id}"})
	SysRole selectById(Long id);

	@Update({"update sys_role",
		     "set role_name = #{roleName},",
				 "enabled = #{enabled},",
				 "create_by = #{createBy},",
				 "create_time = #{createTime, jdbcType=TIMESTAMP}",
			 "where id = #{id}"
		})
	int updateById(SysRole sysRole);
	List<SysRole> selectRoleByUserIdChoose(Long userId);

    List<SysRole> selectRoleByUserId(long id);
}
