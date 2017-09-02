package com.example.yihanwang.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yihanwang.myapplication.entities.ImageGalery;
import com.example.yihanwang.myapplication.entities.ImageInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class CustomSwip extends PagerAdapter {

    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwip(Context c) {
        ctx = c;
    }

    @Override
    public int getCount() {
        return ImageStorage.getInstance().getImageCount();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = layoutInflater.inflate(R.layout.activity_custom_swip, container, false);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.swip_image_view);

        ImageView leftArrow = (ImageView) itemView.findViewById(R.id.image_left_arrow);
        ImageView rightArrow = (ImageView) itemView.findViewById(R.id.image_right_arrow);
        Log.i("image", "update position "+position+","+ImageStorage.getInstance().getImageCount());
        if (position == 0) {
            leftArrow.setVisibility(View.GONE);
        } else {
            leftArrow.setVisibility(View.VISIBLE);
        }
        if (position >= ImageStorage.getInstance().getImageCount() - 1) {
            rightArrow.setVisibility(View.GONE);
        } else {
            rightArrow.setVisibility(View.VISIBLE);
        }

        final TextView textView = (TextView) itemView.findViewById(R.id.imageCount);
        ImageInfo imageInfo = ImageStorage.getInstance().getImageInfo(position);
        textView.setText("Image :" + (position + 1) + "/" + ImageStorage.getInstance().getImageCount());
        textView.setText((position + 1) + "/" + ImageStorage.getInstance().getImageCount());
        if (imageInfo != null && imageInfo.getImages().size() > 0) {
            final String url = imageInfo.getImages().get(0).getImageUrl();
            ImageGalery imageGalery = ImageGaleryStorage.getInstance().getImageGalery(imageInfo.getId());
            if (imageGalery != null) {
                int viewIds[] = {R.id.compare_image_view1, R.id.compare_image_view2, R.id.compare_image_view3};
                if(imageGalery.getImageCount() > 0){
                    View galeryContainer = itemView.findViewById(R.id.image_galery_container);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) galeryContainer.getLayoutParams();
                    layoutParams.weight = 1;
                }
                for (int i = 0; i < viewIds.length; i++) {
                    Bitmap image = imageGalery.getImage(i);
                    if (image == null) {
                        break;
                    }
                    ImageView compare = (ImageView) itemView.findViewById(viewIds[i]);
                    compare.setImageBitmap(image);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) compare.getLayoutParams();
                    layoutParams.weight = 1;
                }
            }
            Log.i("image", "show image url " + url + " id = " + imageInfo.getId());
            new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String... strings) {
                    try {
                        InputStream inputStream = new URL(url).openStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        return bitmap;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        Log.e("image", "can't read image from " + url);
                    }
                }
            }.execute();

        } else {
            Log.w("image", "image is empty at " + position);
        }
        container.addView(itemView);

        return itemView;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.d("view", "destory item");
        container.removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

}