package sqlitetest.syam.com.sqlitetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.syam.helpers.database.*;
import com.syam.models.User;
import com.syam.services.ContactsService;

import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private ContactsService contactsService;

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.editText);

        //Database factory init
        DatabaseFactory.getInstance().init(getApplicationContext());

        contactsService = new ContactsService("sample.db");
    }

    private void InitDatabase() {

    }


    public void onReadButtonClick(View v) {

        contactsService.getContacts(new IQueryCallback<User>() {
            @Override
            public void onSuccess(LinkedList<User> result, long time) {
                Log.d("Database","All users loaded :"+ result.size()+", time taken :"+time);
            }

            @Override
            public void onFail(String message){
                Log.d("Database","fetching contacts failed");
            }
        });

        int userId = 1;
        User user = contactsService.getUser(userId);
        if (user != null) {
            StringBuilder values = new StringBuilder();
            values.append( "userId : "+user.getUserId()+"\n");
            values.append( "name : "+user.getName()+"\n");
            values.append( "nickname : "+user.getDisplayName()+"\n");
            values.append( "email : "+user.getEmail()+"\n");
            values.append( "userType : "+user.getUserType()+"\n");
            values.append( "phone : "+user.getPhone()+"\n");
            text.setText(values.toString(), TextView.BufferType.NORMAL);
        }else
            Toast.makeText(this, "Empty Users", Toast.LENGTH_LONG).show();

    }


    public void onWriteButtonClick(View v){

        //Add one user
        User user = new User("","+917010698675", "syamesh@scanta.io", "Syam Jio", "Syam Jio", "scanta_user", 100078);//(100078, "+917010698675", "syamesh@scanta.io", "Syam Jio", "Syam Jio", "M", null, "scanta_user", null, 715824000000L, 1);
        contactsService.addUser(user);

        Log.d("Database","Start adding stuff");
        LinkedList<User> users = new LinkedList<User>();
        for(int i=0;i<5000;i++) {
            users.add(new User("","+917010698675", "syamesh@scanta.io", "Syam Jio", "Syam Jio", "scanta_user", i));
        }
        contactsService.addUsers(users, new IInsertCallback() {
            @Override
            public void onSuccess(int rows, long time) {
                Log.d("Database","Insert completed, added : "+rows+", time taken :"+time);
            }
            @Override
            public void onFail(String message){
                Log.d("Database","Insert failed ");
            }
        });
    }

    public void onDeleteButtonClick(View v){
        int rows = contactsService.deleteTable();
        Log.d("Database","Deleted :"+rows);

    }

}
