package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import tk.mybatis.simple.model.SysRole;

@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {
	@ResultMap("roleMap")
	@Select({"select id,role_name, enabled, create_by, create_time",
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
	void updateById(SysRole sysRole);

}
