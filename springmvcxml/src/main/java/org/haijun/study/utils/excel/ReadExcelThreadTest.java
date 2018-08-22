package org.haijun.study.utils.excel;

import com.alibaba.fastjson.JSON;

/**
 * 模拟多线程读取excel文件
 */
public class ReadExcelThreadTest implements Runnable {

    private Enum[] keys;

    private String filePath;

    public ReadExcelThreadTest(Enum[] keys,String filePath){
        this.keys = keys;
        this.filePath = filePath;
    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        SimpleSheetContentsHandler handler = new SimpleSheetContentsHandler(this.keys,true);
        ExcelSaxUtils example = ExcelSaxUtils.getInstance(handler);
        //ExampleEventUserModel example = new ExampleEventUserModel(handler);
        try {
            String threadName = Thread.currentThread().getName();
            example.processAllSheets(this.filePath);
            handler.getData().forEach(
                    item -> {

                        System.out.println("threadName="+threadName+"："+JSON.toJSONString(item));
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
