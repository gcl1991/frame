package tk.mybatis.simple.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {
	private Long id;
	private String userName;
	private String userPassword;
	private String userEmail;
	private String userInfo;
	private byte[] headImg;
	private Date createTime;
}
