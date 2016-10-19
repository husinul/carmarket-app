package com.google.appinventor.components.runtime;

import android.view.View;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.SimplePropertyCopier;
import com.google.appinventor.components.runtime.Form.PercentStorageRecord.Dim;
import com.google.appinventor.components.runtime.util.ErrorMessages;

@SimpleObject
public abstract class AndroidViewComponent extends VisibleComponent {
    private int column;
    protected final ComponentContainer container;
    private int lastSetHeight;
    private int lastSetWidth;
    private int percentHeightHolder;
    private int percentWidthHolder;
    private int row;

    public abstract View getView();

    protected AndroidViewComponent(ComponentContainer container) {
        this.percentWidthHolder = -3;
        this.percentHeightHolder = -3;
        this.lastSetWidth = -3;
        this.lastSetHeight = -3;
        this.column = -1;
        this.row = -1;
        this.container = container;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public boolean Visible() {
        return getView().getVisibility() == 0;
    }

    @DesignerProperty(defaultValue = "True", editorType = "visibility")
    @SimpleProperty(description = "Specifies whether the component should be visible on the screen. Value is true if the component is showing and false if hidden.")
    public void Visible(Boolean visibility) {
        getView().setVisibility(visibility.booleanValue() ? 0 : 8);
    }

    @SimpleProperty
    public int Width() {
        return (int) (((float) getView().getWidth()) / this.container.$form().deviceDensity());
    }

    @SimpleProperty
    public void Width(int width) {
        this.container.setChildWidth(this, width);
        this.lastSetWidth = width;
        if (width <= Component.LENGTH_PERCENT_TAG) {
            this.container.$form().registerPercentLength(this, width, Dim.WIDTH);
        }
    }

    @SimpleProperty
    public void WidthPercent(int pCent) {
        if (pCent < 0 || pCent > 100) {
            this.container.$form().dispatchErrorOccurredEvent(this, "WidthPercent", ErrorMessages.ERROR_BAD_PERCENT, Integer.valueOf(pCent));
            return;
        }
        Width((-pCent) + Component.LENGTH_PERCENT_TAG);
    }

    public void setLastWidth(int width) {
        this.percentWidthHolder = width;
    }

    public int getSetWidth() {
        if (this.percentWidthHolder == -3) {
            return Width();
        }
        return this.percentWidthHolder;
    }

    public void setLastHeight(int height) {
        this.percentHeightHolder = height;
    }

    public int getSetHeight() {
        if (this.percentHeightHolder == -3) {
            return Height();
        }
        return this.percentHeightHolder;
    }

    @SimplePropertyCopier
    public void CopyWidth(AndroidViewComponent sourceComponent) {
        Width(sourceComponent.lastSetWidth);
    }

    @SimpleProperty
    public int Height() {
        return (int) (((float) getView().getHeight()) / this.container.$form().deviceDensity());
    }

    @SimpleProperty
    public void Height(int height) {
        this.container.setChildHeight(this, height);
        this.lastSetHeight = height;
        if (height <= Component.LENGTH_PERCENT_TAG) {
            this.container.$form().registerPercentLength(this, height, Dim.HEIGHT);
        }
    }

    @SimpleProperty
    public void HeightPercent(int pCent) {
        if (pCent < 0 || pCent > 100) {
            this.container.$form().dispatchErrorOccurredEvent(this, "HeightPercent", ErrorMessages.ERROR_BAD_PERCENT, Integer.valueOf(pCent));
            return;
        }
        Height((-pCent) + Component.LENGTH_PERCENT_TAG);
    }

    @SimplePropertyCopier
    public void CopyHeight(AndroidViewComponent sourceComponent) {
        Height(sourceComponent.lastSetHeight);
    }

    @SimpleProperty(userVisible = false)
    public int Column() {
        return this.column;
    }

    @SimpleProperty(userVisible = false)
    public void Column(int column) {
        this.column = column;
    }

    @SimpleProperty(userVisible = false)
    public int Row() {
        return this.row;
    }

    @SimpleProperty(userVisible = false)
    public void Row(int row) {
        this.row = row;
    }

    public HandlesEventDispatching getDispatchDelegate() {
        return this.container.$form();
    }
}
