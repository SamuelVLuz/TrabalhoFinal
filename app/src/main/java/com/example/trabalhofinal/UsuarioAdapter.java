package com.example.trabalhofinal;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private List<Usuario> usuarios;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Usuario usuario);
    }
    public UsuarioAdapter(List<Usuario> usuarios, OnItemClickListener listener) {
        this.usuarios = usuarios;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeUsuario;
        public View btnDetalhes;

        public ViewHolder(View itemView){
            super(itemView);
            txtNomeUsuario = itemView.findViewById(R.id.txtNomeUsuario);
            btnDetalhes = itemView.findViewById(R.id.btnDetalhes);
        }

        public void bind(Usuario usuario, OnItemClickListener listener) {
            txtNomeUsuario.setText(usuario.getNome());
            btnDetalhes.setOnClickListener(v -> listener.onItemClick(usuario));

            // Action when the rest of the item is clicked (show Toast)
            itemView.setOnClickListener(v -> {
                // Prevent the button itself from triggering this
                if (v.getId() != R.id.btnDetalhes) {
                    android.widget.Toast.makeText(
                            itemView.getContext(),
                            "Selecionado: " + usuario.getNome(),
                            android.widget.Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }
    }
    @Override
    public UsuarioAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent,
                false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder (UsuarioAdapter.ViewHolder holder, int position) {
        holder.bind(usuarios.get(position),listener);
    }
    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
