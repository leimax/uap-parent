package com.deppon.uap.message.model.mapper;

import com.deppon.uap.message.model.po.PerMesType;

public interface PerMesTypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(PerMesType record);

    int insertSelective(PerMesType record);

    PerMesType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PerMesType record);

    int updateByPrimaryKey(PerMesType record);
}