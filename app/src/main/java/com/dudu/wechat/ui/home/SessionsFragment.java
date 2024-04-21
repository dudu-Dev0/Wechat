package com.dudu.wechat.ui.home;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.dudu.wechat.R;
import com.dudu.wechat.WechatDatabase;
import com.dudu.wechat.dao.ContactDao;
import com.dudu.wechat.model.User;
import com.dudu.wechat.utils.CenterThreadPool;
import com.dudu.wechat.utils.SharedPreferencesUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SessionsFragment extends Fragment {
    List<User> sessionsList;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup group,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sessions,group,false);
		
		List<String> sessions = SharedPreferencesUtil.getListData(SharedPreferencesUtil.SESSIONS_LIST,String.class);
        
        sessionsList = Collections.synchronizedList(new ArrayList<User>());
        CenterThreadPool.run(()->{
            ContactDao dao = WechatDatabase.getInstance(getContext()).getContactsDao();
            Log.e("z",dao.getAll().toString());
            for(String i : sessions){
                List<User> user = dao.getByName(i);
                if(user.size()>0) {
                	sessionsList.add(user.get(0));
                }
            }
        });
        Log.e("count",sessionsList.toString()+"...."+sessions.toString());
		RecyclerView listView = view.findViewById(R.id.sessions_list);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        SessionsAdapter adapter = new SessionsAdapter(sessionsList);
        listView.setAdapter(adapter);
        return view;
    }
    
}
