package org.haijun.study.utils.word;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Html2Word {

    public static boolean writeWordFile() {
        boolean w = false;
        String path = "D:\\data\\";
        try {
            if (!"".equals(path)) {
                // 检查目录是否存在
                File fileDir = new File(path);
                if (fileDir.exists()) {
                    // 生成临时文件名称
                    String fileName = "html2word.docx";
                    /*String content = "<html>" +
                            "<head>你好</head>" +
                            "<body>" +
                            "<table>" +
                            "<tr>" +
                            "<td>信息1</td>" +
                            "<td>信息2</td>" +
                            "<td>t3</td>" +
                            "<tr>" +
                            "</table>" +
                            "</body>" +
                            "</html>";
                    byte b[] = content.getBytes();*/
                    URL url = new URL("file:///C:/Users/zhenx/Downloads/人才招聘求职网-前程无忧%20_%2051job%20简历分析.html");
                    URLConnection context = url.openConnection();
                    String encodeing = context.getContentEncoding();
                    if(encodeing==null){
                        encodeing = "UTF-8";
                    }
                    InputStream in = context.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, encodeing));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while((line = br.readLine()) != null) {
                        sb.append(line);
                    }


                    ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes(encodeing));
                    POIFSFileSystem poifs = new POIFSFileSystem();
                    DirectoryEntry directory = poifs.getRoot();
                    DocumentEntry documentEntry = directory.createDocument("WordDocument",url.openStream() );
                    FileOutputStream ostream = new FileOutputStream(path+ fileName);
                    poifs.writeFilesystem(ostream);
                    bais.close();
                    ostream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w;
    }

    public static void main(String[] args) {
        writeWordFile();
    }
}
