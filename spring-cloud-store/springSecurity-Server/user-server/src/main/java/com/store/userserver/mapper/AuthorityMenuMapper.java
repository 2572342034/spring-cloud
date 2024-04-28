package com.store.userserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.store.commen.model.vo.AuthorityMenu;


import java.util.List;

public interface AuthorityMenuMapper extends BaseMapper<AuthorityMenu> {
    List<String> selectPermsByUserId(Long id);

}
