package wang.jason.recyclerviewfocus.view.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
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
public class RecyclerviewBListView extends RecyclerView{

    public RecyclerviewBListView(Context context, AttributeSet attrs) {
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

        Log.i("focusSearch","focusSearch");

        int curFocusChildIndex = getFocusedChildIndex();

        if(curFocusChildIndex == -1){

            return null;
        }

        if(direction == View.FOCUS_UP || direction == View.FOCUS_DOWN){

            int nextChildIndex = direction == View.FOCUS_UP?curFocusChildIndex-1:curFocusChildIndex+1;

            int itemHeight = getResources().getDimensionPixelOffset(R.dimen.item_height);

            /**
             * 如果这里为true，且此时数据正在刷新，那么很大几率焦点会异常，因此我们手动处理，我们根据方向手动滚动一个子Item的高度，
             * 这时候再重新调用focusSearch时，curFocusChildIndex会变化，下一次执行这里就为false了。
             */

            if(nextChildIndex < 0 || nextChildIndex >= getChildCount()){

                if(nextChildIndex < 0) {
                    scrollBy(0, -itemHeight);
                }else{
                    scrollBy(0,itemHeight);
                }
                Log.i("focusSearch","focous error ,after scroll by");
                return focusSearch(focused,direction);

            }
            /**
             * 获取下一个子Item
             */

            View nextChild = getChildAt(nextChildIndex);

            /**
             * 用于保存下一个子Item可以被焦点的View
             */
            ArrayList<View> focusables = new ArrayList<>();

            if(nextChild != null) {
                /**
                 * 找到可以被焦点的View
                 */
                findFocusables(nextChild, focusables);

                View targetView = null;
                for (View focusableView : focusables) {
                    Log.i("focusSearch","focous focusableView ");
                    if (focusableView instanceof RecyclerviewBItemView) {
                        Log.i("focusSearch","focous focusableView ,find target");
                        targetView = focusableView;
                        break;
                    }
                }
                /**
                 * return我们想要焦点的选项，
                 */
                if (targetView != null) {
                    Log.i("focusSearch","focous focusableView ,return targetView");
                    return targetView;
                }



            }


        }


        return super.focusSearch(focused, direction);
    }

    /**
     * 找到当前被焦点的子item的index，注意这个index不是指dapter中的position，而是指当前可见区域内的位置
     * @return
     */
    private int getFocusedChildIndex() {

        for (int i = 0; i < getChildCount(); ++i) {

            if (getChildAt(i).hasFocus()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 找到可以被焦点的子item，如果子item是ViewGroup，则递归查找,
     * @param v
     * @param outFocusable
     */
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
