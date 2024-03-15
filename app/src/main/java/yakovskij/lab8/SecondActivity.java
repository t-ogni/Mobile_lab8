package yakovskij.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    EditText txt_title;
    EditText txt_content;
    int pos;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        txt_title = findViewById(R.id.TxtTitle);
        txt_content = findViewById(R.id.TxtContent);
        Intent i = getIntent();
        pos = i.getIntExtra("note index", -1);
        if(pos != -1){
            Note a = MainActivity.db.getNote(pos);
            txt_title.setText(a.title);
            txt_content.setText(a.content);
        } else {
            txt_title.setText("Title");
            txt_content.setText("Content");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    public void on_cancel_click(MenuItem v){
        finish();
    }

    public void on_ok_click(MenuItem v){
        Intent i = new Intent();
        if(pos == -1){
            MainActivity.db.addNote(txt_title.getText().toString(), txt_content.getText().toString());
            setResult(RESULT_OK, i);
        } else {
            MainActivity.db.alterNote(pos, txt_title.getText().toString(), txt_content.getText().toString());
        }
        finish();
    }
}