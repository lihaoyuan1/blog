<template>
    <div>
        <el-row type="flex" align="middle">
            <el-col :span="8" style="text-align: center">
                <h2>{{topInfo.views ? topInfo.views:0}}</h2>
                <span style="color: #909399">总访问量</span>
            </el-col>
            <el-col :span="1" style="height: 60px;position: relative;">
                <el-divider direction="vertical" class="vertical-divider" />
            </el-col>
            <el-col :span="6" style="text-align: center">
                <h2>{{topInfo.count ? topInfo.count:0}}</h2>
                <span style="color: #909399">文章数</span>
            </el-col>
            <el-col :span="1"  style="height: 60px;position: relative">
                <el-divider direction="vertical" class="vertical-divider" />
            </el-col>
            <el-col :span="8" style="text-align: center">
                <h2>18</h2>
                <span style="color: #909399">评论数</span>
            </el-col>
        </el-row>
        <el-divider />
        <e-chart
                :options="orgOptions"
                style="width: 100%"
                :auto-resize="true" />
        <h3 style="text-align: center">当天访问情况</h3>
        <el-table
                :data="tableData"
                height="400"
                border
                stripe
                style="width: 100%">
            <el-table-column
                    label="序号"
                    align="center"
                    width="60">
                <template slot-scope="scope">
                    {{scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column
                    prop="ip"
                    label="ip地址"
                    align="center"
                    width="260">
            </el-table-column>
            <el-table-column
                    prop="address"
                    label="地理地址"
                    align="center">
            </el-table-column>
            <el-table-column
                    prop="time"
                    align="center"
                    label="访问时间"
                    width="260">
            </el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
    name: "home",
    data() {
        return{
            topInfo: {},
            orgOptions: {
                title: {
                    text: '最近10天访问情况统计',
                    x: 'center'
                },
                xAxis: {
                    type: 'category',
                    name: '日期/号',
                    data: ['04-01', '04-02', '04-03', '04-04', '04-05', '04-06', '04-07', '04-08', '04-09', '04-10']
                },
                yAxis: {
                    name: '访问量/次',
                    type: 'value'
                },
                tooltip: {},
                series: [{
                    name: '访问量',
                    type: 'line',
                    smooth: true,
                    symbolSize: 10,
                    itemStyle: {
                        normal: {
                            color: '#ffd04b', //改变折线点的颜色
                            lineStyle: {
                                color: '#ffd04b' //改变折线颜色
                            }
                        }
                    },
                    lineStyle: {
                        normal: {
                            color: "#ffd04b",
                            width: 3
                        }
                    },
                    data: [820, 932, 901, 934, 1290, 1330, 1320, 820, 932, 901]
                }]
            },
            tableData: [{
                time: '2016-05-03 12:43:59',
                ip: '192.168.111.104',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                time: '2016-05-03 12:43:59',
                ip: '192.168.111.104',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                time: '2016-05-03 12:43:59',
                ip: '192.168.111.104',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                time: '2016-05-03 12:43:59',
                ip: '192.168.111.104',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                time: '2016-05-03 12:43:59',
                ip: '192.168.111.104',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                time: '2016-05-03 12:43:59',
                ip: '192.168.111.104',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                time: '2016-05-03 12:43:59',
                ip: '192.168.111.104',
                address: '上海市普陀区金沙江路 1518 弄'
            },{
                time: '2016-05-03 12:43:59',
                ip: '192.168.111.104',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                time: '2016-05-03 12:43:59',
                ip: '192.168.111.104',
                address: '上海市普陀区金沙江路 1518 弄'
            }]
        }
    },
    methods: {
        getTopInfo () {
            this.get('/adminHome/topInfo', {}).then(res => {
                this.topInfo = res.data;
                this.$emit('loadingComplete');
            })
        }
    },
    created() {
        this.$emit('loadingStart', 1);
        this.getTopInfo();
    }
}
</script>

<style scoped>
.vertical-divider{
    position: absolute;
    top: 0;
    left: 50%;
    height: 60px;
    margin: 0;
}
</style>