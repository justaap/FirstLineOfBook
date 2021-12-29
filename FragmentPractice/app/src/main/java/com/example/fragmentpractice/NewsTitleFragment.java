package com.example.fragmentpractice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {
    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //绑定fragment布局
        View view= inflater.inflate(R.layout.news_title_frag, container, false);
//        初始化用于显示新闻标题的RecyclerView
        RecyclerView newsTitleRecyclerView = view.findViewById(R.id.news_title_recycler_view);
//        设置recyclerView的布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(layoutManager);
//        设置recyclerView的适配器
        NewsAdapter adapter = new NewsAdapter(getNews());//适配器初始化需要新闻数据
        newsTitleRecyclerView.setAdapter(adapter);

        return view;
    }
    /*初始化新闻数据*/
    private List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            News news = new News("This is News Title " + i,
                    getRandomLengthContent("this is news"+i+"'s content. "));
            newsList.add(news);
        }
        return newsList;
    }
//    随机重复多次内容，生成不同长度的新闻
    private String getRandomLengthContent(String s) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(s);
        }
        return builder.toString();
    }

    /*在父Activity创建时，判断是单页还是双页*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.news_content_layout) != null) {
            // 通过查找父Activity中加载的xml文件的区别确定单双页模式；
            // news_content_layout只存在于双页模式中
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        private List<News> newsList;
//        初始化新闻列表
        public NewsAdapter(List<News> newsList) {
            this.newsList = newsList;
        }

        @NonNull
        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //在view中设置标题显示布局为news_item
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);//holder的初始化需要view
            view.findViewById(R.id.news_title);//获取view中的控件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取点击项的实例，双页模式则更新碎片数据，单页模式则启动新的Activity显示内容
                    News news = newsList.get(holder.getAdapterPosition());
                    if (isTwoPane) {
                        NewsContentFragment newsContentFragment = (NewsContentFragment)
                                getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(), news.getContent());
                    } else {
                        NewsContenActivity.actionStart(getActivity(),news.getTitle(),news.getContent());
                    }
                }
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
            News news = newsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                newsTitleText = itemView.findViewById(R.id.news_title);
            }
        }
    }

}
