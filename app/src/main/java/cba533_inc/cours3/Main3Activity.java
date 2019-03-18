package cba533_inc.cours3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        String name = getIntent().getStringExtra("name");
        Toast.makeText(this,name,Toast.LENGTH_LONG).show();
    }
}
