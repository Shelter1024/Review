package com.palmtop.app2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Shelter
 * Create time: 2022/4/28, 15:09.
 */
public class RecyclerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_recycler_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            final int THRESHOLD_LOAD_MORE = 3;
            boolean loadMore = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    loadMore = false;
                }

                if (newState != RecyclerView.SCROLL_STATE_DRAGGING && !loadMore) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    MyAdapter adapter = (MyAdapter) recyclerView.getAdapter();
                    int itemCount = adapter.getItemCount();
                    if ((itemCount - lastVisibleItemPosition - 1) < THRESHOLD_LOAD_MORE) {
                        adapter.refreshData();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    static class MyAdapter extends RecyclerView.Adapter<MyHolder> {
        List<String> data = new ArrayList<>();
        private int index = 0;

        public MyAdapter() {
            refreshData();
        }

        public void refreshData() {
            for (int i = index; i < (index + 10); i++) {
                data.add("Item " + i);
            }
            index += 10;
        }

        @NonNull
        @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_content_item, parent, false);
            return new MyHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyHolder holder, int position) {
            holder.tvContent.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


    }

    static class MyHolder extends RecyclerView.ViewHolder {
        TextView tvContent;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}