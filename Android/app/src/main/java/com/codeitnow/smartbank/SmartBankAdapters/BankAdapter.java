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
import com.codeitnow.smartbank.AccountMenu;
import com.codeitnow.smartbank.AddAccount;
import com.codeitnow.smartbank.R;
import com.codeitnow.smartbank.SmartBankUtilities.MyApplication;
import com.codeitnow.smartbank.data.model.GetBanksData;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.BankViewHolder> {

    SharedPreferences sharedPreferences;
    Context context;
    LayoutInflater inflater;
    List<GetBanksData> items;
    ImageLoader imageLoader;

    public BankAdapter(Context context, List<GetBanksData> items)
    {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public BankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.banklistitem,parent,false);
        BankViewHolder viewHolder = new BankViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BankViewHolder holder, final int position) {
        holder.bankname.setText(items.get(position).getName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ip = new Intent(context,AddAccount.class);
                ip.putExtra("bankid",items.get(position).getBankid());
                context.startActivity(ip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class BankViewHolder extends RecyclerView.ViewHolder
    {
        public TextView bankname;
        public CardView root;
        public BankViewHolder(View itemView) {
            super(itemView);
            root = (CardView) itemView.findViewById(R.id.row);
            bankname = (TextView) itemView.findViewById(R.id.bankname);
        }
    }

}
