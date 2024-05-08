<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="jakarta.tags.core" %>
    <c:set var="i" value="hi"></c:set>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <c:set var="num1" value="4"></c:set>
<c:if test="${num1 mod 2 eq 0}" var="res" >
${num1} 은 짝수입니다. ${res }<br>
</c:if>
</body>
</html>	