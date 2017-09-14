package sqlitetest.syam.com.sqlitetest;

/**
 * Created by Syamesh on 01-06-2017.
 */

public class Message {
    public int ctId;
    public int mtId;
    public int messageId;
    public String senderPhone;
    public long time;

    public TextContent content;

    public Message() {
        this.ctId = 0;
        this.mtId = 0;
        this.messageId = 0;
        this.senderPhone = "";
        this.time = 0;
    }


    public Message(int ctId, int mtId, int messageId, String senderPhone, long time) {
        this.ctId = ctId;
        this.mtId = mtId;
        this.messageId = messageId;
        this.senderPhone = senderPhone;
        this.time = time;
    }
}
