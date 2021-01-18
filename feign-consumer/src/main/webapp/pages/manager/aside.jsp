<%--
  Created by IntelliJ IDEA.
  User: zpf
  Date: 2020/6/26
  Time: 13:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar user panel -->
        <div class="user-panel">
            <div class="pull-left image">
                <img src="<%=basePath%>img/user2-160x160.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
                <p>${managerInfo.name}</p><br />
            </div>
        </div>
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu">
            <li class="header">菜单</li>
            <!-- 菜单 -->
            <li class="treeview">
                <a href="<%=basePath%>pages/manager/bookDatalist.jsp">
                    <i class="fa fa-folder"></i> <span>图书管理</span>
                </a>
            </li>
            <li class="treeview">
                <a href="<%=basePath%>pages/manager/bookTypes.jsp">
                    <i class="fa fa-folder"></i> <span>图书分类管理</span>
                </a>
            </li>
            
              <li class="treeview">
                <a href="<%=basePath%>pages/manager/borrowBooks.jsp">
                    <i class="fa fa-folder"></i> <span>借阅信息</span>
                </a>
            </li>
              <li class="treeview">
                <a href="<%=basePath%>pages/manager/reserveBorrowBooks.jsp">
                    <i class="fa fa-folder"></i> <span>预定信息</span>
                </a>
            </li>
            
         
          
            <li class="treeview">
                <a href="<%=basePath%>pages/manager/reserveGiveBack.jsp">
                    <i class="fa fa-folder"></i> <span>归还信息</span>
                </a>
            </li>
            <li class="treeview">
                <a href="#">
                    <i class="fa fa-folder"></i> <span>统计管理</span>
                    <span class="pull-right-container">
		                        <i class="fa fa-angle-left pull-right"></i>
		                    </span>
                </a>
                <ul class="treeview-menu">
                    <li id="<%=basePath%>pages/manager/GBStatistics.jsp">
                        <a href="GBStatistics.jsp">
                            <i class="fa fa-circle-o"></i> 借书/归还
                        </a>
                    </li>
                    <li id="<%=basePath%>pages/manager/businessStatistics.jsp">
                        <a href="businessStatistics.jsp">
                            <i class="fa fa-circle-o"></i> 营业额
                        </a>
                    </li>
                    <li id="<%=basePath%>pages/manager/salesStatistics.jsp">
                        <a href="salesStatistics.jsp">
                            <i class="fa fa-circle-o"></i> 销量
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </section>
    <!-- /.sidebar -->
</aside>