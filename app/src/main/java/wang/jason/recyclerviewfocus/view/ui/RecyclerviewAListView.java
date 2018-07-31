package wang.jason.recyclerviewfocus.view.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import wang.jason.recyclerviewfocus.R;

/**
 * @author wj
 * @Date: 2018/7/31
 * @Description:
 **/
public class RecyclerviewAListView extends RecyclerView {

    public RecyclerviewAListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if(event.getAction() == KeyEvent.ACTION_DOWN){

            View focusedView = getFocusedChild();
            if(focusedView != null) {
                switch (event.getKeyCode()) {

                    case KeyEvent.KEYCODE_DPAD_UP: {
                        if ((int) focusedView.getTag() == 0) {
                            return true;
                        }
                        break;
                    }
                    case KeyEvent.KEYCODE_DPAD_DOWN: {
                        if ((int) focusedView.getTag() == getAdapter().getItemCount() - 1) {
                            return true;
                        }
                        break;
                    }
                    default:
                        break;

                }
            }
        }

        return super.dispatchKeyEvent(event);
    }

    @Override
    public View focusSearch(View focused, int direction) {

        int curFocusChildIndex = getFocusedChildIndex();

        if(curFocusChildIndex == -1){
            return null;
        }

        if(direction == View.FOCUS_UP || direction == View.FOCUS_DOWN){



            int nextChildIndex = direction == View.FOCUS_UP?curFocusChildIndex-1:curFocusChildIndex+1;

            int itemHeight = getResources().getDimensionPixelOffset(R.dimen.item_height);

            if(nextChildIndex < 0 || nextChildIndex >= getChildCount()){

                if(nextChildIndex < 0) {
                    scrollBy(0, -itemHeight);
                }else{
                    scrollBy(0,itemHeight);
                }
                return focusSearch(focused,direction);

            }

            View nextChild = getChildAt(nextChildIndex);
            ArrayList<View> focusables = new ArrayList<>();

            if(nextChild != null) {
                findFocusables(nextChild, focusables);
                View targetView = null;
                for (View focusableView : focusables) {
                    if (focusableView instanceof RecyclerviewAItemView) {
                        targetView = focusableView;
                        break;
                    }
                }
                if (targetView != null) {
                    return targetView;
                }



            }


        }


        return super.focusSearch(focused, direction);
    }

    private int getFocusedChildIndex() {
        for (int i = 0; i < getChildCount(); ++i) {
            if (getChildAt(i).hasFocus()) {
                return i;
            }
        }
        return -1;
    }
    private static void findFocusables(View v, ArrayList<View> outFocusable) {
        if (v.isFocusable()) {
            outFocusable.add(v);
        }
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            for (int i = 0; i < viewGroup.getChildCount(); ++i) {
                findFocusables(viewGroup.getChildAt(i), outFocusable);
            }
        }
    }
}
