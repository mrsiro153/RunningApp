package vn.intelin.android.app.running.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import vn.intelin.android.app.running.R;
import vn.intelin.android.running.model.db.EventType;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.EventTypeViewHolder> {
    private List<EventType> eventTypes;
    private Context context;
    private OnEventTypeClickListener eventTypeClickListener;

    public EventTypeAdapter(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public void addAll(List<EventType> eventTypes) {
        if (this.eventTypes == null) eventTypes = new ArrayList<>();
        this.eventTypes.addAll(eventTypes);
        notifyDataSetChanged();
    }

    public void addNew(List<EventType> eventUsers) {
        if (this.eventTypes == null) eventUsers = new ArrayList<>();
        this.eventTypes.clear();
        this.eventTypes.addAll(eventUsers);
        notifyDataSetChanged();
    }

    public EventTypeAdapter setEventTypeClickListener(OnEventTypeClickListener eventTypeClickListener) {
        this.eventTypeClickListener = eventTypeClickListener;
        return this;
    }

    @NonNull
    @Override
    public EventTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lst_event_type, parent, false);
        return new EventTypeViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull EventTypeViewHolder holder, int position) {
        EventType eventType = eventTypes.get(position);
        holder.txtEventTypeName.setText(eventType.getName());
        setUpListener(holder, position);
    }

    @Override
    public int getItemCount() {
        return eventTypes.size();
    }

    public static class EventTypeViewHolder extends RecyclerView.ViewHolder {
        private TextView txtEventTypeName;

        public EventTypeViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            txtEventTypeName = itemView.findViewById(R.id.txt_item_lst_event_type_type_name);
        }
    }

    private void setUpListener(EventTypeViewHolder holder, int position) {
        holder.txtEventTypeName.setOnClickListener(v -> {
            if (eventTypeClickListener != null)
                eventTypeClickListener.onEventTypeClick(eventTypes.get(position));
        });
    }

    public interface OnEventTypeClickListener {
        void onEventTypeClick(EventType eventType);
    }
}
