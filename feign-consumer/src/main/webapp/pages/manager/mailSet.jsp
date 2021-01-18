<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <!-- 页面meta -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>图书管理系统</title>
    <meta name="description" content="AdminLTE2定制版">
    <meta name="keywords" content="AdminLTE2定制版">
    <meta content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" name="viewport">
    <link rel="stylesheet" href="<%=basePath%>plugins/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/ionicons/css/ionicons.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/iCheck/square/blue.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/morris/morris.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/jvectormap/jquery-jvectormap-1.2.2.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/datepicker/datepicker3.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/daterangepicker/daterangepicker.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/treeTable/jquery.treetable.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/treeTable/jquery.treetable.theme.default.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/select2/select2.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/colorpicker/bootstrap-colorpicker.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/bootstrap-markdown/css/bootstrap-markdown.min.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/adminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/adminLTE/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/style.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/ionslider/ion.rangeSlider.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/ionslider/ion.rangeSlider.skinNice.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/bootstrap-slider/slider.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/bootstrap-datetimepicker/bootstrap-datetimepicker.css">
    <style>
        *{
            outline: none;
            border: none;
            margin: 0;
            padding: 0;
        }
        ul li{
            list-style: none;
        }
        .maileditor .header{
            letter-spacing: 2px;
            padding: 5px;
            width: 100%;
            text-align: center;
            background: #838199;
            color: white;
            position: relative;
        }
        .updateMail{
            top: 16%;
            right: 5%;
            position:absolute;
            padding: 2px 5px;
            border-radius: 4px;
            color: #4f4f4f ;
            font-size: 13px;
            background-color: #ccc;
        }
        .updateMail:hover,.symbol_btn button:hover{
            box-shadow: 0px 0px 5px #0c1312;
            transform: scale(1.05);
        }
        .maileditor .header .title{
            font-size: 16px;
            letter-spacing: 2px;
        }
        .maileditor textarea{
            resize:none;
            width: 100%;
            height: 300px;
            letter-spacing: 3px;
            font-size: 15px;
            line-height: 25px;
            border: 1px solid #ccc;
            padding: 5px 10px;
        }
        .symbol_btn {
            display: flex;
            justify-content: center;
            margin-bottom: 1.5%;
        }
        .symbol_btn button{
            padding: 5px 10px;
            background-color: #b9c1c8;
            margin: 5px 10px;
            border-radius: 4px;
        }
        .maileditor .no_dorp:hover{
            cursor: no-drop;
        }
        [v-cloak] {
            display: none !important;
        }

        .saveModelBtn{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .saveModelBtn .btn{
            letter-spacing: 2px;
            margin: 5px;
            padding: 5px 20px;
        }
        .nav-tabs-custom > .tab-content{
            overflow: hidden !important;
        }
        .mailModelmenu{
            text-align: center;
            color: white;
            padding: 0;
        }
        .mailModelmenu li{
            padding:10px;
            background-color: #838199;
        }
        .mailModelmenu ul li:hover{
            background-color: #4f4e63;
            border: 1px solid #ccc;
        }
        .mailModelmenu .mailModelmenuCurrent{
            background-color: #4f4e63;
        }
        .cke_button_label {
            display: block;
        }
    </style>
</head>
<body class="hold-transition skin-purple sidebar-mini">
<div class="wrapper" id='app'>
    <!-- 页面头部 -->
    <%@ include file="heade.jsp"%>
    <!-- 导航侧栏 -->
    <%@ include file="aside.jsp"%>

   <!-- 内容区域 -->
    <div class="content-wrapper">

        <!-- 内容头部 -->
        <section class="content-header">
            <h1>
                借阅信息管理
                <small>邮箱设置</small>
            </h1>
            <ol class="breadcrumb">
                <li><a>借阅管理 </a></li>
                <li><a href="<%=basePath%>pages/manager/borrowBooks.jsp">借阅信息管理</a></li>
                <li onclick="location.reload()">邮箱设置</li>
            </ol>
        </section>
        <!-- 内容头部 /-->

        <!-- 正文区域 -->
        <section class="content">

            <div class="box-body">

                <!--tab页-->
                <div class="nav-tabs-custom">

                    <!--tab头-->
                    <ul class="nav nav-tabs ">
                        <li class="active">
                            <a href="#tab-editer" data-toggle="tab" @click="firstActive">邮件模板</a>
                        </li>
                        <li>
                            <a href="#tab-common" data-show="true" data-toggle="tab" @click="firstActive">修改默认模板</a>
                        </li>
                    </ul>
                    <!--tab头/-->

                    <!--tab内容-->
                    <div class="tab-content" >
                        <!--基础控件-->
                        <!--逾期 邮件模板-->
                        <div class="tab-pane active" id="tab-editer" >
                            <div>
                                <div class="col-md-10 col-md-offset-2">
                                    <div class="symbol_btn " v-cloak>
                                        <button v-for="(item,index) in symbol" key="index" @click="insertSymbol(item.value)">{{item.name}}</button>
                                    </div>
                                </div>
                                <div class="maileditor">
                                    <div class="mailModelmenu col-md-2">
                                        <ul>
                                            <li @click="showNoDueMailModel(true)" class="mailModelmenuCurrent">未逾期</li>
                                            <li @click="showNoDueMailModel(false)" @click.once="getOverDueTemplete">逾期</li>
                                        </ul>
                                    </div>
                                    <!-- 未逾期 -->
                                    <div  v-cloak v-if="noDueMailModel" class="mailEditor col-md-10">
                                        <div class="header">
                                            <div class="title">未逾期</div>
                                            <span v-cloak  class="updateMail" @click="editor">{{titlBtnStr}}</span>
                                        </div>
                                        <textarea id="editor" readonly  :value="noDueMailTemplate.template" name="template"></textarea>
                                        <div v-cloak v-if="saveBtn" class="col-md-12 data text-center saveModelBtn">
                                            <button type="button" class="btn bg-maroon" @click="save">保存</button>
                                            <button type="button" class="btn bg-default" @click="cancel">取消</button>
                                        </div>
                                    </div>
                                    <!-- 逾期 -->
                                    <div v-cloak v-if="!noDueMailModel" class="mailEditor col-md-10">
                                        <div class="header">
                                            <div class="title">逾期</div>
                                            <span v-cloak  class="updateMail" @click="editor">{{titlBtnStr}}</span>
                                        </div>
                                        <textarea id="editor" readonly  :value="overdueMailTemplate.template" name="template"></textarea>
                                        <div v-cloak v-if="saveBtn" class="col-md-12 data text-center saveModelBtn">
                                            <button type="button" class="btn bg-maroon" @click="save">保存</button>
                                            <button type="button" class="btn bg-default" @click="cancel">取消</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%--修改默认模板--%>
                        <div class="tab-pane " id="tab-common">
                            <div>
                                <div v-cloak class="col-md-10 col-md-offset-2">
                                    <div class="symbol_btn ">
                                        <button v-for="(item,index) in symbol" key="index" @click="insertSymbol2(item.value)">{{item.name}}</button>
                                    </div>
                                </div>
                                <div class="maileditor">
                                    <div class="mailModelmenu col-md-2">
                                        <ul>
                                            <li @click="noDueMailModel = true" class="mailModelmenuCurrent">未逾期</li>
                                            <li @click="noDueMailModel = false" @click.once="getOverDueTemplete">逾期</li>
                                        </ul>
                                    </div>
                                    <!-- 未逾期 -->
                                    <div  v-cloak v-if="noDueMailModel" class="mailEditor col-md-10">
                                        <div class="header">
                                            <div class="title">未逾期</div>
                                        </div>
                                        <textarea id="editor2"  :value="noDueMailTemplate.defaultTemplate" name="template"></textarea>
                                        <div v-cloak class="col-md-12 data text-center saveModelBtn">
                                            <button type="button" class="btn bg-maroon" @click="saveDefault(1)">保存</button>
                                        </div>
                                    </div>
                                    <!-- 逾期 -->
                                    <div  v-cloak v-if="!noDueMailModel" class="mailEditor col-md-10">
                                        <div class="header">
                                            <div class="title">逾期</div>
                                        </div>
                                        <textarea id="editor2"  :value="overdueMailTemplate.defaultTemplate" name="template"></textarea>
                                        <div v-cloak class="col-md-12 data text-center saveModelBtn">
                                            <button type="button" class="btn bg-maroon" @click="saveDefault(2)">保存</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--基础控件/-->


                    </div>
                    <!--tab内容/-->

                </div>
                <!--tab页/-->


                <!-- .box-footer
    <div class="box-footer"></div>
    -->
                <!-- /.box-footer-->

            </div>

        </section>
        <!-- 正文区域 /-->

    </div>
    <!-- 内容区域 /-->

</div>
<script src="<%=basePath%>plugins/jQuery/jquery-2.2.3.min.js"></script>
<script src="<%=basePath%>plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>plugins/adminLTE/js/app.min.js"></script>
<script src="<%=basePath%>plugins/chartjs/Chart.min.js"></script>
<script src="<%=basePath%>plugins/bootstrap-slider/bootstrap-slider.js"></script>
<script type="text/javascript" src="<%=basePath%>js/axios-0.18.0.js"></script>
<script src="<%=basePath%>js/echarts.min.js"></script>
<script src="<%=basePath%>js/axios-0.18.0.js"></script>
<script src="<%=basePath%>js/vue/vue.min.js"></script>
<script src="<%=basePath%>js/laydate.js"></script>

<script>
    let basePath = "http://www.zhoupanfeng.club:7300/mock/5ef4c3f22ab3d053f8d3fb77/bm/"
    let vue = new Vue({
        el:'#app',
        data:{
            noDueMailTemplate:{"mtId":"1","template":"","name":"未逾期","defaultTemplate":""}, //未逾期
            noDueMailModel:true,    //显示 未逾期菜单内容
            overdueMailTemplate:{"mtId":"1","template":"","name":"逾期","defaultTemplate":""}, //逾期
            saveBtn : false,//是否显示未逾期邮箱模板底部按钮
            titlBtnStr : "编辑",
            symbol:[
                {
                    "name": "",
                    "value": ""
                },
            ],
        },
        methods: {

            //点击编辑 true表示 可以编辑
            editor:function(){
                //判断当前字符串
                if(this.titlBtnStr == "插入默认模板"){
                    let defaultTemplate = this.noDueMailTemplate.defaultTemplate;
                    if(this.noDueMailModel){    //未逾期
                        if(confirm("使用统的默认“未逾模板”，将会替换您当前页面中的数据，模板内容如下:\n\n  "+defaultTemplate+"\n\n您确认替换吗？")){
                            this.noDueMailTemplate.template = defaultTemplate;
                            Object.assign({},this.noDueMailTemplate);
                        }
                    }else{  //逾期模板
                        defaultTemplate = this.overdueMailTemplate.defaultTemplate;
                        if(confirm("使用统的默认“逾模板”，将会替换您当前页面中的数据，模板内容如下:\n\n  "+defaultTemplate+"\n\n您确认替换吗？")){
                            this.overdueMailTemplate.template=defaultTemplate;
                            Object.assign({},this.overdueMailTemplate);
                        }
                    }
                    return;
                }
                this.setEditor(true);   //设置编辑状态
                this.titlBtnStr = "插入默认模板";
            },
            //true 表示可以编辑
            setEditor(flag){
                $('#editor').prop('readonly',!flag);
                this.saveBtn = flag;
            },
            //点击取消
            cancel:function(){
                this.titlBtnStr = "编辑"
                this.setEditor(false);
            },
            //切换菜单
            showNoDueMailModel:function(flag){
                this.setEditor(false);
                this.noDueMailModel = flag;
                this.titlBtnStr = "编辑";
            },
            insertSymbol2:function(symbol){
                let str = $('#editor2').val();
                console.log(str);
                let doc = document.getElementById("editor2");
                let index = getCursortPosition(doc);
                console.log(index);
                str = str.slice(0,index)+symbol+str.slice(index);
                $('#editor2').val(str);
            },
            //插入占位符
            insertSymbol:function(symbol){
                if(!this.saveBtn){
                    alert("进入编辑状态,后才使用该功能！");
                    return false;
                }
                let str = $('#editor').val();
                let doc = document.getElementById("editor");
                let index = getCursortPosition(doc);
                str = str.slice(0,index)+symbol+str.slice(index);
                $('#editor').val(str);
            },
            //修改默认模板
            saveDefault:function(type){
                axios.put("<%=basePath%>manager/mail/mailTemplate/default/"+type,$('#editor2').val()).then( response =>{
                    if(this.noDueMailModel){
                        this.noDueMailTemplate.defaultTemplate = $('#editor2').val();
                        Object.assign({},this.noDueMailModel);
                    }else{
                        this.overdueMailTemplate.defaultTemplate = $('#editor2').val();
                        Object.assign({},this.overdueMailTemplate);
                    }
                    alert("保存成功!")
                }).catch(function (err) {
                    console.log(err);
                });
            },
            //保存 模板
            save:function(){
                let type = "1" ;    //1表示未逾期
                if(this.noDueMailModel==false ){   //未逾期
                    type = 2;
                }
                axios.put("<%=basePath%>manager/mail/mailTemplate/"+type,$('#editor').val()).then( response =>{
                    console.log(response);
                    if(this.noDueMailModel){
                        this.noDueMailTemplate.template = $('#editor').val();
                        Object.assign({},this.noDueMailModel);
                    }else{
                        this.overdueMailTemplate.template = $('#editor').val();
                        Object.assign({},this.overdueMailTemplate);
                    }
                    this.cancel();
                    alert("保存成功!")
                }).catch(function (err) {
                    console.log(err);
                });
            },
            //获取未逾期模板
            getNoDueTemplete:function(){
                axios.get("<%=basePath%>manager/mail/mailTemplate/1").then( response =>{
                    console.log("getNoDueTemplete");
                    this.noDueMailTemplate = response.data;
                    console.log(response);
                }).catch(function (err) {
                    console.log(err);
                });
            },
            //获取 逾期模板
            getOverDueTemplete:function(){
                axios.get("<%=basePath%>manager/mail/mailTemplate/2").then( response =>{
                    this.overdueMailTemplate = response.data;
                    console.log(response);
                }).catch(function (err) {
                    console.log(err);
                });
            },
            //获取占位符
            getSymbol:function(){
                axios.get("<%=basePath%>manager/mail/mailSetBtn").then( response =>{
                    this.symbol = response.data;
                    console.log(response);
                }).catch(function (err) {
                    console.log(err);
                });
            },
            firstActive:function () {
                if(this.noDueMailModel){    //未逾期
                    $('.mailModelmenu ul li:first-child').addClass("mailModelmenuCurrent");
                }else{
                    $('.mailModelmenu ul li:last-child').addClass("mailModelmenuCurrent");
                }

            }
        },
        created () {
            //获取占位符
            this.getSymbol();
            //获取未逾期模板字符串
            this.getNoDueTemplete();
        }

    });
    //设置当前 模板当菜选中
    $('.mailModelmenu ul li').click(function(){
        $('.mailModelmenu ul li').removeClass("mailModelmenuCurrent");
        $(this).addClass('mailModelmenuCurrent');
        console.log($(this));
    });

    $.fn.serializeObject = function () {
        var ct = this.serializeArray();
        var obj = {};
        $.each(ct, function () {
            if (obj[this.name] !== undefined) {
                if (!obj[this.name].push) {
                    obj[this.name] = [obj[this.name]];
                }
                obj[this.name].push(this.value || "");
            } else {
                obj[this.name] = this.value || "";
            }
        });
        return obj;
    };

    //获取当前光标位置
    function getCursortPosition(element) {
        var CaretPos = 0;
        if (document.selection) {//支持IE
            element.focus();
            var Sel = document.selection.createRange();
            Sel.moveStart('character', -element.value.length);
            CaretPos = Sel.text.length;
        }
        else if (element.selectionStart || element.selectionStart == '0')//支持firefox
            CaretPos = element.selectionStart;
        return (CaretPos);
    }

</script>
</body>

</html>
<!---->