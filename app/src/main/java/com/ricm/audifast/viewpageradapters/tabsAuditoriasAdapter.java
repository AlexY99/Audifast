package com.ricm.audifast.viewpageradapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ricm.audifast.ui.listaAuditorias.auxiliadasFragment;
import com.ricm.audifast.ui.listaAuditorias.lideradasFragment;

public class tabsAuditoriasAdapter extends FragmentStateAdapter {

    public tabsAuditoriasAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new auxiliadasFragment();
        }
        return new lideradasFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
