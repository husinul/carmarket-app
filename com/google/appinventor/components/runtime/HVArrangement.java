package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.util.AlignmentUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.ElementType;
import gnu.lists.Sequence;
import java.io.IOException;

@SimpleObject
public class HVArrangement extends AndroidViewComponent implements Component, ComponentContainer {
    private static final String LOG_TAG = "HVArrangement";
    private AlignmentUtil alignmentSetter;
    private final Handler androidUIHandler;
    private int backgroundColor;
    private Drawable backgroundImageDrawable;
    private final Activity context;
    private Drawable defaultButtonDrawable;
    private ViewGroup frameContainer;
    private int horizontalAlignment;
    private String imagePath;
    private final int orientation;
    private boolean scrollable;
    private int verticalAlignment;
    private final LinearLayout viewLayout;

    /* renamed from: com.google.appinventor.components.runtime.HVArrangement.1 */
    class C00501 implements Runnable {
        final /* synthetic */ AndroidViewComponent val$component;
        final /* synthetic */ int val$fWidth;
        final /* synthetic */ int val$trycount;

        C00501(AndroidViewComponent androidViewComponent, int i, int i2) {
            this.val$component = androidViewComponent;
            this.val$fWidth = i;
            this.val$trycount = i2;
        }

        public void run() {
            Log.d(HVArrangement.LOG_TAG, "(HVArrangement)Width not stable yet... trying again");
            HVArrangement.this.setChildWidth(this.val$component, this.val$fWidth, this.val$trycount + 1);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.HVArrangement.2 */
    class C00512 implements Runnable {
        final /* synthetic */ AndroidViewComponent val$component;
        final /* synthetic */ int val$fHeight;

        C00512(AndroidViewComponent androidViewComponent, int i) {
            this.val$component = androidViewComponent;
            this.val$fHeight = i;
        }

        public void run() {
            Log.d(HVArrangement.LOG_TAG, "(HVArrangement)Height not stable yet... trying again");
            HVArrangement.this.setChildHeight(this.val$component, this.val$fHeight);
        }
    }

    public HVArrangement(ComponentContainer container, int orientation, boolean scrollable) {
        super(container);
        this.scrollable = false;
        this.imagePath = ElementType.MATCH_ANY_LOCALNAME;
        this.androidUIHandler = new Handler();
        this.context = container.$context();
        this.orientation = orientation;
        this.scrollable = scrollable;
        this.viewLayout = new LinearLayout(this.context, orientation, Integer.valueOf(100), Integer.valueOf(100));
        this.viewLayout.setBaselineAligned(false);
        this.alignmentSetter = new AlignmentUtil(this.viewLayout);
        this.horizontalAlignment = 1;
        this.verticalAlignment = 1;
        this.alignmentSetter.setHorizontalAlignment(this.horizontalAlignment);
        this.alignmentSetter.setVerticalAlignment(this.verticalAlignment);
        if (scrollable) {
            switch (orientation) {
                case Sequence.EOF_VALUE /*0*/:
                    Log.d(LOG_TAG, "Setting up frameContainer = HorizontalScrollView()");
                    this.frameContainer = new HorizontalScrollView(this.context);
                    break;
                case ParseFormat.SEEN_MINUS /*1*/:
                    Log.d(LOG_TAG, "Setting up frameContainer = ScrollView()");
                    this.frameContainer = new ScrollView(this.context);
                    break;
            }
        }
        Log.d(LOG_TAG, "Setting up frameContainer = FrameLayout()");
        this.frameContainer = new FrameLayout(this.context);
        this.frameContainer.setLayoutParams(new LayoutParams(100, 100));
        this.frameContainer.addView(this.viewLayout.getLayoutManager(), new LayoutParams(-1, -1));
        this.defaultButtonDrawable = getView().getBackground();
        container.$add(this);
        BackgroundColor(0);
    }

    public Activity $context() {
        return this.context;
    }

    public Form $form() {
        return this.container.$form();
    }

    public void $add(AndroidViewComponent component) {
        this.viewLayout.add(component);
    }

    public void setChildWidth(AndroidViewComponent component, int width) {
        setChildWidth(component, width, 0);
    }

    public void setChildWidth(AndroidViewComponent component, int width, int trycount) {
        int cWidth = this.container.$form().Width();
        if (cWidth == 0 && trycount < 2) {
            this.androidUIHandler.postDelayed(new C00501(component, width, trycount), 100);
        }
        if (width <= Component.LENGTH_PERCENT_TAG) {
            Log.d(LOG_TAG, "HVArrangement.setChildWidth(): width = " + width + " parent Width = " + cWidth + " child = " + component);
            width = ((-(width + 1000)) * cWidth) / 100;
        }
        component.setLastWidth(width);
        if (this.orientation == 0) {
            ViewUtil.setChildWidthForHorizontalLayout(component.getView(), width);
        } else {
            ViewUtil.setChildWidthForVerticalLayout(component.getView(), width);
        }
    }

    public void setChildHeight(AndroidViewComponent component, int height) {
        int cHeight = this.container.$form().Height();
        if (cHeight == 0) {
            this.androidUIHandler.postDelayed(new C00512(component, height), 100);
        }
        if (height <= Component.LENGTH_PERCENT_TAG) {
            height = ((-(height + 1000)) * cHeight) / 100;
        }
        component.setLastHeight(height);
        if (this.orientation == 0) {
            ViewUtil.setChildHeightForHorizontalLayout(component.getView(), height);
        } else {
            ViewUtil.setChildHeightForVerticalLayout(component.getView(), height);
        }
    }

    public View getView() {
        return this.frameContainer;
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how contents of the arrangement are aligned  horizontally. The choices are: 1 = left aligned, 2 = right aligned,  3 = horizontally centered.  Alignment has no effect if the arrangement's width is automatic.")
    public int AlignHorizontal() {
        return this.horizontalAlignment;
    }

    @DesignerProperty(defaultValue = "1", editorType = "horizontal_alignment")
    @SimpleProperty
    public void AlignHorizontal(int alignment) {
        try {
            this.alignmentSetter.setHorizontalAlignment(alignment);
            this.horizontalAlignment = alignment;
        } catch (IllegalArgumentException e) {
            this.container.$form().dispatchErrorOccurredEvent(this, "HorizontalAlignment", ErrorMessages.ERROR_BAD_VALUE_FOR_HORIZONTAL_ALIGNMENT, Integer.valueOf(alignment));
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "A number that encodes how the contents of the arrangement are aligned  vertically. The choices are: 1 = aligned at the top, 2 = vertically centered, 3 = aligned at the bottom.  Alignment has no effect if the arrangement's height is automatic.")
    public int AlignVertical() {
        return this.verticalAlignment;
    }

    @DesignerProperty(defaultValue = "1", editorType = "vertical_alignment")
    @SimpleProperty
    public void AlignVertical(int alignment) {
        try {
            this.alignmentSetter.setVerticalAlignment(alignment);
            this.verticalAlignment = alignment;
        } catch (IllegalArgumentException e) {
            this.container.$form().dispatchErrorOccurredEvent(this, "VerticalAlignment", ErrorMessages.ERROR_BAD_VALUE_FOR_VERTICAL_ALIGNMENT, Integer.valueOf(alignment));
        }
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE, description = "Returns the component's background color")
    public int BackgroundColor() {
        return this.backgroundColor;
    }

    @DesignerProperty(defaultValue = "&H00000000", editorType = "color")
    @SimpleProperty(description = "Specifies the component's background color. The background color will not be visible if an Image is being displayed.")
    public void BackgroundColor(int argb) {
        this.backgroundColor = argb;
        updateAppearance();
    }

    @SimpleProperty(category = PropertyCategory.APPEARANCE)
    public String Image() {
        return this.imagePath;
    }

    @DesignerProperty(defaultValue = "", editorType = "asset")
    @SimpleProperty(description = "Specifies the path of the component's image.  If there is both an Image and a BackgroundColor, only the Image will be visible.")
    public void Image(String path) {
        if (!path.equals(this.imagePath) || this.backgroundImageDrawable == null) {
            if (path == null) {
                path = ElementType.MATCH_ANY_LOCALNAME;
            }
            this.imagePath = path;
            this.backgroundImageDrawable = null;
            if (this.imagePath.length() > 0) {
                try {
                    this.backgroundImageDrawable = MediaUtil.getBitmapDrawable(this.container.$form(), this.imagePath);
                } catch (IOException e) {
                }
            }
            updateAppearance();
        }
    }

    private void updateAppearance() {
        if (this.backgroundImageDrawable != null) {
            ViewUtil.setBackgroundImage(this.viewLayout.getLayoutManager(), this.backgroundImageDrawable);
        } else if (this.backgroundColor == 0) {
            ViewUtil.setBackgroundDrawable(this.viewLayout.getLayoutManager(), this.defaultButtonDrawable);
        } else {
            ViewUtil.setBackgroundDrawable(this.viewLayout.getLayoutManager(), null);
            this.viewLayout.getLayoutManager().setBackgroundColor(this.backgroundColor);
        }
    }
}
