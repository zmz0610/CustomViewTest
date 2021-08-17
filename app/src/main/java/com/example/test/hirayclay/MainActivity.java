package com.example.test.hirayclay;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
                    Log.i(TAG, "i---" + i + "+++maxShowcount++++" + maxShowcount + "+++lastVisibleItemPosition~~" + lastVisibleItemPosition);
                    View view = recyclerView.getLayoutManager().findViewByPosition(i);
                    if (view == null) {
                        return;
                    }
                    if (i >= maxShowcount - 1) {
                        //右边做多显示两个其他的重合在最后一个
                        View lastView;
                        if (i == viewList.size() - 1) {
                            lastView = recyclerView.getLayoutManager().findViewByPosition(i);
                        } else {
                            lastView = recyclerView.getLayoutManager().findViewByPosition(i-1);
                        }
//                        View lastView = recyclerView.getChildAt(i -1);
                        if (i - lastCompletelyVisibleItemPosition == 1) {
                            float scale = 0.8f;
                            float alpha = 0.6f;
                            view.setScaleX(scale);
                            view.setScaleY(scale);
                            view.setAlpha(alpha);
                            int left = 0;
//                           int offset = (int) (view.getRight() - screenWidth+(screenWidth-lastView.getRight())/2+(view.getWidth() - view.getWidth() * scale) / 2);
                            left = (int) (view.getRight() - ((screenWidth + lastView.getRight()) / 2) - (view.getWidth() - view.getWidth() * scale) / 2);
                            view.setTranslationX(-left);
//                            view.setTranslationX(-offset);
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
        Context context = MainActivity.this;
        RelativeLayout relativeLayout = new RelativeLayout(context);
        FrameLayout.LayoutParams lp0 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        relativeLayout.setLayoutParams(lp0);
        for (int i = 0; i < 7; i++) {
            if (i == 0) {
                View view = initFirstView(relativeLayout);
                view.setId(1000 + i);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(view.getMeasuredWidth(), view.getMeasuredHeight());
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);//与父容器的左侧对齐
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);//与父容器的上侧对齐
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);//与父容器的上侧对齐
                view.setLayoutParams(lp);//设置布局参数
                relativeLayout.addView(view);
            }else{
                View childView2 = relativeLayout.getChildAt(i-1);
                View view = initSecondView();
                view.setId(1000 + i);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.RIGHT_OF,  childView2.getId());//与父容器的左侧对齐
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);//与父容器的上侧对齐
                view.setLayoutParams(lp);//设置布局参数
                relativeLayout.addView(view);
            }
        }
        src_atop2.addView(relativeLayout);
        Handler handler = new Handler();
        src_atop2.setHandler(handler);
        src_atop2.setOnScrollStateChangedListener(new ScrollListenerHorizontalScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ScrollListenerHorizontalScrollView.ScrollType scrollType) {

            }

            @Override
            public void onScrollChanged2(ScrollListenerHorizontalScrollView scrollListenerHorizontalScrollView, int scrollX, int scrollY, int oldx, int oldy) {
                scrollView(scrollX, scrollY, oldx, oldy);
            }
        });
    }

    private View initFirstView(RelativeLayout relativeLayout) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_card_first, relativeLayout, false);
        return view;
    }

    private View initSecondView() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_card, null, false);
        return view;
    }

    private void scrollView(int scrollX, int scrollY, int oldx, int oldy) {
//        src_atop2.getChildAt()
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
//            scrollView(0, 0, 0, 0);
        }
        super.onWindowFocusChanged(hasFocus);
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
