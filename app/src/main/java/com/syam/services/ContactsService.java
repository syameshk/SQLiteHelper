package com.syam.services;

import android.util.Log;

import com.syam.helpers.database.*;
import com.syam.models.User;

import java.util.LinkedList;


/**
 * Created by Syamesh on 02-06-2017.
 */

public class ContactsService {
    private Database database;
    private final String tableName = "UserMaster";

    private IInsertCallback insertCallback;
    private IQueryCallback<User> queryCallback;

    public ContactsService(String database){
        //Get the database instance from the factory class
        this.database = DatabaseFactory.getInstance().getDatabase(database);
        //Create the table if it does not exists
        createTable ();
    }

    public void createTable(){
        //Table rule
        Create table = new Create(tableName)
                .pk("id",true).num("userId")
                .str("phone").str("email").str("name").str("displayName")
                .str("imgUrl").str("userType");

        //Create the table
        database.ExcecuteNonQuery(table.build());
    }

    public long addUser(User user) {
        Insert insert = new Insert(tableName)
                .col("userId",user.getUserId())
                .col("phone",user.getPhone()).col("email",user.getEmail())
                .col("name",user.getName()).col("displayName",user.getDisplayName())
                .col("imgUrl",user.getImgUrl()).col("userType",user.getUserType());
        return database.ExcecuteInsert(insert);
    }

    /**
     * Adds a list of users to database.
     *
     * @param users is the list of users to write
     * @param callback is the callback on complete, can be null as well
     */
    public void addUsers(LinkedList<User> users, IInsertCallback callback) {
        InsertDataTask task = new InsertDataTask(taskCallback,database);
        LinkedList<Insert> inserts = new LinkedList<Insert>();
        for (User user:users) {
            Insert insert = new Insert(tableName)
                    .col("userId",user.getUserId())
                    .col("phone",user.getPhone()).col("email",user.getEmail())
                    .col("name",user.getName()).col("displayName",user.getDisplayName())
                    .col("imgUrl",user.getImgUrl()).col("userType",user.getUserType());
            inserts.add(insert);
        }
        insertCallback = callback;
        task.execute(inserts);
    }

    public int updateUser(User user){

        int result = -1;

        Update update = new Update(tableName);

        if(user.getImgUrl() != null && !user.getImgUrl().isEmpty()) { update.col("imgUrl",user.getImgUrl()); }

        if(user.getEmail() != null && !user.getEmail().isEmpty()) { update.col("email",user.getEmail()); }

        if(user.getName() != null && !user.getName().isEmpty()) { update.col("name",user.getName()); }

        if(user.getUserType() != null && !user.getUserType().isEmpty()) { update.col("userType",user.getUserType()); }

        if (user.getUserId() > 0) {
            update.col("userType",user.getUserId());
            result = database.ExcecuteUpdate(update);
        }
        return result;
    }

    public int deleteTable(){
        return database.DeleteTable(new Delete(tableName));
    }

    public void dropTable(){
        database.DropTable(tableName);
    }

    public User getUser(int userId){
        Query query = new Query(tableName).equal("userId",userId);
        LinkedList<User> users = convertFromCursor(database.Excecute(query));
        return users.size() > 0 ? users.get(0) : null;
    }

    public User getUser(String phone){
        Query query = new Query(tableName).equal("phone",phone);
        LinkedList<User> users = convertFromCursor(database.Excecute(query));
        return users.size() > 0 ? users.get(0) : null;
    }

    public LinkedList<User> getContacts(int index, int limit){
        Query query = new Query(tableName).offset(index).limit(limit);
        return convertFromCursor(database.Excecute(query));
    }

    public LinkedList<User> getContacts(){
        Query query = new Query(tableName);
        return convertFromCursor(database.Excecute(query));
    }

    /**
     * Load all contacts from database.
     *
     * @param callback is the callback on loading completed
     */
    public void getContacts(IQueryCallback<User> callback){

        QueryTask task = new QueryTask(taskCallback, database);
        Query query = new Query(tableName);
        this.queryCallback = callback;
        task.execute(query);
    }

    /**
     * Convert the result cursor to list of users.
     *
     * @param table is the cursor contains user table
     * @return the list of users from the cursor
     */
    public static LinkedList<User> convertFromCursor(Cursor table){
        LinkedList<User> users = new LinkedList<User>();

        //userId, phone, email, name, imgUrl, userType,password
        while(table.hasNext()){
            int userId = table.num("userId");
            String phone = table.str("phone");
            String email = table.str("email");
            String name = table.str("name");
            String displayName = table.str("displayName");
            String imgUrl = table.str("imgUrl");
            String userType = table.str("userType");

            User user = new User(imgUrl, phone, email, name, displayName, userType, userId);
            users.add (user);
        }
        return users;
    }

    private ITaskCallback taskCallback = new ITaskCallback() {
        @Override
        public void insertCallback(int rows, long time) {
            Log.d("Database","Inserted, row:"+rows+", time:"+time);
            if(insertCallback != null){
                insertCallback.onSuccess(rows,time);
                insertCallback = null;
            }
        }

        @Override
        public void insertFailedCallback(String message) {

        }

        @Override
        public void queryFailedCallback(String message) {

        }

        @Override
        public void queryCallback(Cursor cursor, long time) {
            Log.d("Database","Inserted, row:"+cursor.getCount()+", time:"+time);
            //Run call back
            if(queryCallback != null){
                queryCallback.onSuccess(convertFromCursor(cursor),time);
                queryCallback = null;
            }
        }
    };

}
