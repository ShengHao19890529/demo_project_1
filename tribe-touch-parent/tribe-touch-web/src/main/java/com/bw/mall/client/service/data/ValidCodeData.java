package com.bw.mall.client.service.data;

import java.io.Serializable;
import java.util.Date;
import com.alibaba.fastjson.JSON;

/**
 * ClassName: ValidCodeData <br/>
 * Function: 存放验证码数据 . <br/>
 * date: 2014年11月3日 下午4:15:08 <br/>
 *
 * @author 柴財財
 * @version
 * @since JDK 1.6
 */
public class ValidCodeData implements Serializable {

    private static final long serialVersionUID = 1L;
	private String code;
	private Integer times = Integer.valueOf(0);
	private Date date;

	public ValidCodeData() {
	}

	public ValidCodeData(String code) {
		this.code = code;
		this.date = new Date();
	}

	public void add() {
		times++;
	}

	public String getCode() {
		return code;
	}

	public Integer getTimes() {
		return times;
	}

	public Date getDate() {
		return date;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
}
