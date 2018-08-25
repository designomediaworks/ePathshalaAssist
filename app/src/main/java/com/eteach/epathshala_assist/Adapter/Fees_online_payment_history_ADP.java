package com.eteach.epathshala_assist.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eteach.epathshala_assist.R;
import com.eteach.epathshala_assist.dataset.TBL_FEES_PAYMENT_DETAILS;

import java.util.List;

/**
 * Created by shree on 08/09/2017.
 */

public class Fees_online_payment_history_ADP extends RecyclerView.Adapter<Fees_online_payment_history_ADP.MyViewHolder>{

    private Context context;
    private Context activity;
    private List<TBL_FEES_PAYMENT_DETAILS> dataFees ;//= Collections.emptyList();
    //private MyViewHolder holder;

    public Fees_online_payment_history_ADP (FragmentActivity activity, List<TBL_FEES_PAYMENT_DETAILS> _dataFees, Context context)
    {
            this.context = activity;
            this.dataFees = _dataFees;
            this.activity=context;
    }



    @Override
    public MyViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listfees2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder (MyViewHolder holder, final int position) {
    try{

    }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount ( ) {
        return dataFees.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView FeesTitle,Feesperiod,FeesPaymentdate,Feespaymode,FeestotalAmt,FeestotalDisc,FeestotalpaidAmt,FeestotalBal,FeesTotalAmount ;
        public Button btn_paynow;
        public MyViewHolder (View itemView) {
            super(itemView);
             FeesTitle = ( TextView ) itemView.findViewById ( R.id.txt_fees_title );
             Feesperiod = ( TextView ) itemView.findViewById ( R.id.txt_fees_period );
             FeesPaymentdate = ( TextView ) itemView.findViewById ( R.id.txt_fees_payment_date );
             Feespaymode = ( TextView ) itemView.findViewById ( R.id.txt_fees_payment_mode );
             FeestotalAmt = ( TextView ) itemView.findViewById ( R.id.txt_total_amount );
             FeestotalDisc = ( TextView ) itemView.findViewById ( R.id.txt_fees_discount );
             FeestotalpaidAmt = ( TextView ) itemView.findViewById ( R.id.txt_total_amount_paid );
             FeestotalBal = ( TextView ) itemView.findViewById ( R.id.txt_totalbalance );//txt_total_amount_pay txt_total_amount_paid txt_totalbalance
             FeesTotalAmount = ( TextView ) itemView.findViewById ( R.id.txt_total_amount_pay );
             btn_paynow = (Button) itemView.findViewById(R.id.btn_paynow);
        }
    }
}
