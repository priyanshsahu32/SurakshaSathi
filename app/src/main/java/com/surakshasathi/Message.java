package com.surakshasathi;

public class Message {
    public static String send_by_me = "me";
    public static String send_by_bot = "bot";
    String message;
    String sendBy;

    public Message(String msg, String sendBy) {
        this.message = msg;
        this.sendBy = sendBy;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }
}
