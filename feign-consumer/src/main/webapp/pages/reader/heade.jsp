<%@ page import="com.bookManagerment.entity.Reader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.readerInfo == null}">   <%--当前用户没有登录--%>
    <c:choose>
        <c:when test="${cookie.get('readerAutoLogin') != null}"><%--有自动登录--%>
            <jsp:forward page="/login/autoLogin"></jsp:forward>
        </c:when>
        <c:otherwise>   <%--跳转到登录页--%>
            <c:redirect url="/pages/login.jsp"></c:redirect>
        </c:otherwise>
    </c:choose>

</c:if>
<%
    Reader reader = (Reader) session.getAttribute("readerInfo");
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
                        <span class="hidden-xs"><%=reader.getRName()%></span>
                    </a>
                    <ul class="dropdown-menu">
                        <!-- User image -->
                        <li class="user-header" data-stopPropagation = "true">
                            <img src="<%=basePath%>img/user2-160x160.jpg" class="img-circle" alt="User Image">
                            <p>
                                <%=reader.getRName()%>
                                <small>账户余额 <span id="accountMoney"><%=reader.getBalance()/100.0%></span>元 <a class="fa fa-refresh" onclick="refreshMoney()"></a></small>
                            </p>
                        </li>
                        <li class="user-footer">
                            <div class="pull-left" data-toggle="modal" data-target="#addBalanceModal">
                                <a href="#" class="btn btn-default btn-flat">充值</a>
                            </div>
                            <div class="pull-right">
                                <a href="<%=basePath%>exit/reader" class="btn btn-default btn-flat">退出</a>
                            </div>
                        </li>
                    </ul>
                </li>

            </ul>
        </div>
    </nav>

</header>
<div>
    <!-- 充值 -->
    <div class="modal fade" id="addBalanceModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content" v-cloak>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title" id="myModalLabel" style="text-align: center;">账户充值</h3>
                </div>
                <div class="modal-body">
                    <!--基础控件-->
                    <div class="tab-pane" id="tab-common" id="addBalanceEl">
                        <form action="<%=basePath%>reader/balance" id="addBalanceFrom" method="post">
                            <div class="row data-type">
                                <div class="col-md-4 title">充值金额</div>
                                <div class="col-md-8 data">
                                    <input type="text" required min="0" class="form-control" placeholder="元"
                                           name="money">
                                </div>
                                <div class="col-md-12 data text-center" style="margin-top: 20px;">
                                    <button type="submit"
                                            class="btn bg-maroon">充值</button>
                                    <button type="button" class="btn bg-default" data-dismiss="modal">取消</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<%=basePath%>plugins/jQuery/jquery-2.2.3.min.js"></script>
<script>
    $("body").on('click','[data-stopPropagation]',function (e) {
        e.stopPropagation();
    });
    $('#addBalanceFrom').submit(function () {
        let money = $('input[name=money]').val();
        if(money == '' || money.length <= 0 || Number(money) == NaN || Number(money) <= 0){
            alert('请输入有效金额！');
            return false;
        }
        return true;
    });
    function refreshMoney(){
        $.get("<%=basePath%>reader/refreshMoney",(response)=>{
            $('#accountMoney').text(response);
        })
    }
</script>


