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