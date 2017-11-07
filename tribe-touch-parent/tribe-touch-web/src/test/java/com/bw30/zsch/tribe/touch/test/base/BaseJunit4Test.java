package com.bw30.zsch.tribe.touch.test.base;

import static com.bwhk.mall.client.service.client.dubbo.utils.TribeInterfaceContextConstants.*;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.bw30.zsch.tribe.touch.constants.SystemConstants.TribeH5PlatFormEnum;
import com.bw30.zsch.tribe.touch.utils.StringUtil;
import com.bw30.zsch.tribe.touch.web.utils.ThreadLocalUtils;

/**
 * 测试基类
 * 
 * @author ShengHao
 *
 *         2017年7月31日 - 上午9:44:33
 */
// 表示整合JUnit4进行测试
@RunWith(SpringJUnit4ClassRunner.class)
// 加载spring配置文件
@ContextConfiguration(locations = { "classpath:/spring-context-test.xml" })
public class BaseJunit4Test {

	private final static Logger LOG = Logger.getLogger(BaseJunit4Test.class);

	protected final static int TELEPHONE_PLATFORMID = TribeH5PlatFormEnum.PLATFORMID_TOUCH.getPlatFormCode();
	protected final static String ORDER_ID = "201708241033512784";

	@Before
	public void before() {
		// 设置当前请求的会话ID，目前设置死，模拟一次会话
		ThreadLocalUtils.setSessionId("60b9999fd9f745b5b11d1c79d8e48969");
		// 设置当前请求的流水号
		ThreadLocalUtils.setSequenceId(StringUtil.getUUID());
		// LOG4J使用，统一添加当前的流水号
		MDC.put(SEQUENCE_ID, ThreadLocalUtils.getSequenceId());
		MDC.put(SESSION_ID, ThreadLocalUtils.getSessionId());

		LOG.info("Junit reqId：" + ThreadLocalUtils.getSequenceId() + " ， " + "Junit sessionId："
				+ ThreadLocalUtils.getSessionId());
	}

}
