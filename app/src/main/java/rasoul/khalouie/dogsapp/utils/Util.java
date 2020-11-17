package rasoul.khalouie.dogsapp.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import rasoul.khalouie.dogsapp.R;

public class Util {

    public static void loadImage(ImageView imageView , String url , CircularProgressDrawable cpd){
        RequestOptions options = new RequestOptions()
                .placeholder(cpd)
                .error(R.mipmap.ic_dog_icon_round);

        Glide.with(imageView.getContext()).setDefaultRequestOptions(options)
                .load(url).into(imageView);
    }

    public static CircularProgressDrawable getCircularProgressDrawable(Context context){
        CircularProgressDrawable cpd = new CircularProgressDrawable(context);
        cpd.setStrokeWidth(10f);
        cpd.setCenterRadius(50f);
        cpd.start();
        return cpd;
    }

    @BindingAdapter("android:imageUrl")
    public static void loadImage(ImageView imageView , String url){
        loadImage(imageView,url,getCircularProgressDrawable(imageView.getContext()));
    }
}
