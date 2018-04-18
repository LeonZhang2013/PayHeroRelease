# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-dontoptimize #关闭代码二进制优化
-dontpreverify #不预校验
-dontshrink #不进行代码压缩
-dontskipnonpubliclibraryclasses #开启对非公共库类的混淆
-dontusemixedcaseclassnames #关闭类名的混合混淆方式，android默认配置，建议使用
-allowaccessmodification
-keepattributes *Annotation*,Exceptions,InnerClasses,Signature,Deprecated,EnclosingMethod
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*


#支付SDK
-keep class com.bill99.smartpos.sdk.api.** { *; }
-keep interface com.bill99.smartpos.sdk.api.** { *; }

-keep class com.bill99.smartpos.sdk.core.base.model.http.** { *; }
-keep class com.bill99.smartpos.sdk.core.payment.cp.model.http.** { *; }
-keep class com.bill99.smartpos.sdk.core.payment.init.model.http.** { *; }
-keep class com.bill99.smartpos.sdk.core.payment.other.model.http.** { *; }
-keep class com.bill99.smartpos.sdk.core.payment.scan.model.http.** { *; }
-keep class com.bill99.smartpos.porting.** { *; }
-keep interface com.bill99.smartpos.porting.** { *; }

#新大陆
#若使用mesdk-log4j-adapter,则以下几项需要追加
-dontwarn org.apache.**
-dontwarn javax.**
-dontwarn org.apache.**
-dontwarn okio.**

-adaptresourcefilenames **/*.properties,**/*.gif,**/*.dtd
-adaptresourcefilecontents
#若需要混淆im81的应用，需要加入该项
-dontwarn android.newland.**
#跳过sdk已经混淆的部分
#meSdk
-keep class android.newland.** {*;}
-keep class com.a.a.** {*;}
-keep class com.newland.** {*;}

-keep class com.newland.smartpos.porting.impl.** { *; }
-keep interface com.newland.smartpos.porting.impl.** { *; }
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
   void add*(***);
}

-dontwarn com.alibaba.fastjson.**
-keepattributes Signature
-keepattributes *Annotation*

#优博讯混淆 i9000S
-keep class android.content.pm.**{*;}
-keep class android.device.**{*;}
-keep class com.imagealgorithm.scansdk.**{*;}
-keep class com.imagealgorithmlab.barcode.**{*;}
-keep class com.urovo.smartpos.porting.impl.**{*;}
-keep class com.urovo.printer.PrintContentBean{*;}
-keep class com.urovo.printer.PrintFormatBean{*;}
-keep class com.jniexport.**{*;}
-keep class com.google.gson.**{*;}
-keep class org.apache.log4j.**{*;}
-keep class de.mindpipe.android.logging.log4j.**{*;}

#联迪 保留接口类不被混淆
-keep class com.landicorp.android.scan.** {*; }
-keep class com.landicorp.android.scanview.** {*; }
-keep class com.landicorp.pinpad.** {*; }
-keep class com.landicorp.security.common.** {*; }
-keep class com.landicorp.android.eptapi.** {*; }
-keep class com.landi.smartpos.porting.impl.**{*;}
-dontwarn android.os.**



#=======txb=========

#retrofit2  混淆
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod
#-keep class com.mvp.entitry.**{*;}//改成自己的实体类包
-keep class com.smartpos.payhero.txb.bean.**{*;}

## Retrofit
#-dontnote retrofit2.Platform
#-dontnote retrofit2.Platform$IOS$MainThreadExecutor
#-dontwarn retrofit2.Platform$Java8
#-keepattributes Signature
#-keepattributes Exceptions
#
## okhttp
#-dontwarn okio.**
#
## Gson
#-keep class com.example.testing.retrofitdemo.bean.**{*;} # 自定义数据模型的bean目录
