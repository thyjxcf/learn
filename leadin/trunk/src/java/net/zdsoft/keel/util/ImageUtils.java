/* 
 * @(#)ImageUtils.java    Created on 2006-4-13
 * Copyright (c) 2005 ZDSoft.net, Inc. All rights reserved.
 * $Header: /project/keel/src/net/zdsoft/keel/util/ImageUtils.java,v 1.12 2008/02/13 08:59:42 yangm Exp $
 */
package net.zdsoft.keel.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片工具类
 * 
 * @author liangxiao
 * @version $Revision: 1.12 $, $Date: 2008/02/13 08:59:42 $
 */
public class ImageUtils {

	private ImageUtils() {
	}

	/**
	 * 修改图片大小
	 * 
	 * @param src
	 *                源图片的路径
	 * @param dest
	 *                目标图片的路径
	 * @param width
	 *                宽
	 * @param height
	 *                高
	 * @throws IOException
	 */
	public static void changeSize(String src, String dest, int width, int height) throws IOException {
		Image srcImage = ImageIO.read(new File(src)); // 构造Image对象
		// int width=src.getWidth(null); //得到源图宽
		// int height=src.getHeight(null); //得到源图高
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bufferedImage.getGraphics().drawImage(srcImage, 0, 0, width, height, Color.WHITE, null); // 绘制缩小后的图
		FileOutputStream out = new FileOutputStream(dest); // 输出到文件流
		ImageIO.write(bufferedImage, "jpeg", out);
		out.close();
	}

	/**
	 * 修改图片大小。原图尺寸小时，则以原图尺寸为准
	 * 
	 * @deprecated 此方法没有大的作用，不推荐使用。
	 * @param src
	 *                源图片的路径
	 * @param dest
	 *                目标图片的路径
	 * @param width
	 *                宽
	 * @param height
	 *                高
	 * @throws IOException
	 */
	public static void changeSize2(String src, String dest, int width, int height) throws IOException {
		Image srcImage = ImageIO.read(new File(src)); // 构造Image对象
		int swidth = srcImage.getWidth(null); // 得到源图宽
		int sheight = srcImage.getHeight(null); // 得到源图高
		if (swidth < width) {
			width = swidth;
		}
		if (sheight < height) {
			height = sheight;
		}
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bufferedImage.getGraphics().drawImage(srcImage, 0, 0, width, height, Color.WHITE, null); // 绘制缩小后的图
		FileOutputStream out = new FileOutputStream(dest); // 输出到文件流
		ImageIO.write(bufferedImage, "jpeg", out);
		out.close();
	}

	/**
	 * 调整图片大小、质量、dpi值（如果目标目录不存在，会自动创建）
	 * 
	 * @param src
	 *                源图片的路径
	 * @param dest
	 *                目标图片的路径
	 * @param width
	 *                宽
	 * @param height
	 *                高
	 * @param opposite
	 *                是否等比例缩放，并且最大值不超过图片本身
	 * @param quality
	 *                压缩质量， 最大值为1，如果为0，则表示不改变（默认是0.8f）
	 * @param dpi
	 *                dpi值，如果为0，表示不改变
	 * @throws IOException
	 */
	public static void changeSize(String src, String dest, int width, int height, boolean opposite, float quality, int dpi) throws IOException {
		Image srcImage = ImageIO.read(new File(src)); // 构造Image对象
		int srcWidth = srcImage.getWidth(null); // 得到源图宽
		int srcHeight = srcImage.getHeight(null); // 得到源图高

		// 若宽高小于指定最大值，不需重新绘制
		if (srcWidth <= width && srcHeight <= height && quality == 0 && dpi == 0) {
			width = srcWidth;
			height = srcHeight;

			// copy文件从src到dest
			if (src.equalsIgnoreCase(dest))
				return;
			File srcFile = new File(src);
			File destFile = new File(dest);
			String destFilePath = FilenameUtils.getFullPath(dest);
			File destPathFile = new File(destFilePath);
			if (!destPathFile.exists())
				destPathFile.mkdirs();
			FileUtils.copyFile(srcFile, destFile);
		} else {
			if (opposite) {
				double scale = 0;
				if (width > 0 && height > 0) {
					scale = ((double) width / srcWidth) > ((double) height / srcHeight) ? ((double) height / srcHeight)
							: ((double) width / srcWidth);
				}
				else{
					if(width == 0){
						scale =  (double) height / srcHeight;
					}
					else{
						scale = (double) width / srcWidth;
					}
				}
				width = (int) (srcWidth * scale);
				height = (int) (srcHeight * scale);

				if (width <= 0) {
					width = 1;
				}
				if (height <= 0) {
					height = 1;
				}
			}

			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// bufferedImage.getGraphics().drawImage(srcImage, 0, 0,
			// width, height, Color.WHITE, null); // 绘制缩小后的图
			// Color color = new Color(224, 255, 255);
			Graphics2D graphics = bufferedImage.createGraphics();

			bufferedImage = graphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			graphics.dispose();

			graphics = bufferedImage.createGraphics();

			Image from = srcImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
			graphics.drawImage(from, 0, 0, null);
			graphics.dispose();

			String destFilePath = FilenameUtils.getFullPath(dest);
			File destPathFile = new File(destFilePath);
			if (!destPathFile.exists())
				destPathFile.mkdirs();
			// ImageIO.write(bufferedImage, "png", new File(desc));

			FileOutputStream out = new FileOutputStream(dest); // 输出到文件流
			if (quality > 0) {
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);
				/** 压缩质量 ，默认是0.8f */
				jep.setQuality(quality, true);
				if (dpi > 0) {
					jep.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
					jep.setXDensity(dpi);
					jep.setYDensity(dpi);
				}
				encoder.encode(bufferedImage, jep);
				out.close();
			} else {
				ImageIO.write(bufferedImage, "PNG", out);
			}
		}
	}

	/**
	 * 等比例修改图片大小，目标图片的大小不会超过指定的宽、高，以小的为准
	 * 
	 * @param src
	 *                源图片的路径
	 * @param dest
	 *                目标图片的路径
	 * @param width
	 *                宽
	 * @param height
	 *                高
	 * @throws IOException
	 */
	public static void changeOppositeSize(String src, String dest, int width, int height) throws IOException {
		changeSize(src, dest, width, height, true, 0, 0);
	}

	/**
	 * 等比例修改原图片大小，大小不会超过指定的宽、高，以小的为准
	 * 
	 * @deprecated 不推荐使用
	 * @param src
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void changeSize(String src, int width, int height) throws IOException {
		Image srcImage = ImageIO.read(new File(src)); // 构造Image对象
		int srcWidth = srcImage.getWidth(null); // 得到源图宽
		int srcHeight = srcImage.getHeight(null); // 得到源图高

		// 若宽高小于指定最大值，不需重新绘制
		if (srcWidth <= width && srcHeight <= height) {
			return;
		} else {
			double scale = ((double) width / srcWidth) > ((double) height / srcHeight) ? ((double) height / srcHeight)
					: ((double) width / srcWidth);
			width = (int) (srcWidth * scale);
			height = (int) (srcHeight * scale);

			if (width <= 0) {
				width = 1;
			}
			if (height <= 0) {
				height = 1;
			}

			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			bufferedImage.getGraphics().drawImage(srcImage, 0, 0, width, height, Color.WHITE, null); // 绘制缩小后的图
			FileOutputStream out = new FileOutputStream(src); // 输出到文件流
			ImageIO.write(bufferedImage, "jpeg", out);
			out.close();
		}

	}

	/**
	 * 给文件名加后缀
	 * 
	 * @param imageName
	 *                图片的文件名
	 * @param suffix
	 *                后缀字符串
	 * @return 修改之后的文件名
	 */
	public static String addSuffix4FileName(String fileName, String suffix) {
		int pointIndex = fileName.lastIndexOf(".");
		if (pointIndex > 0) {
			return fileName.substring(0, pointIndex) + suffix + fileName.substring(pointIndex);
		} else {
			return fileName + suffix;
		}
	}

	/*
	 * public static boolean compressPic(String srcFilePath, String
	 * descFilePath, int width, int height) { File file = null;
	 * BufferedImage src = null; FileOutputStream out = null; ImageWriter
	 * imgWrier; ImageWriteParam imgWriteParams;
	 * 
	 * // 指定写图片的方式为 jpg imgWrier =
	 * ImageIO.getImageWritersByFormatName("jpg").next(); imgWriteParams =
	 * new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null); //
	 * 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
	 * imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT); //
	 * 这里指定压缩的程度，参数qality是取值0~1范围内，
	 * imgWriteParams.setCompressionQuality((float) 0.75);
	 * imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
	 * ColorModel colorModel = ColorModel.getRGBdefault(); // 指定压缩时使用的色彩模式
	 * imgWriteParams.setDestinationType(new
	 * javax.imageio.ImageTypeSpecifier(colorModel, colorModel
	 * .createCompatibleSampleModel(16, 16)));
	 * //imgWriteParams.setTiling(width, height, 0, 0);
	 * 
	 * 
	 * try { if (srcFilePath == null || srcFilePath.trim().length() == 0) {
	 * return false; } else { file = new File(srcFilePath); src =
	 * ImageIO.read(file); out = new FileOutputStream(descFilePath);
	 * 
	 * imgWrier.reset(); // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
	 * OutputStream构造
	 * imgWrier.setOutput(ImageIO.createImageOutputStream(out)); //
	 * 调用write方法，就可以向输入流写图片 imgWrier.write(null, new IIOImage(src, null,
	 * null), imgWriteParams); out.flush(); out.close(); } } catch
	 * (Exception e) { e.printStackTrace(); return false; } return true; }
	 * 
	 * 
	 * 1.ByteArrayInputStream is = new ByteArrayInputStream(data); 2.
	 * 3.BufferedImage src = null; 4.ByteArrayOutputStream out = null;
	 * 5.ImageWriter imgWrier; 6.ImageWriteParam imgWriteParams; 7. 8.//
	 * 指定写图片的方式为 jpg 9.imgWrier =
	 * ImageIO.getImageWritersByFormatName("jpg").next(); 10.imgWriteParams
	 * = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null); 11.//
	 * 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
	 * 12.imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
	 * 13.// 这里指定压缩的程度，参数qality是取值0~1范围内，
	 * 14.imgWriteParams.setCompressionQuality((float)0.1/data.length); 15.
	 * 16.imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
	 * 17.ColorModel colorModel = ColorModel.getRGBdefault(); 18.//
	 * 指定压缩时使用的色彩模式 19.imgWriteParams.setDestinationType(new
	 * javax.imageio.ImageTypeSpecifier(colorModel, colorModel 20.
	 * .createCompatibleSampleModel(16, 16))); 21. 22.try 23.{ 24. src =
	 * ImageIO.read(is); 25. out = new ByteArrayOutputStream(data.length);
	 * 26. 27. imgWrier.reset(); 28. // 必须先指定 out值，才能调用write方法,
	 * ImageOutputStream可以通过任何 OutputStream构造 29.
	 * imgWrier.setOutput(ImageIO.createImageOutputStream(out)); 30. //
	 * 调用write方法，就可以向输入流写图片 31. imgWrier.write(null, new IIOImage(src, null,
	 * null), imgWriteParams); 32. 33. out.flush(); 34. out.close(); 35.
	 * is.close(); 36. data = out.toByteArray(); 37.} 38.catch(Exception e)
	 * 39.{ 40. e.printStackTrace(); 41.}
	 */
	public static void main(String[] args) throws Exception {
		//changeSize("d://old_a.png", "d://old_b.png", 100, 150, true, 0, 0);
		
		changeSize("d://Chrysanthemum.png", "d://Chrysanthemum1.png", 400, 600, true, 0, 0);
		changeSize("d://Chrysanthemum.jpg", "d://Chrysanthemum1.jpg", 400, 600, true, 0, 0);
	}

}
