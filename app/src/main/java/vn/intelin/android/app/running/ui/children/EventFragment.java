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
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.app.running.widget.recycler.adapter.EventAdapter;
import vn.intelin.android.app.running.widget.recycler.adapter.RegisteredEventAdapter;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.Event;
import vn.intelin.android.running.model.db.EventUser;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class EventFragment extends Fragment {
    private Server server = Server.getInstance();
    private final LogCat log = new LogCat(this.getClass());
    EventAdapter.OnEventRegisteredListener eventRegisteredChange = event->{
        getListRegisteredEvent();
    };

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
        setUpLstEvent();
        setUpLstRegisteredEvent();
    }

    private void setUpListener() {
    }

    private void doFirst() {
        if (DataAccess.get(DataAccess.EVENT) != null) {
            List<Event> lst = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(String.valueOf(DataAccess.get(DataAccess.EVENT))), Event[].class);
            RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_list));
            ((EventAdapter) recyclerView.getAdapter()).addAll(lst);
        } else {
            server.handle(Api.GET_EVENT, "", rs -> {
                log.d("SERVER RESPONSE OF API: " + Api.GET_EVENT + " with value: " + rs.getData());
                DataAccess.push(DataAccess.EVENT, rs.getData());
                List<Event> lst = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(String.valueOf(rs.getData())), Event[].class);
                RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_list));
                ((EventAdapter) recyclerView.getAdapter()).addAll(lst);
            });
        }
        getListRegisteredEvent();
    }

    private void setUpLstEvent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_list));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new EventAdapter(new ArrayList<>(),eventRegisteredChange));
    }

    private void setUpLstRegisteredEvent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_registered_list));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RegisteredEventAdapter(new ArrayList<>()));
    }
    private void getListRegisteredEvent(){
        server.handle(Api.GET_REGISTERED_EVENT, DataAccess.get(DataAccess.USER), response -> {
            RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_registered_list));
            List<EventUser> lst = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(String.valueOf(response.getData())), EventUser[].class);
            ((RegisteredEventAdapter) recyclerView.getAdapter()).addNew(lst);
        });
    }
}
