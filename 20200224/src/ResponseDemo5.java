import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/map2")
public class ResponseDemo5 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        int smeg=Integer.parseInt(req.getParameter("tang"));
        String w3c=null;
//        Integer smeg=Integer.parseInt(req.getParameter("filename"));
//        String w3c=req.getParameter("filename1");
        String ecu_url=null;
// String w3c_name=w3c+".bin";
//        System.out.println(smeg);
//        System.out.println(w3c);

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/haoyun";
            connection = DriverManager.getConnection(url, "root", "159459");
            statement = connection.createStatement();
            String sql = "select id,ecu_name,file_name,time,file_url from "+Mysql.getCar_name()+" where id="+smeg;
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()){
//                int a = resultSet.getInt("id");System.out.println(a);
//                String name=resultSet.getString("file_url");  System.out.println(baidu);
               // int  id = resultSet.getInt("id");
                ecu_url = resultSet.getString("file_url");
                w3c=resultSet.getString("file_name");
//                Date time = resultSet.getDate("time");
//                fileNameMap1.put(id,name);
            }
            File file = new File(ecu_url);
            //如果文件不存在
            if (!file.exists()) {
                req.setAttribute("message", "您要下载的资源已被删除！！");
                req.getRequestDispatcher("/message.jsp").forward(req, resp);
                return;
            }
            resp.setHeader("content-disposition", "attachment;filename=" + w3c);
            resp.setContentType("application/zip");
            //读取要下载的文件，保存到文件输入流
            FileInputStream in = new FileInputStream(ecu_url);
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
            System.out.println("121212");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {


                if (statement != null) {
                    statement.close();
                    resultSet = null;
                }

                if (connection != null) {
                    connection.close();
                    connection = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    }
//       String M= (String) req.getSession().getAttribute("testSession");
//        System.out.println(M);
//
//        //获取上传文件的目录
//        String uploadFilePath = this.getServletContext().getRealPath("/WEB-INF/upload");
//        //存储要下载的文件名
//        Map<String,String> fileNameMap = new HashMap<String,String>();
//        //递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
//        listfile(new File(uploadFilePath),fileNameMap);//File既可以代表一个文件也可以代表一个目录
//        //将Map集合发送到listfile.jsp页面进行显示
//        req.setAttribute("fileNameMap", fileNameMap);
//        req.getRequestDispatcher("/listfile.jsp").forward(req, resp);



    public void listfile(File file, Map<String, String> map) {
        //如果file代表的不是一个文件，而是一个目录
        if (!file.isFile()) {
            //列出该目录下的所有文件和目录
            File files[] = file.listFiles();
            //遍历files[]数组
            for (File f : files) {
                //递归
                listfile(f, map);
            }
        } else {
            /**
                          * 处理文件名，上传后的文件是以uuid_文件名的形式去重新命名的，去除文件名的uuid_部分
                             file.getName().indexOf("_")检索字符串中第一次出现"_"字符的位置，如果文件名类似于：9349249849-88343-8344_阿_凡_达.avi
                             那么file.getName().substring(file.getName().indexOf("_")+1)处理之后就可以得到阿_凡_达.avi部分
                          */
            String realName = file.getName().substring(file.getName().indexOf("_") + 1);
            //file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
            map.put(file.getName(), realName);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
