package com.example.trabalhofinal;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.ViewHolder> {

    private List<Evento> eventos;
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Evento evento);
    }
    public EventoAdapter(List<Evento> eventos, OnItemClickListener listener) {
        this.eventos = eventos;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeEvento;
        public View btnDetalhes;

        public ViewHolder(View itemView){
            super(itemView);
            txtNomeEvento = itemView.findViewById(R.id.txtNomeEvento);
            btnDetalhes = itemView.findViewById(R.id.btnDetalhes);
        }

        public void bind(Evento evento, OnItemClickListener listener) {
            txtNomeEvento.setText(evento.getNome());
            btnDetalhes.setOnClickListener(v -> listener.onItemClick(evento));

            itemView.setOnClickListener(v -> {
                if (v.getId() != R.id.btnDetalhes) {
                    android.widget.Toast.makeText(
                            itemView.getContext(),
                            "Selecionado: " + evento.getNome(),
                            android.widget.Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }
    }
    @Override
    public EventoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento, parent,
                false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder (EventoAdapter.ViewHolder holder, int position) {
        holder.bind(eventos.get(position),listener);
    }
    @Override
    public int getItemCount() {
        return eventos.size();
    }
}
