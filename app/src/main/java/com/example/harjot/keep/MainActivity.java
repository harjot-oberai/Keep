package com.example.harjot.keep;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
/*class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}*/
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private com.github.clans.fab.FloatingActionButton btn1;
    private com.github.clans.fab.FloatingActionButton btn2;
    List<ContactInfo> result;
    static int number=0;
    static String txt_arr[]=new String[10000];
    static String tag_arr[]=new String[10000];
    RecyclerView recList;
    static int i=0;
    String str="";
    int pos=0;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1&&resultCode== Activity.RESULT_OK&&data!=null){
            str=data.getStringExtra("for");
            if(str.equals("add")){
                txt_arr[i]=data.getStringExtra("Text");
                tag_arr[i]=data.getStringExtra("Tag");
                i++;
            }
            else{
                Bundle extra=data.getExtras();
                pos=extra.getInt("pos");
                Log.w("position write",String.valueOf(pos));
                txt_arr[pos]=data.getStringExtra("Text");
                tag_arr[pos]=data.getStringExtra("Tag");
            }
            addToList();
        }
        if(requestCode==2&&resultCode==Activity.RESULT_OK&&data!=null){
            number=data.getIntExtra("num",number);
            txt_arr[i]=data.getStringExtra("Text");
            tag_arr[i]=data.getStringExtra("Tag");
            addToList();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            TransitionInflater inflator=TransitionInflater.from(this);
            Transition transition=inflator.inflateTransition(R.transition.transition_main);
            getWindow().setExitTransition(transition);
            Transition transition2=inflator.inflateTransition(R.transition.transition_main);
            getWindow().setReturnTransition(transition2);
            Transition transition3=inflator.inflateTransition(R.transition.transition_main);
            getWindow().setEnterTransition(transition3);
        }
        setContentView(R.layout.activity_main);
        SharedPreferences sets=getPreferences(MODE_PRIVATE);
        if(sets!=null){
            i=sets.getInt("i", i);
            for(int x=0;x<i;x++){
                txt_arr[x]=sets.getString("txt"+x,txt_arr[x]);
                tag_arr[x]=sets.getString("tag"+x,tag_arr[x]);
            }
        }
        addToList();
        OnClickListener();
        //OnClickListener2();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        settings = getPreferences(MODE_PRIVATE);
        editor = settings.edit();
        for(int j=0;j<i;j++) {
            editor.putString("txt"+j, txt_arr[j]);
            editor.putString("tag"+j, tag_arr[j]);
        }
        editor.putInt("i", i);
        // Commit the edits!
        editor.commit();
    }
    public void OnClickListener(){
        btn1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab);
        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        get_intent();
                    }
                }
        );
    }
    public void get_intent(){
        ActivityOptionsCompat compat= ActivityOptionsCompat.makeSceneTransitionAnimation(this,null);
        Intent intent = new Intent("com.android.harjot.keep.Add_Memo");
        intent.putExtra("i", i);
        intent.putExtra("for","add");
        startActivityForResult(intent, 1, compat.toBundle());
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if(id==R.id.clearAll){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Toast.makeText(mAct,"Card "+getPosition(),Toast.LENGTH_SHORT).show();
                            if (editor != null) {
                                editor.clear();
                                editor.commit();
                            }
                            i = 0;
                            addToList();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();

        }

        return super.onOptionsItemSelected(item);
    }
    private List<ContactInfo> createList() {
        result = new ArrayList<ContactInfo>();
        for(int k=0;k<i;k++) {
            ContactInfo ci = new ContactInfo();
            ci.text = txt_arr[k];
            ci.tag = tag_arr[k];
            result.add(ci);
        }
        return result;
    }
    private void addToList(){
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        ContactAdapter ca = new ContactAdapter(createList(),this);
        recList.setAdapter(ca);

        /*ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(ca);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recList);*/
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
