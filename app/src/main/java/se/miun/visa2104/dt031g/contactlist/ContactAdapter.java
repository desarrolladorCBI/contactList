package se.miun.visa2104.dt031g.contactlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;
    private OnItemClickListener listener;

    // Constructor para el adaptador, recibe la lista de contactos y el listener para los clics
    public ContactAdapter(List<Contact> contactList, OnItemClickListener listener) {
        this.contactList = contactList;
        this.listener = listener;
    }

    // Método llamado cuando se debe crear un nuevo ViewHolder para un elemento del RecyclerView
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout del item de contacto
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);

        // Crear y retornar un nuevo ViewHolder para el elemento
        return new ContactViewHolder(view);
    }

    // Método llamado cuando se debe actualizar el contenido de un ViewHolder específico
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        // Obtener el contacto en la posición dada
        Contact contact = contactList.get(position);

        // Vincular los datos del contacto al ViewHolder
        holder.bind(contact);
    }

    // Método que retorna la cantidad de elementos en la lista de contactos
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    // Clase interna para el ViewHolder
    public class ContactViewHolder extends RecyclerView.ViewHolder {

        // Declarar las vistas aquí (por ejemplo, TextViews para el nombre y el número de teléfono del contacto)
        private TextView nameTextView;
        private TextView phoneNumberTextView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            // Inicializar las vistas aquí
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneNumberTextView = itemView.findViewById(R.id.phoneTextView);

            // Establecer un click listener para el elemento del contacto
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Llamar al método onItemClick del listener cuando se haga clic en el elemento del contacto
                        listener.onItemClick(contactList.get(position));
                    }
                }
            });
        }

        // Método para vincular los datos del contacto al ViewHolder
        public void bind(Contact contact) {
            nameTextView.setText(contact.getName());
            phoneNumberTextView.setText(contact.getPhone());
        }
    }

    // Interfaz para el listener de clics en los elementos del RecyclerView
    public interface OnItemClickListener {
        void onItemClick(Contact contact);
    }
}

