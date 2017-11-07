package com.bw30.zsch.tribe.touch.test.order;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderDetailReq;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderDetailReqBody;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderDetailReqHead;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderReq;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderReqBody;
import com.bw.zsch.client.android.model.trip.order.TPQueryOrderReqHead;
import com.bw30.zsch.tribe.touch.order.service.TribeOrderService;
import com.bw30.zsch.tribe.touch.test.base.BaseJunit4Test;
import com.bwhk.ticket.gateway.msg.BookingSearchRequest;
import com.bwhk.ticket.gateway.msg.RetrieveBookingRequest;

/**
 * 订单接口服务测试类
 * 
 * @author ShengHao
 *
 *         2017年7月31日 - 上午10:32:07
 */
public class TribeOrderServiceTest extends BaseJunit4Test {

	@Autowired
	private TribeOrderService tribeOrderService;

	/**
	 * 查询订单列表
	 */
	@Test
	public void test_tp_query_order() throws Exception {
		TPQueryOrderReq req = new TPQueryOrderReq();

		TPQueryOrderReqHead head = new TPQueryOrderReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		req.setHead(head);
		TPQueryOrderReqBody body = new TPQueryOrderReqBody();
		BookingSearchRequest bookingSearchRequest = new BookingSearchRequest();
		bookingSearchRequest.setBookingDateStart("2017-08-01");
		bookingSearchRequest.setBookingDateEnd("2017-08-25");
		body.setBookingSearchRequest(bookingSearchRequest);
		req.setBody(body);

		tribeOrderService.queryOrderList(req);
	}

	/**
	 * 查询订单详情
	 */
	@Test
	public void test_query_order_detail() throws Exception {
		TPQueryOrderDetailReq req = new TPQueryOrderDetailReq();
		TPQueryOrderDetailReqHead head = new TPQueryOrderDetailReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		req.setHead(head);
		TPQueryOrderDetailReqBody body = new TPQueryOrderDetailReqBody();
		RetrieveBookingRequest retrieveBookingRequest = new RetrieveBookingRequest();
		retrieveBookingRequest.setOrderId(ORDER_ID);
		body.setRetrieveBookingRequest(retrieveBookingRequest);
		req.setBody(body);
		tribeOrderService.queryOrderDetail(req);
	}

}
