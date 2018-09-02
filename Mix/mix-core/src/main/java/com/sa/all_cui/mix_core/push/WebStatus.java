package com.sa.all_cui.mix_core.push;

/**
 * Created by all-cui on 2017/11/22.
 */

@SuppressWarnings("WeakerAccess")
public class WebStatus {
    //连接中
    public static final int WS_CONNETING = 0;
    //已经连接
    public static final int WS_CONNECTED = 1;
    //重新连接
    public static final int WS_RECONNECT = 2;
    //连接失败
    public static final int WS_DISCONNECT = -1;

    public class Code {
        public static final int NORMAL_CLOSE = 1001;
        public static final int UNNORMAL_CLOSE = -1001;
    }

    public class Tip {
        public static final String NORMAL_CLOSE = "WebSocket is normal close";
        public static final String UN_NORMAL_CLOSE = "WesSocket is unnormal close";
    }
}
