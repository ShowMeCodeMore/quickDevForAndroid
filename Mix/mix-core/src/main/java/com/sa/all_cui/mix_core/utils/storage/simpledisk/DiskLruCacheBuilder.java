package com.sa.all_cui.mix_core.utils.storage.simpledisk;

import java.io.File;

/**
 * Created by all-cui on 2017/11/1.
 */

@SuppressWarnings("WeakerAccess")
public class DiskLruCacheBuilder {
    private static final String DIR_NAME = "diskCache";
    private static final int MAX_COUNT = 5 * 1024 * 1024;
    private String mDirName = DIR_NAME;
    private int maxCount = MAX_COUNT;
    private File mFileDir = null;
    private boolean isAvailable = false;//二级sd卡说明可卸载sd卡,默认不在sd卡上创建存储保存缓存信息的目录

    public DiskLruCacheBuilder() {

    }

    public final DiskLruCacheBuilder setDirName(String dirName) {
        this.mDirName = dirName;
        return this;
    }

    public final DiskLruCacheBuilder setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }

    public final DiskLruCacheBuilder setFileDir(File fileDir) {
        this.mFileDir = fileDir;
        return this;
    }

    public final DiskLruCacheBuilder setSdSecondAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
        return this;
    }

    public final DiskLruCacheClient build() {
        return new DiskLruCacheClient(mDirName, maxCount, mFileDir, isAvailable);
    }
}
