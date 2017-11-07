package com.bw30.zsch.tribe.touch.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.bw30.zsch.tribe.touch.constants.SystemConstants;

/**
 * 生成图片验证码的servlet，校验工具可以使用#PicVerifyCodeUtil
 * 
 * @author ShengHao
 *
 *         2017年9月14日 - 上午9:16:06
 */
public class PicVerifyCodeServlet extends HttpServlet {

	private static final long serialVersionUID = -2351835925545512244L;
	private final static Logger LOG = Logger.getLogger(PicVerifyCodeServlet.class);

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置页面不缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		// 生成随机类
		Random random = new Random();
		// 在内存中创建图象
		int width = 60;
		int height = 20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = buildGraphics(random, image, width, height);

		// 取随机产生的认证码(4位数字)
		StringBuffer verifyCodeBuf = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			String randCode = String.valueOf(random.nextInt(10));
			verifyCodeBuf.append(randCode);
			// 将认证码显示到图象中 ， 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(randCode, 13 * i + 6, 16);
		}
		// 图象生效
		g.dispose();

		// 将认证码存入SESSION
		request.getSession().setAttribute(SystemConstants.PIC_VERIFY_CODE_SESSION_KEY, verifyCodeBuf.toString());
		LOG.info("当前sessionId：" + request.getSession().getId() + " ，验证码sessionKey： "
				+ SystemConstants.PIC_VERIFY_CODE_SESSION_KEY + " ，验证码：" + verifyCodeBuf.toString());

		OutputStream output = null;
		try {
			output = response.getOutputStream();
			// 输出图象到页面
			ImageIO.write(image, "JPEG", output);
			output.flush();
		} catch (IOException e) {
			LOG.error("验证码图片输出到响应流中报错：" + e);
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}

	/**
	 * 给定范围获得随机颜色
	 */
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 组装画布
	 */
	private Graphics buildGraphics(Random random, BufferedImage image, int width, int height) {
		// 获取图形上下文
		Graphics g = image.getGraphics();

		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);

		// 设定字体
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		// 画边框
		// g.setColor(new Color());
		// g.drawRect(0,0,width-1,height-1);

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		return g;
	}

}
