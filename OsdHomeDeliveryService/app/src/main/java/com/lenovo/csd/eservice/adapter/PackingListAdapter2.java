package com.lenovo.csd.eservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.entity.base.ChangeBoxTypeData;
import com.lenovo.csd.eservice.entity.base.ChangeBoxsInfo;
import com.lenovo.csd.eservice.entity.base.ChangeBoxsInfo2;
import java.util.List;

/**
 * Created by hankw on 16-8-20.
 */
public class PackingListAdapter2 extends RecyclerView.Adapter implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    public final static int TYPE_HIS = 20;
    public final static int TYPE_BOXS = 22;
    public final static int TYPE_BAREBONE = 24;
    public final static int TYPE_TITLE = 26;
    private Context mContext;
    private List<ChangeBoxTypeData> list;
    private View mView;

    public PackingListAdapter2(Context mContext, List<ChangeBoxTypeData> list) {
        this.mContext = mContext;
        this.list = list;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewHisClickListener mOnHisClickListener = null;
    private OnRecyclerViewBareClickListener mOnBareClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
            void onItemClick(View view, ChangeBoxsInfo2.BoxList data);
    }

    public static interface OnRecyclerViewHisClickListener {
        void onItemClick(View view, ChangeBoxsInfo2.ChangeHistoriy data);
    }

    public static interface OnRecyclerViewBareClickListener {
        void onItemClick(View view, ChangeBoxsInfo2.BareBone data);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (i){
            case TYPE_HIS:
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_historyholder_boxfrag,viewGroup,false);
                holder = new ViewHolderHis(mView);
                break;
            case TYPE_BOXS:
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_change_up,viewGroup,false);
                int[] ids = {R.id.Txt_sparePartId,R.id.Txt_sparePartBar,R.id.Txt_SparePartDescribe,
                        R.id.Txt_sparePartClassificat,R.id.Txt_sparePartPriceUp,R.id.RadioBtn_select,
                        R.id.Lin_ChangeGroup,R.id.Lin_changeLine
                };
                //将创建的View注册点击事件
                mView.setOnClickListener(this);
                holder = new ViewHolderBoxs(mView,ids);
                break;
            case TYPE_BAREBONE:
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_barebone_boxfrag,viewGroup,false);
                holder = new ViewHolderBareBone(mView);
                break;
            case TYPE_TITLE:
                mView = LayoutInflater.from(mContext).inflate(R.layout.item_title_boxfrag,viewGroup,false);
                holder = new ViewHolderTitle(mView);
                break;
        }
        return holder;
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        ChangeBoxTypeData data = list.get(i);

        if (viewHolder instanceof ViewHolderHis) {
            ChangeBoxsInfo2.ChangeHistoriy mHis = (ChangeBoxsInfo2.ChangeHistoriy) data;
            ViewHolderHis mVHis = (ViewHolderHis) viewHolder;
            if (mHis != null){
                mVHis.itemView.setTag(mHis);
                mVHis.mTxtRepairTime.setText(mHis.getCreate_time());
                mVHis.mTxtNewpieceCode.setText(mHis.getUp_material_no());
                mVHis.mTxtNewpieceLine.setText(mHis.getUp_parts_sn());
                mVHis.mTxtNewpieceDesc.setText(mHis.getUp_material_no_desc());
                mVHis.mTxtOldpieceCode.setText(mHis.getDown_material_no());
                mVHis.mTxtOldpieceLine.setText(mHis.getDown_parts_sn());
                mVHis.mTxtOldpieceDesc.setText(mHis.getDown_material_no_desc());
            }

        }else if (viewHolder instanceof ViewHolderBoxs) {
            ChangeBoxsInfo2.BoxList mBoxInfo = (ChangeBoxsInfo2.BoxList) data;
            //将数据保存在itemView的Tag中，以便点击时进行获取
            viewHolder.itemView.setTag(mBoxInfo);
            View[] views =((ViewHolderBoxs)viewHolder).getViews();
            if (mBoxInfo != null){
                TextView mTxtSparePartId = (TextView) views[0];
                TextView mTxtSparePartBar = (TextView) views[1];
                TextView mTxtDescribe = (TextView) views[2];
                TextView mTxtClassificat = (TextView) views[3];
                TextView mTxtPriceUp = (TextView) views[4];
                //RadioButton mRadioBtnSelect = (RadioButton) views[5];
                CheckBox mRadioBtnSelect = (CheckBox) views[5];
                // ImageView mRadioBtnSelect = (ImageView) views[5];
                LinearLayout mChangeGroup = (LinearLayout) views[6];
                LinearLayout mchangeLine = (LinearLayout) views[7];

                mTxtSparePartId.setText(mBoxInfo.getCode());
                mTxtSparePartBar.setText(mBoxInfo.getSn());
                mTxtDescribe.setText(mBoxInfo.getDesc());
                mTxtClassificat.setText(mBoxInfo.getType());
                mTxtPriceUp.setText(mBoxInfo.getPrice());
                mRadioBtnSelect.setVisibility(View.GONE);
                mChangeGroup.setPadding(mContext.getResources().getDimensionPixelSize(R.dimen.layout_25dp),mContext.getResources().getDimensionPixelSize(R.dimen.layout_25dp),mContext.getResources().getDimensionPixelSize(R.dimen.layout_25dp),0);
                if ( i != (getItemCount()-1)){
                    mchangeLine.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mchangeLine.getLayoutParams();
                    layoutParams.setMargins(0,mContext.getResources().getDimensionPixelSize(R.dimen.layout_20dp),0,0);
                    mchangeLine.setLayoutParams(layoutParams);
                } else {
                    mchangeLine.setVisibility(View.GONE);
                }
            }

        }else if (viewHolder instanceof ViewHolderBareBone) {
            ChangeBoxsInfo2.BareBone mBare = (ChangeBoxsInfo2.BareBone) data;
            ViewHolderBareBone mVBare = (ViewHolderBareBone) viewHolder;
            if (mBare != null){
                mVBare.itemView.setTag(mBare);
                mVBare.mTxtPieceType.setText(mBare.getMaterial_category());
                mVBare.mTxtPieceCode.setText(mBare.getMaterial_id());
                mVBare.mTxtPieceName.setText(mBare.getMaterial_name());
            }
        }else {
            ChangeBoxsInfo2.Title mTitle = (ChangeBoxsInfo2.Title) data;
            ViewHolderTitle mVTitle = (ViewHolderTitle) viewHolder;
            mVTitle.mTxtTitle.setText(mTitle.getTitleName() );
        }

    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null){
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (ChangeBoxsInfo2.BoxList) v.getTag());
        }

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {

        this.mOnItemClickListener = listener;
    }

    public void setOnHisClickListener(OnRecyclerViewHisClickListener listener) {

        this.mOnHisClickListener = listener;
    }

    public void setOnBareClickListener(OnRecyclerViewBareClickListener listener) {

        this.mOnBareClickListener = listener;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderBoxs extends RecyclerView.ViewHolder {
        View[] views;
        public ViewHolderBoxs(View itemView,int[] ids) {
            super(itemView);
            views = new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = itemView.findViewById(ids[i]);
             }
        }

        public View[] getViews(){
            return views;
        }
    }


    public class ViewHolderHis extends RecyclerView.ViewHolder{
        public TextView mTxtRepairTime;
        public TextView mTxtNewpieceCode;
        public TextView mTxtNewpieceLine;
        public TextView mTxtNewpieceDesc;
        public TextView mTxtOldpieceCode;
        public TextView mTxtOldpieceLine;
        public TextView mTxtOldpieceDesc;
        public ViewHolderHis(View itemView) {
            super(itemView);
            mTxtRepairTime = (TextView) itemView.findViewById(R.id.text_pieceRepairTime);
            mTxtNewpieceCode = (TextView) itemView.findViewById(R.id.text_pieceNewCode);
            mTxtNewpieceLine = (TextView) itemView.findViewById(R.id.text_pieceNewLineCode);
            mTxtNewpieceDesc = (TextView) itemView.findViewById(R.id.text_pieceNewDesc);
            mTxtOldpieceCode = (TextView) itemView.findViewById(R.id.text_pieceOldCode);
            mTxtOldpieceLine = (TextView) itemView.findViewById(R.id.text_pieceOldLineCode);
            mTxtOldpieceDesc = (TextView) itemView.findViewById(R.id.text_pieceOldDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnHisClickListener != null){
                        //注意这里使用getTag方法获取数据
                        mOnHisClickListener.onItemClick(v, (ChangeBoxsInfo2.ChangeHistoriy) v.getTag());
                    }
                }
            });
        }
    }

    public class ViewHolderBareBone extends RecyclerView.ViewHolder{
        public TextView mTxtPieceType;
        public TextView mTxtPieceCode;
        public TextView mTxtPieceName;
        public ViewHolderBareBone(View itemView) {
            super(itemView);
            mTxtPieceType = (TextView) itemView.findViewById(R.id.text_pieceType);
            mTxtPieceCode = (TextView) itemView.findViewById(R.id.text_pieceCode);
            mTxtPieceName = (TextView) itemView.findViewById(R.id.text_pieceName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnBareClickListener != null){
                        //注意这里使用getTag方法获取数据
                        mOnBareClickListener.onItemClick(v, (ChangeBoxsInfo2.BareBone) v.getTag());
                    }
                }
            });
        }
    }

    public class ViewHolderTitle extends RecyclerView.ViewHolder{
        public TextView mTxtTitle;
        public ViewHolderTitle(View itemView) {
            super(itemView);
            mTxtTitle = (TextView) itemView.findViewById(R.id.text_boxfragTitle);
        }
    }


    @Override
    public int getItemViewType(int position) {
        ChangeBoxTypeData typeData = list.get(position);
        int boxType = typeData.getBoxType();
        return boxType;
    }
}
