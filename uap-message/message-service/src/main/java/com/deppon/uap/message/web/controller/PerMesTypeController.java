package com.deppon.uap.message.web.controller;

import com.deppon.uap.framework.web.controller.AbstractUapController;
import com.deppon.uap.message.model.mapper.PerMesTypeMapper;
import com.deppon.uap.message.model.po.PerMesType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController @RequestMapping(value = "/perMesTypeController")
public class PerMesTypeController extends AbstractUapController {

    @Resource
    private PerMesTypeMapper perMesTypeMapper;

    @RequestMapping(value = "/queryByPrimaryKey", method = RequestMethod.GET)
    public PerMesType queryByPrimaryKey(@RequestParam("key") Long key) {
        System.out.println("===============进入了哦");
        return perMesTypeMapper.selectByPrimaryKey(key);
    }


}
