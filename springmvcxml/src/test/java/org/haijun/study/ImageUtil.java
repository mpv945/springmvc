package org.haijun.study;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author sanshizi
 */
public class ImageUtil {

    /**
     * 按比例裁剪图片
     *
     * @param source
     *            待处理的图片流
     * @param startX
     *            开始x坐标:
     * @param startY
     *            开始y坐标
     * @param endX
     *            结束x坐标
     * @param endY
     *            结束y坐标
     * @return
     */
    public static BufferedImage crop(BufferedImage source, int startX, int startY, int endX, int endY) {
        int width = source.getWidth();
        int height = source.getHeight();

        if (startX <= -1) {
            startX = 0;
        }
        if (startY <= -1) {
            startY = 0;
        }
        if (endX <= -1) {
            endX = width - 1;
        }
        if (endY <= -1) {
            endY = height - 1;
        }
        BufferedImage result = new BufferedImage(endX, endY , source.getType());
        for (int y = startY; y < endY+startY; y++) {
            for (int x = startX; x < endX+startX; x++) {
                int rgb = source.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }

    /**
     * 旋转图片为指定角度
     *
     * @param bufferedimage
     *            目标图像
     * @param degree
     *            旋转角度
     * @return
     */
    public static BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(h, w, type)).createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2 + (w > h ? (w - h) / 2 : (h - w) / 2));
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 图片左转90度
     *
     * @param bufferedimage
     * @return
     */
    public static BufferedImage rotateImageLeft90(BufferedImage bufferedimage) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(h, w, type)).createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(270), w / 2, h / 2 + (w - h) / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    /**
     * 图片右转90度
     *
     * @param bufferedimage
     * @return
     */
    public static BufferedImage rotateImageRight90(BufferedImage bufferedimage) {
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(h, w, type)).createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(90), w / 2 - (w - h) / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        return img;
    }

    // 对转
    public File rotateImageOppo(File file) throws Exception {
        BufferedImage bufferedimage = ImageIO.read(file);
        int w = bufferedimage.getWidth();
        int h = bufferedimage.getHeight();
        int type = bufferedimage.getColorModel().getTransparency();
        BufferedImage img;
        Graphics2D graphics2d;
        (graphics2d = (img = new BufferedImage(w, h, type)).createGraphics()).setRenderingHint(
                RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate(Math.toRadians(180), w / 2, h / 2);
        graphics2d.drawImage(bufferedimage, 0, 0, null);
        graphics2d.dispose();
        ImageIO.write(img, "jpg", file);
        return file;
    }

    /**
     * 对图片进行强制放大或缩小
     *
     * @param originalImage
     *            原始图片
     * @return
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return newImage;
    }

    /***
     * 图片镜像处理
     *
     * @param file
     * @param FX
     *            0 为上下反转 1 为左右反转
     * @return
     */
    public void imageMisro(File file, int FX) {
        try {
            BufferedImage bufferedimage = ImageIO.read(file);
            int w = bufferedimage.getWidth();
            int h = bufferedimage.getHeight();
            int[][] datas = new int[w][h];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    datas[j][i] = bufferedimage.getRGB(j, i);
                }
            }
            int[][] tmps = new int[w][h];
            if (FX == 0) {
                for (int i = 0, a = h - 1; i < h; i++, a--) {
                    for (int j = 0; j < w; j++) {
                        tmps[j][a] = datas[j][i];
                    }
                }
            } else if (FX == 1) {
                for (int i = 0; i < h; i++) {
                    for (int j = 0, b = w - 1; j < w; j++, b--) {
                        tmps[b][i] = datas[j][i];
                    }
                }
            }
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    bufferedimage.setRGB(j, i, tmps[j][i]);
                }
            }
            ImageIO.write(bufferedimage, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 针对高度与宽度进行等比缩放
     *
     * @param img
     * @param maxSize 要缩放到的尺寸
     * @param type 1:高度与宽度的最大值为maxSize进行等比缩放 , 2:高度与宽度的最小值为maxSize进行等比缩放
     * @return
     */
    private static Image getScaledImage(BufferedImage img, int maxSize, int type) {
        int w0 = img.getWidth();
        int h0 = img.getHeight();
        int w = w0;
        int h = h0;
        if (type == 1) {
            w = w0 > h0 ? maxSize : (maxSize * w0 / h0);
            h = w0 > h0 ? (maxSize * h0 / w0) : maxSize;
        } else if (type == 2) {
            w = w0 > h0 ? (maxSize * w0 / h0) : maxSize;
            h = w0 > h0 ? maxSize : (maxSize * h0 / w0);
        }
        Image image = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);//在适当的位置画出
        return result;
    }

    /**
     * 先按最小宽高为size等比例绽放, 然后图像居中抠出直径为size的圆形图像
     *
     * @param img
     * @param size
     * @return
     */
    private static BufferedImage getRoundedImage(BufferedImage img, int size) {
        return getRoundedImage(img, size, size / 2, 2);
    }

    /**
     * 先按最小宽高为size等比例绽放, 然后图像居中抠出半径为radius的圆形图像
     *
     * @param img
     * @param size 要缩放到的尺寸
     * @param radius 圆角半径
     * @param type 1:高度与宽度的最大值为maxSize进行等比缩放 , 2:高度与宽度的最小值为maxSize进行等比缩放
     * @return
     */
    private static BufferedImage getRoundedImage(BufferedImage img, int size, int radius, int type) {

        BufferedImage result = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();

        //先按最小宽高为size等比例绽放, 然后图像居中抠出直径为size的圆形图像
        Image fixedImg = getScaledImage(img, size, type);
        g.drawImage(fixedImg, (size - fixedImg.getWidth(null)) / 2, (size - fixedImg.getHeight(null)) / 2, null);//在适当的位置画出

        //圆角
        if (radius > 0) {
            RoundRectangle2D round = new RoundRectangle2D.Double(0, 0, size, size, radius * 2, radius * 2);
            Area clear = new Area(new Rectangle(0, 0, size, size));
            clear.subtract(new Area(round));
            g.setComposite(AlphaComposite.Clear);

            //抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.fill(clear);
            g.dispose();
        }
        return result;
    }

    /**
     * 使用删除alpha值的方式去掉图像的alpha通道
     *
     * @param $image
     * @return
     */
    protected static BufferedImage get24BitImage(BufferedImage $image) {
        int __w = $image.getWidth();
        int __h = $image.getHeight();
        int[] __imgARGB = getRGBs($image.getRGB(0, 0, __w, __h, null, 0, __w));
        BufferedImage __newImg = new BufferedImage(__w, __h, BufferedImage.TYPE_INT_RGB);
        __newImg.setRGB(0, 0, __w, __h, __imgARGB, 0, __w);
        return __newImg;
    }

    /**
     * 使用绘制的方式去掉图像的alpha值
     *
     * @param $image
     * @param $bgColor
     * @return
     */
    protected static BufferedImage get24BitImage(BufferedImage $image, Color $bgColor) {
        int $w = $image.getWidth();
        int $h = $image.getHeight();
        BufferedImage img = new BufferedImage($w, $h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor($bgColor);
        g.fillRect(0, 0, $w, $h);
        g.drawRenderedImage($image, null);
        g.dispose();
        return img;
    }

    /**
     * 将32位色彩转换成24位色彩（丢弃Alpha通道）
     *
     * @param $argb
     * @return
     */
    public static int[] getRGBs(int[] $argb) {
        int[] __rgbs = new int[$argb.length];
        for (int i = 0; i < $argb.length; i++) {
            __rgbs[i] = $argb[i] & 0xFFFFFF;
        }
        return __rgbs;
    }

    public static void toJPG(File img, File save, int size, int quality) throws IOException {
        FileOutputStream out = new FileOutputStream(save);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

        BufferedImage image = (BufferedImage) getRoundedImage(ImageIO.read(img), size, 0, 2);//默认无圆角

        //如果图像是透明的，就丢弃Alpha通道
        if (image.getTransparency() == Transparency.TRANSLUCENT) {
            image = get24BitImage(image);
        }

        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);//使用jpeg编码器
        param.setQuality(1, true);//高质量jpg图片输出
        encoder.encode(image, param);

        out.close();
    }

    public static void toPNG(File img, File save, int size) throws IOException {
        ImageIO.write((BufferedImage) getRoundedImage(ImageIO.read(img), size, 0, 2), "PNG", save);//默认无圆角
    }

    public static void main(String[] args) throws IOException {
        File img = new File("e:\\Users\\rocky\\Desktop\\0\\IMG_0404.PNG");
        File save = new File("e:\\Users\\rocky\\Desktop\\0\\zz.jpg");

        toJPG(img, save, 250, 100);
    }
}
