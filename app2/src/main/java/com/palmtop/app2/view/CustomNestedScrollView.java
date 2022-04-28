package com.palmtop.app2.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;


import com.palmtop.app2.FlingHelper;

/**
 * @author: Shelter
 * Create time: 2022/4/28, 15:36.
 */
public class CustomNestedScrollView extends NestedScrollView {

    private ViewGroup contentView;
    private View headerView;

    public CustomNestedScrollView(@NonNull Context context) {
        this(context, null);
        init(context);
    }

    public CustomNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public CustomNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private FlingHelper flingHelper;
    private int totalDy;
    private int velocity;
    private boolean isStartFling;

    private void init(Context context) {
        flingHelper = new FlingHelper(context);
        setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (isStartFling) {
                    isStartFling = false;
                    totalDy = 0;
                }

                if (scrollY == 0) {
                    Log.d("Shelter", "CustomNestedScrollView onScrollChange() 滑到顶部");
                }
                int totalHeight = getChildAt(0).getMeasuredHeight();
                int measuredHeight = v.getMeasuredHeight();
                if (scrollY == (totalHeight - measuredHeight)) {
                    dispatchChildFling();
                }
                totalDy += scrollY - oldScrollY;
            }
        });
    }

    private void dispatchChildFling() {
        if (velocity != 0) {
            double splineFlingDistance = flingHelper.getSplineFlingDistance(velocity);
            if (splineFlingDistance > totalDy) {
                childFling(flingHelper.getVelocityByDistance(splineFlingDistance - totalDy));
            }
        }
        totalDy = 0;
        velocity = 0;
    }

    private void childFling(int velocityY) {
        RecyclerView flingChild = getChildRecyclerView(contentView);
        Log.d("Shelter", "CustomNestedScrollView childFling() flingChild = " + flingChild);
        if (flingChild != null) {
            flingChild.fling(0, velocityY);
        }
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (velocityY <= 0) {
            velocity = 0;
        } else {
            isStartFling = true;
            velocity = velocityY;
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = ((ViewGroup) getChildAt(0)).getChildAt(0);
//        contentView = findViewById(R.id.contentLayout);
        contentView = (ViewGroup) ((ViewGroup) getChildAt(0)).getChildAt(1);
        Log.d("Shelter", "CustomNestedScrollView onFinishInflate() contentView = " + contentView.getClass().getName());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.height = measuredHeight;
        contentView.setLayoutParams(layoutParams);
    }

//    @Override
//    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
//
//    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.d("Shelter", "CustomNestedScrollView onNestedPreScroll() target " + target.getClass().getSimpleName() + ", dy = " + dy);
        boolean hideTop = dy > 0 && getScrollY() < headerView.getMeasuredHeight();
        if (hideTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    private RecyclerView getChildRecyclerView(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof RecyclerView) {
                return (RecyclerView) viewGroup.getChildAt(i);
            } else if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                ViewGroup childRecyclerView = getChildRecyclerView((ViewGroup) viewGroup.getChildAt(i));
                if (childRecyclerView instanceof RecyclerView) {
                    return (RecyclerView) childRecyclerView;
                }
            }
        }
        return null;
    }
}