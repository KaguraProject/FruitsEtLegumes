package kagura.project.com.a8.adapters;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] mThumbIds;
    String layoutType;

    public ImageAdapter(Context c, Integer[] mThumbIds, String layoutType) {
            mContext = c;
            this.mThumbIds = mThumbIds;
            this.layoutType = layoutType;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            switch (layoutType){
                case "normal":
                    imageView.setLayoutParams(new GridView.LayoutParams(180, 180));
                    break;
                case "xlarge":
                    imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                    break;
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);

        return imageView;
    }



}
