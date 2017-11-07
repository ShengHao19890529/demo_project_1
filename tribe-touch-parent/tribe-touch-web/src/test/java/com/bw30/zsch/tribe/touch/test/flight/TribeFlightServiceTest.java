package com.bw30.zsch.tribe.touch.test.flight;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.bw.zsch.client.android.model.common.pojo.Page;
import com.bw.zsch.client.android.model.trip.enumeration.TPSortType;
import com.bw.zsch.client.android.model.trip.flight.TPFilterAndSortFlightReq;
import com.bw.zsch.client.android.model.trip.flight.TPFilterAndSortFlightReqBody;
import com.bw.zsch.client.android.model.trip.flight.TPFilterAndSortFlightReqHead;
import com.bw.zsch.client.android.model.trip.flight.TPQueryFlightReq;
import com.bw.zsch.client.android.model.trip.flight.TPQueryFlightReqBody;
import com.bw.zsch.client.android.model.trip.flight.TPQueryFlightReqHead;
import com.bw30.zsch.tribe.touch.constants.SystemConstants.TribeH5PlatFormEnum;
import com.bw30.zsch.tribe.touch.flight.service.TribeFlightService;
import com.bw30.zsch.tribe.touch.test.base.BaseJunit4Test;
import com.bwhk.ticket.gateway.msg.FlightFilterAndSortRequest;
import com.bwhk.ticket.gateway.msg.FlightSearchRequest;
import com.bwhk.ticket.gateway.msg.FlightType;
import com.bwhk.ticket.gateway.msg.SearchType;

/**
 * 航班服务测试类
 * 
 * @author ShengHao
 *
 *         2017年7月31日 - 上午9:45:24
 */
public class TribeFlightServiceTest extends BaseJunit4Test {

	@Autowired
	private TribeFlightService tribeFlightService;

	/**
	 * 测试查询航班列表接口
	 */
	@Test
	public void queryFlightListTest() throws Exception {
		TPQueryFlightReq tpQueryFlightReq = new TPQueryFlightReq();
		TPQueryFlightReqHead head = new TPQueryFlightReqHead();
		// 测试用手机和微信，目前只做这两个PLATFORM_WECHAT PLATFORMID_TOUCH
		head.setPlatformId(TribeH5PlatFormEnum.PLATFORMID_TOUCH.getPlatFormCode());
		tpQueryFlightReq.setHead(head);

		FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
		// 到达城市
		flightSearchRequest.setDestCity("PEK");
		// 出发城市
		flightSearchRequest.setOrgCity("CTU");
		flightSearchRequest.setFlightType(FlightType.ONEWAY);
		// 出发时间
		flightSearchRequest.setDepartDate("2017-10-18");
		flightSearchRequest.setAdt(1);
		flightSearchRequest.setChd(0);
		flightSearchRequest.setCalendarSearch(false);
		flightSearchRequest.setSearchType(SearchType.F);
		flightSearchRequest.setUserPrice(false);

		TPQueryFlightReqBody body = new TPQueryFlightReqBody();
		body.setFlightSearchRequest(flightSearchRequest);

		Page page = new Page();
		page.setIndex(1);
		page.setCount(2147483647);
		body.setPage(page);

		body.setSortType(TPSortType.DATE_ASC);

		tpQueryFlightReq.setBody(body);
		tribeFlightService.queryFlightList(tpQueryFlightReq);
	}

	/**
	 * 测试过滤接口
	 */
	@Test
	public void testFilterAndSortFlight() throws Exception {
		TPFilterAndSortFlightReq filterAndSortFlightReq = new TPFilterAndSortFlightReq();
		TPFilterAndSortFlightReqHead head = new TPFilterAndSortFlightReqHead();
		head.setPlatformId(TribeH5PlatFormEnum.PLATFORMID_TOUCH.getPlatFormCode());
		filterAndSortFlightReq.setHead(head);

		TPFilterAndSortFlightReqBody body = new TPFilterAndSortFlightReqBody();
		FlightFilterAndSortRequest flightFilterRequest = new FlightFilterAndSortRequest();
		flightFilterRequest.setDirect(null);
		flightFilterRequest.setShare(null);

		List<String> planeModelList = new ArrayList<String>();
		planeModelList.add("33A");
		planeModelList.add("331");
		flightFilterRequest.setPlaneModel(planeModelList);

		List<String> takeOffTimeList = new ArrayList<String>();
		takeOffTimeList.add("FIRST");
		takeOffTimeList.add("SECOND");
		takeOffTimeList.add("THIRD");
		takeOffTimeList.add("FOURTH");
		flightFilterRequest.setTakeOffTime(takeOffTimeList);

		body.setFlightFilterRequest(flightFilterRequest);
		filterAndSortFlightReq.setBody(body);

		tribeFlightService.filterAndSortFlight(filterAndSortFlightReq);

	}

	/**
	 * 测试点击"订票"按钮，将航班舱位信息添加到购物车里
	 */
	@Test
	public void testAddShoppingcar() throws Exception {

	}

	/**
	 * 测试提供信息确认页面展示的数据
	 */
	@Test
	public void testCheckoutShoppingcart() throws Exception {

	}

	/**
	 * 测试创建订单，提交乘机人信息
	 */
	@Test
	public void testSubmitOrder() throws Exception {

	}

}
