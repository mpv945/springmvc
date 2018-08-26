package org.haijun.study;

import static org.junit.Assert.assertTrue;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.interactions.internal.TouchAction;
import org.openqa.selenium.interactions.touch.ScrollAction;
import org.openqa.selenium.support.ui.Select;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 *  打开浏览器方法【Java】 https://www.jianshu.com/p/305ea89b87e9
 */
public class AppTest 
{


    /**
     * 查询页面元素 driver.findElement(By)：查询单个对象，查询了多个则selenium返回第一个对象
     *      通过id：前端页面可以没有页面元素，有元素就是唯一的id，不会重复
     *      通过Name：Name属性可以重复
     *      通过className：class属性的值，class是给页面一批元素添加一类class属性
     *      通过LinkText：<a href>和</a>之间的内容
     *      通过PartialLinkText：部分文本值
     *      通过XPATH：  如何从chrome浏览器获取Xpath F12打开开发者工具，点击开发者工具内的箭头 ;右击选中的代码，选择Copy-Copy XPath
     *      通过CSS选择器
     *      通过TagName：可以用TagName先缩小范围
     *      组合-连续查找：在findElement找的元素之后，在子元素下继续查询元素，缩小范围
     *      复合元素以及多对象定位：driver.findElements(By) 方法的返回值类型为webElement对象的list（集合形式）
     *
     * @throws Exception
     */
    @Test
    public void testOpenHtml() throws Exception{
        WebDriver driver = null;
        // 谷歌
        System.setProperty("webdriver.chrome.driver", "\\drivers\\chromedriver.exe");// 自己测试在windows下，要创建C:\drivers,然后再复制一份
        ChromeOptions options = new ChromeOptions();
        //options.setHeadless(true);//在后台打开浏览器
        driver = new ChromeDriver(options);//InternetExplorerDriver();//ChromeDriver();//FirefoxDriver();

        driver.get("https://www.baidu.com");// 浏览网页
        driver.getPageSource();// 获取网页内容 获取网页的原始代码
        driver.getCurrentUrl();// 获取url
        //driver.manage().addCookie(cookie);
        //driver.navigate().back();//#向后退
        //driver.navigate().forward();//#向前进
        //driver.navigate().refresh();//#刷新页面
        //driver.navigate().to();// 跳转
        JavascriptExecutor js = (JavascriptExecutor) driver; // 执行js
        String jsStr = String.format("window.scroll(%s, %s)", 100,500);
        //js.executeScript(" window.scrollTo(arguments[0],arguments[1])",100,500);
        js.executeScript(jsStr);
        TimeUnit.SECONDS.sleep(5);

        String searchHandle = driver.getWindowHandle(); // 获取头部信息
        // 如果不是我要的页面 获取百度新闻的连接，然后利用js打开一个新的窗口
        String href = driver.findElement(By.cssSelector("[name=tj_trnews]")).getAttribute("href");
        js.executeScript("window.open('" + href + "')");
        TimeUnit.SECONDS.sleep(5);

        Set<String> handles = driver.getWindowHandles();
        Iterator iterator = handles.iterator();
        Iterator iterator2 = handles.iterator();
        // 进入百度新闻窗口,并获取title验证
        String newsHandle = null;
        while (iterator.hasNext()) {
            String h = (String) iterator.next();
            if (h != searchHandle) {
                driver.switchTo().window(h);
                if (driver.getTitle().contains("百度新闻")) {
                    System.out.println("switch to news page successfully");
                    newsHandle = driver.getWindowHandle();
                    break;
                } else {
                    continue;
                }
            }
        }
        TimeUnit.SECONDS.sleep(5);
        while (iterator2.hasNext()) {
            String h = (String) iterator2.next();
            if (searchHandle.equals(h)) {
                driver.switchTo().window(h);
                if (driver.getTitle().contains("百度一下")) {
                    System.out.println("switch to search page successfully");
                    driver.findElement(By.cssSelector("#kw")).sendKeys("switch successfully");
                    driver.close();// 只关闭百度首页
                    System.out.println("close search page successfully");
                    driver.switchTo().window(newsHandle);// 切换到百度新闻
                    System.out.println("当前的title是： " + driver.getTitle());// 获取title
                    TimeUnit.SECONDS.sleep(5);
                    break;
                } else {
                    continue;
                }
            }
        }
        // 切换到某个frame 或者从一个frame切换到另一个frame：
        driver.switchTo().frame("");
        //返回父iframe：（一般在跳转frame之前都写上这个语句）
        driver.switchTo().defaultContent();
        //切换到某个window：
        driver.switchTo().window("windowName");

        // 查找元素；具体方法注释有说明
        WebElement element = driver.findElement(By.id("txt_1_value1"));
        element.sendKeys("期刊");// 设置
        element.getAttribute("id");//获取属性值
        element.getText();// 获取元素内容
        element.clear();// 清空内容
        // 通过元素对象滚动到目标元素的纵坐标位置(Link)
        Coordinates coor = ((Locatable)element).getCoordinates();
        coor.inViewPort();
        // 超链接
        element.click();
        // 下拉菜单
        WebElement we3 = driver.findElement(By.id("txt_1_sel"));
        Select select = new Select(we3);
        select.selectByIndex(1);// 选中下标的
        List<WebElement> list = select.getOptions();
        list.get(5).click();// 对第5个元素操作
        select.selectByVisibleText("全文");
        select.getFirstSelectedOption();
        //单选项(Radio Button)
        WebElement radio = driver.findElement(By.id("txt_1_sel"));
        radio.click();//选择某个单选项
        radio.clear();//清空某个单选项
        radio.isSelected();//判断某个单选项是否已经被选择
        //多选项(checkbox)
        WebElement checkbox = driver.findElement(By.id("myCheckbox."));
        checkbox.click();checkbox.clear();checkbox.isSelected();checkbox.isEnabled(); //判断按钮是否enable
        // 按钮(button)
        WebElement btn= driver.findElement(By.id("save"));
        btn.click();//点击按钮
        btn.isEnabled ();//判断按钮是否enable
        // 弹出对话框(Popup dialogs)
        Alert alert = driver.switchTo().alert();
        alert.accept();
        alert.dismiss();
        alert.getText();
        //表单(Form) Form中的元素的操作和其它的元素操作一样，对元素操作完成后对表单的提交可以
        WebElement approve = driver.findElement(By.id("approve"));
        approve.click();approve.submit();//只适合于表单的提交
        // 上传文件
        WebElement adFileUpload =driver.findElement(By.id("WAP-upload"));
        String filePath = "C:\test\\uploadfile\\media_ads\\test.jpg";
        adFileUpload.sendKeys(filePath);
        // Windows 和 Frames之间的切换
        driver.switchTo().defaultContent();//返回到最顶层的frame/iframe
        driver.switchTo().frame("leftFrame");//切换到某个frame：
        driver.switchTo().window("windowName");//切换到某个window
        // 调用Java Script
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("JS脚本");
        // 超时设置 (单位：秒）
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);//识别元素时的超时时间
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);//页面加载时的超时时间
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);//异步脚本的超时时间



        // 鼠标模拟
        // 左击实现（和元素的click类似）
        Actions action = new Actions(driver);
        WebElement test1item = driver.findElement(By.xpath("//*[@id='list']/li[1]/div[2]/span[2]"));
        action.click(test1item).perform();
        TimeUnit.SECONDS.sleep(5); //MILLISECONDS 毫秒;SECONDS 秒;MINUTES 分钟
        // 双击实现
        new Actions(driver).doubleClick(driver.findElement(By.xpath("//*[@id='list']/li[1]/div[2]/span[2]"))).perform();
        TimeUnit.SECONDS.sleep(5);
        // 悬停 到更多按钮实现
        new Actions(driver).moveToElement(driver.findElement(By.xpath("//*[@id='topPanel']/ul/li[3]/a"))).perform();
        TimeUnit.SECONDS.sleep(5);
        // 拖动实现
        driver.findElement(By.xpath("//*[@id='tbPic']")).click();
        WebElement begin = driver.findElement(By.xpath("//*[@id='list']/li[1]/div[2]/span[1]"));
        WebElement end = driver.findElement(By.xpath("//*[@id='list']/li[2]/div[2]/span[1]"));
        new Actions(driver).dragAndDrop(begin, end).perform();
        TimeUnit.SECONDS.sleep(5);
        // 右击实现
        // 这里虽然使用的是元素任然是test1item，但是页面刷新过后需要重新定位
        // 参考http://docs.seleniumhq.org/exceptions/stale_element_reference.jsp
        driver.findElement(By.xpath("//*[@id='tbText']")).click();
        new Actions(driver).contextClick(driver.findElement(By.xpath("//*[@id='list']/li[1]/div[2]/span[2]"))).perform();
        TimeUnit.SECONDS.sleep(5);





        driver.quit();//断掉http会话
        assert "搜狐" == driver.getTitle();//#断言标题是否正确
        driver.close();// 关闭当前浏览器打开窗口
    }

    /**
     * 简单使用（截屏）
     */
    @Test
    public void shouldAnswerWithTrue() throws Exception
    {

        String savePath = "D:\\data\\";
        WebDriver driver = null;
        //System.setProperty("webdriver.ie.driver", "\\drivers\\IEDriverServer.exe");
        // 谷歌
        //System.setProperty("webdriver.chrome.driver", "\\drivers\\chromedriver.exe");
        //ChromeOptions options = new ChromeOptions();
        //options.setHeadless(true);//在后台打开浏览器
        //driver = new ChromeDriver(options);//InternetExplorerDriver();//ChromeDriver();//FirefoxDriver();

        System.setProperty("webdriver.gecko.driver", "\\drivers\\geckodriver.exe");
        FirefoxOptions options = new FirefoxOptions();
        //options.setHeadless(true);
        //options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");    //导入firefox安装路径
        driver = new FirefoxDriver(options);

        driver.get("https://www.baidu.com");

        Dimension newPage = new Dimension(1024,768);
        //driver.manage().window().maximize();//.fullscreen();
        driver.manage().window().setSize(newPage);

        Dimension pageSize = driver.manage().window().getSize();
        Point pagePoint = driver.manage().window().getPosition();
        driver.manage().window().getPosition().getX();
        System.out.println("pageSize.getWidth = "+pageSize.getWidth()+"  pageSize.getHeight="+pageSize.getHeight());
        System.out.println("pagePoint.getX() = "+pagePoint.getY()+"  pageSize.getHeight="+pagePoint.getY());

        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.findElement(By.name("wd")).sendKeys("Java自动化测试");
        TimeUnit.SECONDS.sleep(5); //MILLISECONDS 毫秒;SECONDS 秒;MINUTES 分钟
        WebElement btn = driver.findElement(By.id("su"));
        btn.click();
        // 使用style属性突出显示元素
        js.executeScript("arguments[0].style.border='3px solid red'", btn);
        Thread.sleep(3000);


        // document.body.offsetWidth (网页可见区域宽 包括边线的宽) 高度同理
        String clientHeight = js.executeScript("return  document.body.clientHeight").toString();
        String clientWidth = js.executeScript("return  document.body.clientWidth").toString();
        //网页正文全文宽
        String scrollWidth = js.executeScript("return  document.body.scrollWidth").toString();
        String scrollHeight = js.executeScript("var q=document.body.scrollHeight ;return(q)").toString();

        //Thread.sleep(3000);
        // 网页被卷去的高 和 左
        String scrollTop = js.executeScript("return  document.body.scrollTop").toString();
        String scrollLeft = js.executeScript("return  document.body.scrollLeft").toString();
        // 屏幕分辨率的高 宽
        String height = js.executeScript("return window.screen.height").toString();
        String width = js.executeScript("return  window.screen.width").toString();
        //屏幕可用工作区高度  宽度
        String availHeight = js.executeScript("return window.screen.availHeight").toString();
        String availWidth = js.executeScript("return  window.screen.availWidth").toString();

        System.out.println("网页可见区域高="+clientHeight+" 网页可见区域宽="+clientWidth);
        System.out.println("网页正文全文高="+scrollHeight+" 网页正文全文宽="+scrollWidth);
        System.out.println("网页被卷去的高="+scrollTop+" 网页被卷去的左="+scrollLeft);
        System.out.println("屏幕分辨率的高="+height+" 屏幕分辨率的宽="+width);


        //截图到output
        //File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);// OutputType.FILE 返回File；OutputType.BYTES返回 byte[]
        // BASE64 截图
        //String scrFileStr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        //File scrFile = OutputType.FILE.convertFromBase64Png(scrFileStr);
        //复制内容到指定文件中
        //FileUtils.copyFile(scrFile, new File(savePath+System.currentTimeMillis()+"_screenshot.png"));

        int pageSizeHeight = pageSize.getHeight();
        int PageNum = (int) Math.ceil(Double.valueOf(scrollHeight)/pageSizeHeight);
        int juanTop = 0; // 往上面卷多少
        //创建BufferedImage
        BufferedImage[] buffImages = new BufferedImage[PageNum];
        List<Integer[]> fileInfos = new ArrayList<>();
        int chunkWidth = 0;
        int chunkHeight = 0;
        int type = 0;


        for(int i=0;i<PageNum;i++){
            if(i>0 && i<PageNum-1){
                juanTop = (pageSizeHeight*i);
            }else if(i ==PageNum-1 ){
                juanTop = (Integer.valueOf(scrollHeight)-pageSizeHeight);
            }
            js.executeScript("document.documentElement.scrollTop=arguments[0]",juanTop);
            Thread.sleep(1000);
            // 二进制截图
            byte[] scrBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            File imgFile = new File(savePath+System.currentTimeMillis()+"_screenshot.png");
            // convert the bytes to image using ImageIo class
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(scrBytes));
            //img.getSubimage(0, 0, 10, 10);//裁剪
            /*Integer[] fileInfo = {img.getType(),img.getWidth(),img.getHeight()};
            fileInfos.add(fileInfo);*/

            // write the image to the image file we have create
            ImageIO.write(img, "png", imgFile);
            buffImages[i]=img;
            chunkWidth=img.getWidth();
            chunkHeight+=img.getHeight();
            type = img.getType();
        }
        Thread.sleep(1000);
        js.executeScript("document.documentElement.scrollTop=arguments[0]",0);
        //设置拼接后图的大小和类型
        BufferedImage finalImg = new BufferedImage(chunkWidth, chunkHeight, type);// type 为颜色的BufferedImage.TYPE_INT_RGB

        int subChunkHeight = 0;
        int size = buffImages.length;
        int x = 0;
        int y = 0;

        Graphics g = finalImg.createGraphics();
        for(int j =0 ;j< size;j++){

            BufferedImage obj = buffImages[j];

            if(j>1 ){
                y += subChunkHeight;
            }
            // 最后一张裁剪
            if(j==size-1){
                // x - 指定矩形区域左上角的 X 坐标(X 为横线上的点)  y - 指定矩形区域左上角的 Y 坐标(Y坐标为竖线上的点)  w - 指定矩形区域的宽度 h - 指定矩形区域的高度
                // obj =  obj.getSubimage(0,subChunkHeight/2, chunkWidth,subChunkHeight/2); // 从上往下裁剪，剪掉下部分
                obj =  obj.getSubimage(0,0, chunkWidth,subChunkHeight/2); // 剪掉下部分
                //ImageUtil.crop(obj,)
            }
            subChunkHeight = obj.getHeight();
            g.drawImage(obj, x, y, chunkWidth, subChunkHeight, null);

            if(j==0){
                y=subChunkHeight;
            }
        }
        //输出拼接后的图像
        ImageIO.write(finalImg, "png", new File("d:\\data\\all.png"));
        ImageIO.createImageInputStream(finalImg);
        g.dispose();

        //TimeUnit.SECONDS.sleep(5);
        //FileCopyUtils.copy();
        //js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        driver.close();
    }

}
