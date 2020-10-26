<!DOCTYPE html>
<html lang="zh">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta pageEncoding="utf-8" http-equiv="Content-Type" content="text/html; charset=utf-8">
<%--    <meta charset="UTF-8">--%>
    <title>Title</title>
    <style>
        .table{
            width: 100%;
            height: 90px;
            /*border: 8px;*/
        }
        .wlan{
            width: 100%;
        }
        td{
            text-align: center;
        }
    </style>


    <script src="js/jquery-3.3.1.min.js"></script>

    <script>
        //在页面加载完成后
        $(function(){

            myAjax();

        })
        var myAjax=function(){
            $.ajax({
            url:'map1',
            type:'get',
            dataType:'json',
            success:function(ps){

                var str = "";$('table').html(" <th>ECU</th><th>ECU_file_name</th><th>Status</th><th>verify_success</th><th>verify_failed</th><th>DESCRIPTION</th>");
                // str +="<table><thead><tr><th>ID</th><th>ECU_NAME</th><th>DESCRIPTION</th></tr></thead><tbody>";
                //遍历数据
                $.each(ps,function(index,element){
                    str +="<tr><td>"+element['ecu_name']+"</td><td><a href='${pageContext.request.contextPath}/map2?tang="+element['id']+  "' >" +element['file_name']+"</a></td><td>"+element['for_check']+
                        "</td><td>"+"<button onclick='js_method("+element['id']+")'"+ "type=\"button\">验证通过</button>"+"</td><td>"+"<button onclick='js_method_fail("+element['id']+")'"+ "type=\"button\">验证失败</button>" +"</td><td><textarea class='wlan' onblur='js_method_blur(this,"+element['id']+   ")'>"+element['description'] + "</textarea></td></tr>";
                    // str +="<tr><td>"+element['id']+"</td><td><a href='javascript:void(0)' onclick='js_method("+element['id']+   ")' >" +element['file_name']+"</a></td><td>"+element['description']+"</td></tr>";
                })

                $('table').append(str);
            }
             })
        }
        function js_method(id){
            // alert(id);
           // alert(file_name1);
            $.post("success",{"tang":id},function () {
                // update();
                myAjax();
            });
        }


        function js_method_fail(id){
            // alert(id);
            // alert(file_name1);
            $.post("fail",{"tang":id},function () {
                myAjax();
                // update();
            });
        }
        function  js_method_blur(val,id) {
// alert(val.value);
// alert(id);
            $.post("miaoshu",{"description":val.value,"id":id},function () {
                myAjax();
                // update();
            });
        }

    </script>

</head>
<body>

<table border="3" class="table">
    <tr>
        <th>ECU</th>
        <th>ECU_file_name</th>

        <th>Status</th>
        <th>verify_success</th>
        <th>verify_failed</th>
        <th>DESCRIPTION</th>
    </tr>
</table>

</body>
</html>
<%--" <th>ECU</th><th>ECU_file_name</th><th>Status</th><th>verify_success</th><th>verify_failed</th><th>DESCRIPTION</th>"--%>