package vn.intelin.android.app.running.widget.recycler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.CodeResponse;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.Event;
import vn.intelin.android.running.model.db.TagShow;
import vn.intelin.android.running.model.db.event.Tag;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class TagEventAdapter extends RecyclerView.Adapter<TagEventAdapter.TagEventViewHolder> {
    private final Server server = Server.getInstance();
    private final LogCat log = new LogCat(this.getClass());
    private List<TagShow> tagShows;
    private Context context;

    public TagEventAdapter(List<TagShow> tagShows) {
        this.tagShows = tagShows;
    }

    public void addAll(List<TagShow> tagShows) {
        if (this.tagShows == null) tagShows = new ArrayList<>();
        this.tagShows.addAll(tagShows);
        notifyDataSetChanged();
    }

    public void addNew(List<TagShow> tagShows) {
        if (this.tagShows == null) tagShows = new ArrayList<>();
        this.tagShows.clear();
        this.tagShows.addAll(tagShows);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TagEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lst_tag_event, parent, false);
        return new TagEventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TagEventViewHolder holder, int position) {
        //setup view, listener here!
        holder.txtTagName.setText(tagShows.get(position).getTag());
        setUpView(holder);
        setUpListener(holder);
        doFirst(holder, position);
    }

    @Override
    public int getItemCount() {
        return tagShows.size();
    }

    public static class TagEventViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTagName;
        private RecyclerView lstTagDetail;

        public TagEventViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTagName = itemView.findViewById(R.id.txt_item_lst_tag_event_tag_name);
            lstTagDetail = itemView.findViewById(R.id.lst_item_tag_event_event_list);
        }
    }

    public interface OnTagClickListener {
        void onClick(TagShow tagShow);
    }

    private void setUpView(TagEventViewHolder holder) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        holder.lstTagDetail.setLayoutManager(layoutManager);
        holder.lstTagDetail.setAdapter(new TagEventDetailAdapter(new ArrayList<>()));
    }

    private void setUpListener(TagEventViewHolder holder) {
        //todo click, swipe.. .?!
    }

    private void doFirst(TagEventViewHolder holder, int position) {

        if (DataAccess.get(DataAccess.DataKey.EVENT) != null) {
            List<Event> events = DataAccess.getAs(DataAccess.DataKey.EVENT, List.class);
            ((TagEventDetailAdapter) holder.lstTagDetail.getAdapter()).addNew(filterEventBytag(tagShows.get(position),events));
        } else {
            server.handle(Api.GET_EVENT, "", response -> {
                if (response.getCode().equals(CodeResponse.OK.code)) {
                    List<Event> events = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(response.getData()), Event[].class);
                    DataAccess.push(DataAccess.DataKey.EVENT, events);
                    ((TagEventDetailAdapter) holder.lstTagDetail.getAdapter()).addNew(filterEventBytag(tagShows.get(position),events));
                }
            });
        }
    }

    private List<Event> filterEventBytag(TagShow tagShow,List<Event> events){
        List<Event> resp = new ArrayList<>();
        for(Event event: events){
            for(Tag t: event.getTag()){
                if(t.getId().equals(tagShow.getTag())) {
                    resp.add(event);
                    break;
                }
            }
        }
        return resp;
    }
}
