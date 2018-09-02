package com.sa.all_cui.mix_ec.main;

import android.graphics.Color;

import com.sa.all_cui.mix_core.delegate.bottom.BaseBottomDelegate;
import com.sa.all_cui.mix_core.delegate.bottom.BottomItemDelegate;
import com.sa.all_cui.mix_core.delegate.bottom.BottomTabBean;
import com.sa.all_cui.mix_core.delegate.bottom.ItemBuilder;
import com.sa.all_cui.mix_ec.R;
import com.sa.all_cui.mix_ec.main.cart.ShopCartDelegate;
import com.sa.all_cui.mix_ec.main.discover.DiscoverDelegate;
import com.sa.all_cui.mix_ec.main.index.IndexDelegate;
import com.sa.all_cui.mix_ec.main.personal.PersonDelegate;

import java.util.LinkedHashMap;

/**
 * Created by all-cui on 2017/8/21.
 */

public class EcBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean(getString(R.string.bottom_home_key),getString(R.string.bottom_home_value)),new IndexDelegate());
        items.put(new BottomTabBean(getString(R.string.bottom_compass_key),getString(R.string.bottom_compass_value)),new DiscoverDelegate());
        items.put(new BottomTabBean(getString(R.string.bottom_download_key),getString(R.string.bottom_download_value)),new ShopCartDelegate());
        items.put(new BottomTabBean(getString(R.string.bottom_user_key),getString(R.string.bottom_user_value)),new PersonDelegate());
        return builder.addItem(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8888");
    }

}
