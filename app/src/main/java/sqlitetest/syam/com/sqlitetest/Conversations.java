package sqlitetest.syam.com.sqlitetest;

/**
 * Created by Syamesh on 01-06-2017.
 */

public class Conversations {

    public int ctId;
    public int conversationId;
    public String phone;

    public Message message;


    public  Conversations() {
    }

    public Conversations(int ctId, int conversationId, String phone) {
        this.ctId = ctId;
        this.conversationId = conversationId;
        this.phone = phone;
    }
}
