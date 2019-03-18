package cba533_inc.cours3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setListener();
    }

    public void setListener(){

        findViewById(R.id.btn_nextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextView();
            }
        });

    }

    private void goToNextView(){
        EditText editText = findViewById(R.id.editText);

        Intent intent = new Intent(this, Main3Activity.class);
        intent.putExtra("name", editText.getText().toString());
        startActivity(intent);
    }
}
