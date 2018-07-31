package wang.jason.recyclerviewfocus.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wang.jason.recyclerviewfocus.R;
import wang.jason.recyclerviewfocus.view.ui.RecyclerviewAItemView;

/**
 * @author wj
 * @Date: 2018/7/31
 * @Description:
 **/
public class RvAdapterA extends RecyclerView.Adapter<RvAdapterA.ViewHolder> {


    private Context context;
    private List<String> dataList = new ArrayList<>();

    public RvAdapterA(Context context){
        this.context = context;
    }

    public void updateData(List<String> stringList){
        this.dataList = stringList;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.rv_a_item_view_selfdefine;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RvAdapterA.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_a_item_view_selfdefine,parent,false);

        return new RvAdapterA.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RvAdapterA.ViewHolder holder, int position) {
        holder.tvName.setText(dataList.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvName;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = ((RecyclerviewAItemView)itemView).tvName;
        }
    }

}
