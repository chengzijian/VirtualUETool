package me.ele.uetool;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.ele.uetool.base.db.ElementBean;
import me.ele.uetool.base.db.MMKVUtil;

import me.ele.uetool.itemtouch.ItemTouchHelper;
import me.ele.uetool.itemtouch.SwipeAndDragHelper;

import static me.ele.uetool.base.DimenUtil.dip2px;

/**
 * @author zijian.cheng
 * @date 2020/7/13
 */
public class EditTouchLayout extends FrameLayout {

    private EditAdapter adapter = new EditAdapter();

    public EditTouchLayout(@NonNull Context context) {
        this(context, null);
    }

    public EditTouchLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTouchLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setFocusable(true);
        linearLayout.setFocusableInTouchMode(true);
        linearLayout.setBackgroundColor(Color.WHITE);
        linearLayout.setPadding(0, dip2px(40), 0, dip2px(10));
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        LinearLayout childLinearLayout = new LinearLayout(getContext());
        childLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        childLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        Button resetBtn = new Button(getContext());
        resetBtn.setText("重置");
        resetBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                resetList();
            }
        });
        resetBtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        childLinearLayout.addView(resetBtn);

        Button saveBtn = new Button(getContext());
        saveBtn.setText("保存");
        saveBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveList();
            }
        });
        saveBtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        childLinearLayout.addView(saveBtn);

        Button clearBtn = new Button(getContext());
        clearBtn.setText("清除");
        clearBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clearList();
            }
        });
        clearBtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        childLinearLayout.addView(clearBtn);

//        Button closeBtn = new Button(getContext());
//        closeBtn.setText("关闭");
//        closeBtn.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        childLinearLayout.addView(closeBtn);

        RecyclerView recyclerView = new RecyclerView(getContext());
        recyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));

        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        adapter.setTouchHelper(touchHelper);
        touchHelper.attachToRecyclerView(recyclerView);

        linearLayout.addView(recyclerView);
        linearLayout.addView(childLinearLayout);
        addView(linearLayout);
        resetList();
    }

    private void resetList() {
        adapter.setElementList(MMKVUtil.getInstance().getElements());
    }

    private void saveList() {
        final List<ElementBean> adapterList = adapter.getElementList();
        List<ElementBean> mElementList = null;
        if (adapterList != null) {
            int size = adapterList.size();
            if (size > 0) {
                mElementList = new ArrayList<>(size);
                ElementBean bean;
                for (int i = 0; i < size; i++) {
                    bean = adapterList.get(i);
                    bean.setSort(i);
                    mElementList.add(bean);
                }
            }
        }
        MMKVUtil.getInstance().setElement(mElementList);
    }

    private void clearList() {
        adapter.setElementList(null);
        MMKVUtil.getInstance().setElement(null);
    }

    class EditAdapter extends RecyclerView.Adapter<EditViewHolder> implements
            SwipeAndDragHelper.ActionCompletionContract {

        private ItemTouchHelper touchHelper;
        private List<ElementBean> mElementList;

        public void setElementList(List<ElementBean> mElementList) {
            this.mElementList = mElementList;
            notifyDataSetChanged();
        }

        public List<ElementBean> getElementList() {
            return mElementList;
        }

        public void setTouchHelper(ItemTouchHelper touchHelper) {
            this.touchHelper = touchHelper;
        }

        @NonNull
        @Override
        public EditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LinearLayout linearLayout = new LinearLayout(parent.getContext());
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setGravity(Gravity.CENTER_VERTICAL);
            linearLayout.setBackgroundColor(Color.parseColor("#f5f5f5"));

            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            textView.setTextSize(12);
            textView.setId(R.id.name);
            textView.setTextColor(Color.GRAY);
            textView.setMaxLines(1);
            textView.setSingleLine();

            ImageView imageView = new ImageView(parent.getContext());
            imageView.setId(R.id.detail);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(dip2px(40), dip2px(40)));
            imageView.setImageResource(android.R.drawable.ic_menu_sort_by_size);

            linearLayout.addView(textView);
            linearLayout.addView(imageView);
            return new EditViewHolder(linearLayout);
        }

        private String getLastName(String string) {
            return string.substring(string.lastIndexOf(".") + 1);
        }

        @Override
        public void onBindViewHolder(@NonNull final EditViewHolder holder, int position) {
            ElementBean item = mElementList.get(position);

            StringBuffer stringBuffer = new StringBuffer(item.getResName());
            if (stringBuffer.length() == 0) {
                stringBuffer.append(getLastName(item.getClassName()));
            }
            stringBuffer.append("->");
            stringBuffer.append(item.getRect().toShortString());
            String clickClass = item.getViewClickClass();
            if (!TextUtils.isEmpty(clickClass)) {
                stringBuffer.append("->");
                int lastLength = 5;
                if (clickClass.length() > lastLength) {
                    stringBuffer.append(clickClass.substring(clickClass.length() - lastLength));
                } else {
                    stringBuffer.append(clickClass);
                }
            }

            holder.vName.setText(stringBuffer);
            holder.vDetail.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        touchHelper.startDrag(holder);
                    }
                    return false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return mElementList != null ? mElementList.size() : 0;
        }

        @Override
        public void onViewMoved(int oldPosition, int newPosition) {
            ElementBean targetElement = mElementList.get(oldPosition);
            mElementList.remove(oldPosition);
            mElementList.add(newPosition, targetElement);
            notifyItemMoved(oldPosition, newPosition);
        }

        @Override
        public void onViewSwiped(int position) {
            mElementList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class EditViewHolder extends RecyclerView.ViewHolder {

        public TextView vName;
        public ImageView vDetail;

        public EditViewHolder(View itemView) {
            super(itemView);
            vName = itemView.findViewById(R.id.name);
            vDetail = itemView.findViewById(R.id.detail);
        }
    }
}
