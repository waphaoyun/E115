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
  </head>
  <body>
  <!-- 遍历Map集合 -->
      <c:forEach var="me" items="${fileNameMap}">
              <c:url value="/downloadServlet" var="downurl">
                  <c:param name="filename" value="${me.key}"></c:param>
              </c:url>
              ${me.value}<a href="${downurl}">下载</a>

              <br/>
          </c:forEach>

  </body>
</html>
