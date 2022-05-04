package com.lina.baselibs.adapter.listview;

public interface ItemViewDelegate<T> {

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ListViewHolder holder, T t, int position);

}
