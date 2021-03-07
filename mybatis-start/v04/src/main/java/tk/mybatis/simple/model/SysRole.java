package tk.mybatis.simple.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysRole implements Serializable {
	private Long id;
	private String roleName;
	private int enabled;
	private String createBy;
	private Date createTime;
	private SysUser user;
}
