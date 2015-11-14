package com.example.harjot.keep;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.github.clans.fab.FloatingActionButton;

import java.util.logging.Logger;

//import android.support.v7.app.AppCompatActivity;

public class Add_Memo extends Activity {
    private FloatingActionButton btn;
    private FloatingActionButton btn2;
    EditText txt;
    EditText tag;
    int i=0;
    String abc="";
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            Slide slide=new Slide();
            slide.setSlideEdge(80);
            getWindow().setEnterTransition(slide);
            /*Slide slide2=new Slide();
            slide2.setDuration(1000);
            slide2.setSlideEdge(3);
            getWindow().setExitTransition(slide2);*/
        }
        Intent intent=getIntent();
        Bundle extra=intent.getExtras();
        i=extra.getInt("i");
        Log.d("position",String.valueOf(i));
        abc=extra.getString("for");
        if(abc.equals("add")){
            setContentView(R.layout.activity_add_memo);
            btn=(FloatingActionButton)findViewById(R.id.fab);
            btn2=(FloatingActionButton)findViewById(R.id.fab2);
            txt=(EditText)findViewById(R.id.editText2);
            tag=(EditText)findViewById(R.id.editText);
            onClickListner2();
            count=1;
        }
        else{
            setContentView(R.layout.edit_memo);
            btn=(FloatingActionButton)findViewById(R.id.fab);
            txt=(EditText)findViewById(R.id.editText2);
            tag=(EditText)findViewById(R.id.editText);
            txt.setText(MainActivity.txt_arr[i]);
            tag.setText(MainActivity.tag_arr[i]);
            count=0;
        }
        onClickListner();
    }
    public void onClickListner2(){
        btn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }
    public void onClickListner(){
        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(count==0){
                            setContentView(R.layout.activity_add_memo);
                            btn=(FloatingActionButton)findViewById(R.id.fab);
                            btn2=(FloatingActionButton)findViewById(R.id.fab2);
                            txt=(EditText)findViewById(R.id.editText2);
                            tag=(EditText)findViewById(R.id.editText);
                            txt.setText(MainActivity.txt_arr[i]);
                            tag.setText(MainActivity.tag_arr[i]);
                            count=1;
                            onClickListner();
                            onClickListner2();
                        }
                        else{
                            Toast.makeText(Add_Memo.this,"Saved",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            intent.putExtra("Text",txt.getText().toString());
                            intent.putExtra("Tag",tag.getText().toString());
                            intent.putExtra("pos",i);
                            if(abc.equals("edit")){
                                intent.putExtra("for","edit");
                            }
                            else{
                                intent.putExtra("for","add");
                            }
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
