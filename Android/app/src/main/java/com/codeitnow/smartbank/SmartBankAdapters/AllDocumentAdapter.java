package com.codeitnow.smartbank.SmartBankAdapters;

/**
 * Created by Rahul Malhotra on 8/19/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.codeitnow.smartbank.ChooseDocument;
import com.codeitnow.smartbank.R;
import com.codeitnow.smartbank.SmartBankUtilities.CustomVolleyRequest;
import com.codeitnow.smartbank.SmartBankUtilities.MyApplication;
import com.codeitnow.smartbank.ViewDocument;
import com.codeitnow.smartbank.data.model.AddExistingDocument;
import com.codeitnow.smartbank.data.model.GetAllDocumentsData;
import com.codeitnow.smartbank.data.model.GetUserDocumentPost;
import com.codeitnow.smartbank.data.remote.APIService;
import com.codeitnow.smartbank.data.remote.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllDocumentAdapter extends RecyclerView.Adapter<AllDocumentAdapter.DocumentViewHolder> {

    SharedPreferences sharedPreferences;
    Context context;
    LayoutInflater inflater;
    List<GetAllDocumentsData> items;
    ImageLoader imageLoader;
    APIService apiService;

    public AllDocumentAdapter(Context context, List<GetAllDocumentsData> items)
    {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("user",Context.MODE_PRIVATE);
    }


    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.documentlistitem,parent,false);
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
        holder.bankName.setText(items.get(position).getBankname());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ip = new Intent(context,ViewDocument.class);
                context.startActivity(ip);
            }
        });
        holder.root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                apiService = ApiUtils.getAPIService();
                apiService.addExistingDocument(
                        sharedPreferences.getString("userid",null),
                        sharedPreferences.getString("bankid",null),
                        items.get(position).getType(),
                        items.get(position).getAccountnumber(),
                        items.get(position).getDocumentid()
                ).enqueue(new Callback<AddExistingDocument>() {
                    @Override
                    public void onResponse(Call<AddExistingDocument> call, Response<AddExistingDocument> response) {
                        String success = response.body().getSuccess();
                        if(success.equals("1")) {
                            Toast.makeText(MyApplication.getInstance(), "Document Added Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MyApplication.getInstance(), "Document added but not verified", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<AddExistingDocument> call, Throwable t) {
                            Toast.makeText(MyApplication.getInstance(), "Please check your network connection and try again", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
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
        public TextView documentName,bankName;
        public CardView root;
        public DocumentViewHolder(View itemView) {
            super(itemView);
            root = (CardView) itemView.findViewById(R.id.row);
            documentName = (TextView) itemView.findViewById(R.id.docname);
            bankName = itemView.findViewById(R.id.bankname);
            networkImageView = (NetworkImageView) itemView.findViewById(R.id.docimage);
        }
    }

}