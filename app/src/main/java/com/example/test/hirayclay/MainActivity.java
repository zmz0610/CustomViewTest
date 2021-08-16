package com.example.test.hirayclay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    RecyclerView recyclerview;

    //horizontal reverse recyclerview
    RecyclerView hrRecyclerView;
    Button button;
    RecyclerView src_atop;
    private StackLayoutManager layoutManager;
    private ScrollListenerHorizontalScrollView src_atop2;
    private LinearLayout ll_src_atop2;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = findViewById((R.id.recyclerview));
        hrRecyclerView = findViewById((R.id.recyclerview1));
        button = findViewById((R.id.button));
        Button button1 = findViewById((R.id.button1));
        resetDefault();
        resetRight();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDefault();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRight();
            }
        });
        initNestScrollView();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initNestScrollView() {
        src_atop = findViewById(R.id.src_atop);
        src_atop.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        List<View> viewList = new ArrayList<>();
        View viewFirst = LayoutInflater.from(this).inflate(R.layout.item_card_first, null, false);
        View view2 = LayoutInflater.from(this).inflate(R.layout.item_card, null, false);
        viewList.add(viewFirst);
        viewList.add(view2);

        src_atop.setAdapter(new TopAdapter(viewList));


        src_atop2 = findViewById(R.id.src_atop2);
        ll_src_atop2 = findViewById(R.id.ll_src_atop2);
        scrollView(0, 0);
        Handler handler = new Handler();
        src_atop2.setHandler(handler);
        src_atop2.setOnScrollStateChangedListener(new ScrollListenerHorizontalScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ScrollListenerHorizontalScrollView.ScrollType scrollType) {

            }

            @Override
            public void onScrollChanged2(ScrollListenerHorizontalScrollView scrollListenerHorizontalScrollView, int scrollX, int y, int oldx, int oldy) {
                scrollView(scrollX, oldx);
            }
        });
    }

    private void scrollView(int scrollX, int oldx) {
        int count = ll_src_atop2.getChildCount();
        int firstw = ll_src_atop2.getChildAt(0).getWidth();
        int width2 = ll_src_atop2.getChildAt(1).getWidth();
        int curPosition = 0;
        if (oldx + scrollX > firstw) {
            curPosition = (oldx + scrollX - firstw) / width2 + 1;
        }
        int allWidth = 0;
        int widthCount = 0;
        for (int i = curPosition; i < count; i++) {
            View view = ll_src_atop2.getChildAt(i);
            int left = 0;
            if (curPosition != i) {
                if (i > 0) {
                    left = allWidth;
                } else {
                    left = ScreenUtil.getScreenWidth(view.getContext()) - ScreenUtil.dp2px(view.getContext(), 15) - view.getMeasuredWidth();
                }
            }
            int right = left + view.getMeasuredWidth();
            if ( right < ScreenUtil.getScreenWidth(view.getContext())) {
                allWidth = right;
                ++widthCount;
            }
        }
        for (int i = curPosition; i < count; i++) {
            View view = ll_src_atop2.getChildAt(i);
            if (i > curPosition + widthCount) {
                float scale = i > curPosition + widthCount && widthCount != 0 ? (float) (1 - 0.8 * (i - curPosition) / widthCount) : 1f;
                float alpha = i > curPosition + widthCount && widthCount != 0 ? (float) (1 - 0.8 * (count - i) / widthCount) : 1;
                alpha = alpha <= 0.001f ? 0 : alpha;
                view.setAlpha(alpha);
                view.setScaleY(scale);
                view.setScaleX(scale);
                int left = 0;
                if (curPosition != i) {
                    if (i > 0) {
                        left = allWidth;
                    } else {
                        left = ScreenUtil.getScreenWidth(view.getContext()) - ScreenUtil.dp2px(view.getContext(), 15) - view.getMeasuredWidth();
                    }
                }
                int top = 0;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
            } else {
                int left = 0;
                if (i > curPosition) {
                    for (int j = curPosition; j < i; j++) {
                        View viewj = ll_src_atop2.getChildAt(j);
                        left+=viewj.getWidth();
                    }
                }
                int top = 0;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
            }

//                    layoutDecoratedWithMargins(view, left, top, right, bottom);


        }
    }

    public void resetDefault() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add(String.valueOf(i));
        }

        Config config = new Config();
        config.secondaryScale = 0.8f;
        config.scaleRatio = 0.4f;
        config.maxStackCount = 4;
        config.initialStackCount = 0;
        config.space = 15;
        config.align = Align.LEFT;
        recyclerview.setLayoutManager(layoutManager = new StackLayoutManager(config));
        recyclerview.setAdapter(new StackAdapter(datas));

    }

    public void resetRight() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add(String.valueOf(i));
        }

        Config config = new Config();
        config.secondaryScale = 0.8f;
        config.scaleRatio = 0.4f;
        config.maxStackCount = 4;
        config.initialStackCount = 14;
        config.space = getResources().getDimensionPixelOffset(R.dimen.item_space);

        config.align = Align.RIGHT;
        hrRecyclerView.setLayoutManager(new StackLayoutManager(config));
        hrRecyclerView.setAdapter(new StackAdapter(datas));
    }

    public void viewVertical() {
        startActivity(new Intent(this, VerticalActivity.class));
    }

    public void onScrollToItem() {
        layoutManager.scrollToPosition(10);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
