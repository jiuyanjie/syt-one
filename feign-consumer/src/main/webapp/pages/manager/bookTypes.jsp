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
        [v-cloak] {
            display: none !important;
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
                图书分类管理
                <small>数据列表</small>
            </h1>
            <ol class="breadcrumb">
                <li>
                    <a href="#">图书分类管理</a>
                </li>
            </ol>
        </section>
        <!-- 内容头部 /-->

        <!-- 正文区域 -->
        <section class="content">

            <!-- .box-body -->
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">列表</h3>
                </div>

                <div class="box-body">

                    <!-- 数据表格 -->
                    <div class="table-box">

                        <!--工具栏-->
                        <div class="col-md-3" >
                            <div class="form-group form-inline">
                                <div class="btn-group">
                                    <button type="button" @click="addBookTypeBefore" data-toggle="modal" data-backdrop="false" data-target="#addBookType"
                                            class="btn btn-default" title="添加"><i class="fa fa-file-o"></i> 添加</button>
                                    <button type="button" @click="deleteBookTypes" class="btn btn-default"
                                            title="删除"><i class="fa fa-trash-o"></i> 删除</button>
                                    <button type="button" @click="getBookTypes" class="btn btn-default"
                                            title="刷新"><i class="fa fa-refresh"></i> 刷新</button>
                                </div>
                            </div>
                        </div>


                        <!--工具栏/-->

                        <!--数据列表-->
                        <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                            <thead>
                            <tr>
                                <th style="padding-right:0px;text-align: center;">
                                    <input id="selall" type="checkbox" class="icheckbox_square-blue">
                                </th>
                                <th class="text-center">编号</th>
                                <th class="text-center">类别</th>
                                <th class="text-center">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-if="showLoading"><td colspan="5" style="text-align: center;"><img style="width: 5%;" src="<%=basePath%>img/loading.gif"></td></tr>
                            <tr v-if="showData" v-cloak v-for="(type,index) in bookTypes" style="text-align: center;" :key="index">
                                <td><input class="icheckbox_square-blue" v-bind:id="type.btId" name="ids" type="checkbox" v-bind:value="type.btId"></td>
                                <td>{{type.btId}}</td>
                                <td>{{type.tname}}</td>
                                <td class="text-center">
                                    <button type="button" class="btn bg-olive btn-xs"
                                            @click="modifyBookTypeBefore(index)" data-toggle="modal"
                                            data-backdrop="false"
                                            data-target="#modifyBook">修改</button>
                                    <button type="button" class="btn bg-olive btn-xs"
                                            @click="deleteBookOne(index,type.btId,type.tname)" >删除</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <!--数据列表/-->

                    </div>
                    <!-- 数据表格 /-->

                </div>
                <!-- /.box-body -->
            </div>
        </section>
        <!-- 正文区域 /-->
    </div>
    <!-- 修改图书分类 -->
    <div class="modal fade" id="modifyBook" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content" v-cloak>
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title" id="myModalLabel" style="text-align: center;">修改分类</h3>
                </div>
                <div class="modal-body">
                    <!--基础控件-->
                    <div class="tab-pane" id="tab-common">
                        <form id="modifyBookForm">
                            <input type="hidden" name="btId">	<!--图书分类ID-->
                            <div class="row data-type">
                                <div class="col-md-4 title">类别名</div>
                                <div class="col-md-8 data">
                                    <input type="text" required class="form-control" placeholder="类别名" name="tname">
                                </div>
                                <div class="col-md-12 data text-center" style="margin-top: 20px;">
                                    <button type="button" @click="modifyBookByForm"
                                            class="btn bg-maroon">确认修改</button>
                                    <button type="button" class="btn bg-default" data-dismiss="modal">返回</button>
                                </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 添加分类 -->
<div class="modal fade" id="addBookType" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" v-cloak>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h3 class="modal-title" id="myModalLabel" style="text-align: center;">添加分类</h3>
            </div>
            <div class="modal-body">
                <!--基础控件-->
                <div class="tab-pane" id="tab-common">
                    <form id="addBookTypeForm">
                        <div class="row data-type">
                            <div class="col-md-4 title">类别名</div>
                            <div class="col-md-8 data">
                                <input type="text" required class="form-control" placeholder="类别名" name="tName">
                            </div>
                            <div class="col-md-12 data text-center" style="margin-top: 20px;">
                                <button type="button" @click="addBookTypeByForm"
                                        class="btn bg-maroon">确认添加</button>
                                <button type="button" class="btn bg-default" data-dismiss="modal">返回</button>
                            </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

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
<script src="<%=basePath%>plugins/iCheck/icheck.min.js"></script>
<script>

    let app = new Vue({
        el: "#app",
        data: {
            showData: false,        //显示页面表格数据
            showLoading: true,      //显示记载中图片
            bookTypes: [	//图书分类
                { "btId": 0, "tname": "" }
            ],
            modifyBookTypeIndex:0,		//用户要修改的 图书类别在 bookTypes 中的索引
        },
        methods: {
            //在表格中 点击修改图书按钮
            modifyBookTypeBefore: function (index) {
                this.modifyBookTypeIndex = index;
                //表单赋值
                this.setModifyBookTypeValue(index);
            },
            //点击 添加按钮
            addBookTypeBefore:function(){
                //重置表单
                $("#addBookTypeForm")[0].reset();
            },
            //给修改图书对话框中的 input表单赋值
            setModifyBookTypeValue:function(index){
                let modifyBookType = this.bookTypes[index];
                console.log(modifyBookType);
                $("#modifyBook :input[name=btId]").val(modifyBookType.btId);
                $("#modifyBook :input[name=tname]").val(modifyBookType.tname);
            },
            //删除多个图书类别
            deleteBookTypes:function() {
                let ids = $('#dataList :input[name=ids]').checkedInputValueArr();
                if(ids.length == 0){
                    alert("您还未选择图书类别");
                    return false;
                }
                if (confirm("删除的图书分类下的图书，会变成暂定分类，您确定删除选中的图书分类吗？")) {
                    this.deleteBookType(ids);
                }
            },
            //删除一个图书分类
            deleteBookOne:function(index,bId,name){
                if (confirm("删除的图书分类下的图书，会变成暂定分类，您确定删除"+name+"分类吗？")) {
                    axios.delete("<%=basePath%>manager/bookType/"+bId).then(response => {
                        console.log(response);
                        //页面中删除图书分类
                        this.bookTypes.splice(index,1);
                        alert("删除成功!");
                    }).catch(function (err) {
                        console.log(err);
                        alert("删除失败");
                    });
                }
            },
            //服务器删除图书分类
            deleteBookType:function(ids){
                axios.delete("<%=basePath%>manager/bookType", {data:ids}).then(response => {
                    console.log(response);
                    //页面中删除图书分类
                    let bookType = this.bookTypes;
                    for(let i=0;i<ids.length;i++){
                        for(let j=0;j<bookType.length;j++){
                            if(bookType[j].btId == ids[i] ){
                                bookType.splice(j,1);
                            }
                        }
                    }
                    this.bookTypes = bookType;
                    //清除复选框
                    $("#selall").click();
                    $("#selall").click();
                    alert("删除成功!");
                }).catch(function (err) {
                    console.log(err);
                    alert("删除失败");
                });
            },
            //服务器添加图书分类
            addBookTypeByForm:function(){
                if(this.verifyForm('#addBookTypeForm')){	//表单验证
                    let addBookTypeForm = $('#addBookTypeForm').serializeObject();
                    axios.post("<%=basePath%>manager/bookType/"+addBookTypeForm.tName).then(response => {
                        //从服务器获取数据
                        this.getBookTypes();
                        console.log(response);
                        alert("添加成功");
                        //隐藏模态框
                        $('#addBookType').modal('hide')
                    }).catch(err => {
                        console.log(err);
                        let data = err.response.data;
                        if(data.status == 406 ||data.status == 404){
                            alert(data.message);
                        }else{
                            //隐藏模态框
                            $('#addBookType').modal('hide');
                        }
                    });

                }
            },
            //服务器端修改图书
            modifyBookByForm: function () {
                if(this.verifyForm('#modifyBookForm')){	//表单验证
                    let modifyBook = $('#modifyBookForm').serializeObject();
                    axios.put("<%=basePath%>manager/bookType", modifyBook).then(response => {
                        console.log(response);
                        //页面中修改图书
                        this.bookTypes[this.modifyBookTypeIndex]=modifyBook;
                        this.bookTypes = Object.assign({}, this.bookTypes)
                        alert("修改成功");
                        //隐藏模态框
                        $('#modifyBook').modal('hide')
                    }).catch(function (err) {
                        console.log(err);
                        let data = err.response.data;
                        if(data.status == 406){
                            alert(data.message);
                        }else{
                            //隐藏模态框
                            $('#addBookType').modal('hide');
                        }
                    });

                }
            },
            //从服务器获取图书分类信息
            getBookTypes: function () {
                this.showData = false;
                this.showLoading = true;
                axios.get("<%=basePath%>manager/bookType").then(response => {
                    console.log(response)
                    this.bookTypes = response.data;
                    this.showData = true;
                    this.showLoading = false;
                }).catch(function (err) {
                    console.log(err);
                    let data = err.response.data;
                    alert(data.message);
                });
            },
            //表单验证 传递form表单Id带#号 例如 #addBookForm
            verifyForm:function(formId){
                let obj = $(formId).serializeObject();
                if(obj.tname == ""){
                    alert("类别名不能为空");
                    return false;
                }
                return true;
            },
        },
        created: function () {
            //获取图书分类
            this.getBookTypes();
        }
    });

    //返回一个复选框的的jQuery数组 返回选中的复选框中的 值（数组）
    $.fn.checkedInputValueArr = function(){
        let obj = this;
        var checkedValueArr = []
        for (var i = 0; i < obj.length; i++) {
            if (obj[i].checked) {
                checkedValueArr.push($(obj[i]).val());//如果选中，将value添加到变量s中
            }
        }
        return checkedValueArr;
    }

    //将表单中的数据转换为对象的形式
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

    $(document).ready(function () {
        // 全选操作
        $("#selall").click(function () {
            var clicks = $(this).is(':checked');
            if (!clicks) {
                $("#dataList td input[type='checkbox']").iCheck("uncheck");
            } else {
                $("#dataList td input[type='checkbox']").iCheck("check");
            }
            $(this).data("clicks", !clicks);
        });
    });
</script>
</body>

</html>
<!---->