package com.lina.baselibs.adapter.recyclerview;

import android.content.Context;

import java.util.List;

public abstract class RecyclerCommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public RecyclerCommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(RecyclerViewHolder holder, T t, int position) {
                RecyclerCommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(RecyclerViewHolder holder, T t, int position);

}
