package me.ele.uetool.base.item;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import me.ele.uetool.base.Element;
import me.ele.uetool.base.db.ElementBean;

import static me.ele.uetool.base.item.SwitchItem.Type.TYPE_IS_BOLD;
import static me.ele.uetool.base.item.SwitchItem.Type.TYPE_MOVE;
import static me.ele.uetool.base.item.SwitchItem.Type.TYPE_SELECT_STEP;
import static me.ele.uetool.base.item.SwitchItem.Type.TYPE_SHOW_VALID_VIEWS;

public class SwitchItem extends ElementItem {

    @Type
    private int type;
    private boolean isChecked;
    private boolean disEnable;
    private ElementBean elementBean;

    public SwitchItem(String name, Element element, @Type int type) {
        super(name, element);
        this.type = type;
    }

    public SwitchItem(String name, Element element, @Type int type, boolean isChecked) {
        super(name, element);
        this.type = type;
        this.isChecked = isChecked;
    }

    public SwitchItem(String name, Element element, @Type int type, int state, ElementBean elementBean) {
        super(name, element);
        this.type = type;
        this.isChecked = state == 1;
        this.disEnable = state == 2;
        this.elementBean = elementBean;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isDisEnable() {
        return disEnable;
    }

    public int getType() {
        return type;
    }

    public ElementBean getElementBean() {
        return elementBean;
    }

    public void setElementBean(ElementBean elementBean) {
        this.elementBean = elementBean;
    }

    @IntDef({
            TYPE_IS_BOLD,
            TYPE_MOVE,
            TYPE_SHOW_VALID_VIEWS,
            TYPE_SELECT_STEP,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
        int TYPE_IS_BOLD = 1;
        int TYPE_MOVE = 2;
        int TYPE_SHOW_VALID_VIEWS = 3;
        int TYPE_SELECT_STEP = 4;
    }
}
