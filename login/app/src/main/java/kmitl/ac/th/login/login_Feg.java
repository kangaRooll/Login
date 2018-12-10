package kmitl.ac.th.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login_Feg extends Fragment {
    private static final String TAG = "LOGIN";

    private SQLiteDatabase myDB;
    private Account account;

    private EditText zUsername;
    private EditText zPassword;
    private Button zSignin;
    private TextView zSignup;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        account = Account.getAccountInstance();
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        loginFragmentElements();
        initSignup();
        initSignin();
    }
    private void loginFragmentElements() {
        Log.d(TAG, "Get Elements");
        zUsername = getView().findViewById(R.id.login_username);
        zPassword = getView().findViewById(R.id.login_password);
        zSignin = getView().findViewById(R.id.login_signinbtn);
        zSignup = getView().findViewById(R.id.login_signupbtn);
    }
    private void initSignup() {
        zSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Signup : clicked");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new Reg()).addToBackStack(null).commit();
            }
        });
    }
    private void initSignin() {
        zSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = zUsername.getText().toString();
                String password = zPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getActivity(), "กรุณาใส่ข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor loginCheck = myDB.rawQuery("select * from account where username = '" + username + "' and password = '" + password + "'", null);
                    if (loginCheck.getCount() > 0) {
                        loginCheck.move(1);
                        account.setPrimaryid(loginCheck.getInt(0));
                        account.setUsername(loginCheck.getString(1));
                        account.setPassword(loginCheck.getString(2));

                        Cursor myCheck = myDB.rawQuery("select * from my where account_id = '" + account.getPrimaryid() + "'", null);
                        if (myCheck.getCount() > 0) {
                            myCheck.move(1);
                            account.setFullname(myCheck.getString(1));
                            account.setPhonenumber(myCheck.getString(2));

                            myCheck.close();
                            loginCheck.close();

                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new wel()).commit();
                        }
                    } else {
                        Toast.makeText(getActivity(), "ไม่พบข้อมูลผู้ใช้", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lay_login, container, false);
    }
}
