package vn.intelin.android.app.running.ui.children;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.app.running.widget.recycler.adapter.EventAdapter;
import vn.intelin.android.app.running.widget.recycler.adapter.EventTypeAdapter;
import vn.intelin.android.app.running.widget.recycler.adapter.RegisteredEventAdapter;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.CodeResponse;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.Event;
import vn.intelin.android.running.model.db.EventType;
import vn.intelin.android.running.model.db.EventUser;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class EventFragment extends Fragment {
    private Server server = Server.getInstance();
    private final LogCat log = new LogCat(this.getClass());
    EventAdapter.OnEventRegisteredListener eventRegisteredChange = event -> {
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
        setUpLstEventType();
        setUpSwipeRefresh();
    }

    private void setUpListener() {
    }

    private void doFirst() {
        getListEvent();
        getListRegisteredEvent();
        getListEventType();
    }

    private void setUpLstEvent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_list));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new EventAdapter(new ArrayList<>(), eventRegisteredChange));
    }

    private void setUpLstRegisteredEvent() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_registered_list));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new RegisteredEventAdapter(new ArrayList<>()));
    }

    @SuppressWarnings("all")
    private void setUpLstEventType() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        RecyclerView recyclerView = getView().findViewById(R.id.lst_event_type_event);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new EventTypeAdapter(new ArrayList<>()));
        ((EventTypeAdapter) recyclerView.getAdapter()).setEventTypeClickListener(eventType -> {
            List<Event> lst = DataAccess.getAs(DataAccess.DataKey.EVENT,List.class);
            List<Event> newLst = new ArrayList<>();
            for (Event e : lst) {
                if (e.getEventType().getEventTypeId().equals(eventType.getId()))
                    newLst.add(e);
            }
            if (newLst.isEmpty()) {
                Toast.makeText(getContext(),"Not found any event of type: "+eventType.getName(),Toast.LENGTH_SHORT).show();
            } else
                ((EventAdapter) ((RecyclerView) getView().findViewById(R.id.lst_event_event_list)).getAdapter()).addNew(newLst);
        });
        //
    }

    @SuppressWarnings("all")
    private void getListEvent(){
        if (DataAccess.get(DataAccess.DataKey.EVENT) != null) {
            List<Event> lst = DataAccess.getAs(DataAccess.DataKey.EVENT,List.class);
            RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_list));
            ((EventAdapter) recyclerView.getAdapter()).addNew(lst);
        } else {
            server.handle(Api.GET_EVENT, "", rs -> {
                List<Event> lst = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(String.valueOf(rs.getData())), Event[].class);
                DataAccess.push(DataAccess.DataKey.EVENT, lst);
                RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_list));
                ((EventAdapter) recyclerView.getAdapter()).addNew(lst);
            });
        }
    }

    private void getListRegisteredEvent() {
        server.handle(Api.GET_REGISTERED_EVENT, DataAccess.getAs(DataAccess.DataKey.USER,String.class), response -> {
            RecyclerView recyclerView = ((RecyclerView) getView().findViewById(R.id.lst_event_event_registered_list));
            List<EventUser> lst = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(String.valueOf(response.getData())), EventUser[].class);
            ((RegisteredEventAdapter) recyclerView.getAdapter()).addNew(lst);
        });
    }

    private void getListEventType() {
        server.handle(Api.GET_EVENT_TYPE, "", response -> {
            if (response.getCode().equals(CodeResponse.OK.code)) {
                List<EventType> lst = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(response.getData()), EventType[].class);
                RecyclerView recyclerView = getView().findViewById(R.id.lst_event_type_event);
                ((EventTypeAdapter) recyclerView.getAdapter()).addNew(lst);
            }
        });
    }

    private void setUpSwipeRefresh(){
        SwipeRefreshLayout refreshLayout = getView().findViewById(R.id.layout_swipe_event_fragment);
        refreshLayout.setOnRefreshListener(()->{
            doFirst();
            refreshLayout.setRefreshing(false);
        });
    }
}
