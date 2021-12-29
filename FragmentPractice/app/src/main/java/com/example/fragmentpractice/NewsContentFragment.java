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

public class NewsContentFragment extends Fragment {

    private View view = null;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.news_content_frag, container, false);
        return view;
    }

//    刷新一条新闻
    public void refresh(String title, String content) {
        //获取新闻item使其可见
        LinearLayout contentLayout = view.findViewById(R.id.contentLayout);
        contentLayout.setVisibility(View.VISIBLE);
        //新闻数据写入各自的textview中
        TextView newsTitle = view.findViewById(R.id.newsTitle);
        TextView newsContent = view.findViewById(R.id.newsContent);
        newsTitle.setText(title);
        newsContent.setText(content);
    }
}
