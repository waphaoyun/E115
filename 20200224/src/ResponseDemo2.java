import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/downloadServlet")
public class ResponseDemo2 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //得到要下载的文件名
        String fileName = req.getParameter("filename"); //23239283-92489-阿凡达.avi
        fileName = new String(fileName.getBytes("iso8859-1"), "UTF-8");
        //上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
        String fileSaveRootPath = this.getServletContext().getRealPath("/WEB-INF/upload");
        //通过文件名找出文件的所在目录
        String path = fileSaveRootPath;
        //得到要下载的文件
        File file = new File(fileSaveRootPath + "/" + fileName);
        //如果文件不存在
        if (!file.exists()) {
            req.setAttribute("message", "您要下载的资源已被删除！！");
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        }
        //处理文件名
        String realname = fileName.substring(fileName.indexOf("_") + 1);
        //设置响应头，控制浏览器下载该文件
        resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        //读取要下载的文件，保存到文件输入流
        FileInputStream in = new FileInputStream(fileSaveRootPath + "/" + fileName);
        //创建输出流
        OutputStream out = resp.getOutputStream();
        //创建缓冲区
        byte buffer[] = new byte[1024*1024*20];
        int len = 0;
        //循环将输入流中的内容读取到缓冲区当中
        while ((len = in.read(buffer)) > 0) {
            //输出缓冲区的内容到浏览器，实现文件下载
            out.write(buffer, 0, len);
        }
        //关闭文件输入流
        in.close();
        //关闭输出流
        out.close();
    }

    public String findFileSavePathByFileName(String filename, String saveRootPath) {
        int hashcode = filename.hashCode();
        int dir1 = hashcode & 0xf; //0--15
        int dir2 = (hashcode & 0xf0) >> 4; //0-15
        String dir = saveRootPath + "/" + dir1 + "/" + dir2; //upload\2\3  upload\3\5
        File file = new File(dir);
        if (!file.exists()) {
            //创建目录
            file.mkdirs();
        }
        return dir;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
