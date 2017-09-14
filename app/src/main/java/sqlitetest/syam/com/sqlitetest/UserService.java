package sqlitetest.syam.com.sqlitetest;

import com.syam.helpers.database.*;
import com.syam.models.User;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Syamesh on 31-05-2017.
 */

public class UserService {
    Database database;

    public UserService(Database database) {
        this.database = database;
        CreateTable();
    }

    public void CreateTable() {
        /*
        public int userId;
	public string phone;
	public string email;
	public string name;

	public string gender;
	public string nickname;
	public long dateOfBirth;
	public string imgUrl;
	public string userType;
	public string pass;
	public CountryData countryData;
	public string accessToken;
         */
        Create table = new Create("User")
                .pk("userId")
                .str("phone").str("email").str("name").str("nickname").str("gender")
                .str("imgUrl").str("userType").str("accessToken").str("pass")
                .num("dateOfBirth").num("countryId");

        Log.d("Database",table.build());

        //Create the table
        database.ExcecuteNonQuery(table.build());

    }

    public long AddUser(User user){
        Insert insert = new Insert("User")
                .col("userId",user.userId)
                .col("phone",user.phone).col("email",user.email).col("name",user.name).col("nickname",user.nickname)
                .col("gender",user.gender).col("imgUrl",user.imgUrl).col("userType",user.userType).col("accessToken",user.accessToken)
                .col("dateOfBirth",user.dateOfBirth).col("countryId",user.countryId);

        //Log.d("Database",insert.build());

        return database.ExcecuteInsert(insert);
    }

    public long AddUsers(ArrayList<User> users){

        InsertDataTask task = new InsertDataTask(callback,database);
        ArrayList<Insert> inserts = new ArrayList<Insert>();
        for (User user:users) {
            Insert insert = new Insert("User")
                    .col("userId",user.userId)
                    .col("phone",user.phone).col("email",user.email).col("name",user.name).col("nickname",user.nickname)
                    .col("gender",user.gender).col("imgUrl",user.imgUrl).col("userType",user.userType).col("accessToken",user.accessToken)
                    .col("dateOfBirth",user.dateOfBirth).col("countryId",user.countryId);
            inserts.add(insert);
        }
        task.execute(inserts);
        return -1;
    }

    public User GetUser(int userId){
        User user = null;
        Query query = new Query("User")
                .equal("userId",userId);

        Log.d("Database",query.build());

        Cursor result = database.Excecute(query);



        Log.d("Database",result.toString());

        Log.d("Database","Index :"+ result.getCount());

        if(result.hasNext()){
            user = new User();
            user.userId = result.num("userId");
            user.phone = result.str("phone");
            user.email = result.str("email");
            user.name = result.str("name");
            user.nickname = result.str("nickname");
            user.imgUrl = result.str("imgUrl");
            user.gender = result.str("gender");
            user.userType = result.str("userType");

            user.accessToken = result.str("gender");
            user.dateOfBirth = result.lon("gender");
            user.countryId = result.num("gender");
        }
        return user;
    }

    public ArrayList<User> GetUser(int index, int limit){
        ArrayList<User> list = new ArrayList<User>();
        Query query = new Query("User")
                .offset(index)
                .limit(limit);

        Log.d("Database","Query :"+ query.build());
        Cursor cursor = database.Excecute(query);
        while (cursor.hasNext()){
            User user = new User();
            user.userId = cursor.num("userId");
            user.phone = cursor.str("phone");
            user.email = cursor.str("email");
            user.name = cursor.str("name");
            user.nickname = cursor.str("nickname");
            user.imgUrl = cursor.str("imgUrl");
            user.gender = cursor.str("gender");
            user.userType = cursor.str("userType");

            user.accessToken = cursor.str("gender");
            user.dateOfBirth = cursor.lon("gender");
            user.countryId = cursor.num("gender");

            list.add(user);
        }
        return list;
    }

    public int DeleteUsers(){
        return database.DeleteTable(new Delete("User"));
    }

    ITaskCallback callback = new ITaskCallback() {
        @Override
        public void insertCallback(int rows, long time) {
            Log.d("Database","Inserted, row:"+rows+", time:"+time);
        }

        @Override
        public void queryCallback(Cursor cursor, long time) {

        }
    };

}
