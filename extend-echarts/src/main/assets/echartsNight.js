var charts;

$(function () {
    getECharts();
});

function getECharts(){
    if (charts != undefined){
        return charts;
    }
    document.create
    var container = document.getElementById('container');
    var height = window.innerHeight;
    Android.showDebugMessage("height" + height.toString());
    $(container).css('height', height);
    charts = echarts.init(container, 'dark');
    return charts;
}

/**
 * 构建动态图表
 */
function setOption(eChartJson) {
    Android.showDebugMessage(eChartJson);
    var option = JSON.parse(eChartJson);
    option = preTask(option);
    getECharts().setOption(option, true);
}

/*
 *添加图表事件响应监听
 */
function addEChartActionHandler(eventName) {
    showDebugMessage("removeEChartActionHandler:" + eventName);
    getECharts().on(eventName, addEChartViewAction);
}
function addEChartViewAction(param) {
    Android.addEChartActionHandlerResponseResult(JSON.stringify(param));
}

/*
 *移除图表事件响应监听
 */
function removeEChartActionHandler(eventName) {
    showDebugMessage("removeEChartActionHandler:" + eventName);
    getECharts().un(name, removeEChartViewAction);
}
function removeEChartViewAction(param) {
    showDebugMessage("removeEChartViewAction:" + param);
    Android.removeEChartActionHandlerResponseResult(JSON.stringify(param));
}

function showChartsLoading() {
    getECharts().showLoading();
}
function hideChartsLoading() {
    getECharts().hideLoading();
}
