package com.sa.all_cui.mix_core.delegate.web.event;


import com.sa.all_cui.mix_core.utils.log.DoggerLog;

/**
 * Created by all-cui on 2017/8/24.
 */

public class UndefineEvent extends Event {
    @Override
    public String execute(String param) {
        DoggerLog.e(param);
        return null;
    }
}
