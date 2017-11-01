package com.caifeng.security.validate.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import com.caifeng.security.properties.SecurityProperties;

public class ImageCodeGenerator implements ValidateCodeGenerator {

	@Autowired
	private SecurityProperties securityProperties;
	
	public ImageCode generate(HttpServletRequest request) {
		//请求级参数和应用级参数的获取
		int width = ServletRequestUtils.getIntParameter(request, "width", 
				securityProperties.getCode().getImage().getWidth());
		int height = ServletRequestUtils.getIntParameter(request, "height", 
				securityProperties.getCode().getImage().getHeight());
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

		Graphics g = image.getGraphics();
		Random random = new Random();
		
		g.setColor(getRandColor(200,250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman",Font.ITALIC,20));
		g.setColor(getRandColor(160,200));
		for(int i = 0; i < 155; i++){
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, xl, yl);
		}
		
		String sRand = "";
		
		//图片验证码数字长度
		for(int i = 0; i < securityProperties.getCode().getImage().getLength(); i++){
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		
		g.dispose();
		return new ImageCode(image, sRand, securityProperties.getCode().getImage().getExpireIn());
	}
	
	private Color getRandColor(int fc, int fb) {
		Random random = new Random();
		
		if(fc >= 250){
			fc = 250;
		}
		if(fb >= 250){
			fb = 250;
		}
		
		int r = fc + random.nextInt(fb - fc);
		int g = fc + random.nextInt(fb - fc);
		int b = fc + random.nextInt(fb - fc);
		
		return new Color(r,g,b);
	}
	

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}

}
