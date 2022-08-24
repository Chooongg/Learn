var container = document.getElementById('container');
var charts = echarts.init(container);

/**
 * 构建动态图表
 */
function setOption(eChartJson) {
    Android.showDebugMessage(eChartJson);
    var option = JSON.parse(eChartJson);
    option = preTask(option);
    charts.setOption(option, true);
}

/*
 *添加图表事件响应监听
 */
function addEChartActionHandler(eventName) {
    showDebugMessage("removeEChartActionHandler:" + eventName);
    charts.on(eventName, addEChartViewAction);
}
function addEChartViewAction(param) {
    Android.addEChartActionHandlerResponseResult(JSON.stringify(param));
}

/*
 *移除图表事件响应监听
 */
function removeEChartActionHandler(eventName) {
    showDebugMessage("removeEChartActionHandler:" + eventName);
    charts.un(name, removeEChartViewAction);
}
function removeEChartViewAction(param) {
    showDebugMessage("removeEChartViewAction:" + param);
    Android.removeEChartActionHandlerResponseResult(JSON.stringify(param));
}

function showChartsLoading() {
    charts.showLoading();
}
function hideChartsLoading() {
    charts.hideLoading();
}
