package vn.intelin.android.app.running.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import vn.intelin.android.app.running.R;

public class InfoEditText extends ConstraintLayout {
    private String field;
    private Boolean editable;
    private EditText edtValue;

    public InfoEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.item_user_info, this);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InfoEditText, 0, 0);
        this.field = a.getString(R.styleable.InfoEditText_filedName);
        this.editable = a.getBoolean(R.styleable.InfoEditText_fieldEditable, true);
        String hint = a.getString(R.styleable.InfoEditText_edtHint);

        TextView t = findViewById(R.id.txt_item_name);
        t.setText(field);
        //
        edtValue = findViewById(R.id.edt_item_value);
        edtValue.setEnabled(editable);
        edtValue.setHint(hint);
        a.recycle();
    }

    public void setEditTextValue(String value) {
        edtValue.setText(value);
    }

    public String getEditTextValue() {
        return edtValue.getText().toString();
    }
}
