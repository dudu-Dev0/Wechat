package com.dudu.wechat.ui.home;

import android.content.Context;
import android.net.Network;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaderFactory;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.dudu.wechat.R;
import com.dudu.wechat.Wechat;
import com.dudu.wechat.model.User;
import com.dudu.wechat.utils.NetworkUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.ViewHolder> {

    private List<User> list = new ArrayList<>();

    public SessionsAdapter(List<User> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User u = list.get(position);
        GlideUrl glideUrl = new GlideUrl(NetworkUtil.MAIN_BASE_URL+u.HeadImgUrl,new Headers(){
            @Override
            public Map<String, String> getHeaders() {
                return NetworkUtil.WX_HEADERS();
            }
            
        });
        Glide.with(Wechat.getContext())
                .load(glideUrl)
                //.placeholder(R.mipmap.akari)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.headImg);
        holder.name.setText(u.NickName);
        holder.time.setText("11:45");
        holder.content.setText("大好き");
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView headImg;
        private TextView name;
        private TextView time;
        private TextView content;
        private TextView unread;

        public ViewHolder(View view) {
            super(view);
            headImg = (ImageView) view.findViewById(R.id.head_img);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
            content = (TextView) view.findViewById(R.id.content);
            unread = (TextView) view.findViewById(R.id.unread);
        }
    }
}
