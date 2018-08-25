package org.haijun.study;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

public class ImgSum {

    public static void main(String[] args) {
        try {
            ImageTset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void ImageTset() throws Exception {
        //创建四个文件对象（注：这里四张图片的宽度都是相同的，因此下文定义BufferedImage宽度就取第一只的宽度就行了）
        File _file1 = new File("D:\\data\\1535193232605_screenshot.png");
        File _file2 = new File("D:\\data\\1535193233829_screenshot.png");
        File _file3 = new File("D:\\data\\1535193235007_screenshot.png");


        Image src1 = javax.imageio.ImageIO.read(_file1);
        Image src2 = javax.imageio.ImageIO.read(_file2);
        Image src3 = javax.imageio.ImageIO.read(_file3);


        //获取图片的宽度
        int width = src1.getWidth(null);
        //获取各个图像的高度
        int height1 = src1.getHeight(null);
        int height2 = src2.getHeight(null);
        int height3 = src3.getHeight(null);

        int type = ((BufferedImage) src3).getType();


        //构造一个类型为预定义图像类型之一的 BufferedImage。 宽度为第一只的宽度，高度为各个图片高度之和
        BufferedImage tag = new BufferedImage(width, height1 + height2 + height3 , type);
        //创建输出流

        //绘制合成图像
        Graphics g = tag.createGraphics();
        g.drawImage(src1, 0, 0, width, height1, null);
        g.drawImage(src2, 0, height1, width , height2, null);
        g.drawImage(src3, 0, height1 + height2, width, height3, null);
        // 释放此图形的上下文以及它使用的所有系统资源。
        g.dispose();
        //将绘制的图像生成至输出流
        //FileOutputStream out = new FileOutputStream("D:\\data\\treasureMap.png");
        //JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        //encoder.encode(tag);
        //输出拼接后的图像
        ImageIO.write(tag, "png", new File("d:\\data\\all.png"));
        ImageIO.createImageInputStream(tag);
        //关闭输出流
        //out.close();
        System.out.println("藏宝图出来了");
    }
}
