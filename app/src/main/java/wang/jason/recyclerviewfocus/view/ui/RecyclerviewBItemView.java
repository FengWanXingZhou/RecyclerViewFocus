package wang.jason.recyclerviewfocus.view.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import wang.jason.recyclerviewfocus.R;

/**
 * @author wj
 * @Date: 2018/7/31
 * @Description:
 **/
public class RecyclerviewBItemView extends LinearLayout {

    @BindView(R.id.tvName)
    public TextView tvName;
    public RecyclerviewBItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        LayoutInflater.from(context).inflate(R.layout.rv_b_item_view,this);
        ButterKnife.bind(this,this);
    }
}
