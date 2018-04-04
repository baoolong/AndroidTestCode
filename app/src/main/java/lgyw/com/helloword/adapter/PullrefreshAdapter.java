package lgyw.com.helloword.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lgyw.com.helloword.R;

/**
 * Created by MrRight on 2017/11/8.
 */

public class PullrefreshAdapter extends RecyclerView.Adapter<PullrefreshAdapter.MyViewHolder>{
    private List<String> list;
    private Context context;

    public PullrefreshAdapter(Context context,List<String> list){
        this.context=context;
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.pullrefreshitem,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.mTextView);
        }
    }
}
