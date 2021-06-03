package com.ricm.audifast.recyclerviewadapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ricm.audifast.R;
import com.ricm.audifast.entidades.Producto;

import java.util.List;

public class listaProductosAdapter extends RecyclerView.Adapter<listaProductosAdapter.RowViewHolder> {
    List<Producto> productoList;

    public listaProductosAdapter(List<Producto> productoList) {
        this.productoList = productoList;
    }

    @Override
    public RowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.table_productos_item,null,false);

        RowViewHolder vHolder = new RowViewHolder(itemView);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(RowViewHolder rowViewHolder, int position) {
        Producto modal = productoList.get(position);

        rowViewHolder.txtClave.setText(modal.getClave());
        rowViewHolder.txtNombre.setText(modal.getNombre());
    }

    @Override
    public int getItemCount() {
        if(productoList != null){
            return productoList.size();
        }else{
            return 0;
        }

    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtClave;
        protected TextView txtNombre;

        public RowViewHolder(View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.txtNombreProducto);
            txtClave = itemView.findViewById(R.id.txtClaveProducto);
        }
    }

    public void updateList (List<Producto> listaActualizada) {
        if (listaActualizada != null && listaActualizada.size() > 0) {
            productoList.clear();
            productoList.addAll(listaActualizada);
        }
    }
}