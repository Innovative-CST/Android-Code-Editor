package android.code.editor.common;

import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.R;
import com.google.android.material.color.MaterialColors;

public class ToastUtils {
  public static final int TYPE_ERROR = 0;
  public static final int TYPE_SUCCESS = 2;

  public static void showShort(CharSequence message, int type) {
    Toast toast = new Toast(Utils.getContext());
    toast.setView(createToastView(message, type));
    toast.setDuration(Toast.LENGTH_SHORT);
    toast.show();
  }

  public static void showLong(CharSequence message, int type) {
    Toast toast = new Toast(Utils.getContext());
    toast.setView(createToastView(message, type));
    toast.setDuration(Toast.LENGTH_LONG);
    toast.show();
  }

  private static View createToastView(CharSequence message, int type) {
    LayoutToastBinding binding =
        LayoutToastBinding.inflate(LayoutInflater.from(Utils.getContext()));
    binding.icon.setImageResource(getIconForType(type));
    binding.message.setText(message);

    GradientDrawable drawable = new GradientDrawable();
    drawable.setShape(GradientDrawable.RECTANGLE);
    drawable.setCornerRadius(Utils.pxToDp(15));
    drawable.setColor(MaterialColors.getColor(Utils.getContext(), R.attr.colorSurface, 0));
    drawable.setStroke(1, MaterialColors.getColor(Utils.getContext(), R.attr.colorOutline, 0));

    binding.getRoot().setBackground(drawable);
    return binding.getRoot();
  }

  private static int getIconForType(int type) {
    switch (type) {
      case TYPE_ERROR:
        return android.code.editor.common.R.drawable.ic_alert_circle_outline;
      case TYPE_SUCCESS:
        return android.code.editor.common.R.drawable.ic_check;
      default:
        return 0;
    }
  }
}

