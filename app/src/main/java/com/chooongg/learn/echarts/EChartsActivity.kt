package com.chooongg.learn.echarts

import android.os.Bundle
import com.chooongg.basic.ext.doOnClick
import com.chooongg.basic.ext.json
import com.chooongg.basic.ext.jsonArray
import com.chooongg.core.activity.BasicBindingActivity
import com.chooongg.learn.databinding.ActivityEchartsBinding

class EChartsActivity : BasicBindingActivity<ActivityEchartsBinding>() {

    private var change = false

    override fun initView(savedInstanceState: Bundle?) {
        binding.echartsView.setOption(option1())
        binding.btnChange.doOnClick {
            if (change) {
                binding.echartsView.setOption(option3())
            } else {
                binding.echartsView.setOption(option2())
            }
            change = !change
        }
    }

    private fun option1() = json {
        "color" to jsonArray("#80FFA5", "#00DDFF", "#37A2FF", "#FF0087", "#FFBF00")
        "title" to json {
            "text" to "渐变堆叠面积图"
        }
        "tooltip" to json {
            "trigger" to "axis"
            "axisPointer" to json {
                "type" to "cross"
                "label" to json {
                    "backgroundColor" to "#6a7985"
                }
            }
        }
        "grid" to json {
            "left" to "0%"
            "right" to "0%"
            "bottom" to "0%"
            "containLabel" to true
        }
        "xAxis" to json {
            "type" to "category"
            "boundaryGap" to false
            "data" to jsonArray("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        }
        "yAxis" to json {
            "type" to "value"
        }
        "series" to jsonArray(
            json {
                "name" to "Line 1"
                "type" to "line"
                "stack" to "Total"
                "smooth" to true
                "lineStyle" to json {
                    "width" to 0
                }
                "showSymbol" to false
                "areaStyle" to json {
                    "opacity" to 0.8
                    "color" to json {
                        "type" to "linear"
                        "x" to 0
                        "y" to 0
                        "x2" to 0
                        "y2" to 1
                        "colorStops" to jsonArray(
                            json {
                                "offset" to 0
                                "color" to "rgb(128, 255, 165)"
                            }, json {
                                "offset" to 1
                                "color" to "rgb(1, 191, 236)"
                            }
                        )
                    }
                }
                "emphasis" to json {
                    "focus" to "series"
                }
                "data" to jsonArray(140, 232, 101, 264, 90, 340, 250)
            }, json {
                "name" to "Line 2"
                "type" to "line"
                "stack" to "Total"
                "smooth" to true
                "lineStyle" to json {
                    "width" to 0
                }
                "showSymbol" to false
                "areaStyle" to json {
                    "opacity" to 0.8
                    "color" to json {
                        "type" to "linear"
                        "x" to 0
                        "y" to 0
                        "x2" to 0
                        "y2" to 1
                        "colorStops" to jsonArray(
                            json {
                                "offset" to 0
                                "color" to "rgb(0, 221, 255)"
                            }, json {
                                "offset" to 1
                                "color" to "rgb(77, 119, 255)"
                            }
                        )
                    }
                }
                "emphasis" to json {
                    "focus" to "series"
                }
                "data" to jsonArray(120, 282, 111, 234, 220, 340, 310)
            }, json {
                "name" to "Line 3"
                "type" to "line"
                "stack" to "Total"
                "smooth" to true
                "lineStyle" to json {
                    "width" to 0
                }
                "showSymbol" to false
                "areaStyle" to json {
                    "opacity" to 0.8
                    "color" to json {
                        "type" to "linear"
                        "x" to 0
                        "y" to 0
                        "x2" to 0
                        "y2" to 1
                        "colorStops" to jsonArray(
                            json {
                                "offset" to 0
                                "color" to "rgb(55, 162, 255)"
                            }, json {
                                "offset" to 1
                                "color" to "rgb(116, 21, 219)"
                            }
                        )
                    }
                }
                "emphasis" to json {
                    "focus" to "series"
                }
                "data" to jsonArray(320, 132, 201, 334, 190, 130, 220)
            }, json {
                "name" to "Line 4"
                "type" to "line"
                "stack" to "Total"
                "smooth" to true
                "lineStyle" to json {
                    "width" to 0
                }
                "showSymbol" to false
                "areaStyle" to json {
                    "opacity" to 0.8
                    "color" to json {
                        "type" to "linear"
                        "x" to 0
                        "y" to 0
                        "x2" to 0
                        "y2" to 1
                        "colorStops" to jsonArray(
                            json {
                                "offset" to 0
                                "color" to "rgb(255, 0, 135)"
                            }, json {
                                "offset" to 1
                                "color" to "rgb(135, 0, 157)"
                            }
                        )
                    }
                }
                "emphasis" to json {
                    "focus" to "series"
                }
                "data" to jsonArray(220, 402, 231, 134, 190, 230, 120)
            }, json {
                "name" to "Line 5"
                "type" to "line"
                "stack" to "Total"
                "smooth" to true
                "lineStyle" to json {
                    "width" to 0
                }
                "showSymbol" to false
                "areaStyle" to json {
                    "opacity" to 0.8
                    "color" to json {
                        "type" to "linear"
                        "x" to 0
                        "y" to 0
                        "x2" to 0
                        "y2" to 1
                        "colorStops" to jsonArray(
                            json {
                                "offset" to 0
                                "color" to "rgb(255, 191, 0)"
                            }, json {
                                "offset" to 1
                                "color" to "rgb(224, 62, 76)"
                            }
                        )
                    }
                }
                "emphasis" to json {
                    "focus" to "series"
                }
                "data" to jsonArray(220, 302, 181, 234, 210, 290, 150)
            }
        )
    }

    private fun option2() = json {
        "title" to json {
            "text" to "坐标轴刻度与标签对齐"
        }
        "tooltip" to json {
            "trigger" to "shadow"
        }
        "grid" to json {
            "left" to "0%"
            "right" to "0%"
            "bottom" to "0%"
            "containLabel" to true
        }
        "xAxis" to json {
            "type" to "category"
            "data" to jsonArray("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        }
        "yAxis" to json {
            "type" to "value"
        }
        "series" to json {
            "name" to "Direct"
            "type" to "bar"
            "barWidth" to "60%"
            "itemStyle" to json {
                "shadowBlur" to 10
                "shadowColor" to "rgba(128,128,128,0.5)"
            }
            "data" to jsonArray(10, 52, 200, 334, 390, 330, 220)
        }
    }

    private fun option3() = json {
        "title" to json {
            "text" to "坐标轴刻度与标签对齐"
        }
        "tooltip" to json {
            "trigger" to "shadow"
        }
        "grid" to json {
            "left" to "0%"
            "right" to "0%"
            "bottom" to "0%"
            "containLabel" to true
        }
        "xAxis" to json {
            "type" to "category"
            "data" to jsonArray("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
        }
        "yAxis" to json {
            "type" to "value"
        }
        "series" to json {
            "name" to "Direct"
            "type" to "bar"
            "barWidth" to "60%"
            "data" to jsonArray(484, 751, 914, 534, 200, 483, 184)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.echartsView.removeAllViews()
        binding.echartsView.destroy()
    }
}