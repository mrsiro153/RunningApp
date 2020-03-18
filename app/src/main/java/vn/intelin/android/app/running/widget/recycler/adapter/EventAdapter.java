package vn.intelin.android.app.running.widget.recycler.adapter;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import vn.intelin.android.app.running.R;
import vn.intelin.android.running.model.db.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private List<Event> events;
    public EventAdapter(List<Event> dataSet) {
        this.events = dataSet;
    }
    private Resources resources;

    public void addAll(List<Event> events){
        if(this.events==null) events= new ArrayList<>();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.resources = parent.getContext().getResources();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lst_event, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.idEvent.setText(event.getId());
        holder.nameEvent.setText(event.getName());
        //
        if(position%2==0){
            holder.itemView.setBackgroundColor(resources.getColor(R.color.pink_bold));
        } else {
            holder.itemView.setBackgroundColor(resources.getColor(R.color.brown));
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder{
        TextView idEvent;
        TextView nameEvent;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            idEvent = (TextView) itemView.findViewById(R.id.txt_item_lst_event_id_event) ;
            nameEvent= (TextView) itemView.findViewById(R.id.txt_item_lst_event_name_event);
        }
    }
}
