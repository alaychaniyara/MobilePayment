package com.example.mobilepayment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.ViewHolder> {
    Discounts discount;
    DiscountInfo discountList;
    private Context context;
    private static final OkHttpClient client = new OkHttpClient();
    public static String getclienttoken = "http://dev.local:3900/client_token";

    public DiscountAdapter(Context c , DiscountInfo discountArrayList) {
        Log.d("test", "Inside Adapter: ");
        this.context = c;
        this.discountList = discountArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.discount_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("test", "onBindViewHolder: " + discountList.getData().size());
        discount = discountList.getData().get(position);
        Log.d("test", "item on the list : " + discount.toString());
        holder.discountsData = discount;
        holder.discount.setText(discount.getDiscount());
        holder.price.setText(discount.getPrice());
        holder.product.setText(discount.getName());
        holder.region.setText(discount.getRegion());
        Picasso.with(context).load(discount.getPhoto()).centerCrop().resize(50,50).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return discountList.getData().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView product,discount,price,region;
        ImageView productImage;
        Discounts discountsData;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.discountsData = discountsData;
            product = itemView.findViewById(R.id.textProductName);
            discount = itemView.findViewById(R.id.textDiscount);
            price = itemView.findViewById(R.id.textPrice);
            region = itemView.findViewById(R.id.textRegion);
            productImage = itemView.findViewById(R.id.imageProduct);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Request request = new Request.Builder().url(getclienttoken).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("token",call.toString());

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        Log.d("token",response.body().string());

                    }
                });

            }
        });
        }


    }


}
