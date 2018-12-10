package kmitl.ac.th.login;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase myDB = openOrCreateDatabase("my.db", MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS account (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(200), password VARCHAR(200))");
        myDB.execSQL("CREATE TABLE IF NOT EXISTS my (id INTEGER PRIMARY KEY AUTOINCREMENT, fullname VARCHAR(200), phonenumber VARCHAR(200), account_id INTEGER)");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new login_Feg()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
