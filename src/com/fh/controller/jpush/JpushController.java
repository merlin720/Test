package com.fh.controller.jpush;


import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.jpush.JpushService;
import com.fh.util.JPushClientUtil;
import com.fh.util.PageData;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/jpushController")
public class JpushController extends BaseController {
	@Resource(name="jpushService")
	private JpushService jpushService; 
	
	/**
	 * 推送
	 */
	@RequestMapping
	public void  pushMessage(PrintWriter out){
		
		logBefore(logger, "极光推送信息");
		try {
			mv.clear();
			pd = this.getPageData();
			//pd = totalService.findByPushId(pd);
			String msgTitle = "LookPlasMessage";
			String msgContent = pd.getString("content");
			String userid= "";
			JPushClientUtil.pushMessage(msgTitle, msgContent, userid);
			
			out.write("success");
			out.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 显示列表 
	 *//*
	@RequestMapping
	public ModelAndView  list(){
		
		List<PageData> itemList =null;
		try {
			itemList = jpushService.listPageAMenu();
			for(int i=0;i<=itemList.size();i++){
				String	msgTitle="系统通知";
				String  msgContent="尊敬的用户，您的"+itemList.get(i).get("scenic_name")+"门票还有两天就要过期，请您尽快使用。";
				itemList.get(i).put("msgTitle",msgTitle);
				itemList.get(i).put("msgContent",msgContent);
			}
			JPushClientUtil.pushManyMessage(itemList);
		} catch (Exception e){
			e.printStackTrace();
		}
		return mv;
	}*/
	
}
