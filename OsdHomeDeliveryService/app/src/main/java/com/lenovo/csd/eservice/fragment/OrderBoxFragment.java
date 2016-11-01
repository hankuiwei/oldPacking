package com.lenovo.csd.eservice.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.activity.MoreAboutMachineActivity;
import com.lenovo.csd.eservice.adapter.BoxOrderAdapter;
import com.lenovo.csd.eservice.adapter.BoxOrderAdapter2;
import com.lenovo.csd.eservice.cache.SharedPrefManager;
import com.lenovo.csd.eservice.entity.base.BoxOrderData;
import com.lenovo.csd.eservice.entity.base.BoxOrderData2;
import com.lenovo.csd.eservice.entity.base.TypeData;
import com.lenovo.csd.eservice.http.HttpClientManager;
import com.lenovo.csd.eservice.http.NetInterface;
import com.lenovo.csd.eservice.http.ResultUtils;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.util.Utils;
import com.lenovo.csd.eservice.zxing.MipcaActivityCapture;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderBoxFragment extends BaseFragment {
    private EditText mEditSN;
    private ImageView mImgScan;
    private Button mBtnSearch;
    private RecyclerView mRecycler;

    //变量
    private BoxOrderAdapter mAdapter;//适配器
    private BoxOrderAdapter2 mAdapter2;//适配器
    private ArrayList<BoxOrderData.BoxBeanItem> datas;
    private ArrayList<TypeData> datas2;
    private String productSN;//主机SN号码
    private SharedPreferences mSharedPreference;
    private String token;
    private boolean showScan;
    private boolean isSelfSelected;
    private boolean isFirstLoad = true;

    //常量
    public static final String PARAM_SN = "product_sn";
    public static final String PARAM_SCAN = "show_scan";

    public OrderBoxFragment() {
        // Required empty public constructor
    }

    public static OrderBoxFragment newInstance(String sn, boolean showScan) {
        OrderBoxFragment fragment = new OrderBoxFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_SN, sn);
        args.putBoolean(PARAM_SCAN, showScan);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productSN = getArguments().getString(PARAM_SN) == null ? "" : getArguments().getString(PARAM_SN);
            showScan = getArguments().getBoolean(PARAM_SCAN, false);
        }
        mSharedPreference = SharedPrefManager.getSystemSharedPref(getContext());
        token = mSharedPreference.getString(SharedPrefManager.LOGIN_TOKEN, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_box, container, false);
        mEditSN = (EditText) view.findViewById(R.id.edit_inputSN);
        mImgScan = (ImageView) view.findViewById(R.id.imgScanQRcode);
        mBtnSearch = (Button) view.findViewById(R.id.btn_searchSN);
        mRecycler = (RecyclerView) view.findViewById(R.id.list_boxOrders);
        mBtnSearch.setOnClickListener(noDoubleClickLinstenner);
        mImgScan.setOnClickListener(noDoubleClickLinstenner);

        initData();
        return view;
    }

    private void initData() {
        mEditSN.setText(productSN);
        datas2 = new ArrayList<>();
        mAdapter2 = new BoxOrderAdapter2(getActivity(), datas2);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        mRecycler.setAdapter(mAdapter2);
        if (showScan) {
            mImgScan.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void getOrderBoxInfo(String SN) {
        mEditSN.setText("");
        mEditSN.setText(SN);
        mEditSN.setSelection(SN.length());
        if (Utils.checkInternetStatus(getContext()) == 0) {
            try {
                Toast.makeText(getActivity(), R.string.text_internet_unavalible, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
            return;
        }
        if (TextUtils.isEmpty(SN)) {
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("product_no", SN);
        showLoadingDialog();
        HttpClientManager.post(NetInterface.GET_ORDERBOX_INFO + "token=" + token, params, new JsonHttpCallBack<BoxOrderData2>() {
            @Override
            public void onSuccess(BoxOrderData2 result) {
                isFirstLoad = false;
                dismissLoadingDialog();
                if (ResultUtils.isSuccess(getActivity(), result)) {
                    mRecycler.setVisibility(View.VISIBLE);
                    BoxOrderData2.BoxOrderBean bean = result.getData();
                    if (bean != null && ((bean.getMaterial() != null && bean.getMaterial().size() > 0) || (bean.getChange_histories() != null && bean.getChange_histories().size() > 0)
                            || (bean.getBare_bone_material() != null && bean.getBare_bone_material().size() > 0))) {
                        datas2.clear();
                        ArrayList<BoxOrderData2.ChangeHisItem> hisDatas = bean.getChange_histories();
                        BoxOrderData2 boxOrderData2 = new BoxOrderData2();
                        if (hisDatas != null && hisDatas.size() > 0) {

                            datas2.add(boxOrderData2.new TitleItem("历史换件记录"));
                            datas2.addAll(hisDatas);
                        }
                        ArrayList<BoxOrderData2.BoxBeanItem> boxesData = bean.getMaterial();
                        if (boxesData != null && boxesData.size() > 0) {
                            datas2.add(boxOrderData2.new TitleItem("装箱单"));
                            datas2.addAll(boxesData);
                        }
                        ArrayList<BoxOrderData2.BareBoneItem> bareBones = bean.getBare_bone_material();
                        if (bareBones != null && bareBones.size() > 0) {
                            datas2.add(boxOrderData2.new TitleItem("BareBone"));
                            datas2.addAll(bareBones);
                        }
                        mAdapter2.notifyDataSetChanged();
                    } else {
//                        mListView.setVisibility(View.GONE);
                        mRecycler.setVisibility(View.GONE);
                        Utils.showError(getActivity(), "没有找到相关数据");
                    }
                } else {
//                    mListView.setVisibility(View.GONE);
                    mRecycler.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {
                isFirstLoad = false;
//                mListView.setVisibility(View.GONE);
                mRecycler.setVisibility(View.GONE);
                dismissLoadingDialog();
                if (isResumed())
                    Utils.showServerError(getActivity());
            }
        });

    }

    private NoDoubleClickLinstenner noDoubleClickLinstenner = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.btn_searchSN:
                    String sn = mEditSN.getText().toString().trim();
                    if (sn != null && !TextUtils.isEmpty(sn)) {
                        getOrderBoxInfo(sn);
                    } else {
                        Toast.makeText(getContext(), R.string.text_input_sn, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.imgScanQRcode:
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                            if(isResumed() && getActivity() != null){
                            getActivity().requestPermissions(new String[]{Manifest.permission.CAMERA},MoreAboutMachineActivity.REQUEST_FILLBOXCAMARA_ALLOW);
                            }
                        }else{
                            openCamera();
                        }
                    }else {
                        openCamera();
                    }

                    break;
            }
        }
    };

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        isSelfSelected = selected;
        if (isSelfSelected && isFirstLoad) {
            getOrderBoxInfo(productSN);
        }
    }

    /**
     * 打开相机扫码
     */
    public void openCamera() {
        Intent scanIntent = new Intent(getActivity(), MipcaActivityCapture.class);
        getActivity().startActivityForResult(scanIntent, MoreAboutMachineActivity.REQUEST_FILLBOX);
    }

}
