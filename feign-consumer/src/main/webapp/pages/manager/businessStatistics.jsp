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
                        <!-- 营业额 -->
                        <div class="box box-primary">
                            <div class="date box-header with-border">
                                <div class="col-md-9">
                                    <h3 class="box-title">营业额 统计</h3>
                                    <input type="text"  class="demo-input" placeholder="年范围" id="test1">
                                    <input type="text" readonly class="demo-input"placeholder="年月范围" id="test2">
                                    <input type="text" class="demo-input" placeholder="日期范围" id="test3">
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
                nowDateStr:new Date(),  //当前日期

                myChartLine:null,
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
                        text: null
                    },
                    legend: {
                        data:['营业额/元']
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
                            data: null,
                            nameLocation: "end",
                            name: "月份",
                            axisTick: {
                                alignWithLabel: true
                            }
                        },
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            nameLocation: "end",
                            name: "单位/元",
                        }
                    ],
                    series: [
                        {
                            name: '营业额/元',
                            type: 'bar',
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
                    let d = nowDate.getDate()+"日";
                    let start = y + m + "1日";
                    let end = y + m + d;
                    this.nowDateStr = start + " - " + end;
                },
                //初始化选择时间的input
                initDateInput(){
                    //点击确定选择时间，修改表的标题，发送请求
                    let _this = this;
                    $('.demo-input').click(function(){
                        $('.laydate-btns-confirm').click(function(){
                            let parent = this.parentNode.parentNode.parentNode;
                            // layui-laydate 1年范围, 2年月范围, 3日期范围
                            let id = parent.id;
                            id = id[id.length-1];
                            let title = $('#test'+id).val();
                            if(title==undefined || title=='' ||title.length<=0){
                                return ;
                            }
                            //修改表的标题
                            _this.optionLine.title.text = title;
                            Object.assign({},_this.optionLine);
                            //发送请求
                            _this.getLineData(id,_this.optionLine.title.text);
                            //清除其他input的数据
                            for(let i=1;i<=3;i++){
                                if(i+''!=id){
                                    $('#test'+i).val('')
                                }
                            }
                        })
                    })
                },
                //初始化折线图
                initLine(){
                     //初始化标题名
                    this.optionLine.title.text = this.nowDateStr;
                    // 基于准备好的dom，初始化echarts实例
                    this.myChartLine = echarts.init(document.getElementById('line'));
                    this.myChartLine.setOption(this.optionLine);
                    this.myChartLine.showLoading();
                    // 指定图表的配置项和数据  发送请求获取当前月份数据
                    this.getLineData(3,this.nowDateStr);
                },
                //从服务器获取折线图数据
                getLineData(id,dateStr){
                    console.log(dateStr);
                    let index = dateStr.indexOf(" - ");
                    let start = dateStr.substring(0,index).replace("年","-").replace("月","-").replace("日","-");
                    let end = dateStr.substring(index+3).replace("年","-").replace("月","-").replace("日","-");
                    start = start.substring(0,start.length-1);
                    end = end.substring(0,end.length-1);
                    console.log(index,start,end);
                    axios.post("<%=basePath%>manager/turnoverStatistics/"+id+"/"+start+"/"+end).then( response => {
                        console.log(response);
                        let lineData = response.data;
                        let option = this.optionLine;
                        option.xAxis[0].data=lineData.dates; //日期 （*号）
                        option.series[0].data = lineData.turnovers;  //借书人数
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
                //初始化营业额统计图

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
                //初始化选择时间的input
               this.initDateInput();
            },

        })
        let vueT = vue;
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