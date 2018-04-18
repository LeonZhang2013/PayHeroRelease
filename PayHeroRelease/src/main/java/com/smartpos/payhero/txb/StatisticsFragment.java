package com.smartpos.payhero.txb;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.chanven.lib.cptr.recyclerview.RecyclerAdapterWithHF;
import com.google.gson.Gson;
import com.smartpos.payhero.R;
import com.smartpos.payhero.txb.bean.PayRecord;
import com.smartpos.payhero.txb.bean.PayRecordList;
import com.smartpos.payhero.txb.net.NetTools;
import com.smartpos.payhero.txb.net.PullDownObserver;
import com.smartpos.payhero.txb.ui.PtrClassicFrameLayoutEx;
import com.smartpos.payhero.txb.ui.PtrDefaultHandlerEx;
import com.smartpos.payhero.txb.ui.PtrFrameLayoutEx;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Response;


public class StatisticsFragment extends BaseFragment {

    private int mYear, mMonth, mDay;
    private Unbinder unbinder;
    private List<PayRecord> mDatas = new ArrayList<>();
    private RecyclerAdapterWithHF mAdapter;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

    @BindView(R.id.start_date)
    TextView stateDate;
    @BindView(R.id.end_date)
    TextView endDate;

    private String startDateStr,endDateStr;
    private int pageSize = 10,pageIndex = 1;

    @BindView(R.id.test_recycler_view_frame)
    PtrClassicFrameLayoutEx ptrClassicFrameLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private Calendar cal = Calendar.getInstance();


    @Override
    public View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        initDate();
        setListener();

        CommonAdapter adapter = new CommonAdapter<PayRecord>(getActivity(), R.layout.record_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final PayRecord payRecord, int position) {

                holder.setText(R.id.item_time, formatTime.format(new Date(Long.parseLong(payRecord.getTime()))));
                holder.setText(R.id.item_amount, "¥" + payRecord.getTprice());
            }
        };

        mAdapter = new RecyclerAdapterWithHF((MultiItemTypeAdapter)adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(OrderInfoActivity.class, mDatas.get(position).getOrder_id());
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        recyclerView.setAdapter(mAdapter);
    }

    private void initDate() {
        cal.setTime(new Date());
        String current = format.format(new Date());
        startDateStr = current;
        endDateStr = current;
        stateDate.setText(current);
        endDate.setText(current);
    }

    public void setListener(){
        ptrClassicFrameLayout.post(new Runnable() {
            @Override
            public void run() {
                ptrClassicFrameLayout.autoRefresh(true,1000);
            }
        });

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandlerEx() {
            @Override
            public void onRefreshBegin(PtrFrameLayoutEx frame) {
                pageIndex = 1;
                mDatas.clear();
                queryList(startDateStr, endDateStr,pageIndex,pageSize,PullDownObserver.DROP_DOWN);
            }
        });

        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                pageIndex++;
                queryList(startDateStr, endDateStr,pageIndex,pageSize,PullDownObserver.LOAD_MORE);
            }
        });
    }

    public void queryList(String startTime, String endTime,int pageIndex,int pageSize,final int dropDown) {
        try {
            Date start = format.parse(startTime);
            Date end = format.parse(endTime);
            if (start.compareTo(end) == 1) {
                showToast("开始日期 不得大于 结束日期");
                return;
            }
        } catch (ParseException e) {
            showToast("日期格式有误");
        }

        JSONObject data = new JSONObject();
        try {
            data.put("cmd", 4);
            data.put("uid", getConstantUser().getUid());
            data.put("page", pageIndex);
            data.put("pageSize", pageSize);
            data.put("stime", startTime);
            data.put("etime", endTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        NetTools.post(data.toString(), new PullDownObserver<Response<ResponseBody>>(getActivity(),dropDown) {
            @Override
            public void dropdown(@NonNull Response<ResponseBody> response) {
                try {
                    Gson gson = new Gson();
                    PayRecordList temp = gson.fromJson(response.body().string(), PayRecordList.class);
                    List dataList = (List<PayRecord>) temp.getData();
                    mDatas.addAll(dataList);

                    mAdapter.notifyDataSetChangedHF();
                    ptrClassicFrameLayout.refreshComplete();
                    ptrClassicFrameLayout.setLoadMoreEnable(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void loadMore(@NonNull Response<ResponseBody> response) {
                try {
                    Gson gson = new Gson();
                    PayRecordList temp = gson.fromJson(response.body().string(), PayRecordList.class);
                    List dataList = (List<PayRecord>) temp.getData();
                    int size = dataList.size();
                    mDatas.addAll(dataList);
                    int beforeChangeSize = mDatas.size() + mAdapter.getHeadSize();
                    mAdapter.notifyItemRangeInsertedHF(beforeChangeSize, size);
                    ptrClassicFrameLayout.loadMoreComplete(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }




    @OnClick({R.id.end_date, R.id.start_date})
    public void selectDate(View view) {
        final TextView tempDateView = (TextView) view;
        String dateStr = tempDateView.getText().toString();
        Date date = new Date();
        if (dateStr.length() > 0) {
            try {
                date = format.parse(dateStr);
            } catch (ParseException e) {
            }
        }
        cal.setTime(date);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tempDateView.setText(year + "-" + (++month) + "-" + dayOfMonth);
                queryList(stateDate.getText().toString(), endDate.getText().toString(),pageIndex,pageSize,PullDownObserver.DROP_DOWN);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

    }


    /**
     * onDestroyView中进行解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
