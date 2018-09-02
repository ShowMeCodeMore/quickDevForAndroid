package com.sa.all_cui.mix_ec.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by all-cui on 2017/8/28.
 */

public enum EcIcons implements Icon {
    icon_scan('\ue602'),
    icon_ali_pay('\ue606');

    private char mCharacter;

    EcIcons(char character) {
        this.mCharacter = character;
    }

    @Override
    public String key() {
        return name().replace("_", "-");
    }

    @Override
    public char character() {
        return mCharacter;
    }
}
