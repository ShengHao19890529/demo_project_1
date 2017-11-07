//换页面
function changePage(hf){
    window.location.href=hf+";jsessionid="+sessionStorage.jsessionid;
}


/*判断是否为空 或者未定义*/
function isNull(str){
    str = $.trim(str);

    if(!str || str=="" || str=="null" || str=="undefined" || str=="--"|| typeof(str) == "undefined")
      return true;
  return false;
}
function replaceNull(str,rpstr){
  str = $.trim(str);
  if(!str || str=="" || str=="null" || str=="undefined")
      return rpstr;
  return str;
}
/*返回证件值*/
function returnDoctype(doc){
    var docType = "";
    switch(doc){
        case 'ID':
        docType = "身份证";
        break;
        case 'MILITARY_OFFICER':
        docType = "军官证";
        break;
        case 'POLICE':
        docType = "警官证";
        break;
        case 'SOLDIER':
        docType = "士兵证";
        break;
        case 'PASSPORT':
        docType = "护照";
        break;
        case 'PRC':
        docType = "回乡证";
        break;
        case 'OTHER':
        docType = "其他";
        break;
    }
    return docType ;
}
/*返回乘客类型*/

function returnPassengerType(doc){
    var docType = "";
    switch(doc){
        case 'ADT':
        docType = "成人";
        break;
        case 'CHD':
        docType = "儿童";
        break;
        case 'INF':
        docType = "婴儿";
        break;
        case 'GRP':
        docType = "团体";
        break;
    }
    return docType ;
}


/*判断两日期之间差别天数 返回具体代码*/
function returnIsAddDay(date1,date2){

    var day = getDiffTime("d",date1.substring(0,10),date2.substring(0,10)) ;
    var html = "";
    if ( day >0 ) {
        html = '<span>+'+day+'天</span>';
    };
    return html ;
}

/*截断数据*/
function getSplitStr(string,str){  
    var obj = {};
    obj.str_before = string.split(str)[0];  
    obj.str_after = string.split(str)[1];  
    //console.log('前：'+obj.str_before+' - 后：'+obj.str_after);  
    return obj ;
}  
/*根据条件筛序*/
function resetObj(arr,secnd){

    var len = arr.length;
    var preIndex, current;
    for (var i = 1; i < len; i++) {
        preIndex = i - 1;
        current = arr[i];
        while(preIndex >= 0 && arr[preIndex] > current) {//排序规则
            arr[preIndex+1] = arr[preIndex];
            preIndex--;
        }
        arr[preIndex+1] = current;
    }
    return arr;
}

/*var btn={};
btn.lName="左按钮 确定";
btn.rName="右按钮 取消";
btn.fun = function(){alert("fun")};
showConfirm("温馨提示","改期都为自愿改期（需要实际票面规则），如有特殊情况改期请致电川航客服，或前往机场柜台咨询。改期都为自愿改期（需要实际票面规则），如有特殊情况改期请致电川航客服，或前往机场柜台咨询。改期都为自愿改期（需要实际票面规则），如有特殊情况改期请致电川航客服，或前往机场柜台咨询。",btn);*/


//ajax调用
function doAjax(url,data,successCallback, errorCallback,time){  

    url = url+";jsessionid="+sessionStorage.jsessionid;
    var queryData = JSON.stringify(data) ;
    if (isNull(time)) {
        time=30000;//默认30s
    };
    $.ajax({
        async:true,
        url:url,
        type:"POST",
        data:queryData,
        dataType:"json",
        contentType: "application/json; charset=utf-8",
        timeout:time,  //超时设置    
        success:successCallback,
        error:errorCallback
     });
}
function doAjax2(url,data,successCallback, errorCallback,time){  

    url = url+";jsessionid="+sessionStorage.jsessionid;
    var queryData = JSON.stringify(data) ;
    if (isNull(time)) {
        time=30000;//默认30s
    };
    $.ajax({
        async:false,
        url:url,
        type:"POST",
        data:queryData,
        dataType:"json",
        contentType: "application/json; charset=utf-8",
        timeout:time,  //超时设置    
        success:successCallback,
        error:errorCallback
     });
}
/*获得随机数*/
function getRandom(n){
    return Math.floor(Math.random()*n+1)
}
/*----时间类型方法----*/
    localStorage.currentTime=CurentTime();
    setInterval(function(){
      localStorage.currentTime=CurentTime();
    }, 6000);//每秒刷新一次
    function getDate(strDate) {

        var d= new Date(strDate.replace(/-/g,"/"));  
        return d;
    }
    //获取当前时间 精确到时分(如:2009-06-12 12:00)
    function CurentTime(type){ 
            var now = new Date();
            if (type=="1") {
                now.setMinutes(now.getMinutes()+30); //默认初始时间
            }else if (type=="2") {
                now.setHours(now.getHours()+1);   //默认结束时间  
            }
            var year = now.getFullYear();       //年
            var month = now.getMonth() + 1;     //月
            var day = now.getDate();            //日
            var hh = now.getHours();  //时
            var mm = now.getMinutes();   //分
          
            var clock = year + "-";
           
            if(month < 10)
                clock += "0";
           
            clock += month + "-";
           
            if(day < 10)
                clock += "0";
               
            clock += day + " ";
           
            if(hh < 10)
                clock += "0";
               
            clock += hh + ":";
            if (mm < 10) 
                clock += '0'; 
            clock += mm; 
            return(clock); 
    }  
    //获得不同的时间 
    function getDiffTime(interval,objDate1,objDate2){   

        objDate1 = getDate(objDate1);
        objDate2 = getDate(objDate2);
        var d=objDate1, i={}, t=d.getTime(), t2=objDate2.getTime();   
        i['y']=objDate2.getFullYear()-d.getFullYear();   
        i['q']=i['y']*4+Math.floor(objDate2.getMonth()/4)-Math.floor(d.getMonth()/4);   
        i['m']=i['y']*12+objDate2.getMonth()-d.getMonth();   
        i['ms']=objDate2.getTime()-d.getTime();   
        i['w']=Math.floor((t2+345600000)/(604800000))-Math.floor((t+345600000)/(604800000));   
        i['d']=Math.floor(t2/86400000)-Math.floor(t/86400000);   
        i['h']=Math.floor(t2/3600000)-Math.floor(t/3600000);   
        i['n']=Math.floor(t2/60000)-Math.floor(t/60000);   
        i['s']=Math.floor(t2/1000)-Math.floor(t/1000);   

        return i[interval];   
    } 
    /*根据date返回周几*/
    function returnDay(str){
        var d= new Date(str.replace(/-/g,"/"));  
        var weekday=new Array(7)
        weekday[0]="周日"
        weekday[1]="周一"
        weekday[2]="周二"
        weekday[3]="周三"
        weekday[4]="周四"
        weekday[5]="周五"
        weekday[6]="周六"
        return weekday[d.getDay()] ;
    }
    //获得当前制式的时间格式
    function  getTimeUnfiorm(str){
        var value = {};
        if (isNull(str)) {
            var time= new Date();
        }else{
            var time= getDate(str);
        }
        
        var year=time.getFullYear();
        var month=time.getMonth()+1;
        var day=time.getDate();
        var hour=time.getHours();      
        var min=time.getMinutes();
        var weekday=new Array(7);

        weekday[0]="星期天";
        weekday[1]="星期一";
        weekday[2]="星期二";
        weekday[3]="星期三";
        weekday[4]="星期四";
        weekday[5]="星期五";
        weekday[6]="星期六";

        var sweek=weekday[time.getDay()];
        value.week = sweek ;
        value.year = year + "年";
        if(month < 10){
            value.month = "0" + month + "月";
        }else{
            value.month = month + "月";
        }
        
        if(day < 10){
            value.day = "0" + day + "日";
        }else{
            value.day = day + "日";
        }
        

        if (hour>12) {
            //hour=hour-12;
            value.st = "PM";
        }else{
            value.st = "AM";
        };
        if(hour < 10){
            value.hour = "0" + hour;
        }else{
            value.hour = hour;
        }
        
        if (min < 10) {
            value.min = "0" + min ;
        }else{
            value.min = min ;
        }
        return value;
    }
    //增加 减少日期 days参数是要加减的天数，如果往前算就传入负数，往后算就传入正数
    function addDate(date,days){
      date = date.replace(/-/g,"/"); 
      var d=new Date(date); 
      d.setDate(d.getDate()+days); 
      var month=d.getMonth()+1; 
      var day = d.getDate(); 
      if(month<10){ 
        month = "0"+month; 
      } 
      if(day<10){ 
        day = "0"+day; 
      } 
      var val = d.getFullYear()+"-"+month+"-"+day; 

      return val; 
    }

    function addMonth(date,months){
      date = date.replace(/-/g,"/");
      var m = new Date(date);
      m.setMonth(m.getMonth()+months);

      var month=m.getMonth()+1; 
      var day = m.getDate(); 
      if(month<10){ 
        month = "0"+month; 
      } 
      if(day<10){ 
        day = "0"+day; 
      } 
      var val = m.getFullYear()+"-"+month+"-"+day; 
      return val; 
    }
/*----时间类型方法----*/


/*返回顶部*/
var sdelay=null;
function returnTop() {
 /*window.scrollBy(0,-100);//Only for y vertical-axis
 if(document.body.scrollTop>0) { 
  sdelay= setTimeout('returnTop()',50);
 }*/
  window.location.href = "#top";
}

/*
 * @name 是否大于18岁
 * @param dateString { String } dateString: 1991-01-23
 * @return { Boolean }
 * @authorBy wilson wang
 */
function realAdult(dateString) {
    var today = new Date();
    var birthDate = new Date(dateString);
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    return age >= 18;
}