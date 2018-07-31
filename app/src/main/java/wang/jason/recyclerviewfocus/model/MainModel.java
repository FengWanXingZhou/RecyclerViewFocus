package wang.jason.recyclerviewfocus.model;

import java.util.ArrayList;
import java.util.List;

import wang.jason.recyclerviewfocus.contract.MainContract;

/**
 * @author wj
 * @Date: 2018/7/31
 * @Description:
 **/
public class MainModel implements MainContract.MainModel {

    @Override
    public List<String> getAData() {

        List<String> stringList = new ArrayList<>(50);
        for(int i = 0;i< 50;i++){
            String data = ""+'a'+i;
            stringList.add(data);
        }

        return stringList;
    }

    @Override
    public List<String> getBData() {
        List<String> stringList = new ArrayList<>(50);
        for(int i = 0;i< 50;i++){
            String data = ""+'a'+i;
            stringList.add(data);
        }

        return stringList;
    }
}
