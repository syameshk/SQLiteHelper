package sqlitetest.syam.com.sqlitetest;

import android.util.Log;

import com.syam.helpers.database.*;

/**
 * Created by Syamesh on 02-06-2017.
 */

public class ChatService {

    private Database database;

    private String selectconv = "SELECT "+
            "A.conv_trans_id, A.conv_id, A.user_id as user, C.msg_trans_id, C.msg_id, C.time, C.sender_id, C.msg_type,C.msg_status, B.text, D.asset_id, D.ar_json, D.image_url "+
            "FROM ChatMaster A "+
            "LEFT JOIN (SELECT *, MAX(timestamp) as time from MessageMaster GROUP BY conv_trans_id) C ON A.conv_trans_id = C.conv_trans_id "+
            "LEFT JOIN  MessageContent B on B.msg_trans_id = C.msg_trans_id LEFT JOIN ARContentMaster D on D.msg_trans_id = C.msg_trans_id ORDER BY  C.timestamp DESC LIMIT %s,%s";

    public ChatService(String name) {
        this.database = DatabaseFactory.getInstance().getDatabase("name");

        createConversationTable();
        createMessageTable();
        createTextContentTable();
    }

    public ChatService(Database database) {
        this.database = database;

        createConversationTable();
        createMessageTable();
        createTextContentTable();
    }

    public void createConversationTable() {
        Create conv = new Create("ChatMaster").pk("ctId",true).num("conversationId").str("phone");
        //Create the table
        database.ExcecuteNonQuery(conv.build());
    }

    public void createMessageTable() {
        Create msg = new Create("MessageMaster").pk("mtId",true).num("messageId").num("ctId").str("senderPhone")
                .num("time");
        //Create the table
        database.ExcecuteNonQuery(msg.build());
    }

    public void createTextContentTable() {
        Create txt = new Create("TextContent").num("mtId").str("Text");
        //Create the table
        database.ExcecuteNonQuery(txt.build());
    }

    public void AddConversation(Conversations conv){
        int ctId = (int)AddConversation(conv.phone);
        Log.d("Database","ctid :"+ctId);
        conv.ctId = ctId;
        conv.message.ctId = ctId;

        int mtId = (int) AddMessage(conv.message);

        Log.d("Database","mtid :"+mtId);

        conv.message.mtId = mtId;

        int textId = (int) AddTextContent(conv.message.content);

        Log.d("Database","textId :"+textId);

    }

    public Conversations GetConversations(String user){
        QueryFull convfull = SqlParser.query();
        convfull
                .table("ChatMaster")
                .left("MessageMaster","MessageMaster")
                .on("ChatMaster","ctId","=","MessageMaster","ctId")
                .left("TextContent","TextContent")
                .on("MessageMaster","mtId","=","TextContent","mtId");

        convfull.build();

        int offset = 0;
        int limit = 50;
        String query = String.format(selectconv,offset, limit);

        Log.d("Database",query);

        return null;
    }

    public long AddConversation(String user){
        Insert conv = new Insert("ChatMaster")
                .col("conversationId", 0)
                .col("phone", user);
        return database.ExcecuteInsert(conv);
    }

    public long AddMessage(Message message){
        Insert msg = new Insert("MessageMaster")
                .col("messageId", message.messageId)
                .col("ctId", message.ctId)
                .col("senderPhone", message.senderPhone)
                .col("time",message.time);

        return database.ExcecuteInsert(msg);
    }

    public long AddTextContent(TextContent content){
        Insert txt = new Insert("TextContent")
                .col("mtId", content.mtId)
                .col("text",content.text);

        return database.ExcecuteInsert(txt);
    }
}
