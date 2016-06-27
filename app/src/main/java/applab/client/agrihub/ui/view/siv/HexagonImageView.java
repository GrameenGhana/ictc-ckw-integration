package applab.client.agrihub.ui.view.siv;

import android.content.Context;
import android.util.AttributeSet;


import applab.client.search.R;
import applab.client.agrihub.ui.view.siv.shader.SvgShader;
import applab.client.agrihub.ui.view.siv.shader.ShaderHelper;

public class HexagonImageView extends ShaderImageView {

    public HexagonImageView(Context context) {
        super(context);
    }

    public HexagonImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HexagonImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public ShaderHelper createImageViewHelper() {
        return new SvgShader(R.raw.imgview_hexagon);
    }
}
