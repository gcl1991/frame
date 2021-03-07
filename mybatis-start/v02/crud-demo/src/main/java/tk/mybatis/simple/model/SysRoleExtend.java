package tk.mybatis.simple.model;

import lombok.Data;


@Data
public class SysRoleExtend extends SysRole {
	private String userName;
	private String userEmail;
}
