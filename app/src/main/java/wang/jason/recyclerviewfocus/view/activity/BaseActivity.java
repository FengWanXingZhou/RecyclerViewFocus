package wang.jason.recyclerviewfocus.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author wj
 * @Date: 2018/7/31
 * @Description:
 **/
public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initData();
    }

    protected abstract int getLayout();
    protected abstract void initView();
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
