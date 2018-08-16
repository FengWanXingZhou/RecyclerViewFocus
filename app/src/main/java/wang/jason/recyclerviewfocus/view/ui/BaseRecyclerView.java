package wang.jason.recyclerviewfocus.view.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;

/**
 * @Author: wj
 * @Date: 2018/5/8
 * @Description:
 **/
public class BaseRecyclerView extends RecyclerView {


    Context context;


    public BaseRecyclerView(Context context) {
        super(context);
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialInjector();


    }

    private void initialInjector(){

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {


        View view = getFocusedChild();


        if(view != null) {
            //Util.LOG(getClass().getSimpleName()+" dispatchKeyEvent POSITION = "+(int) view.getTag()+" ITEM COUNT = "+getAdapter().getItemCount());

            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN
                    || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {



                if(getLayoutManager() instanceof GridLayoutManager){
                    GridLayoutManager gridLayoutManager = (GridLayoutManager)getLayoutManager();
                    if(gridLayoutManager.getOrientation() == VERTICAL){
                        if(((LayoutParams)view.getLayoutParams()).getViewLayoutPosition()
                                / gridLayoutManager.getSpanCount() == 0
                                && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP){
                            return true;
                        }
                        if(((LayoutParams)view.getLayoutParams()).getViewLayoutPosition()
                                / gridLayoutManager.getSpanCount()
                                == (getAdapter().getItemCount()-1) / gridLayoutManager.getSpanCount()
                                && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN){
                            return true;
                        }

                    }

                }else if(getLayoutManager() instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
                    if (linearLayoutManager.getOrientation() == VERTICAL) {
                        if (((LayoutParams) view.getLayoutParams()).getViewLayoutPosition()
                                == 0
                                && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                            return true;
                        }
                        if (((LayoutParams) view.getLayoutParams()).getViewLayoutPosition()
                                == (getAdapter().getItemCount() - 1)
                                && event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                            return true;
                        }
                    }
                }else if(getLayoutManager() instanceof StaggeredGridLayoutManager){
                    //don't consider
                }

            }

        }

        return  super.dispatchKeyEvent(event);

    }



    @Override
    public View focusSearch(View focused, int direction) {

        //Util.LOG(getClass().getSimpleName()+" SEARCH focus focusSearch");

        int focusChildIndex = getFocusedChildIndex();

        if (focusChildIndex == -1) {
            //Util.LOG("SEARCH focus focusSearch No child view has focus");
            return null;
        }
        View focusChildView = getChildAt(focusChildIndex);
        //Util.LOG(getClass().getSimpleName()+" focusChildIndex = "+focusChildIndex+" COUNT = "+getChildCount());

        int itemHeightDimen = focusChildView.getHeight();
        if(direction == View.FOCUS_UP  || direction == View.FOCUS_DOWN){
            int nextChildIndex = 0;

            if(getLayoutManager() instanceof GridLayoutManager){
                GridLayoutManager gridLayoutManager = (GridLayoutManager)getLayoutManager();
                if(direction == View.FOCUS_DOWN){
                    nextChildIndex = focusChildIndex + gridLayoutManager.getSpanCount();
                    if(nextChildIndex>=getChildCount()
                            &&getChildCount()%gridLayoutManager.getSpanCount() != 0){
                        nextChildIndex = getChildCount()-1;
                    }
                }else{
                    nextChildIndex = focusChildIndex -  gridLayoutManager.getSpanCount();
                }
            }else if(getLayoutManager() instanceof LinearLayoutManager){
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) getLayoutManager();
                if(direction == View.FOCUS_DOWN){
                    nextChildIndex = focusChildIndex +  1;
                }else{
                    nextChildIndex = focusChildIndex -  1;
                }
            }else if(getLayoutManager() instanceof StaggeredGridLayoutManager){
                //don't consider
                return super.focusSearch(focused,direction);
            }

            Rect outRect = new Rect();
            for(int i = 0 ;i < getItemDecorationCount();i++){
                ItemDecoration focusItemDecoration = getItemDecorationAt(i);
                if(focusItemDecoration != null) {

                    focusItemDecoration.getItemOffsets(outRect, focusChildView, this, null);

                }
            }



            if (nextChildIndex < 0 || nextChildIndex >= getChildCount()) {
                //return focused;
                //Util.LOG("SEARCH focus focusSearch error");
                if(direction == View.FOCUS_UP){
                    scrollBy(0,-itemHeightDimen-(outRect.top+outRect.bottom));
                }else{
                    scrollBy(0,itemHeightDimen+(outRect.top+outRect.bottom));
                }
                //return super.focusSearch(focused,direction);
                return focusSearch(focused,direction);
                //return focused;
                //return super.focusSearch(focused, direction);
            }
            View nextChild = getChildAt(nextChildIndex);

            ArrayList<View> focusables = new ArrayList<>();
            //Util.LOG(getClass().getSimpleName()+" foucsable child count = "+getChildCount());
            if(nextChild != null) {
                findFocusables(nextChild, focusables);
                //Util.LOG(getClass().getSimpleName()+" SEARCH focus focusSearch focusable size"+focusables.size());
                View targetView = null;
                for (View focusableView : focusables) {
                    targetView =  focusableView;
                }
                if (targetView != null) {
                    //Util.LOG(getClass().getSimpleName()+" SEARCH focus event ITEM VIEW 2 find return");
                    return targetView;
                }
            }
        }



        //Util.LOG("SEARCH focus focusSearch 2  super");

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
