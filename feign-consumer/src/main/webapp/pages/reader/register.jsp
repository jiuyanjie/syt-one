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

    <title>用户注册-图书租聘系统</title>

    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <link rel="stylesheet" href="<%=basePath%>plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/adminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/adminLTE/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/style.css">
    <script type="text/javascript" src="<%=basePath%>js/vue/vue.min.js" ></script>
</head>

<body class="hold-transition register-page">
<div class="register-box" id="app">
    <div class="register-logo">
        <a href="<%=basePath%>pages/reader/index.jsp"><b>图书租赁系统用户注册</b></a>
    </div>

    <div class="register-box-body">
        <p class="login-box-msg">新用户注册</p>
        <form @submit.prevent="submitFrom">
            <div class="form-group has-feedback">
                <input type="text" placeholder="账号：4_16字母、数字、下划线、减号"  class="form-control" v-model="userInfo.account" name="account" v-on:change="existAccountVerify" >
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password" placeholder="密码：6-20个字母、数字、下划线"  name="password" v-model="userInfo.password" class="form-control" >
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="password"  class="form-control" v-model="confirmPassword" placeholder="确认密码">
                <span class="glyphicon glyphicon-lock form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="text"  class="form-control" v-model="userInfo.rname" name="rname" placeholder="用户名">
                <span class="glyphicon glyphicon-user form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <select class="form-control" name="gender" v-model="userInfo.gender" >
                    <option value='男'>男</option>
                    <option value='女'>女</option>
                </select>
            </div>
            <div class="form-group has-feedback">
                <div class="input-group date">
                    <input type="text"  name="birthday" placeholder="出生日期" class="form-control pull-right" id="datepicker">
                    <div class="input-group-addon">
                        <i class="fa fa-birthday-cake"></i>
                    </div>
                </div>
            </div>
            <div class="form-group has-feedback">
                <input type="email"  class="form-control" name="email" v-model="userInfo.email" placeholder="邮箱">
                <span class=" glyphicon glyphicon-envelope form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback">
                <input type="text"  class="form-control" name="phone" v-model="userInfo.phone" placeholder="电话号码">
                <span class="glyphicon glyphicon-phone form-control-feedback"></span>
            </div>
            <div class="form-group has-feedback" style="overflow: hidden">
                <div class="col-md-8" style="padding: 0">
                    <input type="text"  class="form-control" name="verifyCode" v-model="userInfo.verifyCode" placeholder="邮箱验证码">
                </div>
                <div class="col-md-4" style="padding: 0">
                    <button type="button" class="btn btn-primary btn-block btn-flat" @click="sendEmail($event)">{{sendMailStr}}</button>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-primary btn-block btn-flat">注册</button>
                </div>
            </div>
        </form>
        <div class="has-feedback" style="text-align: center; margin: 2% 0;">
            <a href="<%=basePath%>pages/login.jsp" class="text-center">我有账号，现在就去登录</a>
        </div>
    </div>
    <!-- /.form-box -->
</div>
<!-- /.register-box -->

<!-- jQuery 2.2.3 -->
<!-- Bootstrap 3.3.6 -->
<!-- iCheck -->
<script src="<%=basePath%>plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="<%=basePath%>plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>plugins/iCheck/icheck.min.js"></script>
<script src="<%=basePath%>plugins/datepicker/bootstrap-datepicker.js"></script>
<script src="<%=basePath%>plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>js/vue/vue.min.js" ></script>
<script type="text/javascript" src="<%=basePath%>js/axios-0.18.0.js"></script>
<script>
    $(function() {
        $('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });
        $('#datepicker').datepicker({
            autoclose: true,
            language: 'zh-CN'
        });
    });
    new Vue({
        el:'#app',
        data:{
            userInfo:{
                "account":"","password":"","rname":"","gender":"男","email":"","phone":"","verifyCode":""
            },
            "confirmPassword":"",
            passTime: 1,
            timeFlag: null,
            sending:false,
            sendMailStr:"发送验证码",
            waitTime:30,
            currentTime:0,
            timeFlag:null,
        },

        methods: {
            //发送邮箱验证码
            sendEmail: async function(e){
                    if(this.sending){
                        return;
                    }
                    if(!(await this.verify())){    //验证失败返回false
                        return ;
                    }
                    this.startPassTime(e.target);
                    console.log(e.target);
                    this.sending = true;
                    let userInfo = this.userInfo;
                    await axios.post("<%=basePath%>register/verifyMailCode/"+userInfo.account+"/"+userInfo.email+".").then(response => {
                        
                    }).catch(err => {
                        this.stopPassTime(e.target);
                        alert(err.response.data.message);
                    });
                },
            //判断账号名是否存在
            existAccountVerify: async function(){
                let account = this.userInfo.account;
                console.log(account);
                let existAccount = true;  //用户名已存在
                await axios.post("<%=basePath%>register/account/"+account).then(response => {
                    console.log(response);
                    existAccount = false;   //用户名不存在
                }).catch(err => {
                    let result = err.response.data;
                    if(result.status == 406){
                        alert(result.message);
                    }
                });
                return existAccount;
            },
            //用户注册
            submitFrom: async function() {
                let userInfo = this.userInfo;
                if(!(await this.verify())){    //验证失败返回false
                    return ;
                }
                if(userInfo.verifyCode == "" || userInfo.verifyCode.length != 4){
                    alert('请输入4位邮箱验证码！');
                    return ;
                }
                let birthday = userInfo.birthday;
                    let yearIndex = birthday.indexOf('年');
                let year = Number(birthday.substring(0,yearIndex));
                let month = Number(birthday.substring(yearIndex,2));
                let day = Number(birthday.substr(yearIndex+4,2));
                userInfo.birthday = new Date(year,month,day);
                axios.post("<%=basePath%>register",userInfo).then( response =>{
                    console.log(response);
                    alert('注册成功！');
                    location.href = '<%=basePath%>pages/login.jsp';
                }).catch( err =>{
                    alert(err.response.data.message);
                });
            },
            verify: async function(){
                var regAccount= /^[a-zA-Z0-9_-]{4,16}$/;   //账号验证 4到16位由 字母，数字，下划线，减号组成
                var regPassword = /^(\w){6,20}$/;  //密码验证 6-20个字母、数字、下划线
                var regName =  /^[\u4E00-\u9FA5]{2,20}$/;        //2-20个汉字
                var regEmail = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/; //匹配邮箱
                var regPhone =/^1[34578]\d{9}$/;   //电话验证“XXXX-XXXXXXX”，“XXXX-XXXXXXXX”，“XXX-XXXXXXX”，“XXX-XXXXXXXX”，“XXXXXXX”，“XXXXXXXX”
                let userInfo = this.userInfo;
                userInfo.birthday = $('#datepicker').val();
                console.log(userInfo);
                if(!regAccount.test(userInfo.account)){
                    alert('账号格式错误！格式：4到16位由 字母，数字，下划线，减号组成');
                    return false;
                }
                let existAccount = await this.existAccountVerify();
                if(existAccount){   //用户名存在
                    return false;
                }
                if(!regPassword.test(userInfo.password)){
                    alert('密码格式错误！格式: 6-20个字母、数字、下划线');
                    return false;
                }
                if(userInfo.password != this.confirmPassword){
                    alert('两次输入的密码不匹配');
                    return false;
                }
                if(!regName.test(userInfo.rname)){
                    alert('用户名格式错误！格式:2-20个汉字');
                    return false;
                }
                if(!regEmail.test(userInfo.email)){
                    alert('邮箱格式错误，请输入正确的邮箱！');
                    return false;
                }
                if(!regPhone.test(userInfo.phone)){
                    alert('手机号格式错误，请输入正确的手机号！');
                    return false;
                }
                return true;
            },
            startPassTime: function(e) {
                this.currentTime = this.waitTime;
                this.sendMailStr = this.currentTime+"S";
                $(e).removeClass("btn-primary");
                $(e).addClass("btn-default");
                let timeFlag = setInterval(() => {
                    if(this.currentTime == 1){
                        this.stopPassTime(e);
                        return ;
                    }
                    this.currentTime = this.currentTime-1;
                    this.sendMailStr = this.currentTime+"S";
                }, 1000);
                this.timeFlag = timeFlag;
            },
            stopPassTime:function (e) {
                clearInterval(this.timeFlag);
                this.sendMailStr = "发送验证码";
                $(e).removeClass("btn-default");
                $(e).addClass("btn-primary");
                this.sending = false;
            }
        }
    });
</script>
</body>

</html>