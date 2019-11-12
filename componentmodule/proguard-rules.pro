# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# 参考位置：  https://blog.csdn.net/wzy_1988/article/details/50061501
# https://www.bbsmax.com/A/amd07alj5g/
-libraryjars <java.home>/lib/rt.jar
#-libraryjars  sdk/platforms/android-24/android.jar
-optimizations !code/allocation/variable
-dontwarn
# 指定代码的压缩级别
-optimizationpasses 3
#混淆时不会产生形形色色的类名
-dontusemixedcaseclassnames
#指定不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
# 混淆时是否做预校验
-dontpreverify
# 混淆时是否记录日志
-verbose
#忽略警告，避免打包时某些警告出现
-ignorewarnings
#混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护指定的类文件和类的成员
-keep public class * extends android.app.Activity
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
#-libraryjars <java.home>/lib/rt.jar
#-libraryjars sdk/platforms/android-24/android.jar
# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
# 保留support下的所有类及其内部类
-keep class android.support.** {*;}
# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}


-dontskipnonpubliclibraryclassmembers
-overloadaggressively
-repackageclasses ''
-allowaccessmodification
-dontnote
# 不优化代码
-dontoptimize

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService

# 防止与js交互的方法被 混淆
-keep class com.shuiyinhuo.component.mixdev.jinbean.js.JsJniEntity { *; }
-keep class com.shuiyinhuo.component.mixdev.jinbean.pro.AndroidToJsSignalCommunicationProxy {*; }
# 保持某个包下的所有类不被混淆
-keep class com.shuiyinhuo.component.mixdev.demo.**
-keep class com.shuiyinhuo.component.mixdev.proxy.**
-keep class com.shuiyinhuo.component.mixdev.activity.**
