package com.example.test.hirayclay;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        src_atop.setLayoutManager(linearLayoutManager);
        List<Integer> viewList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            viewList.add(i);
        }
        src_atop.setAdapter(new TopAdapter(viewList));
        src_atop.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int lastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int firstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                int maxShowcount = lastVisibleItemPosition - firstVisibleItemPosition + 1; //4 2
                int screenWidth = ScreenUtil.getScreenWidth(MainActivity.this);

                for (int i = 0; i < viewList.size(); i++) {
//                for (int i = viewList.size()-1; i >=0; i--) {
                    Log.i(TAG, "lastCompletelyVisibleItemPosition++~" + lastCompletelyVisibleItemPosition + "view~~" + viewList.size());
                    Log.i(TAG, "i---"+i + "+++maxShowcount++++" + maxShowcount + "+++lastVisibleItemPosition~~" + lastVisibleItemPosition);
                    View view = recyclerView.getChildAt(i);
                    if (view == null) {
                        return;
                    }
                    if (i >= maxShowcount - 1) {
                        //右边做多显示两个其他的重合在最后一个
                        View lastView;
                        if (i==viewList.size()-1){
                             lastView = recyclerView.getChildAt(i );
                        }else{
                             lastView = recyclerView.getChildAt(i -1);
                        }
//                        View lastView = recyclerView.getChildAt(i -1);
                        if (i - lastCompletelyVisibleItemPosition == 1) {
                            float scale = 0.8f;
                            float alpha = 0.6f;
                            view.setScaleX(scale);
                            view.setScaleY(scale);
                            view.setAlpha(alpha);
                            int left = 0;
                            left = (int) (view.getRight() - ((screenWidth + lastView.getRight()) / 2) - (view.getWidth() - view.getWidth() * scale) / 2);
                            view.setTranslationX(-left);
                        } else if (i == maxShowcount - 2) {
                            float scale = 0.4f;
                            float alpha = 0.4f;
                            view.setScaleX(scale);
                            view.setScaleY(scale);
                            view.setAlpha(alpha);
                            int left = 0;
                            if (view.getRight() > (screenWidth)) {
                                left = view.getRight() - screenWidth - ScreenUtil.dp2px(MainActivity.this, 15);
                            } else {
                                left = screenWidth - (view.getRight() + screenWidth) / 2 - ScreenUtil.dp2px(MainActivity.this, 15);

                            }
                            view.setTranslationX(-left);
                        } else {
                            float scale = 0.6f;
                            float alpha = 0.6f;
                            view.setScaleX(scale);
                            view.setScaleY(scale);
                            view.setAlpha(alpha);
                            int left = 0;
                            if (view.getRight() > (screenWidth)) {
                                left = view.getRight() - screenWidth - ScreenUtil.dp2px(MainActivity.this, 15);
                            } else {
                                left = screenWidth - (view.getRight() + screenWidth) / 2 - ScreenUtil.dp2px(MainActivity.this, 15);

                            }
                            view.setTranslationX(-left);
                        }
                    } else {
                        view.setTranslationX(0);
                        view.setAlpha(1);
                        view.setScaleX(1);
                        view.setScaleY(1);
                    }
                }

            }
        });

        src_atop2 = findViewById(R.id.src_atop2);
        ll_src_atop2 = findViewById(R.id.ll_src_atop2);
        Handler handler = new Handler();
        src_atop2.setHandler(handler);
        src_atop2.setOnScrollStateChangedListener(new ScrollListenerHorizontalScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ScrollListenerHorizontalScrollView.ScrollType scrollType) {

            }

            @Override
            public void onScrollChanged2(ScrollListenerHorizontalScrollView scrollListenerHorizontalScrollView, int scrollX, int y, int oldx, int oldy) {
//                scrollView(scrollX, oldx, y, oldy);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
//            scrollView(0, 0, 0, 0);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    private void scrollView(int scrollX, int oldx, int y, int oldy) {
        int showCount = 4;
        int count = ll_src_atop2.getChildCount();
        int firstw = ll_src_atop2.getChildAt(0).getWidth();
        int width2 = ll_src_atop2.getChildAt(1).getWidth();
        int curPosition = 0;
        Log.i(TAG, oldx + "~~~~" + scrollX + "~~~~~" + firstw);
        if (oldx > scrollX) {
            //左滑
        } else if (scrollX > firstw * 2 / 3) {
            curPosition = (scrollX - firstw) / width2 + 1;
            Log.i(TAG, curPosition + "!!!!!");
            return;
        }
        curPosition = (scrollX - firstw) / width2 + 1;
        Log.i(TAG, curPosition + "!!!!!");
        int allWidth = 0;
        int widthCount = 0;
        for (int i = curPosition; i < count; i++) {
            View view = ll_src_atop2.getChildAt(i);
            int left = 0;
            left = allWidth;
            int right = left + view.getMeasuredWidth();
            if (right < ScreenUtil.getScreenWidth(view.getContext())) {
                allWidth = right;
                widthCount += 1;
            }
        }
        Log.i(TAG, curPosition + "~~~~~" + widthCount + "~~~~~~~~~" + allWidth);
        for (int i = curPosition; i < count; i++) {
            View view = ll_src_atop2.getChildAt(i);
            if (i >= curPosition + widthCount && i < showCount) {
                float scale = (float) (1 - 0.1 * (showCount - i));
                float alpha = (float) (1 - 0.1 * (showCount - i));
                alpha = alpha <= 0.001f ? 0 : alpha;
                view.setAlpha(alpha);
                view.setScaleY(scale);
                view.setScaleX(scale);
                int left = 0;
                left = ScreenUtil.getScreenWidth(view.getContext()) - ScreenUtil.dp2px(view.getContext(), 15) - view.getMeasuredWidth();
                int top = 0;
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);
            } else {
                int left = 0;
                view.setAlpha(1);
                view.setScaleY(1);
                view.setScaleX(1);
                if (i > curPosition) {
                    for (int j = curPosition; j < i; j++) {
                        View viewj = ll_src_atop2.getChildAt(j);
                        left += viewj.getWidth();
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
        for (int i = 0; i < 5; i++) {
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
        for (int i = 0; i < 5; i++) {
            datas.add(String.valueOf(i));
        }

        Config config = new Config();
        config.secondaryScale = 0.8f;
        config.scaleRatio = 0.4f;
        config.maxStackCount = 4;
        config.initialStackCount = 2;
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
