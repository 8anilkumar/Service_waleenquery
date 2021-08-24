package com.ennovation.servicewaleenquery.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ennovation.servicewaleenquery.Model.Banner.BannerResponseData;
import com.ennovation.servicewaleenquery.R;
import com.github.demono.adapter.InfinitePagerAdapter;

import java.util.List;

import static com.ennovation.servicewaleenquery.Utils.Constants.IMAGEURL;

public class SliderAdapter extends InfinitePagerAdapter {

    Context context;
    List<BannerResponseData> bottomBannerResponses;

    public SliderAdapter(Context context, List<BannerResponseData> bottomBannerResponses) {
        this.context = context;
        this.bottomBannerResponses = bottomBannerResponses;
    }

    @Override
    public int getItemCount() {
        return  bottomBannerResponses.size();
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup view) {
        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
        View imageLayout = layoutInflater.inflate(R.layout.slidingimages_layout, view, false);
        final ImageView imageView = imageLayout.findViewById(R.id.image);
        Glide.with(context).load( IMAGEURL+bottomBannerResponses.get(position).getImage()).into(imageView);
        return imageLayout;
    }
}
