package com.deppon.uap.message.model.mapper;


import com.deppon.uap.message.model.po.UmcMesType;

import java.util.ArrayList;

public interface UmcMesTypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UmcMesType record);

    int insertSelective(UmcMesType record);

    UmcMesType selectByPrimaryKey(Long id);

    ArrayList<UmcMesType> selectByUmcMesType(UmcMesType umcMesType);

    int updateByPrimaryKeySelective(UmcMesType record);

    int updateByPrimaryKey(UmcMesType record);

}