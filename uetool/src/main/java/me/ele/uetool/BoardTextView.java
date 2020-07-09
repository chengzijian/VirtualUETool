package me.ele.uetool;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import me.ele.uetool.base.DimenUtil;

public class BoardTextView extends TextView {

    private final String defaultInfo = UETool.getInstance().getTargetActivity().getPackageName() + " / " + UETool.getInstance().getTargetActivity().getClass().getName();

    private final int padding = DimenUtil.dip2px(3);

    public BoardTextView(Context context) {
        this(context, null);
    }

    public BoardTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoardTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setBackgroundColor(0x902395ff);
        setPadding(padding, padding, padding, padding);
        setTextColor(0xffffffff);
        setTextSize(9);
        setText(defaultInfo);
    }

    public void updateInfo(String info) {
        setText(info + "\n" + defaultInfo);
    }
}
