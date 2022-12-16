# Learn
android

learnVersion : [![](https://jitpack.io/v/Chooongg/Learn.svg)](https://jitpack.io/#Chooongg/Learn)

```Groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

```Groovy
dependencies {
    // 核心组件
    implementation "com.github.Chooongg.Learn:core:${learnVersion}"

    implementation "com.github.Chooongg.Learn:form:${learnVersion}"          // 表单组件
    implementation "com.github.Chooongg.Learn:net:${learnVersion}"          // 网络组件
    implementation "com.github.Chooongg.Learn:echarts:${learnVersion}"    // 扩展-event
    implementation "com.github.Chooongg.Learn:eventFlow:${learnVersion}"    // 扩展-event
    implementation "com.github.Chooongg.Learn:stateLayout:${learnVersion}"  // 扩展-状态布局
}
```
