package com.deppon.uap.message.web.controller;

import com.deppon.uap.framework.web.controller.AbstractUapController;
import com.deppon.uap.message.model.mapper.PerMesTypeMapper;
import com.deppon.uap.message.model.mapper.UmcMesTypeMapper;
import com.deppon.uap.message.model.po.PerMesCheckbox;
import com.deppon.uap.message.model.po.PerMesType;
import com.deppon.uap.message.model.po.UmcMesType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/perMesCheckboxController")
public class PerMesCheckboxController extends AbstractUapController {

    @Resource
    private UmcMesTypeMapper umcMesTypeMapper;

    @RequestMapping(value = "/queryCheckboxByEmpCode", method = RequestMethod.POST)
    public ArrayList<PerMesCheckbox> queryCheckboxByEmpCode(@RequestParam("key") Long key) {
        ArrayList<PerMesCheckbox> perMesCheckboxList = new ArrayList<PerMesCheckbox>();
        PerMesCheckbox perMesCheckbox = null;
        UmcMesType umcMesType = new UmcMesType();
        umcMesType.setActive("Y");
        ArrayList<UmcMesType> umcMesTypeList = umcMesTypeMapper.selectByUmcMesType(umcMesType);
        int listsize = umcMesTypeList.size();
        for (int i = 0 ; i< listsize; i++
             ) {
            perMesCheckbox = new PerMesCheckbox();
            perMesCheckbox.setBoxLabel(umcMesTypeList.get(i).getMesName());
            perMesCheckbox.setId(umcMesTypeList.get(i).getId().toString());
            perMesCheckbox.setInputValue(umcMesTypeList.get(i).getMesCode());
            perMesCheckbox.setName(umcMesTypeList.get(i).getMesName());
            perMesCheckboxList.add(perMesCheckbox);
        }
        System.out.println("===============进入了哦" + umcMesTypeList.toString());

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
