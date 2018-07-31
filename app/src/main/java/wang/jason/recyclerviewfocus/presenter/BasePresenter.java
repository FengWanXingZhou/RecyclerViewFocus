package wang.jason.recyclerviewfocus.presenter;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author wj
 * @Date: 2018/7/31
 * @Description:
 **/
public abstract class BasePresenter<V,M> {

    protected V mView;
    protected M mModel;
    private CompositeDisposable compositeDisposable;
    public  BasePresenter(V mView){
        this.mView = mView;
        this.mModel = createModel();
        compositeDisposable = new CompositeDisposable();
    }

    protected abstract M createModel();

    public void destory(){
        compositeDisposable.dispose();
        mView = null;
        mModel = null;
    }



    protected class BaseObserver<T> implements Observer<T>{
        protected Disposable disposable;
        @Override
        public void onSubscribe(Disposable d) {
            this.disposable = d;
            compositeDisposable.add(d);
        }

        @Override
        public void onNext(T t) {

        }

        @Override
        public void onError(Throwable e) {
            compositeDisposable.remove(disposable);
        }

        @Override
        public void onComplete() {
            compositeDisposable.remove(disposable);

        }
    }


}
