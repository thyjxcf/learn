package net.zdsoft.keel.page;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;

import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.RandomUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 产生随机数字图片的工具类，用来作图片验证码
 * 
 * @author liangxiao
 * @version $Revision: 1.9 $, $Date: 2007/04/13 02:50:27 $
 */
public class TextImage {

    private static Logger logger = LoggerFactory.getLogger(TextImage.class);

    private int width;
    private int height;

    private Font font;

    private Color bgColor;
    private Color fontColor;

    private int randomPointNum;

    // 安全级别
    private int level;

    /**
     * 构造方法
     * 
     * @param width
     *            宽度
     * @param height
     *            高度
     */
    public TextImage(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 构造方法
     * 
     * @param width
     *            宽度
     * @param height
     *            高度
     * @param bgColor
     *            背景颜色
     * @param fontColor
     *            字体颜色
     */
    public TextImage(int width, int height, Color bgColor, Color fontColor) {
        this(width, height);

        this.bgColor = bgColor;
        this.fontColor = fontColor;
    }

    /**
     * 构造方法
     * 
     * @param width
     *            宽度
     * @param height
     *            高度
     * @param bgColor
     *            背景颜色
     * @param fontColor
     *            字体颜色
     * @param font
     *            字体
     */
    public TextImage(int width, int height, Color bgColor, Color fontColor,
            Font font) {
        this(width, height, bgColor, fontColor);

        this.font = font;
    }

    /**
     * 设置随机干扰点的数量
     * 
     * @param randomPointNum
     */
    public void setRandomPointNum(int randomPointNum) {
        this.randomPointNum = randomPointNum;
    }

    /**
     * 设置字体
     * 
     * @param font
     *            字体
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * 设置字体大小
     * 
     * @param size
     *            字体大小
     */
    public void setFont(int size) {
        font = new Font("", 0, size);
    }

    /**
     * 取得高度
     * 
     * @return 高度
     */
    public int getHeight() {
        return height;
    }

    /**
     * 设置高度
     * 
     * @param height
     *            高度
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 取得宽度
     * 
     * @return 宽度
     */
    public int getWidth() {
        return width;
    }

    /**
     * 设置宽度
     * 
     * @param width
     *            宽度
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 取得背景颜色
     * 
     * @return 背景颜色
     */
    public Color getBgColor() {
        return bgColor;
    }

    /**
     * 取得字体颜色
     * 
     * @return 字体颜色
     */
    public Color getFontColor() {
        return fontColor;
    }

    /**
     * 设置背景颜色
     * 
     * @param bgColor
     *            背景颜色
     */
    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    /**
     * 设置背景颜色
     * 
     * @param r
     *            红
     * @param g
     *            绿
     * @param b
     *            蓝
     */
    public void setBgColor(int r, int g, int b) {
        bgColor = new Color(r, g, b);
    }

    /**
     * 设置字体颜色
     * 
     * @param fontColor
     *            字体颜色
     */
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    /**
     * 设置字体颜色
     * 
     * @param r
     *            红
     * @param g
     *            绿
     * @param b
     *            蓝
     */
    public void setFontColor(int r, int g, int b) {
        fontColor = new Color(r, g, b);
    }

    /**
     * 设置安全级别，0为最低级，级别越高越难识别，目前只支持0、2两级
     * 
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * 输出图片
     * 
     * @param text
     *            文字内容
     * @param x
     *            起始x坐标
     * @param y
     *            其实y坐标
     * @param out
     *            输出流
     */
    public void export(String text, int x, int y, OutputStream out) {
        try {
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);

            Graphics graphics = image.getGraphics();

            graphics.setColor(bgColor);
            graphics.fillRect(0, 0, width, height);

            graphics.setColor(fontColor);

            if (level == 2) {
                int maxFontSize = font.getSize();
                for (int i = 0; i < text.length(); i++) {
                    Font _font = new Font("", RandomUtils.getRandomInt(0, 5),
                            maxFontSize - RandomUtils.getRandomInt(0, 8));

                    graphics.setFont(_font);
                    graphics.drawString(String.valueOf(text.charAt(i)), x
                            + (i * (maxFontSize / 2))
                            + (i > 0 ? RandomUtils.getRandomInt(0, 5) : 0), y
                            + RandomUtils.getRandomInt(0, 6));
                }
            }
            else {
                graphics.setFont(font);
                graphics.drawString(text, x, y);
            }

            for (int i = 0; i < randomPointNum; i++) {
                int randomX = RandomUtils.getRandomInt(0, width);
                int randomY = RandomUtils.getRandomInt(0, height);
                graphics.drawLine(randomX, randomY, randomX, randomY);
            }

            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
        }
        catch (Exception e) {
            logger.error("Could not create image", e);
        }
        finally {
            FileUtils.close(out);
        }
    }

    public static void main(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream("D:\\test.jpg");

        TextImage textImage = new TextImage(64, 24);
        textImage.setBgColor(0xF3, 0xF3, 0xF3);
        textImage.setFontColor(0x77, 0x77, 0x77);
        textImage.setFont(25);
        textImage.setRandomPointNum(200);
        textImage.setLevel(2);
        textImage.export(RandomUtils.getRandomStr(4).toLowerCase(), 4, 18, out);

        out.close();
    }
}
