package com.sa.all_cui.mix_core.delegate;

/**
 * Created by all-cui on 2017/8/11.
 */

public abstract class DoggerDelegate extends PermissionCheckerDelegate {
    @SuppressWarnings("unchecked")
    public <T extends DoggerDelegate> T getParentDelegate(){
        return (T)getParentFragment();
    }
}
