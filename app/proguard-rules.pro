#代码混淆压缩比，在0~7之间
-optimizationpasses 5
#避免混淆泛型,注解,内部类
-keepattributes Signature,Annotation,InnerClasses
#google推荐算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/,!class/merging/
#保持 native 的方法不去混淆
-keepclasseswithmembernames class * { native <methods>; }
#指定在处理期间输出更多信息。如果程序因异常终止，此选项将打印出整个堆栈跟踪，而不仅仅是异常消息。
-verbose
#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}