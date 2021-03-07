package tk.mybatis.simple.mapper;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.simple.model.SysUser;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    List<SysUser> selectByIds(List<Long> idList);
    SysUser selectById(long id);
    int insertUsers(List<SysUser> userList);
    int updateByMap(@Param("maps") Map<String,Object> map);
    List<SysUser> selectByNameAndEmail(@Param("userName") String userName,@Param("userEmail") String userEmail);
    int updateById(SysUser user);
}
