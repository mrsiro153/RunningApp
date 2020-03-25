package vn.intelin.android.app.running.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import vn.intelin.android.app.running.R;
import vn.intelin.android.running.model.db.Event;

public class TagEventDetailAdapter extends RecyclerView.Adapter<TagEventDetailAdapter.TagEventDetailViewHolder> {
    private List<Event> events;
    private Context context;

    public TagEventDetailAdapter(List<Event> events) {
        this.events = events;
    }

    public void addAll(List<Event> events) {
        if (this.events == null) events = new ArrayList<>();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    public void addNew(List<Event> events) {
        if (this.events == null) events = new ArrayList<>();
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TagEventDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lst_tag_event_event_detail, parent, false);
        return new TagEventDetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TagEventDetailViewHolder holder, int position) {
        if(getItemCount()<2 || position == getItemCount()-1){
            holder.next.setVisibility(View.GONE);
        }
        Event event = events.get(position);
        holder.txtEventName.setText(event.getId());

        Date d = new Date();
        d.setTime(event.getTime());
        String time = new SimpleDateFormat("dd/mm/yyyy hh:MM:ss").format(d);
        holder.txtTimeLocation.setText(time + "-----" + event.getLocation().getName());

        holder.txtFee.setText(event.getFee() != null ? String.valueOf("FEE: "+event.getFee()) : "");
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class TagEventDetailViewHolder extends RecyclerView.ViewHolder {
        private TextView txtEventName;
        private TextView txtTimeLocation;
        private TextView txtFee;
        private ImageView eventAvatar;
        private ImageView next;

        public TagEventDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            txtEventName = itemView.findViewById(R.id.txt_item_lst_tag_event_event_detail_event_name);
            txtTimeLocation = itemView.findViewById(R.id.txt_item_lst_tag_event_event_detail_time_location);
            txtFee = itemView.findViewById(R.id.txt_item_lst_tag_event_event_detail_fee);
            eventAvatar = itemView.findViewById(R.id.img_item_lst_tag_event_detail_avatar_event);
            next = itemView.findViewById(R.id.txt_item_lst_tag_event_event_detail_is_next);
        }
    }
}
