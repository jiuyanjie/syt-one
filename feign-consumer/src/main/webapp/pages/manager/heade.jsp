<%@ page import="com.bookManagerment.entity.Manager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.managerInfo == null}">   <%--当前用户没有登录--%>
    <c:redirect url="/pages/login.jsp"></c:redirect>
</c:if>
<%
    Manager manager = (Manager) session.getAttribute("managerInfo");
%>

<!-- 页面头部 -->
<header class="main-header">
    <!-- Logo -->
    <a href="#" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><b>管理</b></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><b>图书管理系统</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
        <!-- Sidebar toggle button-->
        <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
        </a>
        <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
                <!-- User Account: style can be found in dropdown.less -->
                <li class="dropdown user user-menu">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="<%=basePath%>img/user2-160x160.jpg" class="user-image" alt="User Image">
                        <span class="hidden-xs">  <%=manager.getName()%></span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header">
                            <img src="<%=basePath%>img/user2-160x160.jpg" class="img-circle" alt="User Image">
                            <p>
                                <%=manager.getName()%> - 数据管理员
                            </p>
                        </li>
                        <li class="user-footer">
                            <div class="pull-right">
                                <a href="<%=basePath%>exit/manager" class="btn btn-default btn-flat ">退出</a>
                            </div>
                        </li>
                    </ul>
                </li>

            </ul>
        </div>
    </nav>
</header>

