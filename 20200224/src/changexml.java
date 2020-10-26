import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

//import static com.sun.deploy.ref.Helpers.compress;

@WebServlet("/changexml")
public class changexml extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取上传文件的目录
        String uploadFilePath = this.getServletContext().getRealPath("/WEB-INF/XML");
        int uid = Integer.parseInt(req.getParameter("xtest"));
        //存储要下载的文件名
//        String version=req.getParameter("verison");
        String ecu_name=null;
        String file_for_del=null;
        String oldversion=null;
        String oldfilename=null;
        List<String >  fileforyasuo = new ArrayList<>();
        String list_file_url=null;
        ////////////////////////////////////////
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/haoyun";
            connection = DriverManager.getConnection(url, "root", "159459");
            statement = connection.createStatement();
            //String sql_searchpath = "select * from ecu_file where id= "+uid;
            String sql_searchpath = "select * from "+ Mysql.getCar_name()  +"  where id= "+uid;
            resultSet = statement.executeQuery(sql_searchpath);
            if (resultSet.next()){
                 list_file_url=resultSet.getString("file_url");
                ecu_name=resultSet.getString("ecu_name");
                oldversion=resultSet.getString("version");
                oldfilename=resultSet.getString("file_name");
                int index=  list_file_url.lastIndexOf("/");
                 file_for_del=list_file_url.substring(0,index);
                File file = new File(list_file_url);
                delAllFile(file);
//                System.out.println("");
//                System.out.println("");
            }
//            String sql = "delete from ecu_file  where id= "+uid;
//            statement.execute(sql);


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


       // String file_ecu_path=file_for_del+"/install_"+ecu_name+".xml";
        String file_ecu_path=file_for_del+"/install.xml";
       String verison = req.getParameter("verison");
        OutputFormat format = OutputFormat.createPrettyPrint(); //设置XML文档输出格式
        format.setEncoding("GB2312"); //设置XML文档的编码类型
        format.setSuppressDeclaration(true);
        format.setIndent(true); //设置是否缩进
        format.setIndent("    "); //以空格方式实现缩进
        // format.setNewlines(true); //设置是否换行
        format.setLineSeparator("\r\n");
        try {
            Document document = null;
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(file_ecu_path)); // 读取XML文件,获得document对象

            Element root = document.getRootElement();

            Element element_verison = root.element("target_version");
            element_verison.setText(verison);
            XMLWriter writer_verson = new XMLWriter(new FileOutputStream(file_ecu_path), format);


            writer_verson.write(document);

            writer_verson.close();


        } catch (DocumentException e) {
            e.printStackTrace();
        }
        File file = new File(file_for_del);
        File[] files = file.listFiles();
for (int i=0;i<files.length;i++)
{
    if(files[i].toString().contains(".s19")||files[i].toString().contains(".hex")){


    }
    else {
        fileforyasuo.add(files[i].toString());
    }
}
String s;String s1;String s4;String s2;
       int ms = compress(fileforyasuo, list_file_url,false);
        // int ms = compress(newfilepath, zipTempFilePath,false);
     System.out.println("成功压缩"+ms+"个文件");


//String file_name=oldfilename.replace(oldversion,verison);

int first=oldfilename.lastIndexOf("(")+1;
int lengthx=oldversion.length();
int second=oldfilename.lastIndexOf(")");
String file_name_first=oldfilename.substring(0,first);
String file_name_second=verison;
String file_name_third=oldfilename.substring(second);
        String file_name=file_name_first+file_name_second+file_name_third;
        ////////////////////////////////////////
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/haoyun";
            connection = DriverManager.getConnection(url, "root", "159459");
            statement = connection.createStatement();
            //String sql = "update ecu_file set version= '"+verison+"' "+    ", file_name='"+file_name +"'where id= "+uid;
            String sql = "update "+ Mysql.getCar_name() +" set version= '"+verison+"' "+    ", file_name='"+file_name +"'where id= "+uid;
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



//        String file_ecu_path=uploadFilePath+"/install_"+ecu+".xml";
//        String verison = req.getParameter("verison");
//        OutputFormat format = OutputFormat.createPrettyPrint(); //设置XML文档输出格式
//        format.setEncoding("GB2312"); //设置XML文档的编码类型
//        format.setSuppressDeclaration(true);
//        format.setIndent(true); //设置是否缩进
//        format.setIndent("    "); //以空格方式实现缩进
//        // format.setNewlines(true); //设置是否换行
//        format.setLineSeparator("\r\n");
//        try {
//            Document document = null;
//            SAXReader saxReader = new SAXReader();
//            document = saxReader.read(new File(file_ecu_path)); // 读取XML文件,获得document对象
//
//            Element root = document.getRootElement();
//
//            Element element_verison = root.element("target_version");
//            element_verison.setText(verison);
//            XMLWriter writer_verson = new XMLWriter(new FileOutputStream(file_ecu_path), format);
//
//
//            writer_verson.write(document);
//
//            writer_verson.close();
//
//
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
        req.getRequestDispatcher("/messagex.html").forward(req, resp);
    //  req.getRequestDispatcher("/listfile.jsp").forward(req, resp);

    }



    public static int compress(List<String> filePaths, String zipFilePath, Boolean keepDirStructure) throws IOException{
        byte[] buf = new byte[1024];
        File zipFile = new File(zipFilePath);
        //zip文件不存在，则创建文件，用于压缩
        if(!zipFile.exists())
            zipFile.createNewFile();
        int fileCount = 0;//记录压缩了几个文件？
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            for(int i = 0; i < filePaths.size(); i++){
                String relativePath = filePaths.get(i);
                if(StringUtils.isEmpty(relativePath)){
                    continue;
                }
                File sourceFile = new File(relativePath);//绝对路径找到file
                if(sourceFile == null || !sourceFile.exists()){
                    continue;
                }

                FileInputStream fis = new FileInputStream(sourceFile);
                if(keepDirStructure!=null && keepDirStructure){
                    //保持目录结构
                    zos.putNextEntry(new ZipEntry(relativePath));
                }else{
                    //直接放到压缩包的根目录
                    zos.putNextEntry(new ZipEntry(sourceFile.getName()));
                }
                //System.out.println("压缩当前文件："+sourceFile.getName());
                int len;
                while((len = fis.read(buf)) > 0){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                fis.close();
                fileCount++;
            }
            zos.close();
            //System.out.println("压缩完成");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileCount;
    }
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
