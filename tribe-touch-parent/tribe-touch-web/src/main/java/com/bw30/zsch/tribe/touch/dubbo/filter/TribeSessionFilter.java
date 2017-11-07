package com.bw30.zsch.tribe.touch.dubbo.filter;

import org.apache.log4j.Logger;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.bw30.zsch.tribe.touch.web.utils.ThreadLocalUtils;
import static com.bwhk.mall.client.service.client.dubbo.utils.TribeInterfaceContextConstants.*;

/**
 * 系统级别的session过滤器，用于dubbo调用接口统一设置流水号和会话Id
 * 
 * @author ShengHao
 *
 */
public class TribeSessionFilter implements Filter {

	private final static Logger LOG = Logger.getLogger(TribeSessionFilter.class);

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

		// 流水号
		RpcContext.getContext().setAttachment(SEQUENCE_ID, ThreadLocalUtils.getSequenceId());
		// 会话Id
		RpcContext.getContext().setAttachment(SESSION_ID, ThreadLocalUtils.getSessionId());

		LOG.info("dubbo session过滤器中 seqId：" + RpcContext.getContext().getAttachment(SEQUENCE_ID) + " ， sessionId："
				+ RpcContext.getContext().getAttachment(SESSION_ID));

		/**
		 * 统一设置平台ID
		 */
		// Object[] paramObjArr = invocation.getArguments();
		// Object[] paramObjArr2 = new Object[paramObjArr.length];
		// int index = 0;
		// for (Object paramObj : paramObjArr) {
		// if (paramObj instanceof com.bw.zsch.client.android.model.message.Req)
		// {
		// com.bw.zsch.client.android.model.message.Req req =
		// (com.bw.zsch.client.android.model.message.Req) paramObj;
		// req.getHead().setPlatformId(10);
		// }
		// paramObjArr2[index++] = paramObj;
		// }
		// RpcInvocation rpcInvocation = (RpcInvocation) invocation;
		// rpcInvocation.setArguments(paramObjArr2);

		Result result = invoker.invoke(invocation);
		return result;
	}

}
