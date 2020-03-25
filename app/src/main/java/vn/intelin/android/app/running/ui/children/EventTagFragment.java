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
import vn.intelin.android.app.running.widget.recycler.adapter.TagEventAdapter;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.CodeResponse;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.TagShow;
import vn.intelin.android.running.util.JsonConverter;

public class EventTagFragment extends Fragment {
    private Server server = Server.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tag_event, container, false);
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
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        RecyclerView lstTag = getView().findViewById(R.id.lst_tag_event_tag_list);
        lstTag.setLayoutManager(layoutManager);
        lstTag.setAdapter(new TagEventAdapter(new ArrayList<>()));

    }

    private void setUpListener() {

    }

    private void doFirst(){
        server.handle(Api.GET_TAG_SHOW,"",response -> {
            if(response.getCode().equals(CodeResponse.OK.code)) {
                List<TagShow> tagShows = JsonConverter.fromJsonToList(JsonConverter.toJsonElement(response.getData()), TagShow[].class);
                ((TagEventAdapter) ((RecyclerView) getView().findViewById(R.id.lst_tag_event_tag_list)).getAdapter()).addNew(tagShows);
            }
        });
    }
}
