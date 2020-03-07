package com.lwohvye.springcloud.springcloudlwohvyeprovider.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.lwohvye.springcloud.springcloudlwohvyeapi.common.util.PageUtil;
import com.lwohvye.springcloud.springcloudlwohvyeapi.entity.User;
import com.lwohvye.springcloud.springcloudlwohvyeprovider.common.annotation.LogAnno;
import com.lwohvye.springcloud.springcloudlwohvyeprovider.common.util.ResultModel;
import com.lwohvye.springcloud.springcloudlwohvyeprovider.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Api(value = "用户相关操作API")
@Validated
//RestController相当于@Controller+@ResponseBody
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户查询.
     *
     * @return
     */
    @ApiIgnore
    @RequestMapping("/getUser")
    public String getUser() {
        return "userInfo";
    }

    /**
     * @return com.lwohvye.springcloud.springcloudlwohvyeprovider.common.util.ResultModel
     * @description 获取用户列表
     * @params [username, pageUtil]
     * @author Hongyan Wang
     * @date 2019/9/23 13:26
     */
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表，可以通过用户名模糊查询，包含PageUtil分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", dataType = "String"),
            @ApiImplicitParam(name = "pageUtil", value = "分页相关实体类", dataType = "PageUtil")
    })
//    配置在api中不显示的参数,暂未生效
    @ApiOperationSupport(ignoreParameters = {"pageData", "totalCount", "totalPages"})
    @RequestMapping(value = "/findUser", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultModel<PageUtil<User>> list(String username, PageUtil<User> pageUtil) {
//        JSONObject jsonObject = new JSONObject();
//        查询列表
        return new ResultModel<>(sysUserService.findUser(username, pageUtil));
//        jsonObject.put("result", "success");
//        jsonObject.put("list", pageUtil);
//        return jsonObject.toJSONString();
//        return pageUtil;
    }


    /**
     * 用户添加;
     *
     * @return
     */
    @ApiIgnore
    @RequestMapping("/addUser")
    public String addUser() {
        return "addUser";
    }

    /**
     * @return com.lwohvye.springcloud.springcloudlwohvyeprovider.common.util.ResultModel<java.lang.Integer>
     * @description 添加用户
     * @params [user]
     * @author Hongyan Wang
     * @date 2020/1/14 10:28
     */
    @LogAnno(operateType = "添加用户")
    @ApiOperation(value = "添加新用户", notes = "添加新用户，包含用户的授权")
    @ApiOperationSupport(ignoreParameters = {"roles"})
    @RequestMapping(value = "/saveUser", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultModel<Integer> saveUser(@Valid User user) {
//        JSONObject jsonObject = new JSONObject();
//        sysUserService.saveUser(user);
//        jsonObject.put("result", "success");
//        return jsonObject.toJSONString();
        return new ResultModel<>(sysUserService.insertSelective(user));
    }

    /**
     * 用户删除;
     *
     * @return java.lang.String
     */
    @ApiIgnore
    @RequestMapping("/delUser")
    public String delUser() {
        return "deleteUser";
    }

    /**
     * @return com.lwohvye.springcloud.springcloudlwohvyeprovider.common.util.ResultModel<java.lang.Integer>
     * @description 删除用户
     * @params [uid]
     * @author Hongyan Wang
     * @date 2020/1/14 10:24
     */
    @LogAnno(operateType = "删除用户")
    @ApiOperation(value = "删除指定用户", notes = "根据用户的id删除对应的用户与权限")
    @ApiImplicitParam(name = "uid", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value = "/deleteUser", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultModel<Integer> deleteUser(Long uid) {
//        JSONObject jsonObject = new JSONObject();
//        sysUserService.deleteUser(user);
//        jsonObject.put("result", "success");
//        return jsonObject.toJSONString();
        return new ResultModel<>(sysUserService.deleteByPrimaryKey(uid));
    }

    /**
     * @return com.lwohvye.springcloud.springcloudlwohvyeprovider.common.util.ResultModel
     * @description 修改用户信息
     * @params [user]
     * @author Hongyan Wang
     * @date 2020/1/13 13:35
     */
    @LogAnno(operateType = "修改用户信息")
    @ApiOperation(value = "修改用户信息", notes = "根据用户id修改用户信息，包含部分信息修改。用户名username不可修改")
    @ApiOperationSupport(ignoreParameters = {"roles"})
    @RequestMapping(value = "/updateUser", method = {RequestMethod.GET, RequestMethod.POST})
    public ResultModel<Integer> updateUser(@Valid User user) {
//        JSONObject jsonObject = new JSONObject();
//        sysUserService.updateByPrimaryKeySelective(user);
//        jsonObject.put("result", "success");
//        return jsonObject.toJSONString();
        return new ResultModel<>(sysUserService.updateByPrimaryKeySelective(user));
    }

    @GetMapping(value = "/findLoginUser/{username}")
    public User findLoginUser(@PathVariable("username") String username){
        return sysUserService.findLoginUser(username);
    }

    @ApiIgnore
    @RequestMapping("/testRole")
//    限定只有admin角色可以访问
    public String testRole() {
        return "success";
    }

}
