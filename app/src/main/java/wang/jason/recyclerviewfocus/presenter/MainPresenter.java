package wang.jason.recyclerviewfocus.presenter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wang.jason.recyclerviewfocus.contract.MainContract;
import wang.jason.recyclerviewfocus.model.MainModel;

/**
 * @author wj
 * @Date: 2018/7/31
 * @Description:
 **/
public class MainPresenter extends MainContract.AbstractMainPresenter {



    public MainPresenter(MainContract.MainView mView) {
        super(mView);
    }

    @Override
    public void getData() {
        Observable.interval(0,1, TimeUnit.SECONDS)
                .map(nouse->{
                    return mModel.getAData();
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<String> >(){
                    @Override
                    public void onNext(List<String>  stringList) {
                        super.onNext(stringList);
                        mView.updateRecyclerViewAData(stringList);
                    }
                });
        Observable.interval(0,1, TimeUnit.SECONDS)
                .map(nouse->{
                    return mModel.getBData();
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<String> >(){
                    @Override
                    public void onNext(List<String>  stringList) {
                        super.onNext(stringList);
                        mView.updateRecyclerViewBData(stringList);
                    }
                });
    }

    @Override
    protected MainContract.MainModel createModel() {
        return new MainModel();
    }
}
