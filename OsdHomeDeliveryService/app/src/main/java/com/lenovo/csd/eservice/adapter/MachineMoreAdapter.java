package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.MachineInfoData;
import com.lenovo.csd.eservice.entity.base.TypeData;

import java.util.ArrayList;

/**
 * Created by 彤 on 2016/10/12.
 */
public class MachineMoreAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<TypeData> datas;
    private LayoutInflater inflater;

    public MachineMoreAdapter(Context context, ArrayList<TypeData> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new MachineInfoHolder(inflater.inflate(R.layout.item_machineinfo_single, parent, false));
            case 1:
                return new TitleViewHolder(inflater.inflate(R.layout.item_title_boxfrag, parent, false));
            case 2:
                return new KeepRepairHolder(inflater.inflate(R.layout.item_keeprepair_moremachinefrag, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TypeData data = datas.get(position);
        if (holder instanceof MachineInfoHolder) {
            ((MachineInfoHolder) holder).mTxtProductSN.setText(TextUtils.isEmpty(((MachineInfoData.MachineData) data).getProduct_no()) ? "无" : ((MachineInfoData.MachineData) data).getProduct_no());
            ((MachineInfoHolder) holder).mTxtProductName.setText(TextUtils.isEmpty(((MachineInfoData.MachineData) data).getMachine_name()) ? "无" : ((MachineInfoData.MachineData) data).getMachine_name());
            ((MachineInfoHolder) holder).mTxtGetdoorEndTime.setText(TextUtils.isEmpty(((MachineInfoData.MachineData) data).getOn_site_end_date()) ? "无" : ((MachineInfoData.MachineData) data).getOn_site_end_date());
            ((MachineInfoHolder) holder).mTxtEnsurenceEndTime.setText(TextUtils.isEmpty(((MachineInfoData.MachineData) data).getPart_end_date()) ? "无" : ((MachineInfoData.MachineData) data).getPart_end_date());
            ((MachineInfoHolder) holder).mTxtProductBoutTime.setText(TextUtils.isEmpty(((MachineInfoData.MachineData) data).getBuy_date()) ? "无" : ((MachineInfoData.MachineData) data).getBuy_date());
            ((MachineInfoHolder) holder).mTxtProductCreateTime.setText(TextUtils.isEmpty(((MachineInfoData.MachineData) data).getProduct_date()) ? "无" : ((MachineInfoData.MachineData) data).getProduct_date());
        } else if (holder instanceof TitleViewHolder) {
            ((TitleViewHolder) holder).mTitle.setText(((MachineInfoData.TitleData) data).getmTitle());
        } else if (holder instanceof KeepRepairHolder) {
            ((KeepRepairHolder) holder).mTxtRepairTitle.setText(TextUtils.isEmpty(((MachineInfoData.WarrantyData) data).getWarranty_name()) ? "无" : ((MachineInfoData.WarrantyData) data).getWarranty_name());
            ((KeepRepairHolder) holder).mTxtRepairDesc.setText(TextUtils.isEmpty(((MachineInfoData.WarrantyData) data).getWarranty_description()) ? "无" : ((MachineInfoData.WarrantyData) data).getWarranty_description());
            ((KeepRepairHolder) holder).mTxtRepairStartTime.setText(TextUtils.isEmpty(((MachineInfoData.WarrantyData) data).getLabor_start_date()) ? "无" : ((MachineInfoData.WarrantyData) data).getLabor_start_date());
            ((KeepRepairHolder) holder).mTxtRepairEndTime.setText(TextUtils.isEmpty(((MachineInfoData.WarrantyData) data).getLabor_end_date()) ? "无" : ((MachineInfoData.WarrantyData) data).getLabor_end_date());
            ((KeepRepairHolder) holder).mTxtGetdoorStartTime.setText(TextUtils.isEmpty(((MachineInfoData.WarrantyData) data).getOn_site_start_date()) ? "无" : ((MachineInfoData.WarrantyData) data).getOn_site_start_date());
            ((KeepRepairHolder) holder).mTxtGetdoorEndTime.setText(TextUtils.isEmpty(((MachineInfoData.WarrantyData) data).getOn_site_end_date()) ? "无" : ((MachineInfoData.WarrantyData) data).getOn_site_end_date());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {

        TypeData data = datas.get(position);
        return data.getType();
    }

    public class MachineInfoHolder extends RecyclerView.ViewHolder {
        public TextView mTxtProductSN;
        public TextView mTxtProductName;
        public TextView mTxtProductCreateTime;
        public TextView mTxtProductBoutTime;
        public TextView mTxtEnsurenceEndTime;
        public TextView mTxtGetdoorEndTime;

        public MachineInfoHolder(View itemView) {
            super(itemView);
            mTxtProductSN = (TextView) itemView.findViewById(R.id.text_productSN);
            mTxtProductName = (TextView) itemView.findViewById(R.id.text_productName);
            mTxtProductCreateTime = (TextView) itemView.findViewById(R.id.text_productCreateTime);
            mTxtProductBoutTime = (TextView) itemView.findViewById(R.id.text_productBoutTime);
            mTxtEnsurenceEndTime = (TextView) itemView.findViewById(R.id.text_ensurenceEndline);
            mTxtGetdoorEndTime = (TextView) itemView.findViewById(R.id.text_getDoorEndLine);
        }
    }

    public class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.text_boxfragTitle);
        }
    }

    public class KeepRepairHolder extends RecyclerView.ViewHolder {
        public TextView mTxtRepairTitle;
        public TextView mTxtRepairDesc;
        public TextView mTxtRepairStartTime;
        public TextView mTxtRepairEndTime;
        public TextView mTxtGetdoorStartTime;
        public TextView mTxtGetdoorEndTime;

        public KeepRepairHolder(View itemView) {
            super(itemView);
            mTxtRepairTitle = (TextView) itemView.findViewById(R.id.text_repairTitle);
            mTxtRepairDesc = (TextView) itemView.findViewById(R.id.text_keepRepairDesc);
            mTxtRepairStartTime = (TextView) itemView.findViewById(R.id.text_keepRepairStartTime);
            mTxtRepairEndTime = (TextView) itemView.findViewById(R.id.text_keepRepairEndTime);
            mTxtGetdoorStartTime = (TextView) itemView.findViewById(R.id.text_getDoorStartTime);
            mTxtGetdoorEndTime = (TextView) itemView.findViewById(R.id.text_getDoorEndTime);
        }
    }
}