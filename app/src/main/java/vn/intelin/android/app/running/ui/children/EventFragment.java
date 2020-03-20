package vn.intelin.android.app.running.ui.children;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.widget.recycler.adapter.EventAdapter;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.Event;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class EventFragment extends Fragment {
    private Server server = Server.getInstance();
    private final LogCat log = new LogCat(this.getClass());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
        setUpListener();
        doFirst();
    }

    private void setUpView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_list));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new EventAdapter(new ArrayList<>()));
    }

    private void setUpListener() {

    }

    private void doFirst() {
        server.handle(Api.GET_EVENT, "", rs -> {
            log.d("SERVER RESPONSE OF API: " + Api.GET_EVENT + " with value: " + rs.getData());
            List<Event> lst = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(String.valueOf(rs.getData())), Event[].class);
            RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_list));
            ((EventAdapter) recyclerView.getAdapter()).addAll(lst);
        });

    }
}
