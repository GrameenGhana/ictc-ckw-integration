package applab.client.agrihub.ui.view.siv;

import android.content.Context;
import android.util.AttributeSet;


import applab.client.agrihub.ui.view.siv.shader.SvgShader;
import applab.client.agrihub.ui.view.siv.shader.ShaderHelper;

public class ShapeImageView extends ShaderImageView {

    public ShapeImageView(Context context) {
        super(context);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader();
    }
}
