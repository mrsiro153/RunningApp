package vn.intelin.android.app.running.widget.recycler.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.app.running.widget.LoadingDialog;
import vn.intelin.android.app.running.widget.ToastUtil;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.CodeResponse;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.EventUser;
import vn.intelin.android.running.model.db.User;
import vn.intelin.android.running.model.request.UserCancelEventRequest;
import vn.intelin.android.running.util.JsonConverter;

public class RegisteredEventAdapter extends RecyclerView.Adapter<RegisteredEventAdapter.RegisteredEventViewHolder> {
    private final Server server = Server.getInstance();

    private List<EventUser> eventUsers;

    public RegisteredEventAdapter(List<EventUser> dataSet) {
        this.eventUsers = dataSet;
    }

    private Resources resources;
    private Context context;

    public void addAll(List<EventUser> eventUsers) {
        if (this.eventUsers == null) eventUsers = new ArrayList<>();
        this.eventUsers.addAll(eventUsers);
        notifyDataSetChanged();
    }

    public void addNew(List<EventUser> eventUsers) {
        if (this.eventUsers == null) eventUsers = new ArrayList<>();
        this.eventUsers.clear();
        this.eventUsers.addAll(eventUsers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RegisteredEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.resources = parent.getContext().getResources();
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lst_event, parent, false);
        return new RegisteredEventViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RegisteredEventViewHolder holder, int position) {
        EventUser eventUser = eventUsers.get(position);
        holder.idEvent.setText(eventUser.getEventId());
        if (eventUser.getRegisterDate() != null) {
            String dateRegister = new SimpleDateFormat("dd-mm-yyyy hh:MM:ss").format(new Date(eventUser.getRegisterDate()));
            holder.registerDate.setText(dateRegister);
        } else {
            holder.registerDate.setText("-");
        }
        holder.btnEventDetail.setBackgroundColor(resources.getColor(R.color.red));
        holder.btnEventDetail.setText("CANCEL");
        //
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(resources.getColor(R.color.green_violet));
        } else {
            holder.itemView.setBackgroundColor(resources.getColor(R.color.blue));
        }
        setUpListener(holder, eventUser,position);
    }

    @Override
    public int getItemCount() {
        return eventUsers.size();
    }

    public static class RegisteredEventViewHolder extends RecyclerView.ViewHolder {
        TextView idEvent;
        TextView registerDate;
        Context context;
        Button btnEventDetail;

        public RegisteredEventViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            idEvent = (TextView) itemView.findViewById(R.id.txt_item_lst_event_id_event);
            registerDate = (TextView) itemView.findViewById(R.id.txt_item_lst_event_name_event);
            btnEventDetail = (Button) itemView.findViewById(R.id.btn_item_lst_event_detail_event);
        }
    }

    private void setUpListener(RegisteredEventAdapter.RegisteredEventViewHolder holder, EventUser eventUser,int position) {
        holder.btnEventDetail.setOnClickListener(view -> {
            LoadingDialog.showLoading(((FragmentActivity)context).getSupportFragmentManager());
            UserCancelEventRequest request = new UserCancelEventRequest()
                    .setUserId(eventUser.getUserId())
                    .setEventId(eventUser.getEventId());
            server.handle(Api.USER_CANCEL_EVENT,JsonConverter.toJson(request),response -> {
                LoadingDialog.remove();
                if(response.getCode().equals(CodeResponse.OK.code)) {
                    eventUsers.remove(position);
                    notifyDataSetChanged();
                }else {
                    Toast toast = Toast.makeText(context, "CANCEL event " + eventUser.getEventId() + " failed", Toast.LENGTH_SHORT);
                    ToastUtil.errorToast(toast).show();
                }
            });
        });
    }
}
