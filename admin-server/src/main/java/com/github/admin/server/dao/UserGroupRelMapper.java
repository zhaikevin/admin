package com.github.admin.server.dao;

import com.github.admin.server.model.UserGroupRel;
import com.github.admin.server.model.vo.UserGroupRelVO;
import com.github.foundation.datasource.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: kevin
 * @Date: 2019/11/25 11:23
 */
@Repository
public interface UserGroupRelMapper extends BaseMapper<UserGroupRel> {

    List<UserGroupRelVO> getByUserId(@Param("userId")Long userId);

}
