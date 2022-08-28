package com.wallet.walkthedog.app;

import com.wallet.walkthedog.R;

import java.util.HashMap;
import java.util.Map;

//錯誤碼枚舉類
public enum ErrorCode {
    SERVICE_PASSWORD(2303, R.string.password_error),
    SERVICE_DOG_NULL(2000, R.string.code_2000),
    SERVICE_UNKNOWN_ERRORL_400(400, R.string.unknown_error),
    SERVICE_UNKNOWN_ERRORL_500(500, R.string.unknown_error);

    private int msgType;
    private int message;
    private static Map<Integer, ErrorCode> mapping;

    public int getMsgType() {
        return this.msgType;
    }

    public int getMessage() {
        return message;
    }

    static {
        mapping = new HashMap();


        for (ErrorCode errorCode : values()) {
            if (mapping.put(Integer.valueOf(errorCode.getMsgType()), errorCode) != null)
                throw new IllegalArgumentException("duplicated message type");
        }
    }

    private ErrorCode(int msgType, int message) {
        this.msgType = msgType;
        this.message = message;
    }

    public static ErrorCode getInstance(int msgType) {
        return (ErrorCode) mapping.get(Integer.valueOf(msgType));
    }
}
