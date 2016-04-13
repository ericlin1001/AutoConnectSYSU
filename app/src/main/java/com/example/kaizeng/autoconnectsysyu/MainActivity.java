package com.example.kaizeng.autoconnectsysyu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {
    private final String PREFERENCES_NAME = "userinfo";
    TextView tv;

    Button loginButton,loginMoreButton;
    String version;
    //
    private EditText usernameEdit,passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //add code following.

         tv=(TextView)findViewById(R.id.textView);
        loginButton=(Button)findViewById(R.id.loginOnce);
        loginMoreButton=(Button)findViewById(R.id.loginMore);
        usernameEdit = (EditText)findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);

        loginMoreButton.setAlpha(0);
        version="version5";
        
        tv.setText(version);

        //

        readData();

        if(usernameEdit.getText().toString()==null||usernameEdit.getText().toString()==""||passwordEdit.getText().toString()==null||passwordEdit.getText().toString()==""){
            usernameEdit.setText("zhuguoli");
            passwordEdit.setText("8236308");
        }

        //

        loginButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(version + "|自动登录完成.");

                saveData();
               /* (new Thread() {
                    @Override
                    public void run() {
                        //  tv.setText(tv.getText()+"|try login.");
                        //HttpRequest.sendPost("http://10.10.2.22/portal/logon.cgi", "PtUser=zhuguoli&PtPwd=8236308&PtButton=Logon");
                          HttpRequest.sendPost("http://10.10.2.22/portal/logon.cgi", "PtUser=" + usernameEdit.getText().toString() + "&PtPwd=" + passwordEdit.getText().toString() + "&PtButton=Logon");
                    }
                }).start();*/
                (new AutoLoginThread(usernameEdit.getText().toString(),passwordEdit.getText().toString())).start();
                Intent reg=new Intent(MainActivity.this,AutoLoginService.class);
                reg.putExtra("username", usernameEdit.getText().toString());
                reg.putExtra("password", passwordEdit.getText().toString());
                startService(reg);
            }
        });

        loginMoreButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(version + "|自动登录完成2.");
                saveData();
                Intent reg=new Intent(MainActivity.this,AutoLoginService.class);
                reg.putExtra("username",usernameEdit.getText().toString());
                reg.putExtra("password",passwordEdit.getText().toString());
                startService(reg);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    public void saveData(){
        SharedPreferences agPreferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = agPreferences.edit();
        editor.putString("Username", usernameEdit.getText().toString());
        editor.putString("Password",  passwordEdit.getText().toString());
        editor.commit();
    }
    public void readData(){
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        usernameEdit.setText(preferences.getString("Username", null));
        passwordEdit.setText(preferences.getString("Password", null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
