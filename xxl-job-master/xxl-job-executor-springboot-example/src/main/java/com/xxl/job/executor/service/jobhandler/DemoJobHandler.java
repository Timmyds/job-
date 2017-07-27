package com.xxl.job.executor.service.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.utils.HttpProxy;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * 任务Handler的一个Demo（Bean模式）
 *
 * 开发步骤：
 * 1、继承 “IJobHandler” ；
 * 2、装配到Spring，例如加 “@Service” 注解；
 * 3、加 “@JobHander” 注解，注解value值为新增任务生成的JobKey的值;多个JobKey用逗号分割;
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2015-12-19 19:43:36
 */
@JobHander(value="springbootJobHandler")
@Service
public class DemoJobHandler extends IJobHandler {

	@Override
	public ReturnT<String> execute(String... params) throws Exception {
		XxlJobLogger.log("XXL-JOB, Hello World.");
		XxlJobLogger.log("---------------!!!!!!!!!!!!!!!!!!!!!!!!!!!------------------------------");
		ReturnT ReturnT=new ReturnT<>();
		return ReturnT.SUCCESS;
	}
public static void main(String[] args) {
	try {
		String json = HttpProxy.get("https://www.hao123.com/");
		System.out.println(json);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
