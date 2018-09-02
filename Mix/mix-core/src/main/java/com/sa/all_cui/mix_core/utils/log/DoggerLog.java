package com.sa.all_cui.mix_core.utils.log;

import com.orhanobut.logger.Logger;

/**
 * Created by all-cui on 2017/8/21.
 *
 *
 V—Verbose（啰嗦，最低级别，开发调试中的一些详细信息，仅在开发中使用，不可在发布产品中输出，不是很常见，包含诸如方法名，变量值之类的信息）
 D—Debug（调试，用于调试的信息，可以在发布产品中关闭，比较常见，开发中经常选择输出此种级别的日志，有时在beta版应用中出现）
 I—Info（信息，该等级日志显示运行状态信息，可在产品出现问题时提供帮助，从该级别开始的日志通常包含完整意义的英语语句和调试信息，是最常见的日志级别）
 W—Warning（警告，运行出现异常即将发生错误或表明已发生非致命性错误，该级别日志通常显示出执行过程中的意外情况，例如将try-catch语句块中的异常打印堆栈轨迹之后可输出此种级别日志）
 E—Error（错误，已经出现可影响运行的错误，比如应用crash时输出的日志）
 F—Fatal（严重错误，比error级别更高，目前我只在android系统内核发出的日志中看到此种级别。在Android6.0以前表明开发者认为绝对不应该出现的错误，在此以后一般开发场景下绝不应该输出此种级别的日志）
 S—Silent（寂静，最高级别，没有一条日志会属于这个级别，仅仅作为关闭logcat输出的过滤器参数）
 */

public class DoggerLog {

    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;

    private static final int NOTHING = 6;

    //默认打印当前的类名和方法名称
    private static String getDefaultName(){
        /*StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String className = stackTrace[1].getClassName();
        String methodName = stackTrace[1].getMethodName();
        "className:"+className+"---methodName:"+methodName;*/
        return "DoggerLogger";
    }
    //控制log的等级
    private static final int LEVEL = VERBOSE;


    public static void v(String tag, String msg){
        if (LEVEL <= VERBOSE){
            Logger.t(tag).v(msg);
        }
    }

    public static void v(String msg){
        v(getDefaultName(),msg);
    }

    public static void d(String tag, String msg){
        if (LEVEL <= DEBUG){
            Logger.t(tag).d(msg);
        }
    }

    public static void d( String msg){
        d(getDefaultName(),msg);
    }

    public static void i(String tag, String msg){
        if (LEVEL <= INFO){
            Logger.t(tag).i(msg);
        }
    }

    public static void i(String msg){
        i(getDefaultName(),msg);
    }

    public static void w(String tag, String msg){
        if (LEVEL <= WARN){
            Logger.t(tag).w(msg);
        }
    }

    public static void w( String msg){
        w(getDefaultName(),msg);
    }

    public static void json(String tag, String msg){
        if (LEVEL <= WARN){
            Logger.t(tag).json(msg);
        }
    }

    public static void json( String msg){
        json(getDefaultName(),msg);
    }

    public static void e(String tag, String msg){
        if (LEVEL <= ERROR){
            Logger.t(tag).e(msg);
        }
    }

    public static void e(String msg){
        e(getDefaultName(),msg);
    }
}
