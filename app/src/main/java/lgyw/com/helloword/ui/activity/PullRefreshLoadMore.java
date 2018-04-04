package lgyw.com.helloword.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGAMoocStyleRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import lgyw.com.helloword.R;
import lgyw.com.helloword.adapter.PullrefreshAdapter;

/**
 *
 * Created by MrRight on 2017/11/8.
 */

public class PullRefreshLoadMore extends Activity implements BGARefreshLayout.BGARefreshLayoutDelegate{

    private BGARefreshLayout mRefreshLayout;
    private RecyclerView recyclerView;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pullrefreshloadmore);

        initView();

        initRefreshLayout();
    }


    private void initView(){
        recyclerView=findViewById(R.id.mRecylerView);
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));
        List<String> list=new ArrayList<>();
        for (int i=0;i<30;i++){
            list.add(i+"");
        }

        recyclerView.setAdapter(new PullrefreshAdapter(this,list));
    }


    private void initRefreshLayout() {
        mRefreshLayout = findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGAMoocStyleRefreshViewHolder refreshViewHolder = new BGAMoocStyleRefreshViewHolder(this, true);
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("正在加载");
        refreshViewHolder.setUltimateColor(R.color.colorAccent);
        // 设置下拉刷新控件的背景 drawable 资源 id
        refreshViewHolder.setOriginalImage(R.mipmap.ic_launcher);
        refreshViewHolder.setSpringDistanceScale(0.3f);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);


        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        // mRefreshLayout.setIsShowLoadingMoreView(false);

        // 设置下拉刷新控件的背景颜色资源 id  setOriginalImage

        // 设置下拉刷新控件的背景颜色资源 id
        //refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.colorPrimaryDark);
        // 设置下拉刷新控件的背景 drawable 资源 id
        //refreshViewHolder.setRefreshViewBackgroundDrawableRes(R.mipmap.ic_launcher);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
        mRefreshLayout.setCustomHeaderView(null, false);
        // 可选配置  -------------END
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.endRefreshing();
            }
        },3000);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        // 在这里加载更多数据，或者更具产品需求实现上拉刷新也可以
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.endLoadingMore();
            }
        },3000);
        return true;
    }



    class MyBGARefreshViewHolder extends BGARefreshViewHolder {

        /**
         * @param context
         * @param isLoadingMoreEnabled 上拉加载更多是否可用
         */
        public MyBGARefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
            super(context, isLoadingMoreEnabled);
        }

        @Override
        public View getRefreshHeaderView() {return null;}

        @Override
        public void handleScale(float scale, int moveYDistance) {}

        @Override
        public void changeToIdle() {}

        @Override
        public void changeToPullDown() {}

        @Override
        public void changeToReleaseRefresh() {}

        @Override
        public void changeToRefreshing() {}

        @Override
        public void onEndRefreshing(){}
    }
}
