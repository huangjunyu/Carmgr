package com.yiwucheguanjia.carmgr.transaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.my.AddCarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/2.
 */
public class TransactionFragment extends Fragment {
    private View view;
    @BindView(R.id.transaction_addcar_rl)
    RelativeLayout addCarRl;
    @BindView(R.id.transaction_maintain_rl)
    RelativeLayout maintainRl;
    @BindView(R.id.transaction_insurance_rl)
    RelativeLayout insuranceRl;//有效保险
    @BindView(R.id.transaction_annual_inspection_rl)
    RelativeLayout annualInspectionRl;//下次年检
    @BindView(R.id.transaction_break_rules_rl)
    RelativeLayout breakRulesRl;//查询违章
    @BindView(R.id.transaction_refuel_up_rl)
    RelativeLayout refuelUpRl;
    @BindView(R.id.transaction_insurance_service_rl)
    RelativeLayout insuranceServiceRl;
    @BindView(R.id.transaction_bread_rules_query_rl)
    RelativeLayout breadRulesQueryRl;
    @BindView(R.id.transaction_bread_rules_pay_rl)
    RelativeLayout breakRulesPayRl;//交通违法缴罚
    @BindView(R.id.transaction_park_pay_rl)
    RelativeLayout parkPayRl;
    @OnClick({R.id.transaction_addcar_rl,R.id.transaction_maintain_rl,R.id.transaction_insurance_rl
            ,R.id.transaction_annual_inspection_rl,R.id.transaction_break_rules_rl,R.id.transaction_refuel_up_rl,
            R.id.transaction_insurance_service_rl,R.id.transaction_bread_rules_query_rl,
            R.id.transaction_bread_rules_pay_rl,R.id.transaction_park_pay_rl})
    void click(View view){
        switch (view.getId()){
            case R.id.transaction_addcar_rl:
                Intent addCarIntent = new Intent(getActivity(), AddCarActivity.class);
                startActivity(addCarIntent);
                break;
            case R.id.transaction_maintain_rl:
                break;
            case R.id.transaction_insurance_rl:
                break;
            case R.id.transaction_annual_inspection_rl:
                break;
            case R.id.transaction_break_rules_rl:
                break;
            case R.id.transaction_refuel_up_rl:
                break;
            case R.id.transaction_insurance_service_rl:
                break;
            case R.id.transaction_bread_rules_query_rl:
                break;
            case R.id.transaction_bread_rules_pay_rl:
                break;
            case R.id.transaction_park_pay_rl:
                break;
            case R.id.transaction_car_appraisement_rl://爱车估值
                break;
            case R.id.transaction_technician_online_rl://技师在线
                break;
            case R.id.transaction_more_service_rl:
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = (View)inflater.inflate(R.layout.fragment_transaction,container,false);
        ButterKnife.bind(this,view);
        return view;
    }
}
