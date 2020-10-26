import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/ecuxml")
public class ecuxml extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");
        List<String> fileNamex= new ArrayList<>();
        xmldatacrc datacrc=new xmldatacrc();

        resp.setContentType("application/json;charset=utf-8");
        Map<String,Object> map = new LinkedHashMap<String,Object>();
        String ecu_name = (req.getParameter("checkvalue"));
        System.out.println(ecu_name);
ecu_name="install_"+ecu_name+".xml";
        String xml_dir = "/WEB-INF/XML";
        String xml_dir_real = req.getServletContext().getRealPath(xml_dir);
        String file_ecu_path = xml_dir_real + "/" + ecu_name;
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


            for (Iterator i = root.elementIterator(); i.hasNext(); ) {
                Element el = (Element) i.next();
                if (el.elementTextTrim("filename") != null) {

                    String name = el.elementTextTrim("filename");

                  fileNamex.add(name);
                  datacrc.setFileNamex(fileNamex);

//                  System.out.println("dsdfsdf");
//                    ;
                }


                if (el.elementTextTrim("checksum") != null) {

                    String name = el.elementTextTrim("checksum");

                   if ("".equals(name))
                   {
                       datacrc.setCrc(false);
                   }
                   else {
                       datacrc.setCrc(true);
                   }
//                    datacrc.setFileNamex(fileNamex);

//                  System.out.println("dsdfsdf");
//                    ;
                }


            }
            ObjectMapper mapper =new ObjectMapper();
            mapper.writeValue(resp.getWriter(),datacrc);


        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
        @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
