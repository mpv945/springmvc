package org.haijun.study.controller;

/*MultipartResolver作为spring 上传的接口，下面有个实现，一个依赖apacth，一个需要servlet3 以上支持*/

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/io")
public class IoController {

    // 页面定义
/*<form:form action="/create/upload" enctype="multipart/form-data">
    <input type="file" name="mfile" id="img" /><br>
    <input type="file" name="mfile"  id="img2"/>
    <%--<img src="#" id="ser" >--%>
    <input type="submit" value="上传图片" />
</form:form>*/
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String upload(@RequestParam (value = "desc") String desc,
                         @RequestParam("files")MultipartFile[] mfile,
                         HttpServletRequest request)throws IOException {//多文件使用数组
        if (mfile !=null && mfile.length>0) {
            for (int i = 0;i<mfile.length;i++){
                long start = System.currentTimeMillis();
                System.out.println("-------------------------------------------------");
                System.out.println("获取文件流"+mfile[i].getInputStream());
                System.out.println("获取文件的字节大小【byte】"+mfile[i].getSize());
                System.out.println("文件类型"+mfile[i].getContentType());
                System.out.println("获取文件数据"+mfile[i].getBytes());
                System.out.println("文件名字"+mfile[i].getName());
                System.out.println("获取上传文件的原名"+mfile[i].getOriginalFilename());

                System.out.println("-------------------------------------------------");

                try {
                    String filePath = request.getSession().getServletContext()
                            .getRealPath("/") + "assets/" +start+ mfile[i].getOriginalFilename();

                    //转存文件

                    mfile[i].transferTo(new File(filePath));
                }catch (Exception e){
                    e.printStackTrace();
                }

                //mfile[i].transferTo(new File("D:/ideas/"+mfile[i].getOriginalFilename()+ mfile[i].getOriginalFilename().substring(
                //   mfile[i].getOriginalFilename().lastIndexOf("."))));
                // System.out.println(mfile.getOriginalFilename());
                //  mfile[i].transferTo(new File("/assets" + mfile[i].getOriginalFilename()));


            } return "cg";

        } else {
            System.out.println("失败");
            return "sb";
        }
    }

}
