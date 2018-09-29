package cn.jerio.cart.controller;

import cn.jerio.cart.util.UserUtil;
import cn.jerio.entity.PageResult;
import cn.jerio.entity.Result;
import cn.jerio.order.service.OrderService;
import cn.jerio.pojo.TbOrder;
import cn.jerio.pojo.TbUser;
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
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private HttpServletRequest request;

	@Reference
	private OrderService orderService;

    @Reference
    private UserService userService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbOrder> findAll(){
		return orderService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return orderService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param order
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbOrder order){
		
		//获取当前登录人账号
        TbUser user = UserUtil.getUserByToken(request,userService);
        if (user == null)
            return Result.success("用户不存在");
		order.setUserId(user.getUsername());
		order.setSourceType("2");//订单来源  PC
		
		try {
			orderService.add(order);
			return Result.success("增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.fail("增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param order
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbOrder order){
		try {
			orderService.update(order);
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
	public TbOrder findOne(Long id){
		return orderService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			orderService.delete(ids);
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
	public PageResult search(@RequestBody TbOrder order, int page, int rows  ){
		return orderService.findPage(order, page, rows);		
	}
	
}
