package com.bw30.zsch.tribe.touch.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class PropertiesUtils {

	private static final Map<String, Properties> props = new HashMap<String, Properties>();

	private PropertiesUtils() {
	}

	/**
	 * @功能: 根据name获取properties文件中的value (注意: 虽然静态方法中调用了静态变量, 使得该方法非线程安全,
	 *      但该方法只是读取配置文件, 不做修改, 所以不会有问题)
	 * @作者: chaiph
	 * @创建日期: 2013-11-21 下午07:01:48
	 * @param filePath
	 *            properties文件路径(classpath中的相对路径)
	 * @param name
	 * @return
	 */
	public static String getProperty(String filePath, String name) {
		if (StringUtils.isBlank(filePath) || StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("The parameters must not be null");
		}
		try {
			Properties prop = props.get(filePath);
			if (prop == null) {
				prop = new Properties();
				prop.load(PropertiesUtils.class.getResourceAsStream(filePath));
				props.put(filePath, prop);
			}
			return prop.getProperty(name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @功能: 根据name获取properties文件中的value, 如果为空返回默认值 (注意: 虽然静态方法中调用了静态变量,
	 *      使得该方法非线程安全, 但该方法只是读取配置文件, 不做修改, 所以不会有问题)
	 * @作者: chaiph
	 * @创建日期: 2013-11-21 下午07:01:48
	 * @param resolPath
	 *            properties文件路径(classpath中的相对路径)
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getProperty(String resolPath, String name, String defaultValue) {
		if (StringUtils.isBlank(resolPath) || StringUtils.isBlank(name) || defaultValue == null) {
			throw new IllegalArgumentException("The parameters must not be null");
		}
		try {
			Properties prop = props.get(resolPath);
			if (prop == null) {
				File file = new File(getRootPath() + "/" + resolPath);
				prop = new Properties();
				prop.load(new FileInputStream(file));
				props.put(resolPath, prop);
			}
			String value = prop.getProperty(name);
			return StringUtils.isBlank(value) ? defaultValue : value;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	/**
	 * @功能: properties文件中的name,value (已过期, 会打乱properties文件中顺序, 虽然静态方法中调用了静态变量,
	 *      使得该方法非线程安全, 但是已加锁, 效率低, 不推荐使用)
	 * @作者: chaiph
	 * @创建日期: 2013-11-21 下午07:02:38
	 * @param resolPath
	 *            properties文件路径(classpath中的相对路径)
	 * @param name
	 * @param value
	 */
	@Deprecated
	public static void setProperty(String resolPath, String name, String value) {
		if (StringUtils.isBlank(resolPath) || StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
			throw new IllegalArgumentException("The parameters must not be null");
		}
		BufferedWriter bw = null;
		try {
			synchronized (props) {
				Properties prop = props.get(resolPath);
				File file = new File(getRootPath() + "/" + resolPath);
				if (prop == null) {
					prop = new Properties();
					prop.load(new FileInputStream(file));
					props.put(resolPath, prop);
				}
				prop.put(name, value);
				bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
				prop.store(bw, "保存properties配置文件");
			}
			bw.flush();
			bw.close();
			bw = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static public String getRootPath() {
		String filePath = PropertiesUtils.class.getResource("/").getPath();
		try {
			filePath = filePath.substring(0, filePath.indexOf("WEB-INF"));
		} catch (Exception e) {
			filePath = getClassPath();
		}

		return filePath;
	}

	static public String getClassPath() {
		Class<?> classz = PropertiesUtils.class;
		String path = classz.getResource("/").getPath();
		return path;
	}

	public static void main(String[] args) throws IOException {
		// System.out.println(PropertiesUtils.getProperty("/sys_data.properties",
		// "cnt", "800"));
		// System.out.println(PropertiesUtils.getProperty("/sys_data.properties",
		// "oracle.driver", "mysql"));
		// PropertiesUtils.setProperty("/sys_data.properties", "name", "yangc");

		String filePath = "/shmobilenum.properties";
		File file = PropertiesUtils.getFile(filePath);
		List<String> list = FileUtils.readLines(file, "UTF-8");

		List<String> newlist = new ArrayList<String>();
		for (String str : list) {
			System.err.println(str);
			newlist.add(str.replace(",", ""));
		}

		FileOutputStream file1 = new FileOutputStream("d:/aa.txt");
		IOUtils.writeLines(newlist, "\r\n", file1);

	}

	static public File getFile(String resolPath) {
		File file = new File(getRootPath() + "/" + resolPath);
		return file;
	}

}
