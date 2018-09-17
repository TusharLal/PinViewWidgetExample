package view.pin.example.tusharlal.pinviewwidgetexample.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import view.pin.example.tusharlal.pinviewwidgetexample.R;

/**
 * Created by : Tushar Lal
 * Date       : 12-09-2018
 */
public class PinView extends LinearLayout implements TextWatcher, View.OnKeyListener {
    private final String TAG = PinView.class.getSimpleName();

    View view;

    EditText editTextPinOne;
    EditText editTextPinTwo;
    EditText editTextPinThree;
    EditText editTextPinFour;

    OnPinValueEntered onPinValueEntered;

    public PinView(Context context) {
        super(context);
        initializeView(context);
    }

    public PinView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeView(context);
    }

    public PinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pin_view, this);
        editTextPinOne = view.findViewById(R.id.pinText_one);
        editTextPinTwo = view.findViewById(R.id.pinText_two);
        editTextPinThree = view.findViewById(R.id.pinText_three);
        editTextPinFour = view.findViewById(R.id.pinText_four);

        editTextPinOne.addTextChangedListener(this);
        editTextPinTwo.addTextChangedListener(this);
        editTextPinThree.addTextChangedListener(this);
        editTextPinFour.addTextChangedListener(this);

        editTextPinOne.setOnKeyListener(this);
        editTextPinTwo.setOnKeyListener(this);
        editTextPinThree.setOnKeyListener(this);
        editTextPinFour.setOnKeyListener(this);

        editTextPinOne.setFocusableInTouchMode(true);
        editTextPinOne.setFocusable(true);
        editTextPinOne.requestFocus();
    }

    public void setOnPinValueEntered(OnPinValueEntered onPinValueEntered) {
        this.onPinValueEntered = onPinValueEntered;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Noting to implement
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Noting to implement
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().length() > 0) {
            if (editable == editTextPinOne.getEditableText()) {
                Log.i(TAG, "editTextPinOne : " + editable.toString());
                setPinEnteredBackground(editTextPinOne);
                Log.i(TAG, "Move To (editTextPinTwo) From (editTextPinOne)");
                moveFocus(editTextPinTwo, editTextPinOne);
            } else if (editable == editTextPinTwo.getEditableText()) {
                Log.i(TAG, "editTextPinTwo : " + editable.toString());
                setPinEnteredBackground(editTextPinTwo);
                Log.i(TAG, "Move To (editTextPinThree) From (editTextPinTwo)");
                moveFocus(editTextPinThree, editTextPinTwo);
            } else if (editable == editTextPinThree.getEditableText()) {
                Log.i(TAG, "editTextPinThree : " + editable.toString());
                setPinEnteredBackground(editTextPinThree);
                Log.i(TAG, "Move To (editTextPinFour) From (editTextPinThree)");
                moveFocus(editTextPinFour, editTextPinThree);
            } else if (editable == editTextPinFour.getEditableText()) {
                Log.i(TAG, "editTextPinFour : " + editable.toString());
                setPinEnteredBackground(editTextPinFour);
                onPinValueEntered.onAllPinValueFilled(getPin());
            }
            Log.i(TAG, "getPin >> " + getPin());
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        Log.i(TAG, "getPin <<< " + getPin());
        if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction()==KeyEvent.ACTION_DOWN) {
            final int id = view.getId();
            switch (id) {
                case R.id.pinText_one:
                    setPinRemovedBackground(editTextPinOne);
                    break;
                case R.id.pinText_two:
                    if (editTextPinTwo.getText().toString().isEmpty()) {
                        Log.i(TAG, "editTextPinTwo length 0");
                        editTextPinOne.setText("");
                        setPinRemovedBackground(editTextPinOne);
                        moveFocus(editTextPinOne, editTextPinTwo);
                    } else {
                        Log.i(TAG, "editTextPinTwo : " + editTextPinTwo.getText().toString());
                        setPinRemovedBackground(editTextPinTwo);
                    }
                    break;
                case R.id.pinText_three:
                    if (editTextPinThree.getText().toString().isEmpty()) {
                        Log.i(TAG, "editTextPinThree length 0");
                        editTextPinTwo.setText("");
                        setPinRemovedBackground(editTextPinTwo);
                        moveFocus(editTextPinTwo, editTextPinThree);
                    } else {
                        Log.i(TAG, "editTextPinThree : " + editTextPinThree.getText().toString());
                        setPinRemovedBackground(editTextPinThree);
                    }
                    break;
                case R.id.pinText_four:
                    if (editTextPinFour.getText().toString().isEmpty()) {
                        Log.i(TAG, "editTextPinFour length 0");
                        editTextPinThree.setText("");
                        setPinRemovedBackground(editTextPinThree);
                        moveFocus(editTextPinThree, editTextPinFour);
                    } else {
                        Log.i(TAG, "editTextPinFour : " + editTextPinFour.getText().toString());
                        setPinRemovedBackground(editTextPinFour);
                    }
                    break;
            }
        }
//        else if (keyCode <= KeyEvent.KEYCODE_9 && keyCode >= KeyEvent.KEYCODE_0) {
//            if(!editTextPinOne.getText().toString().isEmpty() && editTextPinOne.hasFocus()){
//                editTextPinOne.setText(keyEvent.getCharacters());
//                setPinEnteredBackground(editTextPinOne);
//                moveFocus(editTextPinTwo, editTextPinOne);
//            } else if(!editTextPinTwo.getText().toString().isEmpty() && editTextPinTwo.hasFocus()){
//                editTextPinTwo.setText(keyEvent.getCharacters());
//                setPinEnteredBackground(editTextPinTwo);
//                moveFocus(editTextPinThree, editTextPinTwo);
//            } else if(!editTextPinThree.getText().toString().isEmpty() && editTextPinThree.hasFocus()){
//                editTextPinThree.setText(keyEvent.getCharacters());
//                setPinEnteredBackground(editTextPinThree);
//                moveFocus(editTextPinFour, editTextPinThree);
//                return onKey(editTextPinFour, keyCode, keyEvent);
//            }
//            else if(editTextPinFour.getText().toString().isEmpty()){
//                editTextPinFour.setText(keyEvent.getCharacters());
//                setPinEnteredBackground(editTextPinFour);
//                onPinValueEntered.onAllPinValueFilled(getPin());
//            }
//        }
        return false;
    }

    private String getPin() {
        return editTextPinOne.getText().toString() + editTextPinTwo.getText().toString() + editTextPinThree.getText().toString() + editTextPinFour.getText().toString();
    }

    private void moveFocus(EditText toView, EditText fromView) {
        toView.setFocusableInTouchMode(true);
        toView.setFocusable(true);
        toView.requestFocus();
        fromView.setFocusableInTouchMode(false);
        fromView.setFocusable(false);
    }

    private void setPinEnteredBackground(View vew) {
        setPinBackground(vew, ContextCompat.getDrawable(this.getContext(), R.drawable.pin_entered));
    }

    private void setPinRemovedBackground(View vew) {
        setPinBackground(vew, ContextCompat.getDrawable(this.getContext(), R.drawable.create_pin_background));
    }

    private void setPinBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    public interface OnPinValueEntered {
        void onAllPinValueFilled(String pin);
    }
}
