package com.likg.weibo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 * 获取当前时间
	 * @return
	 * @author likaige
	 * @create 2015年6月25日 上午9:25:28
	 */
	public static String getNowTime(){
		String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		return nowTime;
	}
}
