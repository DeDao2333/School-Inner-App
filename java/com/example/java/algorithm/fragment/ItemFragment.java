package com.example.java.algorithm.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.ninegrid.NineGridView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public abstract class ItemFragment<T_adapter extends RecyclerView.Adapter,
        T extends BmobObject> extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    public OnFragmentInteractionListener mListener;
    public T_adapter mAdapter;
    public Context mContext;

    //about bmob listeners' setting
    public T theFirst;
    public T theLast;
    public FindListener<T> mListener_up,mListener_down,mListener_init;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {

    }

    public abstract void setListener();

    public abstract void initView();

    public void upRefresh() {
        upRefreshQuery(theLast,mListener_up);
    }

    public void downRefresh() {
        downRefreshQuery(theFirst, mListener_down);
    }

    public void initData() {
        initDataQuery(mListener_init);
    }

    public void initDataQuery(FindListener findListener) {
        BmobQuery<T> query = new BmobQuery<>();
        query.setLimit(5);
        query.order("-createdAt");
        query.include("author");
        query.findObjects(findListener);
    }

    public void upRefreshQuery(T last, FindListener findListener) {
        BmobQuery<T> query = new BmobQuery<>();
        String start = last.getCreatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.addWhereLessThan("createdAt", new BmobDate(date));
        query.order("-createdAt");
        query.include("author");
        query.setLimit(3);
        query.findObjects(findListener);
    }

    public void downRefreshQuery(T first, FindListener findListener) {
        BmobQuery<T> query = new BmobQuery<>();

        //查询时间大于当前页面第一个文章的创建时间的
        String start = first.getCreatedAt();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        query.addWhereGreaterThan("createdAt", new BmobDate(date));
        query.order("createdAt");
        query.include("author");
        query.findObjects(findListener);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(mFragmentId, container, false);
//
//        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(mAdapter);
//        }
//        return view;
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context)
                    .load(url)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }


}
