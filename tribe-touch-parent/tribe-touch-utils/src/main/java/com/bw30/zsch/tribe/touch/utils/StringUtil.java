package com.bw30.zsch.tribe.touch.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName: StringUtil
 * @Description: TODO
 * @author gs
 * @date Nov 10, 2011 3:46:27 PM
 * 
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {

	// 国标码和区位码转换常量
	static final int GB_SP_DIFF = 160;
	// 存放国标一级汉字不同读音的起始区位码
	static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635,
			3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };
	// 存放国标一级汉字不同读音的起始区位码对应读音
	static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'w', 'x', 'y', 'z' };

	// 获取一个字符串的拼音码
	public static String getFirstLetter(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取一个汉字的拼音首字母。 GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
	 * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
	 * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
	 */
	static char convert(byte[] bytes) {
		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}
		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	// fns 标签里使用， 尽量别改

	/**
	 * dateformat:(把 时间毫秒转成 指定日期格式). <br/>
	 * 
	 * @author 柴財財
	 */
	static public String timeformat(String timestamp, String pattern, String defaultstr) {
		String retStr = "";
		if (isNotBlank(timestamp)) {
			long time = Long.parseLong(timestamp);
			Date date = new Date(time);
			retStr = DateUtils.formatDate(date, pattern);
		} else {
			retStr = defaultstr;
		}
		return retStr;
	}

	static public String dateformat(Date date, String pattern, String defaultstr) {
		String retStr = "";
		if (date != null) {
			retStr = DateUtils.formatDate(date, pattern);
		} else {
			retStr = defaultstr;
		}
		return retStr;
	}

	/** 1:a;2:b 转换成Map */
	static public Map<String, String> str2Map(String mapStr) {
		return str2Map(mapStr, ";");
	}

	static public Map<String, String> str2Map(String mapStr, String splitChar) {
		Map<String, String> map = new HashMap<String, String>();
		if (isNotEmpty(mapStr)) {
			String[] arr1 = mapStr.split(splitChar);
			for (String obj : arr1) {
				String[] arr2 = obj.split(":");
				map.put(arr2[0], arr2[1]);
			}
		}
		return map;
	}

	static public String findByKey(String mapStr, String key) {
		return str2Map(mapStr).get(key);
	}

	static private boolean __strInStrs(String str, String strs) {
		boolean retBool = false;
		retBool = (',' + strs + ',').indexOf(',' + str + ',') >= 0;
		return retBool;
	}

	static public boolean strInStrs(String str, String strs, boolean sensitive) {
		if (!sensitive) {
			str = str.toLowerCase();
			strs = strs.toLowerCase();
		}
		return __strInStrs(str, strs);
	}

	/** 日期与周的列表 */
	static public List<String[]> dataweeklist(String curentdate, String parttenin, String parttenout, int range) {
		List<String[]> retList = new ArrayList<String[]>();

		Date date = null;
		try {
			date = DateUtils.parseDate(curentdate, new String[] { parttenin });

			int toNowDayNum = daysBetween(new Date(), date);// 设置时间(curentdate)与服务器当前时间的差值
			if (toNowDayNum > range) {// 按照范围来取

			} else {// 按照服务器时间来取
				date = nDaysAfterOneDate(date, -toNowDayNum);
			}
		} catch (ParseException e) {
			return new ArrayList<String[]>();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, -range);

		Map<String, String> weekMap = str2Map(weeks);
		int cnt = range * 2 + 1;
		for (int i = 1; i <= cnt; i++) {
			String[] tempArr = new String[2];
			int dayofweek = c.get(Calendar.DAY_OF_WEEK);
			tempArr[0] = DateUtils.formatDate(c.getTime(), parttenout);
			tempArr[1] = weekMap.get("" + (dayofweek - 1));
			retList.add(tempArr);

			c.add(Calendar.DAY_OF_YEAR, 1);
		}
		return retList;
	}

	static String weeks = "1:周一;2:周二;3:周三;4:周四;5:周五;6:周六;0:周日";
	static String weeks1 = "1:星期一;2:星期二;3:星期三;4:星期四;5:星期五;6:星期六;0:星期日";

	static public String[] dataweek(String curentdate, String parttenin, String parttenout) {
		return __dataweek(curentdate, parttenin, parttenout, 2);
	}

	static public String[] dataweek1(String curentdate, String parttenin, String parttenout) {
		return __dataweek(curentdate, parttenin, parttenout, 1);
	}

	static private String[] __dataweek(String curentdate, String parttenin, String parttenout, int style) {
		Date date = null;
		try {
			date = DateUtils.parseDate(curentdate, new String[] { parttenin });
		} catch (ParseException e) {
			return new String[2];
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		Map<String, String> weekMap = new HashMap<String, String>();

		String[] tempArr = new String[2];
		int dayofweek = c.get(Calendar.DAY_OF_WEEK);
		tempArr[0] = DateUtils.formatDate(c.getTime(), parttenout);
		if (style == 1) {
			weekMap = str2Map(weeks1);
		} else {
			weekMap = str2Map(weeks);
		}
		tempArr[1] = weekMap.get("" + (dayofweek - 1));

		return tempArr;
	}

	static public Date dayAdd(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime();
	}

	/*****
	 * 设定了起始范围的时间列表
	 * 
	 * @param curentdate
	 *            当前时间
	 * @param parttenin
	 * @param parttenout
	 * @param startDateRange
	 * @param endDateRange
	 * @return
	 */
	static public List<String[]> dataweeklistInRange(String curentdate, String parttenin, String parttenout,
			int startDateRange, int endDateRange) {

		List<String[]> retList = new ArrayList<String[]>();
		Date date = null;
		try {
			date = DateUtils.parseDate(curentdate, new String[] { parttenin });
			int cnt = startDateRange + endDateRange + 1;
			int toNowDayNum = daysBetween(new Date(), date);// 设置时间(curentdate)与服务器当前时间的差值
			if (toNowDayNum > startDateRange) {// 按照范围来取

			} else {// 按照服务器时间来取
				date = nDaysAfterOneDate(date, startDateRange - toNowDayNum - 1);
			}
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DAY_OF_YEAR, -startDateRange);

			Map<String, String> weekMap = str2Map(weeks);

			for (int i = 1; i <= cnt; i++) {
				String[] tempArr = new String[2];
				int dayofweek = c.get(Calendar.DAY_OF_WEEK);
				tempArr[0] = DateUtils.formatDate(c.getTime(), parttenout);
				tempArr[1] = weekMap.get("" + (dayofweek - 1));
				retList.add(tempArr);
				c.add(Calendar.DAY_OF_YEAR, 1);
			}
		} catch (ParseException e) {
			return new ArrayList<String[]>();
		}
		return retList;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	// 给定一个日期，返回加减n天后的日期
	public static Date nDaysAfterOneDate(Date basicDate, int n) {
		long nDay = (basicDate.getTime() / (24 * 60 * 60 * 1000) + 1 + n) * (24 * 60 * 60 * 1000);
		basicDate.setTime(nDay);
		return basicDate;
	}

	@SuppressWarnings("deprecation")
	public static String getPingYin(String src) {
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String pinyin = null;
		try {
			pinyin = PinyinHelper.toHanyuPinyinString(src, t3, "");
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		if (StringUtil.strInStrs(pinyin, "zhongqing", false)) {
			pinyin = "chongqing";
		}
		return pinyin;
	}

	@SuppressWarnings("deprecation")
	public static String getPingYinFirstLatter(String src) {
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		String pinyin = "";
		try {
			for (int i = 0; i < src.length(); i++) {
				String c = "" + src.charAt(i);
				pinyin += PinyinHelper.toHanyuPinyinString(c, t3, "").substring(0, 1);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		if (StringUtil.strInStrs(pinyin, "zhongqing", false)) {
			pinyin = "chongqing";
		}
		return pinyin;
	}

	public static String obj2json(Object obj) {
		String retStr = "";
		if (obj != null) {
			retStr = JSONObject.toJSONString(obj);
		}
		return retStr;
	}

	public static String urlEncoding(String url) {
		String retStr = "";
		if (url != null) {
			try {
				retStr = URLEncoder.encode(url, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return retStr;
	}

	public static void main2(String[] args) {
		String cont = urlEncoding("http://www.baidu.com?op=3&b=1");
		System.err.println(cont);
		// SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		// //前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
		// java.util.Date dt = new Date(654998400 * 1000);
		// String sDateTime = sdf.format(dt); //得到精确到秒的表示：08/31/2006 21:08:00
		// System.out.println(sDateTime);

		// System.err.println(2.0d >= 2.0);
		// List<String[]> aa = dataweeklist("2014-8-19", "yyyy-MM-dd","MM-dd",
		// 2);
		// for(String[] str : aa){
		// System.err.println(str[0] + " " + str[1]);
		// }
		// List<String[]> aa = dataweeklistInRange("2014-8-23",
		// "yyyy-MM-dd","yyyy-MM-dd", 2, 2);
		// for(String[] str : aa){
		// System.err.println(str[0] + " " + str[1]);
		// }
		// System.out.println(StringUtil.getFirstLetter("I love u"));
		// System.out.println(StringUtil.getPingYin("上海"));
		// System.out.println(StringUtil.getFirstLetter("I love 深圳"));
		// 编码与解码
		// String cont;
		// try {
		// cont = URLEncoder.encode("卞长芬", "utf-8");
		// System.out.println("0："+cont);
		// String after=URLDecoder.decode(cont, "utf-8");
		// System.out.println("1："+after);
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }

		// try {
		// String a = new String (Base64.encodeBase64("卞长芬".getBytes()),
		// "UTF-8");
		// System.out.println("a："+a);
		// String b = new String(Base64.decodeBase64(a), "UTF-8");
		// System.out.println("b："+b);
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// String encoded = Base64.encode("卞长芬".getBytes());
		// System.out.println("a："+encoded);
		// String name = "sNe0+rL9";
		// String decode = new String(Base64.decode(name));
		// System.out.println("b："+decode);

		// String strJson = "{'name':" + "白代昌" +
	}

	public static String getPointStr(String amount) {
		if (StringUtils.isEmpty(amount)) {
			return "";
		}
		DecimalFormat df = null;

		if (amount.length() <= 2) {
			df = new DecimalFormat("0.00");
		} else {
			df = new DecimalFormat("##.00");
		}
		Double d = Double.parseDouble(amount);
		return df.format(d / 100);
	}

	public static String addStr(String str, String addKey, String addValue) {
		str = str + "&" + addKey + "=" + addValue;
		return str;
	}

	public static String stringToJsonString(String str) {
		if (StringUtils.isNotEmpty(str)) {
			str = str.replace("\\", "");
			str = str.replace("\"{", "{");
			str = str.replace("}\"", "}");
		}
		return str;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}