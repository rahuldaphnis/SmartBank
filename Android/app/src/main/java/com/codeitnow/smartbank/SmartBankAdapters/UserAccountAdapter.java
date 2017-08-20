package com.codeitnow.smartbank.SmartBankAdapters;

/**
 * Created by Rahul Malhotra on 8/19/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.codeitnow.smartbank.AccountMenu;
import com.codeitnow.smartbank.R;
import com.codeitnow.smartbank.SmartBankObjects.UserAccount;
import com.codeitnow.smartbank.SmartBankUtilities.Config;
import com.codeitnow.smartbank.SmartBankUtilities.CustomVolleyRequest;
import com.codeitnow.smartbank.SmartBankUtilities.MyApplication;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class UserAccountAdapter extends RecyclerView.Adapter<UserAccountAdapter.UserAccountViewHolder> {

    SharedPreferences sharedPreferences;
    Context context;
    LayoutInflater inflater;
    ArrayList<UserAccount> items;
    ImageLoader imageLoader;

    public UserAccountAdapter(Context context, ArrayList<UserAccount> items)
    {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public UserAccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.useraccountlist,parent,false);
        UserAccountViewHolder viewHolder = new UserAccountViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserAccountViewHolder holder, final int position) {
        holder.bankname.setText(items.get(position).bankname);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ip = new Intent(context,AccountMenu.class);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("bankid", items.get(position).bankid);
                editor.putString("accountnumber", items.get(position).number);
                editor.putString("ifsccode", items.get(position).ifsccode);
                editor.commit();
                context.startActivity(ip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class UserAccountViewHolder extends RecyclerView.ViewHolder
    {
        public TextView bankname;
        public CardView root;
        public UserAccountViewHolder(View itemView) {
            super(itemView);
            sharedPreferences = MyApplication.getAppContext().getSharedPreferences("user",MODE_PRIVATE);
            root = (CardView) itemView.findViewById(R.id.row);
            bankname = (TextView) itemView.findViewById(R.id.bankname);
        }
    }

}
