package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

import java.util.List;

public interface UserMapper {
    SysUser selectById(Long id);
    List<SysUser> selectAll();
    List<SysRole> selectRolesByUserId(Long userId);
    List<SysRole> selectExtendRolesByUserId(Long userId);
    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userid ,@Param("enabled") Integer enabled);

    int insert(SysUser sysUser);
    int insertIncrement(SysUser sysUser);
    int insertSelectKey(SysUser sysUser);

    int updateById(SysUser sysUser);

    int deleteById(Long id);
}
