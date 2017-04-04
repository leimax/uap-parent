package com.deppon.uap.message.web.controller;

import com.deppon.uap.framework.web.controller.AbstractUapController;
import com.deppon.uap.message.model.mapper.PerMesTypeMapper;
import com.deppon.uap.message.model.po.PerMesCheckbox;
import com.deppon.uap.message.model.po.PerMesType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/perMesCheckboxController")
public class PerMesCheckboxController extends AbstractUapController {

    //@Resource
    //private PerMesTypeMapper perMesTypeMapper;

    @RequestMapping(value = "/queryCheckboxByEmpCode", method = RequestMethod.POST)
    public ArrayList<PerMesCheckbox> queryCheckboxByEmpCode(@RequestParam("key") Long key) {

        PerMesCheckbox perMesCheckbox = new PerMesCheckbox();
        //perMesTypeMapper.selectByPrimaryKey(key);
        perMesCheckbox.setBoxLabel("css");
        perMesCheckbox.setId("1");
        perMesCheckbox.setInputValue("10");
        perMesCheckbox.setName("我只是测试");
        ArrayList<PerMesCheckbox> perMesCheckboxList = new ArrayList<PerMesCheckbox>();
        perMesCheckboxList.add(perMesCheckbox);
        perMesCheckbox = new PerMesCheckbox();
        perMesCheckbox.setBoxLabel("lmx");
        perMesCheckbox.setId("2");
        perMesCheckbox.setInputValue("10");
        perMesCheckbox.setName("我只是测试2");
        perMesCheckboxList.add(perMesCheckbox);
        System.out.println("===============进入了哦" + perMesCheckbox.toString());

        return perMesCheckboxList;
    }

    @RequestMapping(value = "/submitCheckbox2", method = RequestMethod.POST)
    public void submitCheckbox2(@RequestParam("key") Object key) {

        System.out.print("[数据]:" + key.toString());
        //return perMesCheckboxList;
    }

    @RequestMapping(value = "/submitCheckbox", method = RequestMethod.POST)
    public void submitCheckbox(@RequestParam("key") String key) {
        String[] updatedat = key.split(":");
        System.out.print("[数据]:" + updatedat[0]);
        //return perMesCheckboxList;
    }

}
