package com.lina.baselibs.adapter.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class MultiItemTypeAdapter<T> extends BaseAdapter {

    protected Context mContext;
    protected List<T> mData;

    private ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemTypeAdapter(Context context, List<T> data) {
        this.mContext = context;
        this.mData = data;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    public void setData(List<T> data) {
        this.mData = data;
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager()) {
            return mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            int viewType = mItemViewDelegateManager.getItemViewType(mData.get(position), position);
            return viewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mData.get(position), position);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ListViewHolder listViewHolder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            listViewHolder = new ListViewHolder(mContext, itemView, parent, position);
            listViewHolder.mLayoutId = layoutId;
            onViewHolderCreated(listViewHolder, listViewHolder.getConvertView());
        } else {
            listViewHolder = (ListViewHolder) convertView.getTag();
            listViewHolder.mPosition = position;
        }


        convert(listViewHolder, getItem(position), position);
        return listViewHolder.getConvertView();
    }

    protected void convert(ListViewHolder listViewHolder, T item, int position) {
        mItemViewDelegateManager.convert(listViewHolder, item, position);
    }

    public void onViewHolderCreated(ListViewHolder holder, View itemView) {
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
