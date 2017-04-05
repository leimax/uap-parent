package com.deppon.uap.message.web.controller;

import com.deppon.uap.framework.web.controller.AbstractUapController;
import com.deppon.uap.message.model.mapper.UmcMesTypeMapper;
import com.deppon.uap.message.model.po.UmcMesType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/modelController")
public class ModelController extends AbstractUapController {

	@RequestMapping(value = "/queryByPrimaryKey", method = RequestMethod.GET)
	public String queryByPrimaryKey(@RequestParam("key") String key, Model model) {
		return "layout";
	}

}
