package cn.jerio.cart.controller;

import cn.jerio.cart.util.UserUtil;
import cn.jerio.entity.PageResult;
import cn.jerio.entity.Result;
import cn.jerio.pojo.TbAddress;
import cn.jerio.pojo.TbUser;
import cn.jerio.user.service.AddressService;
import cn.jerio.user.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private HttpServletRequest request;

    @Reference
    private UserService userService;

	@Reference
	private AddressService addressService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbAddress> findAll(){
		return addressService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return addressService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param address
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbAddress address){
		try {
			addressService.add(address);
			return Result.success("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail("增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param address
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbAddress address){
		try {
			addressService.update(address);
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
	public TbAddress findOne(Long id){
		return addressService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			addressService.delete(ids);
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
	public PageResult search(@RequestBody TbAddress address, int page, int rows  ){
		return addressService.findPage(address, page, rows);		
	}
	
	@RequestMapping("/findListByLoginUser")
	public List<TbAddress> findListByLoginUser(){
		//获取登陆用户
        TbUser user = UserUtil.getUserByToken(request,userService);
        if (user == null)
            return null;
		return addressService.findListByUserId(user.getUsername());
	}
}
