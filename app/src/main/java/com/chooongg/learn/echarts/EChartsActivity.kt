package com.chooongg.learn.echarts

import android.os.Bundle
import androidx.paging.PagingDataAdapter
import com.chooongg.basic.ext.doOnClick
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.learn.databinding.ActivityEchartsBinding
import org.json.JSONArray
import org.json.JSONObject

class EChartsActivity : BasicBindingActivity<ActivityEchartsBinding>() {

    private var change = false

    override fun initView(savedInstanceState: Bundle?) {
        binding.echartsView.setOption(setOption1())
        binding.btnChange.doOnClick {
            if (change) {
                binding.echartsView.setOption(setOption3())
            } else {
                binding.echartsView.setOption(setOption2())
            }
            change = !change
        }
    }

    private fun setOption1() = JSONObject().apply {
        put("color", JSONArray().apply {
            put("#80FFA5")
            put("#00DDFF")
            put("#37A2FF")
            put("#FF0087")
            put("#FFBF00")
        })
        put("title", JSONObject().apply {
            put("text", "渐变堆叠面积图")
        })
        put("tooltip", JSONObject().apply {
            put("trigger", "axis")
            put("axisPointer", JSONObject().apply {
                put("type", "cross")
                put("label", JSONObject().apply {
                    put("backgroundColor", "#6a7985")
                })
            })
        })
        put("grid", JSONObject().apply {
            put("left", "3%")
            put("right", "4%")
            put("bottom", "3%")
            put("containLabel", true)
        })
        put("xAxis", JSONObject().apply {
            put("type", "category")
            put("boundaryGap", false)
            put("data", JSONArray().apply {
                put("Mon")
                put("Tue")
                put("Wed")
                put("Thu")
                put("Fri")
                put("Sat")
                put("Sun")
            })
        })
        put("yAxis", JSONObject().apply {
            put("type", "value")
        })
        put("series", JSONArray().apply {
            put(JSONObject().apply {
                put("name", "Line 1")
                put("type", "line")
                put("stack", "Total")
                put("smooth", true)
                put("lineStyle", JSONObject().apply {
                    put("width", 0)
                })
                put("showSymbol", false)
                put("areaStyle", JSONObject().apply {
                    put("opacity", 0.8)
                    put("color", JSONObject().apply {
                        put("type", "linear")
                        put("x", 0)
                        put("y", 0)
                        put("x2", 0)
                        put("y2", 1)
                        put("colorStops", JSONArray().apply {
                            put(JSONObject().apply {
                                put("offset", 0)
                                put("color", "rgb(128, 255, 165)")
                            })
                            put(JSONObject().apply {
                                put("offset", 1)
                                put("color", "rgb(1, 191, 236)")
                            })
                        })
                    })
                })
                put("emphasis", JSONObject().apply {
                    put("focus", "series")
                })
                put("data", JSONArray().apply {
                    put(140)
                    put(232)
                    put(101)
                    put(264)
                    put(90)
                    put(340)
                    put(250)
                })
            })
            put(JSONObject().apply {
                put("name", "Line 2")
                put("type", "line")
                put("stack", "Total")
                put("smooth", true)
                put("lineStyle", JSONObject().apply {
                    put("width", 0)
                })
                put("showSymbol", false)
                put("areaStyle", JSONObject().apply {
                    put("opacity", 0.8)
                    put("color", JSONObject().apply {
                        put("type", "linear")
                        put("x", 0)
                        put("y", 0)
                        put("x2", 0)
                        put("y2", 1)
                        put("colorStops", JSONArray().apply {
                            put(JSONObject().apply {
                                put("offset", 0)
                                put("color", "rgb(0, 221, 255)")
                            })
                            put(JSONObject().apply {
                                put("offset", 1)
                                put("color", "rgb(77, 119, 255)")
                            })
                        })
                    })
                })
                put("emphasis", JSONObject().apply {
                    put("focus", "series")
                })
                put("data", JSONArray().apply {
                    put(120)
                    put(282)
                    put(111)
                    put(234)
                    put(220)
                    put(340)
                    put(310)
                })
            })
            put(JSONObject().apply {
                put("name", "Line 3")
                put("type", "line")
                put("stack", "Total")
                put("smooth", true)
                put("lineStyle", JSONObject().apply {
                    put("width", 0)
                })
                put("showSymbol", false)
                put("areaStyle", JSONObject().apply {
                    put("opacity", 0.8)
                    put("color", JSONObject().apply {
                        put("type", "linear")
                        put("x", 0)
                        put("y", 0)
                        put("x2", 0)
                        put("y2", 1)
                        put("colorStops", JSONArray().apply {
                            put(JSONObject().apply {
                                put("offset", 0)
                                put("color", "rgb(55, 162, 255)")
                            })
                            put(JSONObject().apply {
                                put("offset", 1)
                                put("color", "rgb(116, 21, 219)")
                            })
                        })
                    })
                })
                put("emphasis", JSONObject().apply {
                    put("focus", "series")
                })
                put("data", JSONArray().apply {
                    put(320)
                    put(132)
                    put(201)
                    put(334)
                    put(190)
                    put(130)
                    put(220)
                })
            })
            put(JSONObject().apply {
                put("name", "Line 4")
                put("type", "line")
                put("stack", "Total")
                put("smooth", true)
                put("lineStyle", JSONObject().apply {
                    put("width", 0)
                })
                put("showSymbol", false)
                put("areaStyle", JSONObject().apply {
                    put("opacity", 0.8)
                    put("color", JSONObject().apply {
                        put("type", "linear")
                        put("x", 0)
                        put("y", 0)
                        put("x2", 0)
                        put("y2", 1)
                        put("colorStops", JSONArray().apply {
                            put(JSONObject().apply {
                                put("offset", 0)
                                put("color", "rgb(255, 0, 135)")
                            })
                            put(JSONObject().apply {
                                put("offset", 1)
                                put("color", "rgb(135, 0, 157)")
                            })
                        })
                    })
                })
                put("emphasis", JSONObject().apply {
                    put("focus", "series")
                })
                put("data", JSONArray().apply {
                    put(220)
                    put(402)
                    put(231)
                    put(134)
                    put(190)
                    put(230)
                    put(120)
                })
            })
            put(JSONObject().apply {
                put("name", "Line 5")
                put("type", "line")
                put("stack", "Total")
                put("smooth", true)
                put("lineStyle", JSONObject().apply {
                    put("width", 0)
                })
                put("showSymbol", false)
                put("areaStyle", JSONObject().apply {
                    put("opacity", 0.8)
                    put("color", JSONObject().apply {
                        put("type", "linear")
                        put("x", 0)
                        put("y", 0)
                        put("x2", 0)
                        put("y2", 1)
                        put("colorStops", JSONArray().apply {
                            put(JSONObject().apply {
                                put("offset", 0)
                                put("color", "rgb(255, 191, 0)")
                            })
                            put(JSONObject().apply {
                                put("offset", 1)
                                put("color", "rgb(224, 62, 76)")
                            })
                        })
                    })
                })
                put("emphasis", JSONObject().apply {
                    put("focus", "series")
                })
                put("data", JSONArray().apply {
                    put(220)
                    put(302)
                    put(181)
                    put(234)
                    put(210)
                    put(290)
                    put(150)
                })
            })
        })
    }

    private fun setOption2() = JSONObject().apply {
        put("title", JSONObject().apply {
            put("text", "坐标轴刻度与标签对齐")
        })
        put("tooltip", JSONObject().apply {
            put("trigger", "shadow")
        })
        put("grid", JSONObject().apply {
            put("left", "3%")
            put("right", "4%")
            put("bottom", "3%")
            put("containLabel", true)
        })
        put("xAxis", JSONArray().apply {
            put(JSONObject().apply {
                put("type", "category")
                put("data", JSONArray().apply {
                    put("Mon")
                    put("Tue")
                    put("Wed")
                    put("Thu")
                    put("Fri")
                    put("Sat")
                    put("Sun")
                })
            })
        })
        put("yAxis", JSONArray().apply {
            put(JSONObject().apply {
                put("type", "value")
            })
        })
        put("series", JSONArray().apply {
            put(JSONObject().apply {
                put("name", "Direct")
                put("type", "bar")
                put("barWidth", "60%")
                put("data", JSONArray().apply {
                    put(10)
                    put(52)
                    put(200)
                    put(334)
                    put(390)
                    put(330)
                    put(220)
                })
            })
        })
    }

    private fun setOption3() = JSONObject().apply {
        put("title", JSONObject().apply {
            put("text", "坐标轴刻度与标签对齐")
        })
        put("tooltip", JSONObject().apply {
            put("trigger", "shadow")
        })
        put("grid", JSONObject().apply {
            put("left", "3%")
            put("right", "4%")
            put("bottom", "3%")
            put("containLabel", true)
        })
        put("xAxis", JSONArray().apply {
            put(JSONObject().apply {
                put("type", "category")
                put("data", JSONArray().apply {
                    put("Mon")
                    put("Tue")
                    put("Wed")
                    put("Thu")
                    put("Fri")
                    put("Sat")
                    put("Sun")
                })
            })
        })
        put("yAxis", JSONArray().apply {
            put(JSONObject().apply {
                put("type", "value")
            })
        })
        put("series", JSONArray().apply {
            put(JSONObject().apply {
                put("name", "Direct")
                put("type", "bar")
                put("barWidth", "60%")
                put("data", JSONArray().apply {
                    put(220)
                    put(330)
                    put(390)
                    put(334)
                    put(200)
                    put(52)
                    put(10)
                })
            })
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.echartsView.removeAllViews()
        binding.echartsView.destroy()
    }
}