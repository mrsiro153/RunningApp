package vn.intelin.android.app.running.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import vn.intelin.android.app.running.R;

public class InfoEditText extends ConstraintLayout {
    private String field;
    private Boolean editable;
    private EditText edtValue;

    public InfoEditText(Context context) {
        super(context);
    }

    public InfoEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InfoEditText, 0, 0);
        this.field = a.getString(R.styleable.InfoEditText_filedName);
        this.editable = a.getBoolean(R.styleable.InfoEditText_fieldEditable,true);
        String hint = a.getString(R.styleable.InfoEditText_edtHint);
        a.recycle();
        initializeView(context);
        TextView t = findViewById(R.id.txt_item_name);
        t.setText(field);
        //
        edtValue = findViewById(R.id.edt_item_value);
        edtValue.setEnabled(editable);
        edtValue.setHint(hint);
    }

    public InfoEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEditTextValue(String value){
        edtValue.setText(value);
    }

    public String getEditTextValue(){
        return edtValue.getText().toString();
    }

    private void initializeView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_user_info, this,true);
    }
}
