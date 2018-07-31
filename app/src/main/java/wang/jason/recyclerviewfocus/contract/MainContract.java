package wang.jason.recyclerviewfocus.contract;

import java.util.List;

import wang.jason.recyclerviewfocus.model.BaseModel;
import wang.jason.recyclerviewfocus.presenter.BasePresenter;
import wang.jason.recyclerviewfocus.view.BaseView;

/**
 * @author wj
 * @Date: 2018/7/31
 * @Description:
 **/
public class MainContract {

    public interface MainView extends BaseView{
        void updateRecyclerViewAData(List<String> aDataStringList);
        void updateRecyclerViewBData(List<String> bDataStringList);
    }
    public interface MainModel extends BaseModel{
        List<String> getAData();
        List<String> getBData();
    }
    public static abstract class AbstractMainPresenter extends BasePresenter<MainView,MainModel>{
        public AbstractMainPresenter(MainView mView) {
            super(mView);
        }
        public abstract void getData();
    }

}
