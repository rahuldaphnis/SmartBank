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
import com.codeitnow.smartbank.AddAccount;
import com.codeitnow.smartbank.R;
import com.codeitnow.smartbank.SmartBankUtilities.CustomVolleyRequest;
import com.codeitnow.smartbank.SmartBankUtilities.MyApplication;
import com.codeitnow.smartbank.ViewDocument;
import com.codeitnow.smartbank.data.model.GetBanksData;
import com.codeitnow.smartbank.data.model.GetDocumentsData;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.DocumentViewHolder> {

    SharedPreferences sharedPreferences;
    Context context;
    LayoutInflater inflater;
    List<GetDocumentsData> items;
    ImageLoader imageLoader;

    public DocumentAdapter(Context context, List<GetDocumentsData> items)
    {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.documentonebanklistitem,parent,false);
        DocumentViewHolder viewHolder = new DocumentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DocumentViewHolder holder, final int position) {
        String docName = "";
        if(items.get(position).getType().equals("1")) {
            docName = "Aadhar Card";
        }
        if(items.get(position).getType().equals("2")) {
            docName = "Pan Card";
        }
        ImageLoader imageLoader = CustomVolleyRequest.getInstance(MyApplication.getAppContext()).getImageLoader();
        imageLoader.get(items.get(position).getImage(),ImageLoader.getImageListener(holder.networkImageView,R.mipmap.ic_launcher,android.R.drawable.ic_dialog_alert));
        holder.networkImageView.setImageUrl(items.get(position).getImage(),imageLoader);
        holder.documentName.setText(docName);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ip = new Intent(context,ViewDocument.class);
                context.startActivity(ip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class DocumentViewHolder extends RecyclerView.ViewHolder
    {
        public NetworkImageView networkImageView;
        public TextView documentName;
        public CardView root;
        public DocumentViewHolder(View itemView) {
            super(itemView);
            root = (CardView) itemView.findViewById(R.id.row);
            documentName = (TextView) itemView.findViewById(R.id.docname);
            networkImageView = (NetworkImageView) itemView.findViewById(R.id.docimage);
        }
    }

}