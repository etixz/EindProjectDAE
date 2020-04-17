package dae.mob123.ui.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private String[] imgURLs;

    public ViewPagerAdapter(Context context, String[] imgURLs) {
        this.context = context;
        this.imgURLs = imgURLs;
    }

    @Override
    public int getCount() {
        return imgURLs.length;
    }
    /*lets ViewPager know which item belongs to which page, uses return value from instantiateItem() as key*/
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /*loads the images into the page, provides key for isViewFromObject()*/
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        Picasso.get()
                .load(imgURLs[position])
                .fit()
                .centerCrop()
                .into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
