<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hello</title>
    <script type="text/javascript" src="<%=basePath%>js/ext-all-debug.js"></script>

    <script type="text/javascript" src="<%=basePath%>js/layout.js"></script>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/ext-theme-classic-all-debug.css"/>

    <style type="text/css">
        .x-panel-body p {
            margin: 10px;
            font-size: 12px;
        }

        .myCls{
            color: red;

        }

    </style>
</head>
<body>
${pageContext.request.contextPath}
<p/>
<%=basePath%>
<input type="button" id="show-btn" value="Layout Window"/><br/><br/>
<input type="button" id="add-btn" value="add tab"/><br/><br/>
</body>
</html>