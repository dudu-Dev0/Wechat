package com.dudu.wechat;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.dudu.wechat.dao.ContactDao;
import com.dudu.wechat.model.User;
import com.dudu.wechat.model.converter.UserListConverter;

@Database(entities={User.class},version=1,exportSchema=false)
public abstract class WechatDatabase extends RoomDatabase{

    private static WechatDatabase sInstance;
    private static String DATABASE_NAME = "wechat";

    public static WechatDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (WechatDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context);
                }
            }
        }
        return sInstance;
    }
    private static WechatDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, WechatDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }

                })
                .build();
    }

    public abstract ContactDao getContactsDao();
}

