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
    <script type="text/javascript" src="<%=basePath%>js/vue/vue.min.js" ></script>
    <style>
        [v-cloak] {
			display: none !important;
		}
        .select{
			padding: 5px;
			border-radius: 5px;
			outline: none;
            border: 1px solid #eee;
            background-color: #fff;
		}
        .demo-input{
            padding-left: 10px; 
            height: 38px;
             min-width: 200px; 
             line-height: 38px; 
             border: 1px solid #e6e6e6;
            background-color: #fff;  
            border-radius: 2px;
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
        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper" id="app">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    统计
                    <small>statistics</small>
                </h1>
                <ol class="breadcrumb">
                    <li class="active"><a href="#">统计</a></li>
                </ol>
            </section>

            <!-- Main content -->
            <section class="content">
                <div class="row">
                    <div class="col-md-12">
                        <!-- 销量 -->
                        <div class="box box-primary">
                            <div class="date box-header with-border">
                                <div class="col-md-9">
                                    <h3 class="box-title">销量 统计</h3>
                                </div>
                                <div class="box-tools pull-right">
                                    <button type="button" class="btn btn-box-tool" data-widget="collapse">
                                        <i class="fa fa-minus"></i>
									</button>
                                </div>
                            </div>
                            <div class="box-body">
                                <div class="chart" id="line"  style="height:300px">
                                </div>
                            </div>
                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <!-- 销量 -->
                        <div class="box box-primary">
                            <div class="date box-header with-border">
                                <div class="col-md-9">
                                    <h3 class="box-title">销量详情 统计</h3>
                                </div>
                                <div class="box-tools pull-right" >
                                    <button  type="button" id="btnDetail"  class="btn btn-box-tool" data-widget="collapse">
                                        <i class="fa fa-minus"></i>
									</button>
                                </div>
                            </div>
                            <div class="box-body" >
                                <div class="chart" id="salesDetail"  style="height:300px;">
                                </div>
                            </div>
                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                </div>
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

        <!-- page script -->
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
        lay('#version').html('-v'+ laydate.v);
        let bashPath = 'http://www.zhoupanfeng.club:7300/mock/5ef4c3f22ab3d053f8d3fb77/bm/';

        let vue = new Vue({
            el:"#app",
            data:{
                showDetail:false,   //展示销量详情
                myChartLine:null,
                _this:this,
                optionLine:{
                    toolbox: {
                        show: true,
                        feature: {
                            magicType: {type: ['line', 'bar']},
                            dataView: {readOnly: false},
                            myTool1: {
                                show: true,
                                title: '导出Excel',
                                icon: 'image://<%=basePath%>img/Excel.png',
                                onclick:()=>{
                                  vueT.outExcel();
                                },
                            },
                            saveAsImage: {},
                        }
                    },
                    title: {
                        text: "图书分类销量",
                        subtext: "点击分类查看详情",
                        left: 'center',
                    },
                    legend: {
                        data:['销量'],
                        left: 'left',
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            show:true,
                            data: null,
                            axisTick: {
                                alignWithLabel: true
                            }
                        },
                    ],
                    yAxis: [
                        {
                            show:true,
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '销量',
                            type: 'bar',
                            data:null,
                            label: {
                                show: true,
                                position: "outside"
                            }
                        }
                    ]
                },
                optionPie : {
                    xAxis:{
                        show:false
                    },
                    yAxis:{
                        show:false  
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: '{a} <br/>{b} : {c} ({d}%)'
                    },
                    legend: {
                        orient: 'vertical',
                        data:null
                    },
                    series: [
                        {
                            name: '销量',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '50%'],
                            data:null,
                            emphasis: {
                                itemStyle: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                },
                myChartDetail:null,
                optiontDetail:{
                    dataZoom: [
                        {
                            type: 'slider',
                            show: true,
                            xAxisIndex: [0],
                            start: 0,
                            end: 100
                        },
                        {
                            type: 'slider',
                            show: true,
                            yAxisIndex: [0],
                            start: 0,
                            end: 100
                        },
                        {
                            type: 'inside',
                            xAxisIndex: [0],
                            start: 0,
                            end: 100
                        },
                        {
                            type: 'inside',
                            yAxisIndex: [0],
                            start: 0,
                            end: 100
                        }
                    ],
                    toolbox: {
                        show: true,
                        feature: {
                            magicType: {type: ['line', 'bar']},
                            dataView: {readOnly: false},
                            myTool1: {
                                show: true,
                                title: '导出Excel',
                                icon: 'image://<%=basePath%>img/Excel.png',
                                onclick:()=>{
                                   axios.get(bashPath).then(response => {
                                        alert('导出成功');
                                   }).catch(err => {
                                        alert('导出失败');
                                   });
                                },
                            },
                            saveAsImage: {},
                        }
                    },
                    title: {
                        text: null
                    },
                    legend: {
                        data:['销量']
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            nameLocation: "end",
                            name: "编号",
                            type: 'category',
                            data: null,
                            axisTick: {
                                alignWithLabel: true
                            }
                        },
                    ],
                    yAxis: [
                        {
                            nameLocation: "end",
                            name: "销量",
                            type: 'value'
                        }
                    ],
                    series: [
                        {
                            name: '销量',
                            type: 'line',
                            data:null,
                            markPoint: {
                                data: [{
                                    name:"最大值",
                                    type: "max",
                                },
                                {
                                    type: "average",
                                },
                                {
                                    type: "min",
                                },],
                                symbolSize: 1
                            }
                        }
                    ]
                },
            },
            methods: {
             
                //初始化当前日期
                initNowDateStr(){
                    let nowDate = new Date();
                    let y =  nowDate.getFullYear()+"年";
                    let m = (nowDate.getMonth()+1)+"月";
                    this.nowDateStr = y + m;
                },
                //初始化折线图
                initLine(){
                    // 基于准备好的dom，初始化echarts实例
                    this.myChartLine = echarts.init(document.getElementById('line'));
                    this.myChartLine.setOption(this.optionLine);
                    this.myChartLine.on('magictypechanged',(param)=>{
                        let currentType = param.currentType;
                        console.log(currentType);
                        if(currentType == "line"){
                            this.optionLine.series[0].type = "line"
                            this.myChartLine.setOption(this.optionLine);
                        }else if(currentType == "bar"){
                            this.optionLine.series[0].type = "bar"
                            this.myChartLine.setOption(this.optionLine);
                        }
                    })
                    this.myChartLine.showLoading();
                    // 指定图表的配置项和数据  发送请求获取当前月份数据
                    this.getLineData();
                },
                //初始化分类销量点击事件
                initTypeClick(){
                    this.myChartLine.on('click',(param)=>{
                        this.getSalesDetailByTypeName(param.name);
                    })
                },
                //初始化分类下的图书详情
                initBookDetail(salesMaxBooksByType){
                    //初始化 详情图形
                    this.myChartDetail = echarts.init(document.getElementById('salesDetail'));
                    this.setDetailData(salesMaxBooksByType);
                },
                //设置详情图形的值
                setDetailData(salesMaxBooksByType){
                    let optiontDetail = this.optiontDetail;
                    optiontDetail.title.text = salesMaxBooksByType.typeName;
                    optiontDetail.series[0].data = salesMaxBooksByType.sales;
                    optiontDetail.xAxis[0].data = salesMaxBooksByType.id;
                    this.optiontDetail = optiontDetail;
                    this.myChartDetail.setOption(this.optiontDetail);
                },
                //从服务器获取分类下的销量详情统计
                getSalesDetailByTypeName(typeName){
                     //等待
                     this.myChartDetail.showLoading();
                    axios.post("<%=basePath%>manager/salesByType/"+typeName).then( response => {
                        console.log(response);
                        let data = response.data;
                        data.typeName = typeName;
                       this.setDetailData(data);
                        this.myChartDetail.hideLoading();
                    }).catch( err => {
                        console.log(err);
                        alert(err.response.data.message);
                        this.myChartLine.hideLoading();
                    });
                },
                //从服务器获取折线图数据
                getLineData(){
                    //服务器数据
                     axios.get("<%=basePath%>manager/sales").then( response => {
                        console.log(response);
                        let lineData = response.data;
                        let option = this.optionLine;
                        option.xAxis[0].data=lineData.bookTypes; //图书分类
                        option.series[0].data = lineData.sales;  //借书总数
                        //初始化分类详情
                        this.initBookDetail(lineData.salesMaxBooksByType);
                        //salesMaxBooksByType
                        let obj = []
                        for(let i=0;i<lineData.bookTypes.length;i++){
                            obj.push({"value":lineData.sales[i],"name":lineData.bookTypes[i]});
                        }
                        this.$set(this.optionPie.series[0], "data", obj)
                        console.log(this.optionPie.series[0].data);
                        // 使用刚指定的配置项和数据显示图表。
                        this.optionLine = option;
                        this.myChartLine.setOption(option);
                        this.myChartLine.hideLoading();
                    }).catch( err => {
                        console.log(err);
                        alert(err.response.data.message);
                        this.myChartLine.hideLoading();
                    });
                },
                //导出 Excel
                outExcel(){
                    axios.get(bashPath).then(response => {
                            alert('导出成功');
                    }).catch(err => {
                            alert('导出失败');
                    });
                },
            },
            mounted () {
                //初始化当前日期
                this.initNowDateStr();
                //初始化折线图
                this.initLine();
                //初始化分类销量点击事件
                this.initTypeClick();
            },

        });
        var vueT = vue;
        //年范围
        laydate.render({
        elem: '#test1'
        ,type: 'year'
        ,range: true
        });
        //年月范围
        laydate.render({
        elem: '#test2'
        ,type: 'month'
        ,range: true
        });
         //日期范围
        laydate.render({
        elem: '#test3'
        ,range: true
        });
            

 		
    </script>
</body>

</html>
<!---->