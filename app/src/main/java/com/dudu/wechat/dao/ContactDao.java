package com.dudu.wechat.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.dudu.wechat.model.User;
import java.util.List;

@Dao
public interface ContactDao {
    
    @Query("SELECT * FROM contacts")
    List<User> getAll();
    
    @Query("SELECT * FROM contacts LIMIT :min ,:max")
    List<User> getAll(int min,int max);
    
    @Query("SELECT * FROM contacts WHERE UserName GLOB '@@*'")
    List<User> getGroups();
    
    @Query("SELECT * FROM contacts WHERE UserName GLOB '@*' AND UserName NOT GLOB '@@*'")
    List<User> getFriends();

    @Query("SELECT * FROM contacts WHERE UserName GLOB :name")
    List<User> getByName(String name);
    
    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
    
    @Update
    void update(User user);
}
