package com.lina.baselibs.adapter.recyclerview;

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(RecyclerViewHolder holder, T t, int position);

}
