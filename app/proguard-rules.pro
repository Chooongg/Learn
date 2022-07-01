#代码混淆压缩比，在0~7之间
-optimizationpasses 5
#避免混淆泛型
-keepattributes Signature
#保留Annotation不混淆
-keepattributes Annotation,InnerClasses
#google推荐算法
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/,!class/merging/
#保持 native 的方法不去混淆
-keepclasseswithmembernames class * { native <methods>; }

#-keep class * implements androidx.viewbinding.ViewBinding
#-keepclassmembers class ** implements androidx.viewbinding.ViewBinding {
#  public static ** bind(***);
#  public static ** inflate(***);
#}

#-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
#  public static * inflate(android.view.LayoutInflater);
#  public static * inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
#  public static * bind(android.view.View);
#}
#
#-keep class androidx.startup.** { *; }
#-keep class * extends androidx.startup.Initializer

#-keepattributes Signature
#-keepclassmembers class * implements androidx.viewbinding.ViewBinding {
#  public static * inflate(android.view.LayoutInflater);
#  public static * inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
#  public static * bind(android.view.View);
#}