package com.bw30.zsch.tribe.touch.test.changedate;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.bw.zsch.client.android.model.trip.changedate.TPCancelChangeDateReq;
import com.bw.zsch.client.android.model.trip.changedate.TPCancelChangeDateReqBody;
import com.bw.zsch.client.android.model.trip.changedate.TPCancelChangeDateReqHead;
import com.bw.zsch.client.android.model.trip.changedate.TPChangeDateChargeReq;
import com.bw.zsch.client.android.model.trip.changedate.TPChangeDateChargeReqBody;
import com.bw.zsch.client.android.model.trip.changedate.TPChangeDateChargeReqHead;
import com.bw.zsch.client.android.model.trip.changedate.TPFilterAndSortReshopSearchReq;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderFlightCanChangeDateReq;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderFlightCanChangeDateReqBody;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderFlightCanChangeDateReqHead;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderTicketCanChangeDateReq;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderTicketCanChangeDateReqBody;
import com.bw.zsch.client.android.model.trip.changedate.TPQueryOrderTicketCanChangeDateReqHead;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitCanChangePassengersAndFlightReq;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitCanChangePassengersAndFlightReqBody;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitCanChangePassengersAndFlightReqHead;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitProcessFreeDateChangeReq;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitProcessFreeDateChangeReqBody;
import com.bw.zsch.client.android.model.trip.changedate.TPSubmitProcessFreeDateChangeReqHead;
import com.bw.zsch.client.android.model.trip.flight.TPFilterAndSortFlightReqBody;
import com.bw.zsch.client.android.model.trip.flight.TPFilterAndSortFlightReqHead;
import com.bw30.zsch.tribe.touch.changedate.service.TribeChangeDateService;
import com.bw30.zsch.tribe.touch.test.base.BaseJunit4Test;
import com.bwhk.ticket.gateway.msg.ChooseReshopFlightChangeType;
import com.bwhk.ticket.gateway.msg.ChooseReshopFlightRequest;
import com.bwhk.ticket.gateway.msg.EditProduct;
import com.bwhk.ticket.gateway.msg.EditProduct.Airline;
import com.bwhk.ticket.gateway.msg.EditProduct.Airline.Passenger;
import com.bwhk.ticket.gateway.msg.EditProduct.Airline.Passenger.Flight;
import com.bwhk.ticket.gateway.msg.FlightFilterAndSortRequest;
import com.bwhk.ticket.gateway.msg.ProcessFreeDateChangeRequest;
import com.bwhk.ticket.gateway.msg.ProcessReshopCancelRequest;
import com.bwhk.ticket.gateway.msg.ReshopSearchRequest;
import com.bwhk.ticket.gateway.msg.TicketCanChangeDateRequest;
import com.bwhk.ticket.gateway.msg.ValidateMMBActionType;
import com.bwhk.ticket.gateway.msg.ValidateMMBChangeType;
import com.bwhk.ticket.gateway.msg.ValidateMMBRequest;

/**
 * 改期接口服务测试类
 * 
 * @author ShengHao
 *
 *         2017年7月31日 - 上午10:30:13
 */
public class TribeChangeDateServiceTest extends BaseJunit4Test {

	@Autowired
	protected TribeChangeDateService tribeChangeDateService;

	/**
	 * 测试查询可改期的旅客列表接口
	 */
	@Test
	public void testQueryChangeDatePassengerList() throws Exception {

		TPQueryOrderTicketCanChangeDateReq changeDatePassengerReq = new TPQueryOrderTicketCanChangeDateReq();
		TPQueryOrderTicketCanChangeDateReqHead head = new TPQueryOrderTicketCanChangeDateReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		changeDatePassengerReq.setHead(head);

		TPQueryOrderTicketCanChangeDateReqBody body = new TPQueryOrderTicketCanChangeDateReqBody();
		TicketCanChangeDateRequest ticketCanChangeDateRequest = new TicketCanChangeDateRequest();
		ticketCanChangeDateRequest.setOrderId(ORDER_ID);
		body.setTicketCanChangeDateRequest(ticketCanChangeDateRequest);
		changeDatePassengerReq.setBody(body);
		tribeChangeDateService.queryChangeDatePassengerList(changeDatePassengerReq);
	}

	/**
	 * 提交改期的乘客列表
	 */
	@Test
	public void testSubmitCanChangePassengersFlight() throws Exception {

		TPSubmitCanChangePassengersAndFlightReq submitPassengerReq = new TPSubmitCanChangePassengersAndFlightReq();

		TPSubmitCanChangePassengersAndFlightReqHead head = new TPSubmitCanChangePassengersAndFlightReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		submitPassengerReq.setHead(head);

		TPSubmitCanChangePassengersAndFlightReqBody body = new TPSubmitCanChangePassengersAndFlightReqBody();
		ValidateMMBRequest validateMMBRequest = new ValidateMMBRequest();
		validateMMBRequest.setValidateMMBActionType(ValidateMMBActionType.DATECHANGE);
		validateMMBRequest.setValidateMMBChangeType(ValidateMMBChangeType.CHANGE);

		// 选择的改期产品 只有一个
		List<EditProduct> editProductList = new ArrayList<EditProduct>();
		validateMMBRequest.setEditProductList(editProductList);
		{
			EditProduct editProduct = new EditProduct();
			editProductList.add(editProduct);

			editProduct.setProductNumber(1015);
			Airline airline = new Airline();
			editProduct.setAirline(airline);

			List<Passenger> passengerList = new ArrayList<Passenger>();
			airline.setPassengerList(passengerList);

			// 航班旅客列表，可以是多个
			Passenger passenger = new Passenger();
			passengerList.add(passenger);
			passenger.setRph("1");

			// 改期的航段 只有一个
			List<Flight> flightList = new ArrayList<Flight>();
			Flight flight = new Flight();
			flight.setRph("1");
			flightList.add(flight);

			passenger.setFlightList(flightList);
		}

		validateMMBRequest.setEditProductList(editProductList);
		body.setValidateMMBRequest(validateMMBRequest);
		submitPassengerReq.setBody(body);

		tribeChangeDateService.submitCanChangePassengersFlight(submitPassengerReq);
	}

	/**
	 * 查询可改期的航班列表
	 */
	@Test
	public void testQueryChangeDateFlightList() throws Exception {

		TPQueryOrderFlightCanChangeDateReq changeDateFlightReq = new TPQueryOrderFlightCanChangeDateReq();

		TPQueryOrderFlightCanChangeDateReqHead head = new TPQueryOrderFlightCanChangeDateReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		changeDateFlightReq.setHead(head);

		TPQueryOrderFlightCanChangeDateReqBody body = new TPQueryOrderFlightCanChangeDateReqBody();
		ReshopSearchRequest reshopSearchRequest = new ReshopSearchRequest();
		reshopSearchRequest.setNewDate("2017-12-20");
		body.setReshopSearchRequest(reshopSearchRequest);

		changeDateFlightReq.setBody(body);

		tribeChangeDateService.queryChangeDateFlightList(changeDateFlightReq);
	}

	/**
	 * 用户点击"改"按钮，调用的接口，判断是否免费改期
	 */
	@Test
	public void testChangeDate() throws Exception {

		TPChangeDateChargeReq tpChangeDateChargeReq = new TPChangeDateChargeReq();
		TPChangeDateChargeReqHead head = new TPChangeDateChargeReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		tpChangeDateChargeReq.setHead(head);

		TPChangeDateChargeReqBody body = new TPChangeDateChargeReqBody();
		ChooseReshopFlightRequest chooseReshopFlightRequest = new ChooseReshopFlightRequest();

		chooseReshopFlightRequest.setChooseReshopFlightChangeType(ChooseReshopFlightChangeType.DATECHANGE);
		String flightSegmentRPH = "1";
		String sequenceNumber = "1015";
		chooseReshopFlightRequest.setFlightSegmentRPH(flightSegmentRPH);
		chooseReshopFlightRequest.setSequenceNumber(sequenceNumber);

		body.setChooseReshopFlightRequest(chooseReshopFlightRequest);
		tpChangeDateChargeReq.setBody(body);

		tribeChangeDateService.changeDate(tpChangeDateChargeReq);
	}

	/**
	 * 测试免费改期
	 */
	@Test
	public void testFreeChangeDate() throws Exception {

		TPSubmitProcessFreeDateChangeReq submitProcessFreeDateChangeReq = new TPSubmitProcessFreeDateChangeReq();
		TPSubmitProcessFreeDateChangeReqHead head = new TPSubmitProcessFreeDateChangeReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		submitProcessFreeDateChangeReq.setHead(head);
		TPSubmitProcessFreeDateChangeReqBody body = new TPSubmitProcessFreeDateChangeReqBody();
		body.setProcessFreeDateChangeRequest(new ProcessFreeDateChangeRequest());
		submitProcessFreeDateChangeReq.setBody(body);

		tribeChangeDateService.freeChangeDate(submitProcessFreeDateChangeReq);
	}

	/***
	 * 测试过滤改期航班列表
	 */
	@Test
	public void testFilterFlightList() throws Exception {

		TPFilterAndSortReshopSearchReq filterFlightListReq = new TPFilterAndSortReshopSearchReq();
		TPFilterAndSortFlightReqHead head = new TPFilterAndSortFlightReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		filterFlightListReq.setHead(head);
		TPFilterAndSortFlightReqBody body = new TPFilterAndSortFlightReqBody();
		// 过滤条件，在这里做筛选条件添加过滤
		FlightFilterAndSortRequest flightFilterRequest = new FlightFilterAndSortRequest();

		body.setFlightFilterRequest(flightFilterRequest);
		filterFlightListReq.setBody(body);

		tribeChangeDateService.filterFlightList(filterFlightListReq);
	}

	/**
	 * 测试取消改期
	 */
	@Test
	public void testCancelChangeDate() throws Exception {
		TPCancelChangeDateReq tpCancelChangeDateReq = new TPCancelChangeDateReq();
		TPCancelChangeDateReqHead head = new TPCancelChangeDateReqHead();
		head.setPlatformId(TELEPHONE_PLATFORMID);
		tpCancelChangeDateReq.setHead(head);
		TPCancelChangeDateReqBody body = new TPCancelChangeDateReqBody();
		ProcessReshopCancelRequest processReshopCancelRequest = new ProcessReshopCancelRequest();
		body.setProcessReshopCancelRequest(processReshopCancelRequest);
		tpCancelChangeDateReq.setBody(body);
		tribeChangeDateService.cancelChangeDate(tpCancelChangeDateReq);
	}

}
