package com.ricm.audifast.viewpageradapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ricm.audifast.ui.infoAuditoria.auxiliaresFragment;
import com.ricm.audifast.ui.infoAuditoria.contactosFragment;
import com.ricm.audifast.ui.infoAuditoria.infoFragment;
import com.ricm.audifast.ui.infoAuditoria.productosFragment;

public class tabsApartadosAdapter extends FragmentStateAdapter {
    String json;

    public tabsApartadosAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String json) {
        super(fragmentManager, lifecycle);
        this.json = json;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new auxiliaresFragment();
            case 2:
                return new contactosFragment();
            case 3:
                return new productosFragment();
        }
        return new infoFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
