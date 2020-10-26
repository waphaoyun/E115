import com.oreilly.servlet.MultipartRequest;
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
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
//import org.springframework.util.StringUtils;


//@MultipartConfig
@WebServlet("/liu")
public class ljy_new extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("text/html;charset=utf-8");
        String msg=null;
        //     String fileName;
        ////////////////////////////////
        MultipartRequest multi = null;
        String dir = "/Data";
        String dir1 = req.getServletContext().getRealPath(dir);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String path = dir1 + "/" + uuid;
       // String path = dir1 + File.separator + uuid;
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        String saveDirectory = req.getServletContext().getRealPath(uuid);
        // 限制上传之文件大小为 5 MB
        int maxPostSize = 60 * 1024 * 1024;
        //  FileRenameUtil rfrp = new FileRenameUtil();
        multi = new MultipartRequest(req, path, maxPostSize, "utf8");
        Enumeration files = multi.getFileNames();
        String fileName = "";
        String filePath="";
        String newpath=null;
        List<String >  newfilepath= new ArrayList<>();
        List<String >  newfilepathtiaozheng = new ArrayList<>();
        List<String >  fileforyasuo = new ArrayList<>();
        while (files.hasMoreElements()) {
            fileName = (String) files.nextElement();
            System.out.println("FileName============"+fileName);
//用此方法得到上传文件的真正的文件名，这里的fileName指文件输入类型的名称
            filePath = multi.getFilesystemName(fileName);
            System.out.println("FilePath============"+filePath);


            File f = multi.getFile(fileName);
           ;
            if (null != f)

                newfilepath.add(f.toString());
        }
        //int idx=filexianhouwithoutbin.get(i).lastIndexOf("/")+1;
        Collections.reverse(newfilepath);
        String ecu=multi.getParameter("waphaoyun");
    String crc=null;

        String version=multi.getParameter("verison");
        String crc1=multi.getParameter("crc1");
        String crc2=multi.getParameter("crc2");
        String crc3=multi.getParameter("crc3");
        if(("".equals(version)))//||("".equals(crc)
        {
            msg="请填写 verison版本！";

            req.setAttribute("msg",msg);
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        }
        if(ecu.equals(""))
        {
            msg="请选择ECU";

            req.setAttribute("msg",msg);
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        }
//        List<String>   fileNamexx= new ArrayList<>();
        List<String> filenamelist = new ArrayList<>();
        List<String> realfilenamelist = new ArrayList<>();
//        List<String> filexianhou = new ArrayList<>();
//        List<String> filexianhouwithoutbin = new ArrayList<>();
//        List<String> file_name_forsql = new ArrayList<>();
        List<String> file_s19_hex = new ArrayList<>();
//        List<String> liststring = new ArrayList<>();
        //  String path = "/usr/local/tomcat/webapps/shop/img/";
        findFileList(new File(path), filenamelist);
        if(filenamelist.size()==0)
        {
            msg="没有上传文件！";

            req.setAttribute("msg",msg);
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        }
        String s=filenamelist.get(0);

        for (int i = 0; i < filenamelist.size(); i++) {
            int lastindex=filenamelist.get(i).lastIndexOf(".")+1;
            String suffix=filenamelist.get(i).substring(lastindex);
//            if (filenamelist.get(i).contains("xml"))  {
//                realfilenamelist.add(filenamelist.get(i));
//            }
            if (filenamelist.get(i).toLowerCase().contains("s19"))  {
                realfilenamelist.add(filenamelist.get(i));
            }
            if (filenamelist.get(i).toLowerCase().contains("hex"))  {
                realfilenamelist.add(filenamelist.get(i));
            }
        }
//        File tempFile =new File( fName.trim());
//
//        String fileName = tempFile.getName();

       // String ecu_name="install_"+ecu+".xml";
        String ecu_name="install_"+ecu+".xml";
        String ecu_namex="install.xml";
        if (realfilenamelist.size() == 0) {
            msg="请上传hex或s19文件，未找到有效文件";

            req.setAttribute("msg",msg);
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;

        }



        String xml_dir = "/WEB-INF/XML";
        String xml_dir_real = req.getServletContext().getRealPath(xml_dir);
        String file_ecu_path=xml_dir_real+"/"+ecu_name;
       // String file_ecu_path1 = req.getServletContext().getRealPath(ecu_name);
//if(true){
//    System.out.println("d");
//}
       // File xmlfile =new File(file_ecu_path);
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


            /////////////////

            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Element el = (Element) i.next();
                if (el.elementTextTrim("block")!=null) {
                   Element block=el.element("block");
                    el.remove(block);
                }


            }
            ////////////////

//            for (Iterator i = root.elementIterator(); i.hasNext();) {
//                Element el = (Element) i.next();
//                if (el.elementTextTrim("checksum")!=null) {
//                  Element m= el.element("checksum");
//                  m.setText(crc1);
//                }
//
//
//            }

            XMLWriter writer = new XMLWriter(new FileOutputStream(path+"/"+ecu_namex),format);



            writer.write(document);

            writer.close();

//            for (Iterator i = root.elementIterator(); i.hasNext();) {
//                Element el = (Element) i.next();
//                if (el.elementTextTrim("filename")!=null) {
//                    int index=el.elementTextTrim("filename").lastIndexOf(".");
//                    String name=el.elementTextTrim("filename").substring(0,index).toLowerCase();
//                    file_s19_hex.add((el.elementTextTrim("filename")));
//                    filexianhou.add(name);
////                   System.out.println("dsdfsdf");
//                }
//
//                XMLWriter writer = new XMLWriter(new FileOutputStream(path+"/"+ecu_name),format);
//
//
//
//                writer.write(document);
//
//                writer.close();
//            }

//            if (realfilenamelist.size()!=filexianhou.size())
//            {
//                msg="原文件数量不足,与XML文件中要求的数量不符,如果已经全部上传，查看文件与下拉列表中的ECU是否一致";
//                req.setAttribute("msg",msg);
//                req.getRequestDispatcher("/message.jsp").forward(req, resp);
//                return;
//            }

//            for (int i = 0; i < realfilenamelist.size(); i++) {
//             fileNamexx.add(realfilenamelist.get(i).substring(realfilenamelist.get(i).lastIndexOf("/")+1));}
//            int count=0;
//            for (int i = 0; i < fileNamexx.size(); i++) {
//                for (int j = 0; j <filexianhou.size() ; j++) {
//                    if (true==fileNamexx.get(i).toLowerCase().contains(filexianhou.get(j))) {
//                        count++;
//                    }
//                }
//            }
//            if (realfilenamelist.size()!=count)
//            {
//                msg="上传文件与XML文件不匹配";//+realfilenamelist.toString() + "_______________________"+filexianhou.toString()+realfilenamelist.size()+"dddddddddddd"+count+filexian;
//                req.setAttribute("msg",msg);
//                req.getRequestDispatcher("/message.jsp").forward(req, resp);
//                return;
//            }
//            for (int i = 0; i < filexianhou.size() ; i++) {
//                for (int j = 0; j < realfilenamelist.size(); j++) {
//                    if (true==realfilenamelist.get(j).toLowerCase().contains(filexianhou.get(i))) {
//
//                        filexianhouwithoutbin.add(realfilenamelist.get(j));
//                    }
//                }
//            }
//            for (int i = 0; i <filexianhouwithoutbin.size() ; i++) {
////                int index=el.elementTextTrim("filename").lastIndexOf(".");
////                String name=el.elementTextTrim("filename").substring(0,index).toLowerCase();
//                int idx=filexianhouwithoutbin.get(i).lastIndexOf("/")+1;
//                String name=filexianhouwithoutbin.get(i).substring(idx);
//                file_name_forsql.add(name);
//                System.out.println("ddd");
//            }
            Element element_verison=root.element("target_version");
            element_verison.setText(version);
            XMLWriter writer_verson = new XMLWriter(new FileOutputStream(path+"/"+ecu_namex),format);



            writer_verson.write(document);

            writer_verson.close();
            long startTime = System.currentTimeMillis();
            for(int i=0;i<newfilepath.size();i++)
            {
                String filexname=newfilepath.get(i);
                //由//修改成/
                int newindex=filexname.lastIndexOf("\\")+1;
                newpath=newfilepath.get(i).substring(0,newindex);
                int newindexdot=filexname.lastIndexOf(".")+1;
                String newpathbin=newfilepath.get(i).substring(0,newindexdot);
                String newpathbinxml=newpathbin+"xml";
                String newpathbinfile=newpathbin+"bin";
                fileforyasuo.add(newpathbinfile);
//                String filename_chun=newpathbinfile.substring(newindex);
                File tempFile=new File(newpathbinfile.trim());
                String filename_chun=tempFile.getName();
//                String filename_chun=   newpathbinfile.
                //尝试修改
                //
                int hexors19= check_file_postfix(filexname);
                if(i==0){crc=crc1;}
                if(i==1){crc=crc2;}
                if(i==2){crc=crc3;}
                if (hexors19==19){

                    running_s19(newpathbinfile,filexname,path+"/"+ecu_namex,filename_chun,i+1,crc);
                }
                else if(hexors19==16){
                    //running_hex(newpathbinfile,filexname,path+"/"+ecu_name,file_s19_hex.get(i),i+1);
                    running_hex(newpathbinfile,filexname,path+"/"+ecu_namex,filename_chun,i+1,crc);
                }
                else
                {
                    msg="校验错误,不是s19或hex文件";
                    req.setAttribute("msg",msg);
                    req.getRequestDispatcher("/message.jsp").forward(req, resp);
                    return;
                }
//                String a=null;
//                System.out.println("w");
            }
            fileforyasuo.add(path+"/"+ecu_namex);
            newfilepath.add(path+"/"+ecu_namex);
            String zipTempFilePath =path+"/"+  ecu+  ".zip";
//            filexianhouwithoutbin.clear();
//            for (int i=0;i<file_s19_hex.size();i++)
//            {
//                filexianhouwithoutbin.add(path+"/"+file_s19_hex.get(i));
//            }

            /////////////////////////////////
//            Element element_verison=root.element("target_version");
//            element_verison.setText(version);
//            XMLWriter writer = new XMLWriter(new FileOutputStream(path+"/"+ecu_name),format);
//
//
//
//            writer.write(document);
//
//            writer.close();


            //////////////////////////////



//            filexianhouwithoutbin.add(path+"/"+ecu_name);
            int ms = compress(fileforyasuo, zipTempFilePath,false);
           // int ms = compress(newfilepath, zipTempFilePath,false);
            System.out.println("成功压缩"+ms+"个文件");
            long endTime = System.currentTimeMillis();
            System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); //输出程序运行时间
//////////////////////////////////////////////////////////////////////////////







            ///////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////
            Connection connection = null;
            Statement statement = null;
            int resultSet = 0;
            String user_up = ((User)req.getSession().getAttribute("user")).getUsername();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/haoyun?useUnicode=true&characterEncoding=utf-8";
                connection = DriverManager.getConnection(url, "root", "159459");
                statement = connection.createStatement();

               // for (int i = 0; i <filexianhouwithoutbin.size() ; i++) {
                  //  String sql_url=filexianhouwithoutbin.get(i);
                if(ms>=1) {
                    String sql_url=zipTempFilePath;
                 //   String name = file_name_forsql.get(i);

//                    Date dNow=new Date();
//                    SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
//                    Date data=ft.format(dNow);
//                    Timestamp timestamp = new Timestamp(Date agetTime());
                  //  Date data = new Date(System.currentTimeMillis());
                    Date dt = new Date();

                   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
                    String currentTime = sdf.format(dt);
                    //String name = ecu+currentTime+ ".zip";
                    //这里修改
                    String name = ecu+"-verison("+version+")"+currentTime+ ".zip";
                   // String sql = "insert into  ecu_file (ecu_name,time,version,file_url,file_name,for_check,verified,verifyfail,description,user ) values (?,?,?,?,?,?,?,?,?,?)";
                    String sql = "insert into  "+Mysql.getCar_name()+" (ecu_name,time,version,file_url,file_name,for_check,verified,verifyfail,description,user ) values (?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pstmt = connection.prepareStatement(sql);//获得预置对象
                    pstmt.setString(1, ecu);
                    pstmt.setString(2, currentTime);
                    pstmt.setString(3, version);
                    pstmt.setString(4, sql_url);
                    pstmt.setString(5, name);
                    pstmt.setString(6,"Not verified");
                    pstmt.setShort(7,(short) 2);
                    pstmt.setShort(8,(short) 2);
                    pstmt.setString(9,"记录...");
                    pstmt.setString(10, user_up);


//                    resultSet = statement.executeUpdate(sql);
                    int res = pstmt.executeUpdate();//执行sql语句
                    if (res > 0) {
                        System.out.println("数据录入成功");
                        req.getRequestDispatcher("/listfile.html").forward(req, resp);
                    }else {
                        System.out.println("数据库执行失败");
                    }
                }
               // }

//                if(resultSet>0){
//                int a = resultSet.getInt("id");System.out.println(a);
//                String baidu=resultSet.getString("file_url");  System.out.println(baidu);
//                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {


                    if (statement != null) {
                        statement.close();
                        resultSet = 0;
                    }

                    if (connection != null) {
                        connection.close();
                        connection = null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }















            /////////////////////////////////////////////////////////////////

//            System.out.println("dsdfsdf");
//            if (true)
//            {
//                System.out.println("dsdfsdf");
//            }
//            System.out.println("dsdfsdf");
        } catch (Exception ex) {
            //ex.printStackTrace();
            msg=ex.getMessage();

            req.setAttribute("msg",msg);
            req.getRequestDispatcher("/message.jsp").forward(req, resp);
            return;
        }
//        System.out.println("dsdfsdf");
//        if (true)
//        {
//
//        }

    }
//1001124658 FAW H7 Shifter V2.s19
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
    public static void findFileList(File dir, List<String> fileNames) {
        if (!dir.exists() || !dir.isDirectory()) {// 判断是否存在目录
            return;
        }
        String[] files = dir.list();// 读取目录下的所有目录文件信息
        for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
            File file = new File(dir, files[i]);
            if (file.isFile()) {// 如果文件
                fileNames.add(dir + "/" + file.getName());// 添加文件全路径名
            } else {// 如果是目录
                findFileList(file, fileNames);// 回调自身继续查询
            }
        }
    }


    private int check_file_postfix(String strFilex) throws IOException
    {
        InputStreamReader in = new InputStreamReader(new FileInputStream(strFilex));
        BufferedReader _rstream = new BufferedReader(in, 60 * 1024 * 1024);
        String line;
        if((line = _rstream.readLine()) != null)
        {
            _rstream.close();
            //string s = line[0];
            if ("S".equals(line.substring (0,1).toString()))
            {
                List<Byte> crc =new  ArrayList<>();
                byte scrc=0;
                for (int i = 0; i < line.length(); i++)
                {
                    if ((i%2==0)&&(i!=0))
                    {//addr = Integer.parseInt(liststring.get(i).substring(8, addr_len*2+8).replaceAll("^0[xX]", ""), 16);
                        //crc.add( byte.Parse(line.Substring(i, 2), System.Globalization.NumberStyles.HexNumber));
                        crc.add( (byte)Integer.parseInt(       line.substring(i, i+2).replaceAll("^0[xX]", ""), 16             ));
                    }
                }
                for (int i = 0; i < crc.size()-1; i++)
                {
                    scrc += crc.get(i);
                }
//                byte num=crc.get(crc.size()-1);
//                byte numb= (byte)(0xff-82);
//                System.out.println("wwww");
//                System.out.println("wwww");

                if ((byte)(0xff-scrc)==crc.get(crc.size()-1))
                {
                    return 19;
                }
                else
                {
                    return 0;
                }

            }
            if (":".equals(line.substring (0,1).toString()))
            {
                List<Byte> crc =new  ArrayList<>();
                byte scrc=0;
                String Linewithout0=line.substring(1);
                for (int i = 0; i < Linewithout0.length(); i++)
                {
                    if ((i%2==0))
                    {//addr = Integer.parseInt(liststring.get(i).substring(8, addr_len*2+8).replaceAll("^0[xX]", ""), 16);
                        //crc.add( byte.Parse(line.Substring(i, 2), System.Globalization.NumberStyles.HexNumber));
                        crc.add( (byte)Integer.parseInt(      Linewithout0.substring(i, i+2).replaceAll("^0[xX]", ""), 16             ));
                    }
                }
                for (int i = 0; i < crc.size()-1; i++)
                {
                    scrc += crc.get(i);
                }
                if ((byte)(0xff&  (0x100-scrc))==crc.get(crc.size()-1))
                {
                    return 16;
                }
//                return 16;
            }
            else
            {
                return 0;
            }
        }
        return 0;




    }




    enum  MyEnum
    {
        Status_data ,
        Status_addr,
        Status_end,
        Status_zerothree,
        Status_S2
    }
    private void running_hex(String bin_file_path, String strFilex,String file_ecu_path,String filename, int index,String crc) throws IOException, DocumentException
    {
        boolean NewBlock=false;
//        Map<Long,Long> block_start = new HashMap<Long,Long>();
//        Map<Long,Long> block_len = new HashMap<Long,Long>();
        List <Long>block_start =new ArrayList<>();
        List<Long> block_len =new ArrayList<>();
        List<Byte> listBuffer = new ArrayList<>(); //存储全部flashing升级文件
        MyEnum Status = MyEnum.Status_end;
        MyEnum LastStatus = MyEnum.Status_end;
        boolean isfirst=false;
        List<Long> bufferforend= new ArrayList<>();
        List<Hang> hang = new ArrayList<>();
        List<Long> bufferforfirst=new ArrayList<>();
        List<String> liststring= new ArrayList<>();
        List<String> liststringx= new ArrayList<>();
        InputStreamReader in = new InputStreamReader(new FileInputStream(strFilex));
        BufferedReader _rstream = new BufferedReader(in, 60 * 1024 * 1024);
        long addr = 0;
        long last_line_addr = 0;
        int time=0;
        int addr_len=0;
        int last_line_addr_len=0;
        int last_line_jilulen=0;
        long last_line_address=0;
        String line;
        int last_04_id=0;
        int last_04_index=0;
int indexwidth=0;
        while ((line = _rstream.readLine()) != null)
        {
            liststringx.add(line);
        }
        _rstream.close();
        //public static List<string> liststring = new List<string>(); //存储全部flashing
        for (int i = 0; i < liststringx.size(); i++)
        {
//           String mse= liststringx.get(i).substring(1);
            liststring.add(liststringx.get(i).substring(1));
        }
        // System.IO.StreamWriter sw = new System.IO.StreamWriter(strPath + "Recross.bin", false, System.Text.Encoding.Default);
        // BinaryWriter sw = new BinaryWriter(new FileStream(strPath+"//" + name, FileMode.Create)); //创建文件  bin目录下
//        BinaryWriter sw = new BinaryWriter(new FileStream(bin_file_path, FileMode.Create)); //创建文件  bin目录下

        DataOutputStream sw = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bin_file_path)));
        // FileStream sw = (new FileStream(strPath + "//" + name, FileMode.Create)); //创建文件  bin目录下
        for (int i = 0; i < liststring.size(); i++)
        {
            //if (Convert.ToInt32(liststring[i].Substring(1, 1)) == 9)
            //{
            //    bufferforend.Add(hang[hang.Count - 1].id);



            //    //time = 0;
            //}

//            if (Integer.parseInt(liststring.get(i).substring(6, 2+6)) == 0x05){
//                int c;
//                int m;
//                m=10;
//            }


            if (Integer.parseInt(liststring.get(i).substring(6, 2+6)) == 0x01)
            {
////                if (LastStatus!=MyEnum.Status_zerothree){
//
//
//                bufferforend.add(hang.get(hang.size() - 1).id);//}
//                Status = MyEnum.Status_end;
//                LastStatus = MyEnum.Status_end;
            }
            if (Integer.parseInt(liststring.get(i).substring(6, 2+6)) == 0x03)
            {
//                Status = MyEnum.Status_zerothree;
//                LastStatus = Status;
////                bufferforend.add(hang.get(hang.size() - 1).id);
            }

            if ((Integer.parseInt(liststring.get(i).substring(6,6+2)) == 0x04))
            {
                indexwidth=16;
                last_line_addr=addr;
                addr_len = Integer.parseInt(liststring.get(i).substring(0,2+0).replaceAll("^0[xX]", ""), 16);
                addr = Integer.parseInt(liststring.get(i).substring(8, addr_len*2+8).replaceAll("^0[xX]", ""), 16);
                Status = MyEnum.Status_addr;
//                long last_temp=(last_line_addr<<16)+last_line_address+last_line_jilulen;
//                long now_temp=addr<<16;
//                if (LastStatus==MyEnum.Status_data&&(last_temp!=now_temp)) //458752  ?458752
//                {
//                    bufferforend.add(hang.get(hang.size() - 1).id);
//                    isfirst=true;
//                }
//                if (hang.size()>=1){
//                last_04_id=hang.get(hang.size() - 1).id;
//                last_04_index=i;
//                }


            }
            if ((Integer.parseInt(liststring.get(i).substring(6,6+2)) == 0x02))
            {
                indexwidth=4;
//                last_line_addr=addr;
                addr_len = Integer.parseInt(liststring.get(i).substring(0,2+0).replaceAll("^0[xX]", ""), 16);
                addr = Integer.parseInt(liststring.get(i).substring(8, addr_len*2+8).replaceAll("^0[xX]", ""), 16);
//                Status = MyEnum.Status_S2;

//                }
//                if (hang.size()>=1){
//                    last_04_id=hang.get(hang.size() - 1).id;
//                    last_04_index=i;
//                }
//                LastStatus=Status;

                //addr_len=Convert.ToInt32( liststring[i].Substring(0,2));

                //addr_len = Int32.Parse(liststring[i].Substring(0,2+0), System.Globalization.NumberStyles.HexNumber);
                //addr = Convert.ToInt32( liststring[i].Substring(8, addr_len));
                //  addr = Int64.Parse(liststring[i].Substring(8, addr_len*2+8), System.Globalization.NumberStyles.HexNumber);

            }
            if (Integer.parseInt(liststring.get(i).substring(6,2+6)) == 0x00)
            {
//                Status = MyEnum.Status_data;
//                time++;
                //
               // int jilulen = Integer.parseInt(liststring.get(i).substring(0, 2+0), System.Globalization.NumberStyles.HexNumber);
                int jilulen = Integer.parseInt(liststring.get(i).substring(0, 2+0).replaceAll("^0[xX]", ""), 16);
                // int savepath = 3;

                //Int64 address =  Int32.Parse(liststring.get(i).substring(2, 4+2), System.Globalization.NumberStyles.HexNumber);
                long address = Integer.parseInt(liststring.get(i).substring(2, 4+2).replaceAll("^0[xX]", ""), 16);
                bufferforfirst.add((((addr)) << indexwidth) | address);
                bufferforend.add((((addr)) << indexwidth) | address+jilulen-1);
                //                if (((LastStatus == MyEnum.Status_S2)||(LastStatus == MyEnum.Status_addr))&&time!=1)
////                if (time!=1)
//                {
//
//                    long temp_now=(addr<<indexwidth)+address;
//            long temp_last_line=(last_line_addr<<indexwidth)+last_line_jilulen+last_line_address;
//                    if (temp_now!=temp_last_line)
//                    {
//                        bufferforend.add( last_04_id);
//                        //新添加
//                        isfirst=true;
//                    }
//
//                }
//                if (LastStatus== MyEnum.Status_data)
//                {
//                    if (address-last_line_address!=last_line_jilulen)
//                    {
//                        bufferforend.add(time-2);
//                        //bufferforfirst.add(i);
//                        isfirst=true;
//                    }
//                }
//                Hang h = new Hang(0, false, false, 0, 0,0);
//                if (time==1)
//                //if (LastStatus == MyEnum.Status_addr||time==1)
//                {
//                    isfirst = true;
//                }
//                if (isfirst == true)
//                {
//                    //double whh=32808 *Math.Pow(2,16);
//                    //         Int64 m = 32808;
//                    //       Int64 t = m << 16;
//                    //    double sd = ((addr << 16) | address);
//                    isfirst = false;
//                    h.address = (((addr))<<indexwidth)| address;
//                    h.first = true;
//                    h.end = false;
//                    h.id = time - 1;
//                    h.len = jilulen;
//                    hang.add(h);
//                    bufferforfirst.add(h.id);
//                }
//                else
//                {
//                    h.address = (((addr)) << indexwidth) | address;
//                    h.first = false;
//                    h.end = false;
//                    h.id = time - 1;
//                    h.len = jilulen;
//                    hang.add(h);
//                }
                //if (h.first)
                //{

                //}

                //if (time - 2 >= 0)
                //{
                //    if (hang[time - 1].address - hang[time - 2].address != hang[time - 2].len)
                //    {
                //        bufferforend.Add(h.id - 1);
                //        bufferforfirst.Add(h.id);
                //        hang[h.id].first = true;

                //        // MessageBox.Show("length:" + (hang[time - 2].address-hang[0].address+hang[time-2].len-3-1));
                //    }
                //}
                //if (time - 2 >= 0)
                //{
                //    if (hang[time - 1].address - hang[time - 2].address != hang[time - 2].len)
                //    {
                //        bufferforend.Add(h.id - 1);
                //        bufferforfirst.Add(h.id);
                //        hang[h.id].first = true;

                //        // MessageBox.Show("length:" + (hang[time - 2].address-hang[0].address+hang[time-2].len-3-1));
                //    }
                //}

                String s = liststring.get(i).substring(8, jilulen*2+8);
                for (int j = 0; j < s.length(); j++)
                {
                    if (j % 2 == 0)
                    {
                        String sm = s.substring(j, 2+j).toString();
                        //  listBuffer.Add(sm);
                        ;
                       // byte fds = ((byte)Int32.Parse(sm, System.Globalization.NumberStyles.HexNumber));
                        byte fds = (byte)Integer.parseInt(sm.replaceAll("^0[xX]", ""), 16);

                        listBuffer.add(fds);
                        sw.write(fds);
                    }
                }



//                last_line_address=address;
//                last_line_jilulen=jilulen;
            }
//            LastStatus = Status;

        }
//        for (int i = 0; i < bufferforfirst.size(); i++)
//        {
//            block_start.add(i,hang.get(bufferforfirst.get(i)).address);
//            block_len.add(i, (hang.get((bufferforend.get(i))).address - hang.get(bufferforfirst.get(i)).address + hang.get((bufferforend.get(i))).len));
//        }
        sw.close();
///////////////////////////////////////////////////////////////////////////
        Collections.sort(bufferforfirst);
        Collections.sort(bufferforend);

        int h = 0;

        while(h < bufferforfirst.size()-1) {
            if (bufferforend.get(h) + 1 == bufferforfirst.get(h + 1)) {
                bufferforfirst.set(h, bufferforfirst.get(h));
                bufferforend.set(h, bufferforend.get(h + 1));
                bufferforfirst.remove(h + 1);
                bufferforend.remove(h + 1);
            }
            if(bufferforfirst.size()-1 == h)
            {
                break;
            }
            if(bufferforend.get(h) + 1 != bufferforfirst.get(h+1))
            {
                h++;
            }
        }
        for (int i = 0; i < bufferforfirst.size(); i++){
            block_len.add(bufferforend.get(i)-bufferforfirst.get(i)+1);
        }
        block_start=bufferforfirst;
        ///////////////////////////////////////////////////////////////////

        OutputFormat format= OutputFormat.createPrettyPrint();
        format.setEncoding("GB2312"); //设置XML文档的编码类型
        format.setSuppressDeclaration(true);
        format.setIndent(true); //设置是否缩进
        format.setIndent("    "); //以空格方式实现缩进
        format.setNewlines(true); //设置是否换行
        format.setLineSeparator("\r\n");
        Document document = null;
        SAXReader saxReader = new SAXReader();
        document = saxReader.read(new File(file_ecu_path)); // 读取XML文件,获得document对象

//        Element root = document.getRootElement();
        Element root=document.getRootElement();
        System.out.println(root);
        Element nodex= (Element) root.selectSingleNode("image[@index='"+index +"']/filename");
        nodex.setText(filename);
        System.out.println(nodex.getData());
       // if (crc!=""){
            if (!("".equals(crc))){
            Element nodecrc = (Element) root.selectSingleNode("image[@index='" + index + "']/checksum");

    nodecrc.setText(crc);

}

        Element nodeimage= (Element) root.selectSingleNode("image[@index='"+index +"']");
        for(int ct=0;ct<block_start.size();ct++) {
            Element block = nodeimage.addElement("block");
            block.addAttribute("index", ct+1+"");
            Element start_address= block.addElement("start_address");
            Long l=block_start.get(ct);
            String text=Long.toHexString(l);

            start_address.setText("0x" +  text);
            Element size= block.addElement("size");
            size.setText("0x"+Long.toHexString(block_len.get(ct)));

        }
        System.out.println("x");
        System.out.println("x");
//        for (Iterator i = root.elementIterator(); i.hasNext();) {
//            Element el = (Element)i.next();
//            if (el.elementTextTrim("filename")!=null) {
//
////                if(el.elementTextTrim("filename").equals(filename)){
//                    for(int ct=0;ct<block_start.size();ct++) {
//                        Element block = el.addElement("block");
//                        block.addAttribute("index", 1+ct+"");
//                        Element start_address= block.addElement("start_address");
//                        Long l=block_start.get(ct);
//                        String text=Long.toHexString(l);
//
//                        start_address.setText("0x" +  text);
//                        Element size= block.addElement("size");
//                        size.setText("0x"+Long.toHexString(block_len.get(ct)));
//
//                    }
////               }
//            }
//
//        }
        XMLWriter writer = new XMLWriter(new FileOutputStream(file_ecu_path),format);



        writer.write(document);

        writer.close();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }





    public void running_s19(String bin_file_path,String strFilex,String file_ecu_path,String filename,int index,String crc) throws IOException, DocumentException
    {
//        Map<Long,Long> block_start = new HashMap<Long,Long>();
//        Map<Long,Long> block_len = new HashMap<Long,Long>();
        List<Long> block_start= new ArrayList<>();
        List<Long> block_len= new ArrayList<>();
        List<Byte> listBuffer=new ArrayList<>();
        boolean isfirst=false;
        int savepath=0;
        int time=0;
        String msg=null;
        List<String> liststring= new ArrayList<>();
        List<Long> bufferforend= new ArrayList<>();
        List<Hang> hang = new ArrayList<>();
        List<Long> bufferforfirst=new ArrayList<>();
        try{
            InputStreamReader in = new InputStreamReader(new FileInputStream(strFilex));
            BufferedReader _rstream = new BufferedReader(in, 60 * 1024 * 1024);
            String line=null;
            while ((line = _rstream.readLine())!=null)
            {
                liststring.add(line);
            }
            _rstream.close();
            DataOutputStream sw = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bin_file_path)));
            for (int i = 0; i < liststring.size(); i++)
            {
//                String cd=liststring.get(1);
//                String cdc=cd.substring(1,1+1);
//                String mm=null;
//                mm="dd";
                if (Integer.parseInt(liststring.get(i).substring(1, 1+1)) == 9)
                {
//                    bufferforend.add(hang.get(hang.size() - 1).id);
                }
                if (Integer.parseInt(liststring.get(i).substring(1, 1+1)) == 8)
                {
//                    bufferforend.add(hang.get(hang.size() - 1).id);
                }
                if (Integer.parseInt(liststring.get(i).substring(1, 1+1)) == 7)
                {
//                    bufferforend.add(hang.get(hang.size() - 1).id);
                }
                if (Integer.parseInt(liststring.get(i).substring(1, 1+1)) == 3)
                {
//                    time++;
                    int jilulen = Integer.parseInt(liststring.get(i).substring(2, 4).replaceAll("^0[xX]", ""), 16);
                  //  int jilulen = Integer.parseInt(liststring.get(i).substring(2, 2), System.Globalization.NumberStyles.HexNumber);//这里一会儿更改
                    savepath = 4;
                  //  Int32 address = Integer.parseInt(liststring.get(i).substring(4, savepath * 2), System.Globalization.NumberStyles.HexNumber);
//                    int address= Integer.parseInt(liststring.get(i).substring(4, savepath * 2+4).replaceAll("^0[xX]", ""),16);
                    Long address= Long.parseLong(liststring.get(i).substring(4, savepath * 2+4).replaceAll("^0[xX]", ""),16);
                    //if (time!=1)
                    //{
                    //    //if (address - lastaddress != lastjilulen - 3-1)
                    //    //    MessageBox.Show(address.ToString());

                    //}
//                    Hang h = new Hang(0, false, false, 0, 0,0);
//                    if (time == 1)
//                    {
//                        isfirst = true;
//                    }
//                    if (isfirst == true)
//                    {
//                        isfirst = false;
//                        h.address = address;
//                        h.first = true;
//                        h.end = false;
//                        h.id = time - 1;
//                        h.len = jilulen;
//                        h.savepath=savepath;
//                        hang.add(h);
//                    }
//                    else
//                    {
//                        h.address = address;
//                        h.first = false;
//                        h.end = false;
//                        h.id = time - 1;
//                        h.len = jilulen;
//                        h.savepath=savepath;
//                        hang.add(h);
//                    }
//                    if (h.first)
//                    {
//                        bufferforfirst.add(h.id);
//                    }
bufferforfirst.add(address);
bufferforend.add(address+jilulen-savepath-1-1);
//                    if (time - 2 >= 0)
//                    {
//                        int xtest=hang.get(time - 2).savepath;
//                        if (hang.get(time - 1).address - hang.get(time - 2).address != hang.get(time - 2).len - savepath - 1)
//                        {
//                            bufferforend.add(h.id - 1);
//                            bufferforfirst.add(h.id);
//                            hang.get(h.id).first = true;
//                            //   isfirst = true;
//                            //time = 0;
//                            //for (int k = 0; k < time-2; k++)
//                            //    {
//                            //length=(time-2)*h.len;
//                            //}
//                            // MessageBox.Show("length:" + (hang[time - 2].address-hang[0].address+hang[time-2].len-3-1));
//                        }
//                    }
                    //if (liststring.Count-1==i)
                    //{
                    //    bufferforend.Add(h.id);
                    //}
                    //lastaddress=address;
                    //lastjilulen = jilulen;
                    String s = liststring.get(i).substring(2 + 2 + savepath * 2,2 + 2 + savepath * 2+ jilulen * 2 - 2 - savepath * 2);
                    for (int j = 0; j < s.length(); j++)
                    {
                        if (j % 2 == 0)
                        {
                            String sm = s.substring(j, 2+j).toString();
                            //  listBuffer.Add(sm);
                            // Integer.parseInt(liststring.get(i).substring(2, 2).replaceAll("^0[xX]", ""), 16);
                           // byte fds = ((byte)Integer.parseInt(sm, System.Globalization.NumberStyles.HexNumber));
                            byte fds = ((byte)Integer.parseInt(sm.replaceAll("^0[xX]", ""), 16));
                            listBuffer.add(fds);
                            sw.write(fds);
                        }
                    }
                }





                if (Integer.parseInt(liststring.get(i).substring(1, 1+1)) == 1)
                {
//                    time++;
                    //
                 //   int jilulen = Integer.parseInt(liststring.get(i).substring(2, 2), System.Globalization.NumberStyles.HexNumber);
                    int jilulen =      Integer.parseInt(liststring.get(i).substring(2, 2+2).replaceAll("^0[xX]", ""),16);
                    savepath = 2;
                  //  Int32 address = Int32.Parse(liststring[i].Substring(4, savepath*2), System.Globalization.NumberStyles.HexNumber);
                    Long address=Long.parseLong(liststring.get(i).substring(4, savepath*2+4).replaceAll("^0[xX]", ""),16);
                    //if (time!=1)
                    //{
                    //    //if (address - lastaddress != lastjilulen - 3-1)
                    //    //    MessageBox.Show(address.ToString());

                    //}
//                    Hang h = new Hang(0, false, false, 0, 0,0);
//                    if (time == 1)
//                    {
//                        isfirst = true;
//                    }
//                    if (isfirst == true)
//                    {
//                        isfirst = false;
//                        h.address = address;
//                        h.first = true;
//                        h.end = false;
//                        h.id = time - 1;
//                        h.len = jilulen;
//                        h.savepath=savepath;
//                        hang.add(h);
//                    }
//                    else
//                    {
//                        h.address = address;
//                        h.first = false;
//                        h.end = false;
//                        h.id = time - 1;
//                        h.len = jilulen;
//                        h.savepath=savepath;
//                        hang.add(h);
//                    }
//                    if (h.first)
//                    {
//                        bufferforfirst.add(h.id);
//                    }
//
//                    if (time - 2 >= 0)
//                    {
//                        int xtest=hang.get(time - 2).savepath;
//                        if (hang.get(time - 1).address - hang.get(time - 2).address != hang.get(time - 2).len - savepath - 1)
//                        {
//                            bufferforend.add(h.id - 1);
//                            bufferforfirst.add(h.id);
//                            hang.get(h.id).first = true;
//                            //   isfirst = true;
//                            //time = 0;
//                            //for (int k = 0; k < time-2; k++)
//                            //    {
//                            //length=(time-2)*h.len;
//                            //}
//                            // MessageBox.Show("length:" + (hang[time - 2].address-hang[0].address+hang[time-2].len-3-1));
//                        }
//                    }
                    bufferforfirst.add(address);
                    bufferforend.add(address+jilulen-savepath-1-1);
                    //if (liststring.Count-1==i)
                    //{
                    //    bufferforend.Add(h.id);
                    //}
                    //lastaddress=address;
                    //lastjilulen = jilulen;
                    String s = liststring.get(i).substring(2 + 2 + savepath * 2, jilulen * 2 - 2 - savepath * 2+2 + 2 + savepath * 2);
                    for (int j = 0; j < s.length(); j++)
                    {
                        if (j % 2 == 0)
                        {
                            String sm = s.substring(j, 2+j).toString();
                            //  listBuffer.Add(sm);
                            ;
                         //   byte fds = ((byte)Int32.Parse(sm, System.Globalization.NumberStyles.HexNumber));
                            byte fds = (byte) Integer.parseInt(sm.replaceAll("^0[xX]", ""),16);
                            listBuffer.add(fds);
                            sw.write(fds);
                        }
                    }
                }
                if (Integer.parseInt(liststring.get(i).substring(1, 1+1)) == 2)
                {
                    time++;
                    //
                    int jilulen =  Integer.parseInt(liststring.get(i).substring(2, 2+2).replaceAll("^0[xX]", ""),16);
                   // int jilulen = Int32.Parse(liststring[i].Substring(2, 2), System.Globalization.NumberStyles.HexNumber);
                    savepath = 3;
                    //Int32 address = Int32.Parse(liststring[i].Substring(4, 6), System.Globalization.NumberStyles.HexNumber);
                    //Long address= Long.parseLong
                   // int address=Integer.parseInt(liststring.get(i).substring(4,4+6).replaceAll("^0[xX]", ""),16);
                    Long address= Long.parseLong(liststring.get(i).substring(4,4+6).replaceAll("^0[xX]", ""),16);
                    //if (time!=1)
                    //{
                    //    //if (address - lastaddress != lastjilulen - 3-1)
                    //    //    MessageBox.Show(address.ToString());

                    //}
//                    Hang h = new Hang(0, false, false, 0, 0,0);
//                    if (time == 1)
//                    {
//                        isfirst = true;
//                    }
//                    if (isfirst == true)
//                    {
//                        isfirst = false;
//                        h.address = address;
//                        h.first = true;
//                        h.end = false;
//                        h.id = time - 1;
//                        h.len = jilulen;
//                        h.savepath=savepath;
//                        hang.add(h);
//                    }
//                    else
//                    {
//                        h.address = address;
//                        h.first = false;
//                        h.end = false;
//                        h.id = time - 1;
//                        h.len = jilulen;
//                        h.savepath=savepath;
//                        hang.add(h);
//                    }
//                    if (h.first)
//                    {
//                        bufferforfirst.add(h.id);
//                    }
//
//                    if (time - 2 >= 0)
//                    {
//                        int xtest=hang.get(time - 2).savepath;
//                        if (hang.get(time - 1).address - hang.get(time - 2).address != hang.get(time - 2).len - xtest - 1)
//                        {
//                            bufferforend.add(h.id - 1);
//                            bufferforfirst.add(h.id);
//                            hang.get(h.id).first = true;
//                            //   isfirst = true;
//                            //time = 0;
//                            //for (int k = 0; k < time-2; k++)
//                            //    {
//                            //length=(time-2)*h.len;
//                            //}
//                            // MessageBox.Show("length:" + (hang[time - 2].address-hang[0].address+hang[time-2].len-3-1));
//                        }
//                    }

                    //if (liststring.Count-1==i)
                    //{
                    //    bufferforend.Add(h.id);
                    //}
                    //lastaddress=address;
                    //lastjilulen = jilulen;
                    bufferforfirst.add(address);
                    bufferforend.add(address+jilulen-savepath-1-1);
                    String s = liststring.get(i).substring(2 + 2 + savepath * 2, 2 + 2 + savepath * 2+jilulen * 2 - 2 - savepath * 2);
                    for (int j = 0; j < s.length(); j++)
                    {
                        if (j % 2 == 0)
                        {
                            String sm = s.substring(j, 2+j).toString();
                            //  listBuffer.Add(sm);
                            ;
                         //   byte fds = ((byte)Int32.Parse(sm, System.Globalization.NumberStyles.HexNumber));
                            byte fds=(byte) Integer.parseInt(sm.replaceAll("^0[xX]", ""),16);
                            listBuffer.add(fds);
                            sw.write(fds);
                        }
                    }





                }

            }


///////////////////////////////////////////////////////////////////////////////////test


            Collections.sort(bufferforfirst);
            Collections.sort(bufferforend);

            int h = 0;

            while(h < bufferforfirst.size()-1) {
                if (bufferforend.get(h) + 1 == bufferforfirst.get(h + 1)) {
                    bufferforfirst.set(h, bufferforfirst.get(h));
                    bufferforend.set(h, bufferforend.get(h + 1));
                    bufferforfirst.remove(h + 1);
                    bufferforend.remove(h + 1);
                }
                if(bufferforfirst.size()-1 == h)
                {
                    break;
                }
                if(bufferforend.get(h) + 1 != bufferforfirst.get(h+1))
                {
                    h++;
                }
            }
            for (int i = 0; i < bufferforfirst.size(); i++){
                block_len.add(bufferforend.get(i)-bufferforfirst.get(i)+1);
            }
            block_start=bufferforfirst;






            //////////////////////////////////////////////////////
//            for (int i = 0; i < bufferforfirst.size(); i++)
////            {
////                block_start.add(i,hang.get(bufferforfirst.get(i)).address);
////                block_len.add(i, (hang.get(bufferforend.get(i))).address - hang.get(bufferforfirst.get(i)).address + hang.get(bufferforend.get(i)).len - savepath - 1);
//////                richTextBox1.Text += ("start:" + hang[bufferforfirst[i]].address.ToString("x") + "\r\n");
//////                //MessageBox.Show("块大小:"+(hang[(bufferforend[i])].address - hang[bufferforfirst[i]].address + hang[(bufferforend[i])].len-3-1).ToString("x"));
//////                richTextBox1.Text += ("块大小:" + (hang[(bufferforend[i])].address - hang[bufferforfirst[i]].address + hang[(bufferforend[i])].len - savepath - 1).ToString("x") + "\r\n");
////                /******************************************************************************************************************************************************************
////                 *   public static List<byte> listBuffer = new List<byte>(); //存储全部flashing升级文件
////                 public static List<string> liststring = new List<string>(); //存储全部flashing
////                 public static List<byte> Buffer = new List<byte>(); //存储全部flashing升级文件
////                 public static List<int> bufferforfirst = new List<int>(); //存储全部flashing升级文件
////                 public static List<int> bufferforend = new List<int>(); //存储全部flashing升级文件
////                 public int time = 0;
////                 *   public bool isfirst = false;
////                 public static List<Hang> hang = new List<Hang>(); //存储全部flashing升级文件
////                 *
////                 *
////                 *
////                 * *******************************************************************************************************************************************************************/
////
////            }



            ////////////////////////////////////////////////////////////////////////////////////
            OutputFormat format = OutputFormat.createPrettyPrint(); //设置XML文档输出格式
            format.setEncoding("GB2312"); //设置XML文档的编码类型
            format.setSuppressDeclaration(true);
            format.setIndent(true); //设置是否缩进
            format.setIndent("    "); //以空格方式实现缩进
//            format.setNewlines(true); //设置是否换行
            format.setLineSeparator("\r\n");

            Document document = null;
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(new File(file_ecu_path)); // 读取XML文件,获得document对象
            Element root=document.getRootElement();
            System.out.println(root);
           Element nodex= (Element) root.selectSingleNode("image[@index='"+index +"']/filename");
           nodex.setText(filename);
           System.out.println(nodex.getData());
            if (!("".equals(crc))) {
                Element nodecrc = (Element) root.selectSingleNode("image[@index='" + index + "']/checksum");
                nodecrc.setText(crc);
            }

            Element nodeimage= (Element) root.selectSingleNode("image[@index='"+index +"']");
            for(int ct=0;ct<block_start.size();ct++) {
                           Element block = nodeimage.addElement("block");
                           block.addAttribute("index", ct+1+"");
                          Element start_address= block.addElement("start_address");
                          Long l=block_start.get(ct);
                          String text=Long.toHexString(l);

                          start_address.setText("0x" +  text);
                           Element size= block.addElement("size");
                           size.setText("0x"+Long.toHexString(block_len.get(ct)));

                       }
            System.out.println("x");
            System.out.println("x");


//            List<Node> nodefilename= root.selectNodes("image[@index='"+index +"']/filename");
//            nodefilename.get()
//            nodecode.forEach(s->System.out.println(s.getText()));
//            for(int ct=0;ct<block_start.size();ct++) {
//                           Element block = el.addElement("block");
//                           block.addAttribute("index", ct+"");
//                          Element start_address= block.addElement("start_address");
//                          Long l=block_start.get(ct);
//                          String text=Long.toHexString(l);
//
//                          start_address.setText("0x" +  text);
//                           Element size= block.addElement("size");
//                           size.setText("0x"+Long.toHexString(block_len.get(ct)));
//
//                       }

            System.out.println("ddd"); System.out.println("ddd"); System.out.println("ddd");
            XMLWriter writer = new XMLWriter(new FileOutputStream(file_ecu_path),format);
//
//
//
                writer.write(document);

                writer.close();










//            org.jsoup.nodes.Document document = Jsoup.parse(new File(file_ecu_path), "utf-8");
//            Elements elements2=document.getElementsByTag("code");
////            Elements elements2 = document.select("img[index=\"1\"]");
////            Elements elementsfilename = elements2.get(0).getElementsByTag("filename");
////            System.out.println(elementsfilename);
////            System.out.println(elements2);
//            System.out.println(elements2);
//            String m="sdf";

//            //////////////////////////////////////////////////////////////////////////////////////
//            OutputFormat format = OutputFormat.createPrettyPrint(); //设置XML文档输出格式
//            format.setEncoding("GB2312"); //设置XML文档的编码类型
//            format.setSuppressDeclaration(true);
//            format.setIndent(true); //设置是否缩进
//            format.setIndent("    "); //以空格方式实现缩进
////            format.setNewlines(true); //设置是否换行
//            format.setLineSeparator("\r\n");
//
//            Document document = null;
//            SAXReader saxReader = new SAXReader();
//            document = saxReader.read(new File(file_ecu_path)); // 读取XML文件,获得document对象
//
//       //     Element root = document.getRootElement();
//            Element root = document.getRootElement();
//            for (Iterator i = root.elementIterator(); i.hasNext();) {
//                Element el = (Element) i.next();
//                if (el.elementTextTrim("filename")!=null) {
//
////                    el.setText("sdfsdfsdfds");
////                    Element x=el.element("filename");
////                    x.setText("xxx");
////if (el.element("filename")!=null)
////{
////    Element x=e
////    el.setText("xxxx");}
////                    if(el.elementTextTrim("filename").equals(filename)){
//                       for(int ct=0;ct<block_start.size();ct++) {
//                           Element block = el.addElement("block");
//                           block.addAttribute("index", ct+"");
//                          Element start_address= block.addElement("start_address");
//                          Long l=block_start.get(ct);
//                          String text=Long.toHexString(l);
//
//                          start_address.setText("0x" +  text);
//                           Element size= block.addElement("size");
//                           size.setText("0x"+Long.toHexString(block_len.get(ct)));
//
//                       }
//                    //}
//                }
//
//                }
//                XMLWriter writer = new XMLWriter(new FileOutputStream(file_ecu_path),format);
//
//
//
//                writer.write(document);
//
//                writer.close();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////



          sw.close();
            System.out.println("ddd");
            System.out.println("ddd");
//            {
//                listBuffer.Clear();
//                liststring.Clear();
//                Buffer.Clear();
//                bufferforfirst.Clear();
//                bufferforend.Clear();
//                time = 0;
//                isfirst = false;
//                hang.Clear();
//
//
//            }

        }
        catch(Exception e) {
            msg="hex文件打开失败";
        }

//        int len=-1;
//        byte buffer[] = new byte[1024*1024*10];



    }


        /**
     * @Title: compress
     * @Description: TODO
     * @param filePaths 需要压缩的文件地址列表（绝对路径）
     * @param zipFilePath 需要压缩到哪个zip文件（无需创建这样一个zip，只需要指定一个全路径）
     * @param keepDirStructure 压缩后目录是否保持原目录结构
     * @throws IOException
     * @return int   压缩成功的文件个数
     */
    public static int compress(List<String> filePaths, String zipFilePath,Boolean keepDirStructure) throws IOException{
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


}

