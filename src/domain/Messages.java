package domain;

import java.io.Serializable;
import java.util.Date;

public class Messages implements Serializable {
    private String msg_id;
    private String sender_email;
    private String receiver_email;
    private String content;
    private Date date;

    public Messages() {
        super();
    }

    public Messages(String msg_id, String sender_email, String receiver_email, String content, Date date) {
        super();
        this.msg_id = msg_id;
        this.sender_email = sender_email;
        this.receiver_email = receiver_email;
        this.content = content;
        this.date = date;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public String getSender_email() {
        return sender_email;
    }

    public void setSender_email(String sender_email) {
        this.sender_email = sender_email;
    }

    public String getReceiver_email() {
        return receiver_email;
    }

    public void setReceiver_email(String receiver_email) {
        this.receiver_email = receiver_email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Messages [msg_id=" + msg_id
                + ", sender_email=" + sender_email
                + ", receiver_email=" + receiver_email
                + ", content=" + content
                + ", date=" + date + "]";
    }
}
