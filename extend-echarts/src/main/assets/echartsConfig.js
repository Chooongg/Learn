var charts;

$(function () {
   init();
});

function init() {
    getECharts();
    var echarts
}

function getECharts() {
    if(charts != undefined){
        return charts;
    }
    var chartsDoc = document.getElementById('charts');
    var height = window.innerHeight;
    $(chartsDoc).css('height', height);
    charts = echarts.init(chartsDoc);
    return charts;
}

/**
 * 构建动态图表
 */
function loadEcharts(eChartJson) {
    showDebugMessage("loadEcharts");
    //
    var option = JSON.parse(eChartJson);
    option = preTask(option);
    getECharts().setOption(option);
}

/*
 *刷新图表
 */
function refreshEchartsWithOption(eChartJson) {
    showDebugMessage("refreshEchartsWithOption");
    var option = JSON.parse(eChartJson);
    option = preTask(option);
    getECharts().setOption(option, true);//刷新，带上第二个参数true
}

/*
 *添加图表事件响应监听
 */
function addEChartActionHandler(eventName) {
    var ecConfig = echarts.config;
    getECharts().on(eventName, addEChartViewAction);
}
function addEChartViewAction(param) {
    alert(JSON.stringify(param));
    Android.addEChartActionHandlerResponseResult(JSON.stringify(param));
}

function preTask(obj) {
    var result;
    if(typeof(obj) == 'object') {
        if(obj instanceof Array) {
            result = new Array();
            for (var i = 0, len = obj.length; i < len ; i++) {
                 result.push(preTask(obj[i]));
            }
            return result;
        } else if(obj instanceof RegExp){
            return obj;
        } else {
            result = new Object();
            for (var prop in obj) {
                result[prop] = preTask(obj[prop]);
            }
            return result;
        }
    } else if(typeof(obj) == 'string'){
        try {
            if(typeof(eval(obj)) == 'function'){
                return eval(obj);
            } else if (typeof(eval(obj) == 'object') && (eval(obj) instanceof Array || eval(obj) instanceof CanvasGradient)) {
                return eval(obj);
            }
        }catch(e) {
            return obj;
        }
        return obj;
    } else {
        return obj;
    }
}