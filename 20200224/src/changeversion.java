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

@WebServlet("/changeversion")
public class changeversion extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取上传文件的目录
        String uploadFilePath = this.getServletContext().getRealPath("/WEB-INF/XML");
        String ecu = req.getParameter("waphaoyun");
        //存储要下载的文件名
        String file_ecu_path=uploadFilePath+"/install_"+ecu+".xml";
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

        req.getRequestDispatcher("/messagex.html").forward(req, resp);
//        req.getRequestDispatcher("/listfile.jsp").forward(req, resp);

    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
