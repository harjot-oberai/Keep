package com.example.harjot.keep;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactInfo> contactList;
    static Activity mAct;

    public ContactAdapter(List<ContactInfo> contactList,Activity activity) {
        mAct=activity;
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {

        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        //final int k=i;
        ContactInfo ci = contactList.get(i);
        contactViewHolder.vText.setText(ci.text);
        contactViewHolder.vTag.setText(ci.tag);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }
    public void delete(int position){
        contactList.remove(position);
        notifyItemRemoved(position);
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected TextView vText;
        protected TextView vTag;
        protected  Button  vBtn;
        protected LinearLayout vLin;
        protected int vNum;
        public ContactViewHolder(View v) {
            super(v);
            vText =  (TextView) v.findViewById(R.id.txt);
            vTag = (TextView)  v.findViewById(R.id.txtTag);
            vNum = MainActivity.i;
            vBtn=(Button)v.findViewById(R.id.button);
            vBtn.setOnClickListener(this);
            vLin=(LinearLayout)v.findViewById(R.id.lin2);
            vLin.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent("com.android.harjot.keep.Add_Memo");
                            intent.putExtra("i", getPosition());
                            intent.putExtra("for","edit");
                            mAct.startActivityForResult(intent, 1);
                        }
                    }
            );
        }
        @Override
        public void onClick(View view){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Toast.makeText(mAct,"Card "+getPosition(),Toast.LENGTH_SHORT).show();
                            delete(getPosition());
                            for(int i=getPosition();i<(MainActivity.i)-1;i++){
                                MainActivity.txt_arr[i]=MainActivity.txt_arr[i+1];
                                MainActivity.tag_arr[i]=MainActivity.tag_arr[i+1];
                            }

                            MainActivity.txt_arr[MainActivity.i-1]=" ";
                            MainActivity.tag_arr[MainActivity.i-1]=" ";
                            MainActivity.i--;
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

    }
}
