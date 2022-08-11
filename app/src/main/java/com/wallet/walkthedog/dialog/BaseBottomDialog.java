package com.wallet.walkthedog.dialog;

import android.graphics.Color;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.wallet.walkthedog.R;

class BaseBottomDialog extends BottomSheetDialogFragment {
    private int peekHeight = -1;
    private BottomSheetBehavior<FrameLayout> behavior;

    // 默认为展开状态
    private Integer state = BottomSheetBehavior.STATE_EXPANDED;

    @Override
    public void onStart() {
        super.onStart();
        //设置成全屏
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        FrameLayout bottomSheet = dialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            bottomSheet.setBackgroundColor(Color.TRANSPARENT);
            BottomSheetBehavior<FrameLayout> _behavior = BottomSheetBehavior.from(bottomSheet);
            behavior = _behavior;

            if (this.state != null) {
                _behavior.setState(state);
                state = null;
            }
            _behavior.setSkipCollapsed(true);
            if (peekHeight != -1) {
                _behavior.setPeekHeight(peekHeight);
                peekHeight = -1;
            }
        }
    }

    protected void setState(@BottomSheetBehavior.State int state) {
        this.state = state;
        if (behavior != null) {
            behavior.setState(state);
            this.state = null;
        }
    }

    protected void setPeekHeight(int height) {
        this.peekHeight = height;
        if (behavior != null) {
            behavior.setPeekHeight(peekHeight);
            peekHeight = -1;
        }
    }
}