package cn.jerio.user.controller;

import cn.jerio.entity.PageResult;
import cn.jerio.entity.Result;
import cn.jerio.pojo.TbUser;
import cn.jerio.user.service.UserService;
import cn.jerio.util.PhoneFormatCheckUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Jerio on 2018/09/27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbUser> findAll(){
        return userService.findAll();
    }


    /**
     * 返回全部列表
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows){
        return userService.findPage(page, rows);
    }

    /**
     * 增加
     * @param user
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbUser user, String smscode){

        //校验验证码是否正确
        boolean checkSmsCode = userService.checkSmsCode(user.getPhone(), smscode);
        if(!checkSmsCode){
            return Result.fail("验证码不正确！");
        }


        try {
            userService.add(user);
            return Result.success("增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("增加失败");
        }
    }

    /**
     * 修改
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbUser user){
        try {
            userService.update(user);
            return Result.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("修改失败");
        }
    }

    /**
     * 获取实体
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbUser findOne(Long id){
        return userService.findOne(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            userService.delete(ids);
            return Result.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("删除失败");
        }
    }

    /**
     * 查询+分页
     * @param brand
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbUser user, int page, int rows  ){
        return userService.findPage(user, page, rows);
    }

    @RequestMapping("/sendCode")
    public Result sendCode(String phone){

        if(!PhoneFormatCheckUtils.isPhoneLegal(phone)){
            return Result.fail("手机格式不正确");
        }

        try {
            userService.createSmsCode(phone);
            return Result.success("验证码发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("验证码发送失败");
        }
    }

}
