/**
 * ajax请求模板,
 * 必须用jQuery，velocity的$与之冲突
 */
function doAjaxReq(options){
	var defaultOptions={
			type:"POST",
			dataType:"json"
	};
    var realOptions = jQuery.extend(true,{}, defaultOptions, options);
	jQuery.ajax(realOptions);
}