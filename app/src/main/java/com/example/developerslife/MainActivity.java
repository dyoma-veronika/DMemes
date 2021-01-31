package com.example.developerslife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //URL url = new URL("http://static.devli.ru/public/images/gifs/202005/4491a587-061f-47aa-85cd-2082ff89ab97.gif");

    String[] title = {"Latest", "Top", "Hot"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);

        viewPager.setAdapter(new ViewPagerFragmentAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(title[position]);
        }).attach();
    }


    /*public void ShowGif(View view) {
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(this).load(url).into(imageView);
    }*/


    static class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        ViewPagerFragmentAdapter(FragmentActivity fragmentActivity){
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0 :
                    return new LatestFragment();
                case 1 :
                    return new TopFragment();
                case 2:
                    return new HotFragment();
            }
            return new LatestFragment();
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}