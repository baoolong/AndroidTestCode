package com.test.caiwen.customview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.test.caiwen.bean.Pool;

/**
 * @author Created by MrRight on 2018/1/30.
 */

public class CustomRecyleView extends RecyclerView.LayoutManager{

    public static final int DEFAULT_GROUP_SIZE = 5;
    private Pool<Rect> mItemFrames;
    private int mGroupSize;
    private boolean isGravityCenter;

    public CustomRecyleView(boolean center) {
        this(DEFAULT_GROUP_SIZE, center);
    }


    public CustomRecyleView(int groupSize, boolean center) {
        mGroupSize = groupSize;
        isGravityCenter = center;
        mItemFrames = new Pool<>(new Pool.New<Rect>() {
            @Override
            public Rect get() { return new Rect();}
        });
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        if (getItemCount() <= 0 || state.isPreLayout()) { return;}
//
//        detachAndScrapAttachedViews(recycler);
//        View first = recycler.getViewForPosition(0);
//        measureChildWithMargins(first, 0, 0);
//        int itemWidth = getDecoratedMeasuredWidth(first);
//        int itemHeight = getDecoratedMeasuredHeight(first);
//
//        int firstLineSize = mGroupSize / 2 + 1;
//        int secondLineSize = firstLineSize + mGroupSize / 2;
//        if (isGravityCenter && firstLineSize * itemWidth < getHorizontalSpace()) {
//            mGravityOffset = (getHorizontalSpace() - firstLineSize * itemWidth) / 2;
//        } else {
//            mGravityOffset = 0;
//        }
//
//        for (int i = 0; i < getItemCount(); i++) {
//            Rect item = mItemFrames.get(i);
//            float coefficient = isFirstGroup(i) ? 1.5f : 1.f;
//            int offsetHeight = (int) ((i / mGroupSize) * itemHeight * coefficient);
//
//            // 每一组的第一行
//            if (isItemInFirstLine(i)) {
//                int offsetInLine = i < firstLineSize ? i : i % mGroupSize;
//                item.set(mGravityOffset + offsetInLine * itemWidth, offsetHeight, mGravityOffset + offsetInLine * itemWidth + itemWidth,
//                        itemHeight + offsetHeight);
//            }else {
//                int lineOffset = itemHeight / 2;
//                int offsetInLine = (i < secondLineSize ? i : i % mGroupSize) - firstLineSize;
//                item.set(mGravityOffset + offsetInLine * itemWidth + itemWidth / 2,
//                        offsetHeight + lineOffset, mGravityOffset + offsetInLine * itemWidth + itemWidth  + itemWidth / 2,
//                        itemHeight + offsetHeight + lineOffset);
//            }
//        }
//
//        mTotalWidth = Math.max(firstLineSize * itemWidth, getHorizontalSpace());
//        int totalHeight = getGroupSize() * itemHeight;
//        if (!isItemInFirstLine(getItemCount() - 1)) { totalHeight += itemHeight / 2;}
//        mTotalHeight = Math.max(totalHeight, getVerticalSpace());
//        fill(recycler, state);
    }
}
