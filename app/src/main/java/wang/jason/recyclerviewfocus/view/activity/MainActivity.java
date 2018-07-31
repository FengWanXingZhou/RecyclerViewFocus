package wang.jason.recyclerviewfocus.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wang.jason.recyclerviewfocus.R;
import wang.jason.recyclerviewfocus.contract.MainContract;
import wang.jason.recyclerviewfocus.presenter.MainPresenter;
import wang.jason.recyclerviewfocus.view.adapter.RvAdapterA;
import wang.jason.recyclerviewfocus.view.adapter.RvAdapterB;
import wang.jason.recyclerviewfocus.view.ui.DividerItemDecoration;

import static wang.jason.recyclerviewfocus.view.ui.DividerItemDecoration.VERTICAL_LIST;

public class MainActivity extends BaseActivity implements MainContract.MainView{


    private MainPresenter mainPresenter;

    @BindView(R.id.rvALsitView)
    RecyclerView rvALsitView;
    @BindView(R.id.rvBLsitView)
    RecyclerView rvBLsitView;

    RvAdapterA rvAdapterA;
    RvAdapterB rvAdapterB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initRecyclerviewA();
        initRecyclerviewB();
    }

    @Override
    protected void initData() {
        mainPresenter = new MainPresenter(this);
        mainPresenter.getData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initRecyclerviewA(){

        rvAdapterA = new RvAdapterA(this);

        rvAdapterA.setHasStableIds(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rvALsitView.setItemAnimator(null);

        rvALsitView.setLayoutManager(layoutManager);

        rvALsitView.addItemDecoration(new DividerItemDecoration(this,VERTICAL_LIST));

        rvALsitView.setAdapter(rvAdapterA);

    }

    private void initRecyclerviewB(){

        rvAdapterB = new RvAdapterB(this);

        rvAdapterB.setHasStableIds(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rvBLsitView.setItemAnimator(null);

        rvBLsitView.setLayoutManager(layoutManager);

        rvBLsitView.addItemDecoration(new DividerItemDecoration(this,VERTICAL_LIST));

        rvBLsitView.setAdapter(rvAdapterB);
    }

    @Override
    public void updateRecyclerViewAData(List<String> aDataStringList) {
        rvAdapterA.updateData(aDataStringList);
        rvAdapterA.notifyDataSetChanged();
    }

    @Override
    public void updateRecyclerViewBData(List<String> bDataStringList) {
        rvAdapterB.updateData(bDataStringList);
        rvAdapterB.notifyDataSetChanged();
    }
}
