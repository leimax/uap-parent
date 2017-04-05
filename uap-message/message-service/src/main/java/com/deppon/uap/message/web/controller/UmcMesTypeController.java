package com.deppon.uap.message.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deppon.uap.framework.web.controller.AbstractUapController;
import com.deppon.uap.message.model.mapper.UmcMesTypeMapper;
import com.deppon.uap.message.model.po.UmcMesType;

@Controller
@RequestMapping(value = "/umcMesTypeController")
public class UmcMesTypeController extends AbstractUapController {
	@Resource
	private UmcMesTypeMapper umcMesTypeMapper;

	@RequestMapping(value = "/queryByPrimaryKey", method = RequestMethod.POST)
	public String queryByPrimaryKey(@RequestParam("key") String key, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", Integer.valueOf(key));
		UmcMesType umcMesType = umcMesTypeMapper.selectByPrimaryKey(Long
				.valueOf(key));
		model.addAttribute("id","wo");
		return "layout";
	}

}
