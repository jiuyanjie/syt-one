<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>登录-图书租聘系统</title>
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <link rel="stylesheet" href="<%=basePath%>plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/adminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/iCheck/square/blue.css">
</head>

<body class="hold-transition login-page">
    <div class="login-box" id="app">
        <div class="login-logo">
            <a href="<%=basePath%>pages/reader/index.jsp"><b>图书租租赁系统登录</b></a>
        </div>
        <!-- /.login-logo -->
        <div class="login-box-body" id="app">
            <p class="login-box-msg">登录系统</p>

            <form @submit.prevent="submitFrom">
                <div class="form-group has-feedback">
                    <input type="text" class="form-control" v-model="userLogin.account" required name="account" placeholder="账号/邮箱">
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control" v-model="userLogin.password" required placeholder="密码">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div style="display: flex; justify-content: space-around;" class="form-group has-feedback">
                    <div>
                        <label>管理员</label><input  type="radio" name="type" v-model="userLogin.type" class="form-control" value="管理员">
                    </div>
                    <div>
                        <label>读者</label><input type="radio" checked name="type" checked v-model="userLogin.type" class="form-control" value="读者">
                    </div>
                </div>

                <div class="row"  >
                    <div class="col-xs-8">
                        <div >
                            <label v-on:click="userLogin.autoLogin = !userLogin.autoLogin;" ><input type="checkbox" name="auotoLogin" v-model="userLogin.autoLogin" > 记住 下次自动登录</label>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
                    </div>
                </div>

            </form>
            <div style="display: flex; justify-content: space-around;">
                <div> <a href="<%=basePath%>pages/reader/register.jsp" class="text-center">新用户注册</a></div>
            </div>
        </div>
        <!-- /.login-box-body -->
    </div>
>
    <script src="<%=basePath%>plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="<%=basePath%>plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>plugins/iCheck/icheck.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/vue/vue.min.js" ></script>
    <script type="text/javascript" src="<%=basePath%>js/axios-0.18.0.js"></script>
    <script>
        new Vue({
            el:'#app',
            data:{
                userLogin:{"account":"","password":"","type":"读者","autoLogin":false}
            },
            methods:{
                submitFrom:function () {
                    var regPassword = /^(\w){6,20}$/;  //密码验证 6-20个字母、数字、下划线
                    let userLogin = this.userLogin;
                    userLogin.type = $('input:checked').val();
                     if(userLogin.account==''){
                        alert('请输入账号!');
                        return;
                    }
                    if(!regPassword.test(userLogin.password)){
                        alert('密码格式错误！格式: 6-20个字母、数字、下划线');
                        return;
                    }
                    console.log(userLogin);
                     axios.post("<%=basePath%>login",userLogin).then(response => {
                        location.href="<%=basePath%>"+response.data;       //主页
                    }).catch(err => {
                        let result = err.response.data;
                        if(result.status == 406){
                            alert(result.message);
                        }
                    });
                }
            },
        });
        $(function() {
            $('input').iCheck({
                checkboxClass: 'icheckbox_square-blue',
                radioClass: 'iradio_square-blue',
                increaseArea: '20%' // optional
            });
        });
    </script>
</body>

</html>