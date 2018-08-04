/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.baidu.tripsight.core.base.BaseDaggerActivity;
import com.baidu.tripsight.ui.account.AccountFragment;
import com.baidu.tripsight.ui.attention.AttentionFragment;
import com.baidu.tripsight.ui.discover.DiscoverFragment;
import com.baidu.tripsight.ui.home.HomeFragment;
import com.baidu.tripsight.ui.publish.PublishActivity;
import com.baidu.tripsight.util.Utils;
import com.google.common.base.Objects;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.baidu.tripsight.MainActivity.Page.PAGE_ACCOUNT;
import static com.baidu.tripsight.MainActivity.Page.PAGE_ATTENTION;
import static com.baidu.tripsight.MainActivity.Page.PAGE_DISCOVER;
import static com.baidu.tripsight.MainActivity.Page.PAGE_PUBLISH;
import static com.google.common.collect.Lists.newArrayList;

/**
 * main activity
 *
 * @author jungletian
 */
public class MainActivity extends BaseDaggerActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    private List<Page> pageList;

    private final PageController pageController = new PageController() {
        @Override
        public void showPage(Page page) {
            if (pageList.indexOf(page) == -1) return;
            tabLayout.getTabAt(pageList.indexOf(page)).select();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setFullScreen(getWindow());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupViews();
        pageController.showPage(Page.PAGE_HOME);
    }

    private void setupViews() {
        pageList = newArrayList(Page.PAGE_HOME, PAGE_ATTENTION, PAGE_PUBLISH, PAGE_DISCOVER, PAGE_ACCOUNT);
        for (int index = 0, size = pageList.size(); index < size; index++) {
            tabLayout.addTab(createTabWithCustomView(pageList.get(index).layoutId()), index, false);
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Page page = pageList.get(position);
                if (page == PAGE_PUBLISH) {
                    startActivity(new Intent(MainActivity.this, PublishActivity.class));
                } else {
                    showFragment(page);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Page page = pageList.get(position);
                if (page == PAGE_PUBLISH) {
                    startActivity(new Intent(MainActivity.this, PublishActivity.class));
                }
            }
        });
    }

    private TabLayout.Tab createTabWithCustomView(@LayoutRes int resId) {
        TabLayout.Tab tab = tabLayout.newTab();
        View customView = getLayoutInflater().inflate(resId, tabLayout, false);
        tab.setCustomView(customView);
        return tab;
    }

    /**
     * 根据{@link Page} 显示fragment，并隐藏其他已经添加的fragment
     */
    private void showFragment(Page page) {
        Fragment fragment = getFragment(page);
        if (fragment == null) return;
        if (fragment.isVisible()) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(getSupportFragmentManager(), transaction, page);
        if (fragment.isAdded()) {
            transaction.show(fragment);
            fragment.setUserVisibleHint(true);
        } else {
            transaction.add(R.id.content, fragment, getFragmentTagName(page));
        }
        transaction.commitNowAllowingStateLoss();
    }

    /**
     * 隐藏 {@code fm} 中已经添加的fragment
     */
    private void hideFragments(FragmentManager fm, FragmentTransaction transaction, Page excluded) {
        for (Page page : pageList) {
            if (Objects.equal(page, excluded)) continue;
            Fragment fragment = fm.findFragmentByTag(getFragmentTagName(page));
            if (fragment != null && fragment.isAdded()) {
                fragment.setUserVisibleHint(false);
                transaction.hide(fragment);
            }
        }
    }

    private String getFragmentTagName(Page page) {
        return String.valueOf(page);
    }

    private Fragment getFragment(Page page) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(getFragmentTagName(page));
        if (fragment == null) {
            switch (page) {
                case PAGE_ATTENTION:
                    fragment = AttentionFragment.create();
                    break;
                case PAGE_DISCOVER:
                    fragment = DiscoverFragment.create();
                    break;
                case PAGE_ACCOUNT:
                    fragment = AccountFragment.create();
                    break;
                case PAGE_HOME:
                default:
                    fragment = HomeFragment.create();
                    break;
            }
        }
        return fragment;
    }

    public enum Page {
        PAGE_HOME("首页", R.layout.layout_tab_home),
        PAGE_ATTENTION("关注", R.layout.layout_tab_attention),
        PAGE_PUBLISH("发布", R.layout.layout_tab_publish),
        PAGE_DISCOVER("发现", R.layout.layout_tab_discover),
        PAGE_ACCOUNT("我", R.layout.layout_tab_account);

        private final String title;
        @LayoutRes
        private int layoutId;

        Page(String value, @LayoutRes int layoutId) {
            this.title = value;
            this.layoutId = layoutId;
        }

        public String title() {
            return title;
        }

        @LayoutRes
        public int layoutId() {
            return this.layoutId;
        }
    }

    /**
     * 控制首页tab的选择
     */
    public interface PageController {
        /**
         * 跳转到指定tab
         *
         * @param page {@link Page}
         */
        void showPage(Page page);
    }
}
