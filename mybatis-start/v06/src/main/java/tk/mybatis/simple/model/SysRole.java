package tk.mybatis.simple.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import tk.mybatis.simple.type.Enabled;

@Data
public class SysRole implements Serializable {
	private Long id;
	private String roleName;
	private Enabled enabled;
	private String createBy;
	private Date createTime;

	private SysUser user;
	List<SysPrivilege> privilegeList;
}
