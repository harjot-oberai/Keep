package com.example.harjot.keep;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.github.clans.fab.FloatingActionButton;

public class Edit_Memo extends AppCompatActivity {
    private FloatingActionButton btn=(FloatingActionButton)findViewById(R.id.fab);
    EditText txt;
    EditText tag;
    int i=0;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            Slide slide=new Slide();
            slide.setSlideEdge(5);
            getWindow().setEnterTransition(slide);
            /*Slide slide2=new Slide();
            slide2.setDuration(1000);
            slide2.setSlideEdge(3);
            getWindow().setExitTransition(slide2);*/
        }
        setContentView(R.layout.edit_memo);
        Intent intent=getIntent();
        Bundle extra=intent.getExtras();
        i=extra.getInt("i");
        onClickListner();
    }
    public void onClickListner(){
        txt=(EditText)findViewById(R.id.editText2);
        tag=(EditText)findViewById(R.id.editText);
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(count==0){
                            btn.setImageResource(R.mipmap.ic_tick);
                            txt.setEnabled(true);
                            tag.setEnabled(true);
                            count++;
                        }
                        else{
                            count=0;
                            Intent intent=new Intent();
                            intent.putExtra("Text",txt.getText().toString());
                            intent.putExtra("Tag",tag.getText().toString());
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }
                    }
                }
        );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
