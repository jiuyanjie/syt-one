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
    <link rel="stylesheet" href="<%=basePath%>plugins/adminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/adminLTE/css/skins/_all-skins.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/style.css">
    <link rel="stylesheet" href="<%=basePath%>css/style.css">
    <link rel="stylesheet" href="<%=basePath%>plugins/iCheck/square/blue.css">
    <style>
        [v-cloak] {
            display: none !important;
        }
        .mailSet{
            height: 100%;
            text-align: center;
            margin: 10px 5px;
        }
        #set div:hover{
            background-color: #ff9595;
            cursor:pointer;
        }
        .setActive{
            background-color: #ff9595  !important;
        }
        .mailSet div:first-child{
            border-bottom: 1px solid #949494;
        }
        @media (min-width: 768px) {
            .modal-dialog {
                width: 700px;
                margin: 30px auto;
            }
        }


        .activePageNum{
            background-color: #cccccc !important;
        }
        .pageination_align {
            text-align: center
        }

        .pageination {
            color: #48576a;
            font-size: 12px;
            display: inline-block;
            user-select: none;
        }

        .pagination_page {
            padding: 0 4px;
            border: 1px solid #d1dbe5;
            border-right: 0;
            background: #fff;
            font-size: 13px;
            min-width: 28px;
            height: 28px;
            line-height: 28px;
            cursor: pointer;
            box-sizing: border-box;
            text-align: center;
            float: left;
        }

        .pagination_page_right {
            border-right: 1px solid #d1dbe5;
        }

        .pagination_page:hover {
            color: #20a0ff;
        }

        .disabled {
            color: #e4e4e4 !important;
            background-color: #fff;
            cursor: not-allowed;
        }

        .pagination_page_active {
            border-color: #20a0ff;
            background-color: #20a0ff;
            color: #fff !important;;
            cursor: default;
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
    <!-- 内容区域 -->
    <div class="content-wrapper">
        <!-- 内容头部 -->
        <section class="content-header">
            <h1>
                借阅中
                <small>数据列表</small>
            </h1>
            <ol class="breadcrumb">
                <li>
                    <a href="#">借阅信息</a>
                </li>
                <li>
                    <a href="#">借阅中</a>
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
                        <div class="col-md-4" >
                            <div class="form-group form-inline">
                                <div class="btn-group">
                                    <button type="button" @click="getBooksAndReloadPageNum" class="btn btn-default"
                                            title="刷新"><i class="fa fa-refresh"></i> 刷新</button>
                                </div>
                            </div>
                        </div>
                        <form id="searchForm">
                            <div class="col-md-6 form-group" style="display: flex;" v-cloak>
                                <div class="col-md-2 data" >
                                    <input type="text"  name="bName" autocomplete="off" required class="form-control"  placeholder="图书名">
                                </div>
                                <button type="button" @click="searchBooks" style="display: inline-block; margin-right: 5px;" title="搜索" class="btn btn-default">搜索</button>
                                <button type="reset" style="display: inline-block;" title="搜索" class="btn btn-default">重置</button>
                            </div>
                        </form>

                        <!--工具栏/-->

                        <!--数据列表-->
                        <table id="dataList" class="table table-bordered table-striped table-hover dataTable">
                            <thead>

                            <tr>
                                <th style="padding-right:0px;text-align: center;">
                                    <input id="selall" type="checkbox" class="icheckbox_square-blue">
                                </th>
                                <th class="text-center">编号</th>
                                <th class="text-center">图书编号</th>
                                <th class="text-center">图书名</th>
                                <th class="text-center">借阅时间</th>
                                <th class="text-center">应还书日期</th>
                                <th class="text-center">剩余借阅天数</th>
                            </tr>

                            </thead>
                            <tbody>
                            <tr v-if="showLoading"><td colspan="9" style="text-align: center;"><img style="width: 5%;" src="<%=basePath%>img/loading.gif"></td></tr>
                            <tr  v-if="showData" v-cloak v-for="(borrowBook,index) in borrowBooks" style="text-align: center;" :key="index">
                                <td><input class="icheckbox_square-blue" v-bind:id="borrowBook.bbId" name="ids" type="checkbox" v-bind:value="borrowBook.bbId"></td>
                                <td>{{borrowBook.bbId}}</td>
                                <td>{{borrowBook.number}}</td>
                                <td>{{borrowBook.bname}}</td>
                                <td>{{borrowBook.bbTime}}</td>
                                <td>{{borrowBook.dueTime}}</td>
                                <td>{{borrowBook.remainingDays}}</td>
                            </tr>
                            <tr v-if="showMessage"  v-cloak style="text-align: center;font-size: 1.5em">
                                <td colspan="9" style="letter-spacing: 5px;padding: 15px;">{{message}}</td>
                            </tr>
                            </tbody>
                        </table>
                        <!--数据列表/-->

                    </div>
                    <!-- 数据表格 /-->

                </div>
                <!-- /.box-body -->

                <!-- .box-footer-->
                <div class="box-footer">
                    <div class="pull-left">
                        <div class="form-group form-inline">
                            <span v-cloak>总共{{page}} 页，共{{total}} 条数据。 每页</span>
                            <select class="form-control" v-on:change="changeSize" id="size" >
                                <option  v-if="size==5" selected value="5">5 条</option>
                                <option v-else value="5">5 条</option>

                                <option v-if="size==10" selected value="10">10 条</option>
                                <option v-else value="10">10 条</option>

                                <option v-if="size==15" selected value="15">15 条</option>
                                <option v-else value="15">15 条</option>

                                <option v-if="size==30" selected value="30">30 条</option>
                                <option v-else value="30">30 条</option>

                                <option v-if="size==40" selected value="40">40 条</option>
                                <option v-else value="40">40 条</option>

                            </select>
                        </div>
                    </div>

                    <div class="box-tools pull-right">
                        <ul class="pagination">
                            <li>
                                <a v-cloak v-if="pageNumArr[0] != 1">...</a>	<!--前面还有页码没有显示-->
                            </li>
                            <li v-cloak v-for="(num,index) in pageNumArr" :key="index" id="pageNum">
                                <a v-if="index == 0" class="activePageNum"  @click="getBookByPage(index)">{{num}}</a>
                                <a v-else  @click="getBookByPage(index)">{{num}}</a>
                            </li>
                            <li>
                                <a v-cloak v-if="page > pageNumArr[pageNumArr.length-1]">...</a>	<!--总页数 大于 当前显示的最后一个元素的页码-->
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- /.box-footer-->
            </div>
        </section>
        <!-- 正文区域 /-->
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
    function setActive(_this){
        $('#set>div').removeClass('setActive');
        $(_this).addClass('setActive');
        console.log($(_this).text(),$(_this).text()=="账号设置");
        if($(_this).text() == "账号设置"){
            $("#setMailModel").css("display","none");
            $('#setAccount').css("display","block");
        }else{
            $("#setMailModel").css("display","block");
            $('#setAccount').css("display","none");
        }
    }
    let app = new Vue({
        el: "#app",
        data: {
            showData: false,        //显示页面表格数据
            showLoading: true,      //显示记载中图片
            showMessage : false,    //显示服务端提示信息
            borrowBooks: [	//图书信息
                {
                    "bbId": 1,
                    "rid": 1,
                    "bid": 1,
                    "bname": "农使平气容",
                    "rname": "运就山几任使复",
                    "bbTime": "1989-01-04",
                    "dueTime": "2001-06-28",
                    "remainingDays": "5",
                },
            ],
            bookTypes: [	//图书分类
                { "btId": 0, "name": "文学作品" }
            ],
            modifyBookIndex:0,		//用户要修改的 图书在books中的索引
            total:0,	//总记录数
            page:1,		//总页数
            size:5,		//每页显示几条
            currentPage:1,	//当前第几页
            pageNumArr:[],	//用于底部分页的数据
            pageNumMax : 22,//底部显示的最大页码个数
            pageNumCurrentClass : "activePageNum",	//当前页码的 css样式名
            pageNumIndex : 0,	//点击的页码在pageNumArr的索引
        },

        methods: {
            //获取借阅图书，并重新加载页码
            getBooksAndReloadPageNum: async function(start,end){
                await this.getBorrowBooks();
                this.pageNumReload();
            },
            //搜索
            searchBooks:async function(){
                let searchUrl = ""
                let searchObj = $('#searchForm').serializeObject();
                if(searchObj.rName != undefined && searchObj.rName != ""){
                    searchUrl += "&rName="+searchObj.rName;
                }
                if(searchObj.bName != undefined && searchObj.bName != ""){
                    searchUrl += "&bName="+searchObj.bName;
                }
                if(searchObj.remainingDays != undefined && searchObj.remainingDays != ""){
                    searchUrl += "&remainingDays="+searchObj.remainingDays;
                }
                searchUrl = searchUrl.substr(1);
                this.currentPage = 1;
                await this.getBorrowBooks(searchUrl);
                this.pageNumReload();
            },
            //从服务器获取图书信息
            getBorrowBooks: async function (searchUrl = "") {
                let url = "<%=basePath%>reader/borrowBook/"+this.currentPage+"/"+this.size;
                if(searchUrl!=""){
                    url += "?"+searchUrl;
                }
                this.showData = false;
                this.showLoading = true;
                await axios.get(url).then(response => {
                    console.log(response)
                    this.borrowBooks = response.data.rows;
                    this.page = response.data.page;
                    this.size = response.data.size;
                    this.total = response.data.total;
                    this.showData = true;
                    this.showMessage = false;
                    this.showLoading = false;
                }).catch(err => {
                    this.message = err.response.data.message;
                    this.showMessage = true;
                    this.showLoading = false;
                    console.log(err);
                });
            },
            //点击页码获取图书信息
            getBookByPage:function(pageNumIndex){
                this.pageNumIndex = pageNumIndex;
                //获取当前元素的页码
                let num = Number($($('#pageNum a')[pageNumIndex]).text());
                //设置要获取的页码
                this.currentPage = num;
                //获取图书
                this.getBorrowBooks();

                //根据图书获取图书后的总页数
                let page = this.page;	//总页数
                let pageNumArr = this.pageNumArr;	//页面中显示的页码
                let pageNumFist = pageNumArr[0];	//页码中的第一个元素的值
                let pageNumLast = pageNumArr[pageNumArr.length-1];	//页码中的最后一个元素的值
                let middle = Math.ceil(pageNumArr.length/2); //计算页码元素中间值
                let addNum = (pageNumIndex+1)-middle;	//计算 中间的元素到 点击的元素的个数

                //判断是否还有页码没有显示
                if(page > pageNumArr[pageNumArr.length-1] && addNum>0){	//当前页面中 还有后面页码没有显示,并且点击的是中间之后的元素
                    //判断点击当前点击的是否是中间或者中间之后的元素
                    if(middle < pageNumIndex+1){	//是中间以后的元素
                        if(page >= pageNumLast+addNum){	//总页码 大于 要将要显示的最后一个页码的值 如果显示之后还有页码没有显示
                            //重新加载页码
                            this.pageNumReload(pageNumFist+addNum,pageNumLast+addNum);
                            //添加选中样式
                            this.addActive(middle-1);
                        }else{	//如果显示之后 页码可以全部显示
                            //计算
                            let addNum = page-pageNumLast;
                            this.pageNumReload(pageNumFist+addNum);
                            //添加选中样式
                            this.addActive(pageNumIndex-addNum);
                        }
                    }else{	//是中间以及之前的元素
                        //设置选中的样式
                        this.addActive(pageNumIndex);
                    }
                }else if( pageNumFist > 1 && addNum<0 ){		///当前页面中 还有前面还有页码没有显示 并且点击的是中间及之前的页码元素
                    if(pageNumFist+addNum >0){	//点击后页码不会溢出 第一个页码大于等于 1
                        //重新加载页码
                        this.pageNumReload(pageNumFist+addNum,pageNumLast+addNum);
                    }else{	//点击后页码会溢出
                        this.pageNumReload();
                        //添加选中样式
                        this.addActive(pageNumIndex+pageNumFist-1);
                    }
                }else{	//页码已经全部显示了
                    //设置选中的样式
                    this.addActive(pageNumIndex);//重新加载页码
                }
            },
            //底部页码的处理 刷新页码 start开始页码，end结束页码
            pageNumReload:function(start = 1, end = this.page){	//开始的页码默认为 1 结束的页码默认为 总页数
                let max = this.pageNumMax;	//获取最大的页码
                this.pageNumArr = [];
                for(let i=start,j=1;i<=end&j<=max;i++,j++){	//最多显示24个页码
                    this.pageNumArr.push(i);
                }
            },
            //底部页码添加选中的样式
            addActive:function(pageNumIndex){
                $("#pageNum a").removeClass(this.pageNumCurrentClass);
                $($('#pageNum a')[pageNumIndex]).addClass(this.pageNumCurrentClass);
            },
            //修改每页的数量
            changeSize:function(){
                //改变每页显示的条数
                this.size = $('#size').val();
                //获取图书并重新加载页码
                this.getBooksAndReloadPageNum();
            },
            //表单验证 传递form表单Id带#号 例如 #addBookForm
            verifyForm:function(formId){
                let obj = $(formId).serializeObject();
                if(obj.name == ""){
                    alert("图书名不能为空");
                    return false;
                }
                if(obj.author == ""){
                    alert("图书作者不能为空");
                    return false;
                }
                if(obj.publisher == ""){
                    alert("图书出版社不能为空");
                    return false;
                }
                if(obj.total == 0){
                    if(confirm("图书总数量为 0 您确定图书吗？")){
                        return true;
                    }else{
                        return false;
                    }
                }
                return true;
            },
            //只能输入数字的表单
            verifyNum:function(e){
                var keyCode = Number(event.keyCode);
                console.log(keyCode);
                if(!(keyCode >= 48 && keyCode <= 57) && keyCode!=8 && keyCode!=46) {
                    //2.阻止默认行为执行
                    event.preventDefault();
                }
            }
        },
        created: function () {
            //获取图书并加载页码
            this.getBooksAndReloadPageNum();
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