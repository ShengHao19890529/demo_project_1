/*touch.js lym 20170824*/

/*Common*/
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURI(r[2]);
	return null;
}
function getSessionId() {
	
	var r = window.location.href.split(";");
	var session = r[1].split("?");
	return session[0].substring(11) ;

}
/*弹框调用方法*/
function showConfirm(title,context,btn){
	var box='<div class="alert-box"><div class="alert-box-title">'+title+'</div><div class="alert-box-context">'+context+'</div>';
	if (isNull(btn)) {
		box = box + ('<div class="alert-btn" onclick="layer.closeAll()">确定</div></div>')
	}else{
		box = box + ('<div class="alert-btn-2"><div class="alert-btn-l"  onclick="'+btn.fun()+'">'+btn.lName+'</div><div class="alert-btn-r"  onclick="layer.closeAll()">'+btn.rName+'</div></div></div>');
	}

	layer.open({
		type: 1,
		content: box,
		anim: 'up',
		style: 'position:fixed; top:0; left:0; width: 100%; height: 100%;    background-color: rgba(0,0,0,0.5);overflow-y:hidden;z-index: 999;'
	});
	$("body").attr('style', 'overflow-y:hidden');


}
function Init_hb(){/*后退按钮设置*/
	
	$("header>.back").bind('click', function(event) {
		window.history.back();
	});
}
function loading(){//缓冲页面

	layer.open({
		type: 1,
		content: '<div class="loading"><img src="../../resource/images/loading.gif"></div>',
		anim: 'up',
		style: 'position:fixed; top:0; left:0; width: 100%; height: 100%;    background-color: rgba(0,0,0,0.5);overflow-y:hidden;z-index: 999;'
	});
	$("body").attr('style', 'overflow-y:hidden');
}

function setload(){//设置定时缓冲条
	loading();
	setTimeout(function(){
		layer.closeAll();
		$("body").attr('style', 'overflow-y:scroll');
	},1000);
}

function switchStaus(status){
	var message = "";
	switch(status){
		case "OK":message = "";break;
		case "SEND_VERIFY_CODE_SUCCESS":message = "";break;
		case "CHECK_IN_SUCCEED":message = "";break;
		case "SUBMIT_FEED_BACK_SUCCEED":message = "";break;


		case "DB_OPER_FAILED":message = "数据库操作失败";break;
		case "NETWORK_BUSY":message = "网络繁忙，请稍后重试";break;
		case "QUERY_FLIGHT_FAILED":message = "查询不到航班信息";break;
		case "QUERY_FLIGHT_FAILED_CAN_EXCHANE":message = "查询不到可改期航班信息";break;
		case "MEMBER_NOT_LOG_IN_ERROR":message = "请您先登录";break;
		case "QUERY_MEMBER_FAILED":message = "没有查询到该常旅客信息";break;
		case "MEMBER_OR_PSD_ERROR":message = "用户名或密码错误，请查证后重新输入";break;
		case "PSD_ERROR":message = "您输入的旧密码不正确，请重新输入";break;
		case "INPUT_RIGHT_MEMBER_ID":message = "请输入正确的会员卡号";break;
		case "LOG_IN_ERROR_MANY_TIMES":message = "请输入验证码";break;
		case "LOGIN_PSD_ERROR":message = "密码错误，请重新输入";break;

		case "LOGIN_TOKEN_ERROR":message = "登录失效,请重新登录";break;
		case "VERIFY_CODE_NO_MATCH_ERROR":message = "验证码错误";break;
		case "VERIFY_CODE_INVALID_ERROR":message = "验证码失效";break;
		case "VERIFY_CODE_MOBILE_NOT_FIND":message = "系统未找到您的手机号信息";break;
		case "MOBILE_REGISTERWD_ERROR":message = "手机号码已被注册";break;
		case "FOID_NO_REGISTERWD_ERROR":message = "证件号码已被注册";break;
		case "EXCHANGE_CHECK_FAILED":message = "很抱歉，您的累计里程达5000公里或有效乘机3次，才能进行首次兑换";break;
		case "CREATE_ORDER_FAILED_NOT_ENOUGH_AMOUNT":message = "生成订单失败，余票不足";break;
		case "CREATE_ORDER_FAILED_NOT_ENOUGH_MILES":message = "您的里程余额不足";break;
		case "QUERY_ORDER_FAILED":message = "未查询到订单记录";break;
		case "QUERY_ORDER_DETAIL_FAILED":message = "未查询到该订单记录";break;
		case "QUERY_FLIGHT_STATUS_FAILED":message = "查询不到航班信息";break;
		case "SEAT_IS_SAVED":message = "您选择的座位已经被占用请重新选择";break;
		case "CHECK_IN_FAILED":message = "值机失败";break;
		case "CREATE_MILES_RECORD_INFO_ERROR":message = "补登信息有误，无法验证信息有效性，请与川航常旅客服务中心联系";break;
		case "NAME_NOT_MATCH_FOID":message = "旅客姓名与证件不匹配";break;
		case "ORDERID_NOT_MATCH_MOBILE":message = "订单号和手机号不匹配";break;
		case "GET_JOURNEY_FAILED":message = "很抱歉！无法提取到您的行程信息，请检查你的输入是否正确。";break;
		case "GET_BOARDING_PASS_ERROR":message = "很抱歉，暂时无法获取到登机牌信息！";break;
		case "DEAD_LINE_BEFORE_TODAY_ERROR":message = "截止日期不能大于当前日期";break;
		case "REFUND_FAILED":message = "退票失败，请先取消值机后再进行退票";break;
		case "FOID_OR_BIRTHDAY_ERROR":message = "证件号码或生日错误，请确认后重新输入！";break;
		case "QUERY_WEATHER_FAILED":message = "暂时无法获取本地区天气信息";break;
		case "HEAD_ERROR":message = "请求头格式错误";break;
		case "SIGN_ERROR":message = "签名验证异常";break;
		case "VERIFY_CODE_NULL_ERROR":message = "验证码不能为空";break;
		case "MOBILE_UN_REGISTER":message = "手机号未注册";break;
		case "PLACE_ORDER_ERROR":message = "提交订单失败";break;
		case "PPAY_TYPE_INVALID_ERROR":message = "您选择的支付方式已不支持该笔订单";break;
		case "ORDER_RE_PAY_ERROR":message = "订单已支付成功";break;
		case "ADD_CART_ERROR":message = "商品加入购物车失败";break;
		case "OTHER_ERROR":message = "其他异常";break;

	}
	return message ; 
}
function returnCityName(citycode){/*调动接口 返回城市编码*/

	var name = "";
	var para = { "body": {"keyword": citycode },"head": {"platformId": 3} }
	//loading();
	doAjax2("../city/queryCity",para,function(obj){
		//layer.closeAll();
  
	   
		if ( (!isNull( obj.body.message)&& (obj.body.message.keyCode%10==0 ) )||(!isNull(obj.body.status)&&(isNull(switchStaus(obj.body.status))))  ){
	
			name = obj.body.cityList[0].name ;
		} else {
			if (!isNull(obj.body.message)) {
				showConfirm("提示",obj.body.message.value);
			}else{
				showConfirm("提示",switchStaus(obj.body.status));
			}
			
		}
	}, function(){
		//layer.closeAll();
		showConfirm("提示","网络繁忙，请稍后再试");
	},30000);
	return name ;
}
/*gqsq*/
function Init_gqsq(){
	loading();
	var param = {"body": {"ticketCanChangeDateRequest": {"orderId": sessionStorage.orderId}},"head": {"platformId": 3}} ;
	
	doAjax("queryChangeDatePassengerList",param,function(obj){
		
		if (  (!isNull( obj.body.message)&& (obj.body.message.keyCode%10==0 ) )||(!isNull(obj.body.status)&&(isNull(switchStaus(obj.body.status))))  ){
			setHTML_gqsq(obj);
		}else{
			if (!isNull( obj.body.message)) {
				showConfirm("提示",obj.body.message.value);
			}else{
				showConfirm("提示",switchStaus(obj.body.status));
			}

		}
		
	}, function(){
		layer.closeAll();
		showConfirm("提示","网络繁忙，请稍后再试");
	},30000);//超时时间设为30s

}
function setHTML_gqsq(obj){
	obj_ajax = obj ;
	//航班列表
	var obj_detail = obj.body.ticketCanChangeDateResponse.editProductList[0].airline.passengerList;

	for (var i = 0; i < obj_detail[0].flightList.length; i++) {
		
		var currentFlight = obj_detail[0].flightList[i];
		var priceType = currentFlight.priceType;
		var ticketingStatus = currentFlight.ticketingStatus;
		
		if ( (priceType == null || ( priceType != "Upgrade" && priceType != "DateChange" ) ) && ( ticketingStatus == null || ticketingStatus == "OPEN FOR USE") ) {
			var hblist ='<p class="ticketinfo-list-ps"><span class="gray2">' + obj_detail[0].flightList[i].marketingAirline+obj_detail[0].flightList[i].flightNo+'</span></p>';
			if ( i == (obj_detail.length-1) ) {
				hblist = hblist +'<div class="content">';
			}else{
				hblist = hblist +'<div class="content dashed">';
			}

			hblist = hblist +'<div class="l-p"><p class="airp-p">'+obj_detail[0].flightList[i].takeOffAirport+obj_detail[0].flightList[i].takeOffTerminal+'</p><p class="date-p">'+obj_detail[0].flightList[i].takeOffTime.substring(10,16)+' &nbsp' +obj_detail[0].flightList[i].takeOffTime.substring(5,10)+' &nbsp '+returnDay(obj_detail[0].flightList[i].takeOffTime)+'</p></div>';
			hblist = hblist +'<div class="m-p"><img src="../../resource/images/hourline.png"></div>';
			hblist = hblist +'<div class="r-p"><p class="airp-p">'+obj_detail[0].flightList[i].arriveAirport+obj_detail[0].flightList[i].arriveTerminal+'</p><p class="date-p">'+obj_detail[0].flightList[i].arriveTime.substring(10,16)+' &nbsp' +obj_detail[0].flightList[i].arriveTime.substring(5,10)+' &nbsp '+returnDay(obj_detail[0].flightList[i].arriveTime)+'</p></div>';
			hblist = hblist +'<b value='+i+'></b></div>';
			$("#hblist").append(hblist);
		};
			
	};
	
	//乘机人列表
	var obj_passenger = obj.body.ticketCanChangeDateResponse.editProductList[0].airline.passengerList;
	
	
	for (var i = 0; i < obj_passenger.length; i++) {

		var cjrlist ='';
		if ( i == (obj_passenger.length-1) ) {
			cjrlist = cjrlist +'<div class="content">';
		}else{
			cjrlist = cjrlist +'<div class="content dashed">';
		}
		
		cjrlist = cjrlist +'<p class="bgr-nm"><span class="red-sqr">'+(i+1)+'</span><span>姓名：'+obj_passenger[i].surnName+obj_passenger[i].givenName+'（'+returnPassengerType(obj_passenger[i].passengerType)+'）</span></p>';
		cjrlist = cjrlist +'<p class="bgr-nu">'+returnDoctype(obj_passenger[i].docType)+'：'+obj_passenger[i].docId+'<b value='+i+' passengerType="'+obj_passenger[i].passengerType+ '" birthday="' + obj_passenger[i].birthday + '"></b></p></div>';
		
		$("#cjrlist").append(cjrlist);
		
	};
	
	
	setBInd_gqsq();
	layer.closeAll();
}
function setBInd_gqsq(){
	$("#hblist .content").bind('click', function(e) {
		$("#hblist  b").removeClass('checked');
		var b = e.currentTarget.getElementsByTagName('b') ;
		$(b).toggleClass("checked");
	});

	$("#cjrlist .content").bind('click', function(e) {
		
		var b = e.currentTarget.getElementsByTagName('b') ;
		
		$(b).toggleClass("checked");
	});

	$("#gqstep1btn").bind('click', function(event) {

		var para = { "body": { "validateMMBRequest": { "editProductList": [ { "airline": { "passengerList": [{ "flightList": [{ "rph": ""}], "rph": "" }]}, "productNumber": "" }], "validateMMBActionType": "DATECHANGE","validateMMBChangeType": "CHANGE"}},"head": {"platformId": 3}};

		var change_Info = {} ;
		para.body.validateMMBRequest.editProductList[0].productNumber= obj_ajax.body.ticketCanChangeDateResponse.editProductList[0].productNumber ;
		sessionStorage.setItem("selectedProductNumber",obj_ajax.body.ticketCanChangeDateResponse.editProductList[0].productNumber);

		var hb = $("#hblist .checked").attr('value');//航班数
		var cjr = $("#cjrlist .checked"),cjr_b =$("#cjrlist b"); // 乘机人
		var hbObj=hb,cjrObj={"list":[]};
		var cjr_num = [], adNum = 0 ,chlNum = 0, notRealAdultNum = 0, isOpenForUse = false;//已选中
		var ad = 0,chl = 0,adNum_nc = 0,chlNum_nc = 0, notRealAdult = 0;//未选中
		
		
		// 当前被选择的乘机人
		jQuery.each(cjr, function(index, val) {
			cjr_num[index] = cjr[index].getAttribute('value');
			if ( cjr[index].getAttribute('passengerType') =="ADT") {
//				adNum++ ;
				var birth = cjr[index].getAttribute('birthday')
				// 大于18岁的成人
				if (realAdult(birth)) {
					adNum++;
				} else {
					notRealAdultNum++;
				}
			}else if ( cjr[index].getAttribute('passengerType') =="CHD" ) {
				chlNum ++;
			};
		});
		
		// 所有的乘机人
		jQuery.each(cjr_b, function(index, val) {
			if ( cjr_b[index].getAttribute('passengerType') =="ADT") {
//				ad++ ;
				var birth = cjr_b[index].getAttribute('birthday')
				// 大于18岁的成人
				if (realAdult(birth)) {
					ad++;
				} else {
					notRealAdult++;
				}
			}else if ( cjr_b[index].getAttribute('passengerType') =="CHD" ) {
				chl ++;
			};
		});

		adNum_nc = ad - adNum ;
		chlNum_nc = chl - chlNum ;
		
		if ( isNull(hb) || isNull(cjr_num) ) {
			showConfirm("温馨提示","航班或者乘机人不能为空，请选择后再提交变更申请");
			return;
		}

		para.body.validateMMBRequest.editProductList[0].airline.passengerList=[];
		jQuery.each(cjr_num, function(index, val) {
			
			para.body.validateMMBRequest.editProductList[0].airline.passengerList[index]={};

			para.body.validateMMBRequest.editProductList[0].airline.passengerList[index].rph=obj_ajax.body.ticketCanChangeDateResponse.editProductList[0].airline.passengerList[ cjr_num[index] ].rph;

			cjrObj.list[index] = obj_ajax.body.ticketCanChangeDateResponse.editProductList[0].airline.passengerList[ cjr_num[index] ];

			// 航线rph
			para.body.validateMMBRequest.editProductList[0].airline.passengerList[index].flightList = [];
			para.body.validateMMBRequest.editProductList[0].airline.passengerList[index].flightList[0]= {};


			para.body.validateMMBRequest.editProductList[0].airline.passengerList[index].flightList[0].rph =obj_ajax.body. ticketCanChangeDateResponse.editProductList[0].airline.passengerList[cjr_num[index]].flightList[hb].rph;

			if (obj_ajax.body.ticketCanChangeDateResponse.editProductList[0].airline.passengerList[cjr_num[index]].flightList[hb].ticketingStatus != "OPEN FOR USE") {
				isOpenForUse = true;
			};
		
		});
		
		if ( isOpenForUse ) {
			showConfirm("温馨提示","乘机人客票状态无法改期");
			return;
		}else if ( adNum == 0 && chlNum != 0) {
			showConfirm("温馨提示","携带儿童的成人乘机人年龄必须大于等于18岁");
			return;
		}else  if ( (adNum_nc*2)<chlNum_nc ) {
			showConfirm("温馨提示","该航班未改期乘机人中有儿童无成人陪同");
			return;
		}else if ( (adNum*2)<chlNum ) {
			showConfirm("温馨提示","1名成人只能携带2名儿童改期");
			return;
		}else{
			loading();
			doAjax("submitCanChangePassengersFlight",para,function(obj){
				layer.closeAll();
				if (  (!isNull( obj.body.message)&& (obj.body.message.keyCode%10==0 ) )||(!isNull(obj.body.status)&&(isNull(switchStaus(obj.body.status))))  ){
					sessionStorage.submitCanChangePassengersFlightObj = JSON.stringify(obj);
					sessionStorage.cjrInfo = JSON.stringify(cjrObj) ;//乘机人信息存储
					sessionStorage.hbInfo = hbObj ;//乘机人信息存储
					changePage("goChangeDateChoosePage");
				} else {
					if (!isNull( obj.body.message)) {
						showConfirm("提示",obj.body.message.value);
					}else{
						showConfirm("提示",switchStaus(obj.body.status));
					}
				}
			}, function(){
				layer.closeAll();
				showConfirm("提示","网络繁忙，请稍后再试");
				
			},30000);//超时时间设为30s
			

		}	
	});
	
}

/*hbcx*/
function Init_hbcx(){
	Init_hb();
	var cjr = JSON.parse(sessionStorage.cjrInfo) ;  //乘机人信息存储
	var hb = sessionStorage.hbInfo ; //乘机人信息存储
	//时间定义插件
	var hb_time = new Date(cjr.list[0].flightList[hb].takeOffTime.substr(0,10));//航班时间
	var set_time ="";
	var opt={};

	if (cjr.list[0].flightList[hb].rph == "1") {// 	航段为中转首段或直飞：航班时间>当前时间时，则可选日期为[当前时间,c出票时间+1年]
		set_time = new Date() ;
	}else{//	航段联程航班中的为非首段航班：则可选日期为上一个航段到达时间+1年
		var index = hb-1 < 0 ? 0 : (hb - 1);
		set_time = new Date(cjr.list[0].flightList[index].arriveTime.substr(0,10)) ;
	}
	// opt.default
	opt['default'] = {
		preset : 'date', 
		theme: 'android-ics light', //皮肤样式
		display: 'modal', //显示方式 
		mode: 'scroller', //日期选择模式
		lang:'zh',
		dateOrder: 'yymmdd',
		dateFormat: 'yyyy-mm-dd', 
		startYear: set_time.getFullYear(), //开始年份
		startMonth: set_time.getMonth(), //开始月份
		startDay: set_time.getDate(), //开始日
		endYear: set_time.getFullYear() + 1, //结束年份
		endMonth: set_time.getMonth(), //结束月份
		endDay: set_time.getDate(), //结束日
	};

	var optDateTime = $.extend(opt['date'], opt['default']) 
	var optTime = $.extend(opt['time'], opt['default']);

	$("#appDateTime").mobiscroll(optDateTime);
	$("#appDateTime").val( addDate(cjr.list[0].flightList[hb].takeOffTime.substr(0,10),1) );
	$("#weekday").html(returnDay(addDate(cjr.list[0].flightList[hb].takeOffTime.substr(0,10),1)));

	$("#appDateTime").change(function(event) {
		$("#weekday").html(returnDay($("#appDateTime").val()));
	});
	$("#appDateTimeAll").click(function(){
		$("#appDateTime").click();
	})


	$("#hbInfo").html( returnCityName(cjr.list[0].flightList[hb].orgCity)+"——"+returnCityName(cjr.list[0].flightList[hb].destCity) );

	//乘机人列表
	$("#cjrlist").append('<div class="content2"><p class="bgr-h1">已择乘机人</p></div>');

	for (var i = 0; i < cjr.list.length; i++) {
		
		var cjrlist ='';
		if ( i == (cjr.length-1) ) {
			cjrlist = cjrlist +'<div class="content2">';
		}else{
			cjrlist = cjrlist +'<div class="content2 solid">';
		}
		cjrlist = cjrlist +'<p class="bgr-h2">姓名：'+cjr.list[i].surnName+cjr.list[i].givenName+'（'+returnPassengerType(cjr.list[i].passengerType)+'）</p><p class="bgr-h2">'+returnDoctype(cjr.list[i].docType)+'：'+cjr.list[i].docId+'</p></div>';
		$("#cjrlist").append(cjrlist);
	};


	$("#gqstep2btn").bind('click', function(event) {
		var para = {"body": {"reshopSearchRequest": {"newDate": $("#appDateTime").val()}},"head": {"platformId": 3}};
		loading();
		doAjax("queryChangeDateFlightList",para,function(obj){
			layer.closeAll();
			
			if (  (!isNull( obj.body.message)&& (obj.body.message.keyCode%10==0 ) )||(!isNull(obj.body.status)&&(isNull(switchStaus(obj.body.status))))  ){
				if (obj.body.airlines.length == 0||isNull(obj.body.airlines)) {//查询结果 没有航班 弹框提醒
					showConfirm("提示","很抱歉，没有查询到符合您要求的航班");
				}else{
					sessionStorage.flightList = JSON.stringify(obj);//存储下一页面数据 进行直接展示
					changePage("goChangeDateFlightListPage");
				}
			} else {
				if (!isNull( obj.body.message)) {
					showConfirm("提示",obj.body.message.value);
				}else{
					showConfirm("提示",switchStaus(obj.body.status));
				}
			}
			
		}, function(){
			layer.closeAll();
			showConfirm("提示","网络繁忙，请稍后再试");
		},60000);		
	
	});
}

/*hblb*/
var setSortList = true ;
function setHTML_hblb(){//根据上一页面传值进行页面渲染
	setload();
	$("#flightList").html("");
	var airlines = flight_obj.body.airlines[0].airline;
	if (isNull(airlines)||airlines.length==0) {
		showConfirm("提示","根据筛选条件未查到对应的航班信息");
		return;
	};

	$("#hbcity").html( returnCityName(airlines[0].airlineDetailList[0].airlineDetail.summary.orgCity )+'<img class="to" src="../../resource/images/to.png">'+returnCityName(airlines[0].airlineDetailList[0].airlineDetail.summary.destCity));

	$.each(airlines, function(index, val) {
		var flight_html = '<li>';
		
		flight_html = flight_html+ '<div class="content"><div class="dc-lp"><div class="l-p"><p class="time-p">'+airlines[index].airlineDetailList[0].airlineDetail.summary.departTime.substring(10,16)+'</p><p class="airport">'+airlines[index].airlineDetailList[0].airlineDetail.summary.departAirport+airlines[index].airlineDetailList[0].airlineDetail.flights[airlines[index].airlineDetailList[0].airlineDetail.journeyId].takeOffTerminal+'</p></div><div class="m-p"><p>'+ getSplitStr(airlines[index].airlineDetailList[0].airlineDetail.duration,":").str_before+'h'+getSplitStr(airlines[index].airlineDetailList[0].airlineDetail.duration,":").str_after+'m</p><img src="../../resource/images/hourline.png"></div><div class="r-p"><p class="time-p">'+airlines[index].airlineDetailList[0].airlineDetail.summary.arrivalTime.substring(10,16)+returnIsAddDay(airlines[index].airlineDetailList[0].airlineDetail.summary.departTime,airlines[index].airlineDetailList[0].airlineDetail.summary.arrivalTime)+'</p><p class="airport">'+airlines[index].airlineDetailList[0].airlineDetail.summary.arriveAirport+airlines[index].airlineDetailList[0].airlineDetail.flights[airlines[index].airlineDetailList[0].airlineDetail.journeyId].arriveTerminal+'</p></div></div><div class="dc-rp"><div class="price-p"><p class="price"><span >￥</span>'+ airlines[index].price.fareFamilies[0].fareSegments[0].fareSegment.passengerFares.ADT.reissueAmt +'</p></div></div></div>';


		var segment = '<p class="ticket-psline"><span class="gray">';

		//航班号 等信息
		$.each(airlines[index].airlineDetailList[0].flightNoSegment, function(index2, val) {
			if (airlines[index].airlineDetailList[0].flightNoSegment[index2].shareFlight ) {
				segment = segment +airlines[index].airlineDetailList[0].flightNoSegment[index2].marketingAirline+airlines[index].airlineDetailList[0].flightNoSegment[index2].flegFlightNo +'<span class="red-sqr">享</span>'+'|'+airlines[index].airlineDetailList[0].flightNoSegment[index2].planeModel +'<span class="red-sqr">享</span>';
			}else{
				segment = segment +airlines[index].airlineDetailList[0].flightNoSegment[index2].marketingAirline+ airlines[index].airlineDetailList[0].flightNoSegment[index2].flegFlightNo +'|'+airlines[index].airlineDetailList[0].flightNoSegment[index2].planeModel ;
			}
			segment = segment + '|' ;
			
		});

		segment = segment + '</span></p>';
		flight_html = flight_html + segment;

		flight_html = flight_html+'<div class="hide-detail"><ul class="hide-detail-list">'
		//舱位信息
		$.each(airlines[index].price.fareFamilies[0].fareSegments, function(index3, val3) {

			flight_html = flight_html+'<li><div class="l-p"><p class="p1"><span>'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.cabinCode+'&nbsp;舱</span><span class="price">'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.passengerFares.ADT.reissueAmt +'</span><span class="redyuan">￥</span></p>';
			if (!isNull(airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.refund)&&!isNull(airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.datechange)) { //改退签规则有数据
				
				flight_html = flight_html+'<p class="p2"><span class="blue" onclick="getRlue_hblb('+index+','+index3+')" >使用条件</span></p>';
			};

			flight_html = flight_html+'</div><div class="r-p" align="center"><div onclick="changePageWithInfo('+index+','+index3+')" class="square-btn">改</div></div></li>';
		});

		flight_html = flight_html+'</ul></div></li>';
		
		$("#flightList").append(flight_html);

		
	});
	
	var html_jx = '<li class ="bxjx">不限<b class="checked"></b></li>';
	$.each(jx_obj, function(index, val) {
		html_jx = html_jx + '<li value='+jx_obj[index]+'><span>'+jx_obj[index]+'</span><b class="checked"></b></li>';
	});
	$("#jxinfo").html(html_jx);
	


	if (setSortList) {
		setSortList = false ;
		setSXControler();
	}
	setSXControler2();
	
}
function sortList(type){//根据筛选条件 排序
	
	var sorlRule = $("#"+type).children('img').attr('value');//升降序 标识符
	var arr = flight_obj.body.airlines[0].airline ;
	var len = arr.length ;
	var preIndex, current ;

	//具体排序规则代码
	if (type=="time_px") {//根据起飞时间排序

		for (var i = 1; i < len; i++) {
			preIndex = i - 1;
			current = arr[i];
			if (sorlRule == "ascend") {//升序
				while(preIndex >= 0 && arr[preIndex].airlineDetailList[0].airlineDetail.summary.departTime  > current.airlineDetailList[0].airlineDetail.summary.departTime) {//排序规则
					arr[preIndex+1] = arr[preIndex];
					preIndex--;
				}
			}else{//降序
				while(preIndex >= 0 && arr[preIndex].airlineDetailList[0].airlineDetail.summary.departTime < current.airlineDetailList[0].airlineDetail.summary.departTime) {//排序规则
					arr[preIndex+1] = arr[preIndex];
					preIndex--;
				}
			}
			arr[preIndex+1] = current;
		}

	}else{ //根据总价 排序
		for (var i = 1; i < len; i++) {
			preIndex = i - 1;
			current = arr[i];
			if (sorlRule == "ascend") {//升序

				while(preIndex >= 0 && arr[preIndex].price.fareFamilies[0].fareSegments[0].fareSegment.passengerFares.ADT.reissueAmt  > current.price.fareFamilies[0].fareSegments[0].fareSegment.passengerFares.ADT.reissueAmt) {//排序规则
					arr[preIndex+1] = arr[preIndex];
					preIndex--;
				}
			}else{//降序
				while(preIndex >= 0 && arr[preIndex].price.fareFamilies[0].fareSegments[0].fareSegment.passengerFares.ADT.reissueAmt < current.price.fareFamilies[0].fareSegments[0].fareSegment.passengerFares.ADT.reissueAmt) {//排序规则
					arr[preIndex+1] = arr[preIndex];
					preIndex--;
				}
			}
			arr[preIndex+1] = current;
		}
	}

	flight_obj.body.airlines[0].airline = arr ;
	setHTML_hblb();
}

function setSXControler(){//设置筛选的控制器

	var time_html = '<img src="../../resource/images/time.png"><p>时间</p>',price_html='<img src="../../resource/images/price.png"><p>票价</p>';
	$(".filter").bind('click', function(event) {//控制筛选框展示
		$("#sxBox").fadeIn("500");
		$("#time_px").html(time_html);
		$("#price_px").html(price_html);
		$("body").attr('style', 'overflow-y:hidden');
	});
	
	$("#time_px").toggle(function() {//时间控制
		$("#time_px").html('<img src="../../resource/images/up_blue.png" value="ascend"><p class="blue">时间从早到晚</p>');
		$("#price_px").html(price_html);
		sortList("time_px");
	}, function() {
		$("#time_px").html('<img src="../../resource/images/down_blue.png" value="descend"><p class="blue">时间从晚到早</p>');
		$("#price_px").html(price_html);
		sortList("time_px");
	});

	$("#price_px").toggle(function() {//价格控制
		$("#price_px").html('<img src="../../resource/images/up_blue.png" value="ascend"><p class="blue">票价从低到高</p>')
		sortList("price_px");
		$("#time_px").html(time_html);
	}, function() {
		$("#price_px").html('<img src="../../resource/images/down_blue.png" value="descend"><p class="blue">票价从高到低</p>')
		sortList("price_px");
		$("#time_px").html(time_html);
	});
	
	
	$(".sxtj>li").bind('click', function(e) {//筛选 左侧栏点击控制
		var id = e.currentTarget,idName = $(id).attr('id');
		$(".sxtj>li").removeClass('on');
		$(id).addClass('on');
		$(".fliter-box-rp>ul").hide();
		$("#"+idName+"info").show();
	});
	$("#reset").bind('click',function(event) {//重置按钮
		$(".fliter-box-rp  b").attr("class",'checked');
		$("#gxhbinfo b:gt(0)").removeClass('checked');
	});
	$("#confirm").bind('click', function(event) {//确定按钮 调用筛选接口进行筛选排序

		var para = {"body": {"reshopSearchFilterAndSortRequest": { "direct": true,"planeModel": [],"share": true,"takeOffTime": []}},"head": {"platformId": 3}};

		var qfsj = $("#qfsjinfo li:gt(0) .checked").parent('li'),jx = $("#jxinfo li:gt(0) .checked").parent('li'),gxhb = $("#gxhbinfo .checked").parent('li'),zfzz = $("#zfzzinfo li:gt(0) .checked").parent('li');
		if (qfsj.length == 0 || jx.length == 0 ||zfzz.length == 0) {
			showConfirm("提示","筛选条件不能为空，请至少选择一个条件后再查询");
			return;
		};
		
		$.each(qfsj, function(index, val) {
			para.body.reshopSearchFilterAndSortRequest.takeOffTime.push(qfsj[index].getAttribute("value"));
		});
		$.each(jx, function(index, val) {
			para.body.reshopSearchFilterAndSortRequest.planeModel.push(jx[index].getAttribute("value"));
		});

		if (zfzz.length == 2) {
			para.body.reshopSearchFilterAndSortRequest.direct = null;
		}else{
			para.body.reshopSearchFilterAndSortRequest.direct = zfzz[0].getAttribute("value");
		}
		if (isNull(gxhb[0].getAttribute("value"))) {
			para.body.reshopSearchFilterAndSortRequest.share  = null;
		}else{
			para.body.reshopSearchFilterAndSortRequest.share  = gxhb[0].getAttribute("value");
		}
		
		//调用筛选接口
		loading();
		doAjax("filterFlightList",para,function(obj){
			layer.closeAll();
			if (  (!isNull( obj.body.message)&& (obj.body.message.keyCode%10==0 ) )||(!isNull(obj.body.status)&&(isNull(switchStaus(obj.body.status))))  ){
				
				flight_obj= obj;
				setHTML_hblb();
				$("#sxBox").fadeOut("500");
				$("body").attr("style","overflow-y:scroll");

			} else {
				if (!isNull( obj.body.message)) {
					showConfirm("提示",obj.body.message.value);
				}else{
					showConfirm("提示",switchStaus(obj.body.status));
				}
			}
		}, function(){
			layer.closeAll();
			showConfirm("提示","网络繁忙，请稍后再试");
		},60000);
		

	});
	$("#qfsjinfo li:gt(0)").bind('click', function(e) {//时间段选择
		var b = e.currentTarget.getElementsByTagName('b') ;
		if ( $(b).attr('class') == "checked" ) {
			$("#qfsjinfo li b:first").removeClass('checked');
		};
		$(b).toggleClass("checked");
		
		if ( $("#qfsjinfo li:gt(0) b").filter( $(".checked") ).length == $("#qfsjinfo li:gt(0) b").length ) {
			$("#qfsjinfo li b:first").addClass('checked');
		};
		
	}); 
	$("#zfzzinfo li:gt(0)").bind('click', function(e) {//直飞/中转
		var b = e.currentTarget.getElementsByTagName('b') ;
		
		if ( $(b).attr('class') == "checked" ) {
			$("#zfzzinfo li b:first").removeClass('checked');
		};
		$(b).toggleClass("checked");
		
		
		if ( $("#zfzzinfo li:gt(0) b").filter( $(".checked") ).length == $("#zfzzinfo li:gt(0) b").length ) {
			$("#zfzzinfo li b:first").addClass('checked');
		};

	});
	
	$("#gxhbinfo li").bind('click', function(e) {//共享航班
		$("#gxhbinfo li b").removeClass('checked');
		var b = e.currentTarget.getElementsByTagName('b') ;
		$(b).toggleClass("checked");
	});

	$(".bx").bind('click', function(e) {//不限 的按钮设置

		var li = e.currentTarget;
		if ( $(li).children('b').attr("class")=="checked" ) {//变为不选
			$(li).children('b').removeClass('checked');
			$(li).siblings('li').children('b').removeClass('checked');
		}else{//变为全选
			$(li).children('b').addClass('checked');
			$(li).siblings('li').children('b').addClass('checked');
		}
	});
	
}
function setSXControler2(){
	
	
	$("#jxinfo li:gt(0)").bind('click', function(e) {//机型选择

		var b = e.currentTarget.getElementsByTagName('b') ;
		
		if ( $(b).attr('class') == "checked" ) {
			$("#jxinfo li b:first").removeClass('checked');
		};
		$(b).toggleClass("checked");
		
		
		if ( $("#jxinfo li:gt(0) b").filter( $(".checked") ).length == $("#jxinfo li:gt(0) b").length ) {
			$("#jxinfo li b:first").addClass('checked');
		};
	});


	$(".bxjx").bind('click', function(e) {//不限 的按钮设置

		var li = e.currentTarget;
		if ( $(li).children('b').attr("class")=="checked" ) {//变为不选
			$(li).children('b').removeClass('checked');
			$(li).siblings('li').children('b').removeClass('checked');
		}else{//变为全选
			$(li).children('b').addClass('checked');
			$(li).siblings('li').children('b').addClass('checked');
		}
	});

	//控制页面航班信息打开/关闭
	$("#flightList > li ").bind('click', function(e) { 
		var sth = e.currentTarget.getElementsByClassName('hide-detail')[0];
		if ( $(sth).css('display') == "none" ) {
			$(".hide-detail").slideUp();
			$(sth).slideDown() ;
		}else{
			$(sth).slideUp() ;
		}
	});
}


function changePageWithInfo(index,index3){
	//airlines[index].price.fareFamilies[0].fareSegments lym
	var hbObj = {},para={"body": {"chooseReshopFlightRequest": {"chooseReshopFlightChangeType": "DATECHANGE","flightSegmentRPH": flight_obj.body.airlines[0].airline[index].price.fareFamilies[0].fareSegments[index3].fareSegment.flightSegmentRPH,"sequenceNumber": flight_obj.body.airlines[0].airline[index].sequenceNumber} },"head": {"platformId": 3 }};

	hbObj.airline = flight_obj.body.airlines[0].airline[index];
	hbObj.index3 = index3 ;
	sessionStorage.hbInfo2 = JSON.stringify(hbObj);
	sessionStorage.changeDatePara = JSON.stringify(para);
	changePage('goChangeDateDetailInfoPage');
	//调用接口 成功后跳转下一页面
	
  

}

function getRlue_hblb(index,index3){//index 第几个航班 index3 对应仓位

	var airlines = flight_obj.body.airlines[0].airline,totalLoyal=0//总里程;
	var html_rule = '<header><img class="back" onclick="closeRule()" src="../../resource/images/back.png"><span>使用条件</span></header>';

	$("#hblbPage").hide();
	$("#rulePage").html('');

	html_rule = html_rule + '<div class="ruler-con"><h2>'+returnCityName(airlines[index].airlineDetailList[0].airlineDetail.summary.orgCity )+'<img src="../../resource/images/hourline.png">'+returnCityName(airlines[index].airlineDetailList[0].airlineDetail.summary.destCity)+'</h2><p class="lic-box" id="ljlc">累计里程：公里<span>仅供参考，请以实际入账里程为准</span></p>';
	
	//根据flights航段 进行循环展示
	$.each(airlines[index].airlineDetailList[0].airlineDetail.flights, function(index4, val4) {
		html_rule =  html_rule + '<div class="fli-box"><h3><span>'+index4+'</span>'+airlines[index].airlineDetailList[0].airlineDetail.summary.departAirport+airlines[index].airlineDetailList[0].airlineDetail.flights[index4].takeOffTerminal+' — '+airlines[index].airlineDetailList[0].airlineDetail.summary.arriveAirport+airlines[index].airlineDetailList[0].airlineDetail.flights[index4].arriveTerminal +'['+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.cabinCode+']</h3>';

		if (!isNull(airlines[index].airlineDetailList[0].airlineDetail.flights[index4].loyalty)) {
			totalLoyal = totalLoyal + parseInt(airlines[index].airlineDetailList[0].airlineDetail.flights[index4].loyalty) ;
		}
		

		//根据rph 找出对应的燃油基建费用
		if (airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.flightSegmentRPH == airlines[index].airlineDetailList[0].airlineDetail.flights[index4].flightRPH) {//通过rph判断

			html_rule =  html_rule + '<p class="fli-info">票价:￥'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.passengerFares.ADT.baseAmount+'&nbsp;&nbsp;&nbsp;&nbsp;燃油:￥'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.passengerFares.ADT.taxs.YQ.amount+'&nbsp;&nbsp;&nbsp;&nbsp;基建:￥'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.passengerFares.ADT.taxs.CN.amount+'</p>';
		};
		
		if(!isNull(airlines[index].airlineDetailList[0].airlineDetail.flights[index4].baggage)){
			html_rule =  html_rule +'<p class="fli-info"><span class="fli-info-icon"><b class="icon-xl"></b>行李额</span><span class="fli-info-txt">'+airlines[index].airlineDetailList[0].airlineDetail.flights[index4].baggage.weight.value+'</span></p>';
		}
		
		if(!isNull(airlines[index].airlineDetailList[0].airlineDetail.flights[index4].loyalty)){
			var number = parseInt(airlines[index].airlineDetailList[0].airlineDetail.flights[index4].loyalty)
			html_rule =  html_rule +'<p class="fli-info"><span class="fli-info-icon"><b class="icon-lc"></b>里程</span><span class="fli-info-txt">'+ (isNaN(number) ?  '--' : number) + '公里  仅供参考，请以实际入账里程为准</span></p>';
		}
		

		html_rule =  html_rule +'<h3>成人退改签说明</h3><table width="100%" cellspacing="0" cellpadding="0" border="0" class="refund-tab">';

		if (!isNull(airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.datechange)) {//改签
			html_rule =  html_rule + '<tr><td width="30%" class="txalcenter" rowspan="2">改期费</td><td width="35%"><b class="icon-lz"></b>起飞2小时前</td><td class="txalcenter">'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.refund.beforeDepartureRate*100+'%</td></tr><tr><td><b class="icon-lz"></b>起飞2小时后</td><td class="txalcenter">'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.refund.afterDepartureRate*100+'%</td></tr>';
		};
		if (!isNull(airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.refund)) {//退款
			html_rule =  html_rule + '<tr><td class="txalcenter" rowspan="2">退票费</td><td><b class="icon-lz"></b>起飞2小时前</td><td class="txalcenter">'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.refund.beforeDepartureRate*100+'%</td></tr><tr><td><b class="icon-lz"></b>起飞2小时后</td><td class="txalcenter">'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.refund.afterDepartureRate*100+'%</td></tr><tr><td class="txalcenter">自愿转签</td><td class="txalcenter" colspan="2">'+airlines[index].price.fareFamilies[0].fareSegments[index3].fareSegment.description+'</td></tr>';
		};


		html_rule =  html_rule + '</table></div>';
	
	});
	html_rule =  html_rule + '<p class="tip-txt">以上为成人票退改签费用标准，仅供参考，请以实际情况为准客票变更时需同时收取票价价差和变更手续费</p></div>';

	$("#rulePage").html(html_rule);
	$("#rulePage").show();
	$("header>.back").unbind();
	$("header>.back").bind('click', function(event) {
		$("#hblbPage").show();
		$("#rulePage").hide();
		$("header>.back").unbind();
		Init_hb();
	});
	
	if (totalLoyal!=0 && totalLoyal != null && totalLoyal != undefined) {
		$("#ljlc").html('累计里程：'+totalLoyal+'公里<span>仅供参考，请以实际入账里程为准</span>');	
	}else{
		$("#ljlc").html('累计里程：--公里<span>仅供参考，请以实际入账里程为准</span>');	
	}
		
}

/*gqqr*/
function setGQQRHtml(){//获取页面信息数据
	//乘机人 + 价格
	var obj_passengerFares = hbObj.airline.price.fareFamilies[0].fareSegments[hbObj.index3].fareSegment.passengerFares ;
	var aduNum = 0,
	chlNum = 0,
	reissueAmt ={'ADT':obj_passengerFares.ADT.reissueAmt,"CHD":0},//变更总额
	differFare ={'ADT':obj_passengerFares.ADT.differFare,"CHD":0},//差价
	reissueFee = {'ADT':obj_passengerFares.ADT.reissueFee,"CHD":0},//手续费
	cjr_html = "",hb_html="",segment_html = "";
	$(".chlInfo").hide();
	//lym q
	if (!isNull(hbObj.airline.price.fareFamilies[0].fareSegments[hbObj.index3].fareSegment.refund)&&!isNull(hbObj.airline.price.fareFamilies[0].fareSegments[hbObj.index3].fareSegment.datechange)) { //改退签规则有数据

		$("#hb_city").html('<span style="font-size: 14px;font-weight:600;margin-right:10px;" >'+returnCityName(hbObj.airline.airlineDetailList[0].airlineDetail.summary.orgCity )+'&nbsp;一&nbsp;'+returnCityName(hbObj.airline.airlineDetailList[0].airlineDetail.summary.destCity)+'</span><span class="airport">'+getSplitStr(hbObj.airline.airlineDetailList[0].airlineDetail.duration,":").str_before+'h'+getSplitStr(hbObj.airline.airlineDetailList[0].airlineDetail.duration,":").str_after+'m</span><span class="blue-right" onclick="getRlue_gqqr('+hbObj.index3+')">使用条件</span>');	
	}else{
		$("#hb_city").html('<span style="font-size: 14px;font-weight:600;margin-right:10px;" >'+returnCityName(hbObj.airline.airlineDetailList[0].airlineDetail.summary.orgCity )+'&nbsp;一&nbsp;'+returnCityName(hbObj.airline.airlineDetailList[0].airlineDetail.summary.destCity)+'</span><span class="airport">'+getSplitStr(hbObj.airline.airlineDetailList[0].airlineDetail.duration,":").str_before+'h'+getSplitStr(hbObj.airline.airlineDetailList[0].airlineDetail.duration,":").str_after+'m</span>');

	}

	//航班号 等信息
		if (eval(hbObj.airline.airlineDetailList[0].flightNoSegment[0].shareFlight) ) {
			segment_html = segment_html + '<span class="gray"><img src="../../resource/images/logo.png">'+hbObj.airline.airlineDetailList[0].flightNoSegment[0].marketingAirline+hbObj.airline.airlineDetailList[0].flightNoSegment[0].flegFlightNo+'<span class="blue-sqr" style="width:30px;">共享</span></span><span class="gray">'+hbObj.airline.airlineDetailList[0].flightNoSegment[0].planeModel+'</span>';
		}else{
			segment_html = segment_html + '<span class="gray"><img src="../../resource/images/logo.png">'+hbObj.airline.airlineDetailList[0].flightNoSegment[0].marketingAirline+hbObj.airline.airlineDetailList[0].flightNoSegment[0].flegFlightNo+'</span><span class="gray">'+hbObj.airline.airlineDetailList[0].flightNoSegment[0].planeModel+'</span>';
		}
	
	$("#hb_ps").html(segment_html);
	
	$("#hb_datail").html('<div class="l-p"><p class="date-p">'+hbObj.airline.airlineDetailList[0].airlineDetail.summary.departTime.substring(5,10)+' &nbsp '+returnDay(hbObj.airline.airlineDetailList[0].airlineDetail.summary.departTime)+'</p><p class="time-p">'+hbObj.airline.airlineDetailList[0].airlineDetail.summary.departTime.substring(10,16)+'</p><p class="airport">'+hbObj.airline.airlineDetailList[0].airlineDetail.summary.departAirport+hbObj.airline.airlineDetailList[0].airlineDetail.flights[hbObj.airline.airlineDetailList[0].airlineDetail.journeyId].takeOffTerminal+'</p></div><div class="m-p"><p>'+ getSplitStr(hbObj.airline.airlineDetailList[0].airlineDetail.duration,":").str_before+'h'+getSplitStr(hbObj.airline.airlineDetailList[0].airlineDetail.duration,":").str_after+'m</p><img src="../../resource/images/hourline.png"></div><div class="r-p"><p class="date-p">'+hbObj.airline.airlineDetailList[0].airlineDetail.summary.arrivalTime.substring(5,10)+' &nbsp '+returnDay(hbObj.airline.airlineDetailList[0].airlineDetail.summary.arrivalTime)+'</p><p class="time-p">'+hbObj.airline.airlineDetailList[0].airlineDetail.summary.arrivalTime .substring(10,16)+returnIsAddDay(hbObj.airline.airlineDetailList[0].airlineDetail.summary.departTime,hbObj.airline.airlineDetailList[0].airlineDetail.summary.arrivalTime)+'</p><p class="airport">'+hbObj.airline.airlineDetailList[0].airlineDetail.summary.arriveAirport+hbObj.airline.airlineDetailList[0].airlineDetail.flights[hbObj.airline.airlineDetailList[0].airlineDetail.journeyId].arriveTerminal+'</p></div>');

	//乘机人信息展示
	$.each(cjrObj.list, function(index, val) {
		 if (cjrObj.list[index].passengerType == "ADT") {
			aduNum ++;
		 }else{
			chlNum++
		 };

		cjr_html =cjr_html + '<div class="info noright"><span class="gray">'+cjrObj.list[index].surnName+cjrObj.list[index].givenName+'</span><span class="gray">'+returnPassengerType(cjrObj.list[index].passengerType)+'</span><p class="gray"><span class="gray">'+returnDoctype(cjrObj.list[index].docType)+'</span>'+cjrObj.list[index].docId+'</p></div>';
	});
	if(!isNull(obj_passengerFares.CHD)){
		reissueAmt.CHD = obj_passengerFares.CHD.reissueAmt ;
		reissueFee.CHD = obj_passengerFares.CHD.reissueFee ;
		differFare.CHD = obj_passengerFares.CHD.differFare ;
		$(".chlInfo").show();
	}
	$(".chlreissueFee").html("￥"+reissueFee.CHD);
	$(".chlreissueAmt").html("￥"+reissueAmt.CHD);
	$(".chldifferFare").html("￥"+differFare.CHD);
	$(".adtnum").html(aduNum);
	$(".chlnum").html(chlNum);
	$("#cjrInfo").html(cjr_html);
	//总价
	$("#adtamount").html(reissueAmt.ADT*aduNum);
	$("#chlamount").html(reissueAmt.CHD*chlNum);
	totalAmount = reissueAmt.ADT*aduNum+reissueAmt.CHD*chlNum ;
	$("#payamount").html('<span style="font-weight:500;font-size:12px;">￥</span>'+totalAmount );
	//对应金额
	$(".adtreissueFee").html("￥"+reissueFee.ADT);
	$(".adtreissueAmt").html("￥"+reissueAmt.ADT);
	$(".adtdifferFare").html("￥"+differFare.ADT);
	
	
	//人数
	$("#passamount").html('<span style="font-weight:500;font-size:12px;">x</span>'+(aduNum+chlNum));
}

function getRlue_gqqr(index3){//index 第几个航班 index3 对应仓位

	var totalLoyal=0//总里程;
	var html_rule = '<header><img class="back" onclick="closeRule()" src="../../resource/images/back.png"><span>使用条件</span></header>';

	$("#ddPage").hide();
	$("#rulePage").html('');

	html_rule = html_rule + '<div class="ruler-con"><h2>'+returnCityName(hbObj.airline.airlineDetailList[0].airlineDetail.summary.orgCity )+'<img src="../../resource/images/hourline.png">'+returnCityName(hbObj.airline.airlineDetailList[0].airlineDetail.summary.destCity)+'</h2><p class="lic-box" id="ljlc">累计里程：'+'0'+'公里<span>仅供参考，请以实际入账里程为准</span></p>';
	
	//根据flights航段 进行循环展示
	$.each(hbObj.airline.airlineDetailList[0].airlineDetail.flights, function(index4, val4) {

		html_rule =  html_rule + '<div class="fli-box"><h3><span>'+index4+'</span>'+hbObj.airline.airlineDetailList[0].airlineDetail.summary.departAirport+hbObj.airline.airlineDetailList[0].airlineDetail.flights[index4].takeOffTerminal+' — '+hbObj.airline.airlineDetailList[0].airlineDetail.summary.arriveAirport+hbObj.airline.airlineDetailList[0].airlineDetail.flights[index4].arriveTerminal +'['+hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.cabinCode+']</h3>';
		//lym
		totalLoyal = totalLoyal + parseInt(hbObj.airline.airlineDetailList[0].airlineDetail.flights[index4].loyalty) ;
		//根据rph 找出对应的燃油基建费用
		if (hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.flightSegmentRPH == hbObj.airline.airlineDetailList[0].airlineDetail.flights[index4].flightRPH) {//通过rph判断

			html_rule =  html_rule + '<p class="fli-info">票价:￥'+hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.passengerFares.ADT.baseAmount+'&nbsp;&nbsp;&nbsp;&nbsp;燃油:￥'+hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.passengerFares.ADT.taxs.YQ.amount+'&nbsp;&nbsp;&nbsp;&nbsp;基建:￥'+hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.passengerFares.ADT.taxs.CN.amount+'</p>';
		};
		if(!isNull(hbObj.airline.airlineDetailList[0].airlineDetail.flights[index4].baggage)){
			html_rule =  html_rule +'<p class="fli-info"><span class="fli-info-icon"><b class="icon-xl"></b>行李额</span><span class="fli-info-txt">'+hbObj.airline.airlineDetailList[0].airlineDetail.flights[index4].baggage.weight.value+'</span></p>';
		}
		
		if(!isNull(hbObj.airline.airlineDetailList[0].airlineDetail.flights[index4].loyalty)){
			html_rule =  html_rule +'<p class="fli-info"><span class="fli-info-icon"><b class="icon-lc"></b>里程</span><span class="fli-info-txt">'+parseInt(hbObj.airline.airlineDetailList[0].airlineDetail.flights[index4].loyalty)+ '公里  仅供参考，请以实际入账里程为准</span></p>';
		}
		
		
		html_rule =  html_rule +'<h3>成人退改签说明</h3><table width="100%" cellspacing="0" cellpadding="0" border="0" class="refund-tab">';

		if (!isNull(hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.datechange)) {//改签
			html_rule =  html_rule + '<tr><td width="30%" class="txalcenter" rowspan="2">改期费</td><td width="35%"><b class="icon-lz"></b>起飞2小时前</td><td class="txalcenter">'+hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.refund.beforeDepartureRate*100+'%</td></tr><tr><td><b class="icon-lz"></b>起飞2小时后</td><td class="txalcenter">'+hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.refund.afterDepartureRate*100+'%</td></tr>';
		};
		if (!isNull(hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.refund)) {//退款
			html_rule =  html_rule + '<tr><td class="txalcenter" rowspan="2">退票费</td><td><b class="icon-lz"></b>起飞2小时前</td><td class="txalcenter">'+hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.refund.beforeDepartureRate*100+'%</td></tr><tr><td><b class="icon-lz"></b>起飞2小时后</td><td class="txalcenter">'+hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.refund.afterDepartureRate*100+'%</td></tr><tr><td class="txalcenter">自愿转签</td><td class="txalcenter" colspan="2">'+hbObj.airline.price.fareFamilies[0].fareSegments[index3].fareSegment.description+'</td></tr>';
		};


		html_rule =  html_rule + '</table></div>';
	
	});
	html_rule =  html_rule + '<p class="tip-txt">以上为成人票退改签费用标准，仅供参考，请以实际情况为准客票变更时需同时收取票价价差和变更手续费</p></div>';

	$("#rulePage").html(html_rule);
	$("#rulePage").show();
	$("header>.back").unbind();
	$("header>.back").bind('click', function(event) {
		$("#ddPage").show();
		$("#rulePage").hide();
		$("header>.back").unbind();
		Init_hb();
	});
	if (totalLoyal!=0 && totalLoyal != null && totalLoyal != undefined) {
		$("#ljlc").html('累计里程：'+totalLoyal+'公里<span>仅供参考，请以实际入账里程为准</span>');	
	}else{
		$("#ljlc").html('累计里程：--公里<span>仅供参考，请以实际入账里程为准</span>');	
	}
		
}

function setBind_gqqr(){
			$(".ddconfirm>.detail").toggle(function() {//价格详情控制
					$("#gqjine").fadeIn('500');
					$("body").attr("style","overflow-y:hidden");
			}, function() {
					$("#gqjine").fadeOut('500');
					$("body").attr("style","overflow-y:scroll");
			});
			//172.16.10.212:8686
			$("#safeRule").bind('click',function(event) {
				location.href="http://m.sichuanair.com/tribe-touch-web/staticResources/safeTransport.html";//规则网址
			});
			$("#helpRule").bind('click',function(event) {
				location.href="http://m.sichuanair.com/tribe-touch-web/staticResources/help.html";//规则网址
			});
			$("#ddtjbtn").bind('click', function(event) {
				var para ={"body": {"processFreeDateChangeRequest": { }},"head": {"platformId": 3}};
				if (isNull($("#isRead").attr('checked'))) {
					showConfirm("温馨提示","请仔细阅读《购票、旅行安全运输的相关条款》及相关条例后勾选已阅读选项，以保障用户您的权益。");
					return;
				};
				loading();
				var para_changeDate =JSON.parse(sessionStorage.changeDatePara);	
				doAjax("changeDate",para_changeDate,function(obj){
					layer.closeAll();
					if (  (!isNull( obj.body.message)&& (obj.body.message.keyCode%10==0 ) )||(!isNull(obj.body.status)&&(isNull(switchStaus(obj.body.status))))  ){
						var changeDateObj = obj;
						if ( eval(changeDateObj.body.chooseReshopFlightResponse.freeDateChange) ) {//免费改签
							var para_freeChangeDate ={"body": {"processFreeDateChangeRequest": { }},"head": {"platformId": 3}};
							loading();
							doAjax("freeChangeDate",para_freeChangeDate,function(obj2){
								layer.closeAll();
								if (  (!isNull( obj2.body.message)&& (obj2.body.message.keyCode%10==0 ) )||(!isNull(obj2.body.status)&&(isNull(switchStaus(obj2.body.status))))  ){
									changePage("goFreeChangeSuccessPage");
								} else {
									if (!isNull( obj2.body.message)) {
										showConfirm("提示",obj.body.message.value);
									}else{
										showConfirm("提示",switchStaus(obj.body.status));
									}
								}
							}, function(){
								layer.closeAll();
								showConfirm("提示","网络繁忙，请稍后再试");
							},180000);//3min 
							
						}else{
							var telephoneNo=sessionStorage.getItem("telephoneNo");
							var returnProductNumber=obj.body.chooseReshopFlightResponse.paymentDetails.bookingDetail.payment.productNumber;
							
							if(telephoneNo!="null" && telephoneNo!=null){
								location.href="http://m.sichuanair.com/tribe-touch-web/tribe/payment/goPaymentListPage?orderId="+sessionStorage.orderId+"&payAmount="+totalAmount+"&paymentType=DATE_CHANGE&productNumber="+returnProductNumber+"&telephoneNo="+telephoneNo;//拼接的网址
							}else{
								location.href="http://m.sichuanair.com/tribe-touch-web/tribe/payment/goPaymentListPage?orderId="+sessionStorage.orderId+"&payAmount="+totalAmount+"&paymentType=DATE_CHANGE&productNumber="+returnProductNumber;//拼接的网址
							}
							
						}
						
					} else {
						if (!isNull( obj.body.message)) {
							showConfirm("提示",obj.body.message.value);
						}else{
							showConfirm("提示",switchStaus(obj.body.status));
						}
					}
				}, function(){
					layer.closeAll();
					showConfirm("提示","网络繁忙，请稍后再试");
				},180000);
				
			});
}

/*mfgqcg*/
function setBindHtml(){
	hbObj = JSON.parse(sessionStorage.hbInfo2);
	$("#orderId").html(sessionStorage.orderId);
	$("#hb_info").html(hbObj.airline.airlineDetailList[0].airlineDetail.summary.departTime.substring(5,7)+'月'+hbObj.airline.airlineDetailList[0].airlineDetail.summary.departTime.substring(8,10)+'日&nbsp;'+returnCityName(hbObj.airline.airlineDetailList[0].airlineDetail.summary.orgCity )+'-'+returnCityName(hbObj.airline.airlineDetailList[0].airlineDetail.summary.destCity)+'&nbsp;'+hbObj.airline.airlineDetailList[0].flightNoSegment[0].flegFlightNo+'');
	$(".b-btn").bind('click', function(event) {
		
		var telephoneNo=sessionStorage.getItem("telephoneNo");
		if(telephoneNo!="null" && telephoneNo!=null){
			location.href="http://m.sichuanair.com/tribe-touch-web/tribe/order/goOrderListPage?orderId="+sessionStorage.orderId+"&telephoneNo="+telephoneNo;
		}else{
			location.href="http://m.sichuanair.com/tribe-touch-web/tribe/order/goOrderListPage?orderId="+sessionStorage.orderId;
		}
		
	});

}