package kawa;

import gnu.kawa.models.Paintable;
import gnu.kawa.models.Viewable;
import gnu.kawa.swingviews.SwingDisplay;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/* compiled from: ReplPane */
class ReplEditorKit extends StyledEditorKit {
    ViewFactory factory;
    final ReplPane pane;
    ViewFactory styledFactory;

    /* renamed from: kawa.ReplEditorKit.1 */
    class ReplPane implements ViewFactory {
        final /* synthetic */ ReplPane val$pane;

        /* renamed from: kawa.ReplEditorKit.1.1 */
        class ReplPane extends ComponentView {
            ReplPane(Element x0) {
                super(x0);
            }

            protected Component createComponent() {
                AttributeSet attr = getElement().getAttributes();
                JPanel panel = new JPanel();
                ((Viewable) attr.getAttribute(ReplPane.ViewableAttribute)).makeView(SwingDisplay.getInstance(), panel);
                if (panel.getComponentCount() == 1) {
                    Component comp = panel.getComponent(0);
                    panel.removeAll();
                    return comp;
                }
                panel.setBackground(ReplPane.this.val$pane.getBackground());
                return panel;
            }
        }

        ReplPane(ReplPane replPane) {
            this.val$pane = replPane;
        }

        public View create(Element elem) {
            String kind = elem.getName();
            if (kind == ReplPane.ViewableElementName) {
                return new ReplPane(elem);
            }
            if (kind == ReplPane.PaintableElementName) {
                return new PaintableView(elem, (Paintable) elem.getAttributes().getAttribute(ReplPane.PaintableAttribute));
            }
            return ReplEditorKit.this.styledFactory.create(elem);
        }
    }

    public ReplEditorKit(ReplPane pane) {
        this.pane = pane;
        this.styledFactory = super.getViewFactory();
        this.factory = new ReplPane(pane);
    }

    public ViewFactory getViewFactory() {
        return this.factory;
    }
}
