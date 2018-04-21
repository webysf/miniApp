package com.miniapp.web.backuser;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.miniapp.common.jsonAnalysis.JsonParameterAnalysis;
import com.miniapp.common.util.StringUtil;
import com.miniapp.dto.ResponseResult;
import com.miniapp.entity.back.User;
import com.miniapp.service.back.BackUserService;

import reactor.core.publisher.Flux;

/**
 * 
 * @author wesion 后台用户Controller
 *
 */
@RestController
@RequestMapping("/backuser")
public class BackUserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BackUserService service;
	
	@Autowired
	private JsonParameterAnalysis analysis;

	/**
	 * 添加后台用户
	 * 
	 * @param user:
	 *            后台管理用户
	 * @return responseResult: 响应结果 {code = 1 , message = "成功" }
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult addUser(@RequestBody User user) {

		logger.info("--info--user:" + user.getUserName());
		logger.debug("--debug--user:" + user.getUserName());

		if (1 == service.addUser(user)) {
			return new ResponseResult();
		}

		return new ResponseResult(0, "失败");
	}

	/**
	 * 查询所有用户信息
	 * 
	 * @return Flux<User>:用户列表
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ResponseBody
	public Flux<User> test() {
		return service.findAll();
	}

	/**
	 * 后台用户修改密码
	 * 
	 * @param password:密码
	 * @return responseResult: 响应结果 {code = 1 , message = "成功" }
	 */
	@RequestMapping(value = "/modify/password", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult modifyPassword(@RequestBody User user) {

		if (StringUtil.isBlank(user.getPassword()) || user.getId() == null) {
			return new ResponseResult(0, "id 和 密码不能为空");
		}

		if (1 == service.modifyPassword(user)) {
			return new ResponseResult();
		}
		return new ResponseResult(0, "失败");
	}
	
	/**
	 *  前端json参数解析
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@RequestMapping(value = "/analysis/json", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult analysisJson(@RequestBody String jsonParam) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		
		logger.info("---入参：---" + jsonParam);
		
		Map<String,Object> paramMap =  analysis.parameterAndBeanAnalysis(jsonParam,"user");
		
		
		System.out.println("----keyy----: " + (paramMap.get("keyy")).toString());
		System.out.println("----keyy.Password----: " + ((User) paramMap.get("keyy")).getPassword());
		
		return new ResponseResult();
	}
}
