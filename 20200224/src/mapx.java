import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/mapx")
public class mapx extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Map<Integer,String> fileNameMap1 = new HashMap<Integer, String>();
        Map<Integer,String> fileNameMap1 = new LinkedHashMap<Integer, String>();
//        Map<Integer,String> fileNameMap9 = new HashMap<Integer, String>();
////        req.setAttribute("fileNameMap9",fileNameMap9);
////        fileNameMap9.put(10,"zhang");
////        fileNameMap9.put(11,"wang");   LinkedHashMap
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        ////////////////////////////////////////
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/haoyun?useUnicode=true&characterEncoding=utf-8";
            connection = DriverManager.getConnection(url, "root", "159459");
            statement = connection.createStatement();
           // String sql = "select * from ecu_file order by time desc ";
            String sql = "select * from "+Mysql.getCar_name()+" order by time desc ";
            resultSet = statement.executeQuery(sql);
            List <EcuFileInfo> list_ecu_info =new ArrayList<>();
            //id ecu_name,time,version,file_url,file_name,for_check,verified,verifyfail,description
            while(resultSet.next()){
                EcuFileInfo ecufileinfo=new EcuFileInfo();

//                int a = resultSet.getInt("id");System.out.println(a);
//                String name=resultSet.getString("file_url");  System.out.println(baidu);
                int  list_id = resultSet.getInt("id");
                String list_ecu_name = resultSet.getString("ecu_name");
                Date list_time = resultSet.getDate("time");
                String list_version=resultSet.getString("version");
                String list_file_url=resultSet.getString("file_url");
                String list_file_name=resultSet.getString("file_name");
                String list_for_check=resultSet.getString("for_check");
                short list_verified=resultSet.getShort("verified");
                short list_verifyfail=resultSet.getShort("verifyfail");
                String list_description=resultSet.getString("description");
                String user=resultSet.getString("user");
             // fileNameMap1.put(id,name);
                ecufileinfo.setId(list_id);
                ecufileinfo.setEcu_name(list_ecu_name);
                ecufileinfo.setTime(list_time);
                ecufileinfo.setVersion(list_version);
                ecufileinfo.setFile_url(list_file_url);
                ecufileinfo.setFile_name(list_file_name);
                ecufileinfo.setFor_check(list_for_check);
                ecufileinfo.setVerified(list_verified);
                ecufileinfo.setDescription(list_description);
                ecufileinfo.setUser(user);
                list_ecu_info.add(ecufileinfo);


            }
            ObjectMapper mapper =new ObjectMapper();
            mapper.writeValue(resp.getWriter(),list_ecu_info);
//            req.setAttribute("fileNameMap", list_ecu_info);
//            req.getRequestDispatcher("/listfile.jsp").forward(req, resp);
//            System.out.println("121212");

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


        ////////////////////////////////////////
//        //获取上传文件的目录
//        String uploadFilePath = this.getServletContext().getRealPath("/WEB-INF/upload");
//        //存储要下载的文件名
//        Map<String,String> fileNameMap = new HashMap<String,String>();
//        //递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
//        listfile(new File(uploadFilePath),fileNameMap);//File既可以代表一个文件也可以代表一个目录
//        //将Map集合发送到listfile.jsp页面进行显示
//        req.setAttribute("fileNameMap", fileNameMap);
//        req.getRequestDispatcher("/listfile.jsp").forward(req, resp);
//
//    }
//
//    public void listfile(File file, Map<String, String> map) {
//        //如果file代表的不是一个文件，而是一个目录
//        if (!file.isFile()) {
//            //列出该目录下的所有文件和目录
//            File files[] = file.listFiles();
//            //遍历files[]数组
//            for (File f : files) {
//                //递归
//                listfile(f, map);
//            }
//        } else {
//            /**
//                          * 处理文件名，上传后的文件是以uuid_文件名的形式去重新命名的，去除文件名的uuid_部分
//                             file.getName().indexOf("_")检索字符串中第一次出现"_"字符的位置，如果文件名类似于：9349249849-88343-8344_阿_凡_达.avi
//                             那么file.getName().substring(file.getName().indexOf("_")+1)处理之后就可以得到阿_凡_达.avi部分
//                          */
//            String realName = file.getName().substring(file.getName().indexOf("_") + 1);
//            //file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
//            map.put(file.getName(), realName);
//        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
