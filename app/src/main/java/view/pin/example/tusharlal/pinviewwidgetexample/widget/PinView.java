package view.pin.example.tusharlal.pinviewwidgetexample.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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

    private View view;

    private EditText editTextPinOne;
    private EditText editTextPinTwo;
    private EditText editTextPinThree;
    private EditText editTextPinFour;

    private OnPinValueEntered onPinValueEntered;

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

    /**
     * To initialize all the internal components of PinView.
     *
     * @param context
     */
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

    /**
     * Sets the listener for all values have been entered.
     *
     * @param onPinValueEntered
     */
    public void setOnPinValueEntered(OnPinValueEntered onPinValueEntered) {
        this.onPinValueEntered = onPinValueEntered;
    }

    /**
     * Method to clear all the PIN values
     */
    public void clearPin() {
        editTextPinOne.setText("");
        editTextPinTwo.setText("");
        editTextPinThree.setText("");
        editTextPinFour.setText("");
        setPinRemovedBackground(editTextPinOne);
        setPinRemovedBackground(editTextPinTwo);
        setPinRemovedBackground(editTextPinThree);
        setPinRemovedBackground(editTextPinFour);
        editTextPinFour.setFocusableInTouchMode(false);
        editTextPinFour.setFocusable(false);
        editTextPinThree.setFocusableInTouchMode(false);
        editTextPinThree.setFocusable(false);
        editTextPinTwo.setFocusableInTouchMode(false);
        editTextPinTwo.setFocusable(false);
        editTextPinOne.setFocusableInTouchMode(true);
        editTextPinOne.setFocusable(true);
        editTextPinOne.requestFocus();
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
                setPinEnteredBackground(editTextPinOne);
                moveFocus(editTextPinTwo, editTextPinOne);
                onPinValueEntered.allValuesEntered(false);
            } else if (editable == editTextPinTwo.getEditableText()) {
                setPinEnteredBackground(editTextPinTwo);
                moveFocus(editTextPinThree, editTextPinTwo);
                onPinValueEntered.allValuesEntered(false);
            } else if (editable == editTextPinThree.getEditableText()) {
                setPinEnteredBackground(editTextPinThree);
                moveFocus(editTextPinFour, editTextPinThree);
                onPinValueEntered.allValuesEntered(false);
            } else if (editable == editTextPinFour.getEditableText()) {
                setPinEnteredBackground(editTextPinFour);
                onPinValueEntered.onAllPinValueFilled(getPin());
                onPinValueEntered.allValuesEntered(true);
            }
        }
    }

    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_DEL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = view.getId();
            switch (id) {
                case R.id.pinText_one:
                    setPinRemovedBackground(editTextPinOne);
                    break;
                case R.id.pinText_two:
                    if (editTextPinTwo.getText().toString().isEmpty()) {
                        removeText(editTextPinOne);
                        moveFocus(editTextPinOne, editTextPinTwo);
                    } else {
                        setPinRemovedBackground(editTextPinTwo);
                    }
                    break;
                case R.id.pinText_three:
                    if (editTextPinThree.getText().toString().isEmpty()) {
                        removeText(editTextPinTwo);
                        moveFocus(editTextPinTwo, editTextPinThree);
                    } else {
                        setPinRemovedBackground(editTextPinThree);
                    }
                    break;
                case R.id.pinText_four:
                    if (editTextPinFour.getText().toString().isEmpty()) {
                        removeText(editTextPinThree);
                        moveFocus(editTextPinThree, editTextPinFour);
                    } else {
                        setPinRemovedBackground(editTextPinFour);
                    }
                    break;
            }
        }
        return false;
    }

    /**
     * This method returns the entered PIN value.
     *
     * @return Pin value
     */
    private String getPin() {
        return editTextPinOne.getText().toString() + editTextPinTwo.getText().toString() + editTextPinThree.getText().toString() + editTextPinFour.getText().toString();
    }

    /**
     * Method to remove a PIN value.
     *
     * @param editText
     */
    private void removeText(EditText editText) {
        editText.setText("");
        setPinRemovedBackground(editText);
    }

    /**
     * Method to shift focus between PIN views
     *
     * @param toView View to which focus needs to be shifted.
     * @param fromView View from which the focus needs to be shifted.
     */
    private void moveFocus(EditText toView, EditText fromView) {
        toView.setFocusableInTouchMode(true);
        toView.setFocusable(true);
        toView.requestFocus();
        fromView.setFocusableInTouchMode(false);
        fromView.setFocusable(false);
    }

    /**
     * Method to set background when value is entered for view.
     *
     * @param vew whose background needs to be changed
     */
    private void setPinEnteredBackground(View vew) {
        setPinBackground(vew, ContextCompat.getDrawable(this.getContext(), R.drawable.pin_entered));
    }

    /**
     * Method to set background when value is removed for view.
     *
     * @param vew whose background needs to be changed
     */
    private void setPinRemovedBackground(View vew) {
        setPinBackground(vew, ContextCompat.getDrawable(this.getContext(), R.drawable.create_pin_background));
    }

    /**
     * Method to set background on the bases of OS version code.
     *
     * @param view
     * @param background
     */
    private void setPinBackground(View view, Drawable background) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    /**
     * Interface to receive event that all
     * PIN values have been entered.
     */
    public interface OnPinValueEntered {
        void onAllPinValueFilled(String pin);

        void allValuesEntered(boolean flag);
    }
}
