package com.zkp.android.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.jetbrains.annotations.NotNull;

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.db.entity
 * @time: 2019/6/4 15:02
 * @description:
 */
@Entity
public class SearchHistory {

    @Id(autoincrement = true)
    private Long id;

    private long date;

    private String data;

    @Generated(hash = 1462840955)
    public SearchHistory(Long id, long date, String data) {
        this.id = id;
        this.date = date;
        this.data = data;
    }

    @Generated(hash = 1905904755)
    public SearchHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @NotNull
    @Override
    public String toString() {
        return "SearchHistory{" +
                "id=" + id +
                ", date=" + date +
                ", data='" + data + '\'' +
                '}';
    }
}
