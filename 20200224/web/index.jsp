<%--
  Created by IntelliJ IDEA.
  User: wangtianjiao
  Date: 2019/12/25
  Time: 9:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>$Title$</title>
      <style>
            *{
                color: blue;
                font-size: xx-large;
            }
          .wap{
                ;
          }
          table{
              width: 30%;
          }
      </style>
  </head>
  <body>

  <form action="up" method="POST" enctype="multipart/form-data">

      <table border="1"  align="center">
          <tr>

              <td align="left" class="wap">
                  <select name="waphaoyun">
                      <option value="">--请选择ECU--</option>
                      <option value="egsm">egsm</option>
                      <option value="dms">dms</option>
                      <option value="eps">eps</option>
                      <option value="acm">acm</option>
                      <option value="adv">adv</option>
                      <option value="scm">scm</option>
                      <option value="mcu1">mcu1</option>
                      <option value="amp">amp</option>
                      <option value="acm">acm</option>
                      <option value="dcdc">dcdc</option>
                      <option value="ddcu">ddcu</option>
                      <option value="dscu">dscu</option>
                      <option value="headlamp">headlamp</option>
                      <option value="hud">hud</option>
                      <option value="lcda1">lcda1</option>
                      <option value="lcda2">lcda2</option>
                      <option value="mcu">mcu</option>
                      <option value="pdcu">pdcu</option>
                      <option value="plg">plg</option>
                      <option value="rearlamp">rearlamp</option>
                      <option value="rldcu">rldcu</option>
                      <option value="rrdcu">rrdcu</option>
                      <option value="dct">dct</option>



                  </select>
              </td>
          </tr>

              <td  class="wap">
                  <input type="file" name="photo" multiple="multiple" />
              </td>
          <tr>

              <td  class="wap">
                  <input type="submit" value="生成bin与XML文件"/>
              </td>
          </tr>
          <tr>

              <td  class="wap">
                  <a href="listfile.html">GO TO查看ECU TEST文件</a>
              </td>
          </tr>
      </table>




  </form>

  </body>
</html>
