package com.palmtop.app2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.palmtop.app2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Shelter
 * Create time: 2022/4/28, 12:52.
 */
public class HeaderRecyclerView extends RecyclerView {

    public HeaderRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public HeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeaderRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setLayoutManager(new LinearLayoutManager(context));
        addItemDecoration(new SimpleDividerDecoration(context));
        setAdapter(new HeaderAdapter(getData()));
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            data.add("Header Item " + i);
        }
        return data;
    }


    static class HeaderAdapter extends RecyclerView.Adapter<HeaderHolder> {
        List<String> headerList;

        public HeaderAdapter(List<String> headerList) {
            this.headerList = headerList;
        }

        @NonNull
        @Override
        public HeaderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_header_item, parent, false);
            return new HeaderHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull HeaderHolder holder, int position) {
            String title = headerList.get(position);
            holder.tvTitle.setText(title);
        }

        @Override
        public int getItemCount() {
            return headerList.size();
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}