package vn.intelin.android.app.running.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import vn.intelin.android.app.running.R;
import vn.intelin.android.app.running.util.DataAccess;
import vn.intelin.android.running.api.Api;
import vn.intelin.android.running.api.CodeResponse;
import vn.intelin.android.running.api.Server;
import vn.intelin.android.running.model.db.Event;
import vn.intelin.android.running.model.db.User;
import vn.intelin.android.running.model.request.UserEnrollEventRequest;
import vn.intelin.android.running.util.JsonConverter;
import vn.intelin.android.running.util.LogCat;

public class EventDetailDialog extends DialogFragment {
    private final LogCat log = new LogCat(this.getClass());
    private Server server = Server.getInstance();
    private Event event;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_event_detail);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_event_detail, container);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public static void show(FragmentManager manager, Event event) {
        EventDetailDialog dialog = new EventDetailDialog();
        dialog.setEvent(event);
        dialog.show(manager, "fds");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView t = view.findViewById(R.id.txt_dialog_event_detail_title);
        t.setText(event.getId());
        //
        InfoEditText idtEventName = view.findViewById(R.id.idt_dialog_event_detail_event_name);
        idtEventName.setEditTextValue(event.getName());
        InfoEditText idtEventFee = view.findViewById(R.id.idt_dialog_event_detail_event_fee);
        idtEventFee.setEditTextValue(String.valueOf(event.getFee()));
        InfoEditText idtEventParticipants = view.findViewById(R.id.idt_dialog_event_detail_event_participants);
        idtEventParticipants.setEditTextValue(String.valueOf(event.getParticipants()));
        InfoEditText idtEventRule = view.findViewById(R.id.idt_dialog_event_detail_event_rule);
        idtEventRule.setEditTextValue(String.valueOf(event.getRule()));
        //
        Button btnEnroll = view.findViewById(R.id.btn_dialog_event_detail_event_enroll);
        btnEnroll.setOnClickListener(v -> {
            LoadingDialog.showLoading(this.getFragmentManager());
            UserEnrollEventRequest request = new UserEnrollEventRequest()
                    .setUserId(DataAccess.getAs(DataAccess.USER, User.class).getId())
                    .setEventId(event.getId());
            server.handle(Api.ENROLL_EVENT, JsonConverter.toJson(request), rs -> {
                LoadingDialog.remove();
                log.e("server response api: " + Api.ENROLL_EVENT + " with data: " + JsonConverter.toJson(rs));
                if (rs.getCode().equals(CodeResponse.OK.code)) {
                    Toast toast = Toast.makeText(this.getActivity(), "register event " + event.getId() + " success", Toast.LENGTH_SHORT);
                    toast.getView().setBackgroundColor(getResources().getColor(R.color.green));
                    ((TextView)toast.getView().findViewById(android.R.id.message)).setTextColor(getResources().getColor(R.color.white));
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(this.getActivity(), "register event " + event.getId() + " failed", Toast.LENGTH_SHORT);
                    toast.getView().setBackgroundColor(getResources().getColor(R.color.red));
                    ((TextView)toast.getView().findViewById(android.R.id.message)).setTextColor(getResources().getColor(R.color.white));
                    toast.show();
                    dismiss();
                }
                this.dismiss();
            });
        });
    }
}
