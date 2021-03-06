package com.bzh.dytt.base.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bzh.dytt.base.eventbus.EventCenter;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * ==========================================================<br>
 * <b>版权</b>：　　　别志华 版权所有(c)2016<br>
 * <b>作者</b>：　　  biezhihua@163.com<br>
 * <b>创建日期</b>：　16-3-20<br>
 * <b>描述</b>：　　　<br>
 * <b>版本</b>：　    V1.0<br>
 * <b>修订历史</b>：　<br>
 * ==========================================================<br>
 */
public abstract class BaseActivity extends AppCompatActivity {

    private ActivityConfig activityConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        activityConfig = new ActivityConfig();
        initUIConfig(activityConfig);
        super.onCreate(savedInstanceState);

        setContentView(getContentViewResId());

        if (activityConfig.isApplyButterKnife) {
            ButterKnife.bind(this);
        }

        if (activityConfig.isApplyEventBus) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        if (activityConfig.isApplyButterKnife) {
            ButterKnife.unbind(this);
        }
        if (activityConfig.isApplyEventBus) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    protected void initUIConfig(ActivityConfig activityConfig) {

    }

    protected final void onEventMainThread(EventCenter eventCenter) {
        if (eventCenter != null) {
            onEventComing(eventCenter);
        }
    }

    protected void onEventComing(EventCenter eventCenter) {
    }

    protected abstract int getContentViewResId();

    protected final static class ActivityConfig extends UIConfig {
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
