package com.example.travellet.feature.travel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travellet.R;

import java.util.Set;

public class SetTitleActivity extends AppCompatActivity {
    EditText Edittext_title;
    Button btn_Next;
    String title;
    int mainPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_set_title);
        Intent intent = getIntent();
        //얜 뭔지 기억안난다
        mainPosition = intent.getIntExtra("mainPosition", 0);
        Edittext_title = (EditText) findViewById(R.id.title_text_set_title);
        //next 버튼 눌러서 다음 페이지로
        btn_Next = (Button) findViewById(R.id.next_button_set_title);
        
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                
                title = Edittext_title.getText().toString();
                //타이틀 입력 안하면 다음으로 넘어가지 못하게
                if(title == null || title.length() == 0){
                    Edittext_title.setHintTextColor(getColor(R.color.coral_red));
                    //btn_Next.isClickable(false);
                }else{
                    //인텐트로 타이틀값 넘기기
                    Intent intent = new Intent(SetTitleActivity.this, SetBudgetActivity.class); //나중에 날짜 화면으로 변경하기
                    intent.putExtra("travelTitle", title);
                    intent.putExtra("mainPosition", mainPosition);
                    //Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

            }
        });
    }
}
