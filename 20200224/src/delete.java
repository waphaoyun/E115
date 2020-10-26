import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.*;

@WebServlet("/del")
public class delete extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        int uid=Integer.parseInt( req.getParameter("tang"));






        ////////////////////////////////////////
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/haoyun";
            connection = DriverManager.getConnection(url, "root", "159459");
            statement = connection.createStatement();
            String sql_searchpath = "select * from "+ Mysql.getCar_name()  +" where id= "+uid;

            resultSet = statement.executeQuery(sql_searchpath);
            if (resultSet.next()){
                String list_file_url=resultSet.getString("file_url");
              int index=  list_file_url.lastIndexOf("/");
              String file_for_del=list_file_url.substring(0,index);
                File file = new File(file_for_del);
                delAllFile(file);
//                System.out.println("");
//                System.out.println("");
            }
          //  String sql = "delete from ecu_file  where id= "+uid;
            String sql = "delete from "+ Mysql.getCar_name()   +"  where id= "+uid;
            statement.execute(sql);


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


    /**
     * 删除文件或文件夹
     * @param directory
     */
    public static void delAllFile(File directory){
        if (!directory.isDirectory()){
            directory.delete();
        } else{
            File [] files = directory.listFiles();

            // 空文件夹
            if (files.length == 0){
                directory.delete();
                System.out.println("删除" + directory.getAbsolutePath());
                return;
            }

            // 删除子文件夹和子文件
            for (File file : files){
                if (file.isDirectory()){
                    delAllFile(file);
                } else {
                    file.delete();
                    System.out.println("删除" + file.getAbsolutePath());
                }
            }

            // 删除文件夹本身
            directory.delete();
            System.out.println("删除" + directory.getAbsolutePath());
        }
    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
