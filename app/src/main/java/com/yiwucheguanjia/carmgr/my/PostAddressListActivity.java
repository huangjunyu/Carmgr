package com.yiwucheguanjia.carmgr.my;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yiwucheguanjia.carmgr.R;
import com.yiwucheguanjia.carmgr.my.controller.AddressAdapter;
import com.yiwucheguanjia.carmgr.utils.RecyclerViewDivider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAddressListActivity extends AppCompatActivity {
    AddressAdapter addressAdapter;
    @BindView(R.id.addr_list_addr_rv)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_address_list);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewDivider(PostAddressListActivity.this,LinearLayoutManager.HORIZONTAL,1, ContextCompat.getColor(PostAddressListActivity.this,R.color.gray_divide)));
        addressAdapter = new AddressAdapter(PostAddressListActivity.this,null);
        recyclerView.setAdapter(addressAdapter);
    }
}
