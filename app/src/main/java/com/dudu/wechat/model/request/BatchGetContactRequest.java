package com.dudu.wechat.model.request;
import com.dudu.wechat.model.BaseRequest;
import com.dudu.wechat.model.User;
import java.util.List;

public class BatchGetContactRequest {
    public BaseRequest BaseRequest = new BaseRequest();
    public int Count;
    public List<User> List;
    public BatchGetContactRequest(int count,List<User> list){
        Count = count;
        List = list;
    }
}
