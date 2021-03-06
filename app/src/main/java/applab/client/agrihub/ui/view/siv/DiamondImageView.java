package applab.client.agrihub.ui.view.siv;

import android.content.Context;
import android.util.AttributeSet;

import applab.client.search.R;
import applab.client.agrihub.ui.view.siv.shader.SvgShader;
import applab.client.agrihub.ui.view.siv.shader.ShaderHelper;


public class DiamondImageView extends ShaderImageView {

    public DiamondImageView(Context context) {
        super(context);
    }

    public DiamondImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiamondImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader(R.raw.imgview_diamond);
    }
}
