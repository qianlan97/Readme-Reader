package com.lina.baselibs.adapter.listview;

import android.content.Context;

import java.util.List;

public abstract class ListCommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public ListCommonAdapter(Context context, final int layoutId, List<T> data) {
        super(context, data);
        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ListViewHolder holder, T t, int position) {
                ListCommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    @Override
    protected abstract void convert(ListViewHolder listViewHolder, T item, int position);

}
