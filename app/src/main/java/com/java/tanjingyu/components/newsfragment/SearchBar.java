package com.java.tanjingyu.components.newsfragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.java.tanjingyu.R;
import com.java.tanjingyu.components.news.RequestForm;

// 弹出的搜索栏
public class SearchBar {
    private View view;
    private PopupWindow window;
    private TextInputLayout inputKeyword, inputStartDate, inputEndDate;
    private Spinner spinner;
    private OnSearchClickListener listener;

    public PopupWindow create(Context context, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.search_bar, parent, false);
        window = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setAnimationStyle(R.style.search_animation);

        inputKeyword = view.findViewById(R.id.input_keyword);
        inputStartDate = view.findViewById(R.id.input_start_date);
        inputEndDate = view.findViewById(R.id.input_end_date);
        setTextInputColor(inputKeyword);
        setTextInputColor(inputStartDate);
        setTextInputColor(inputEndDate);

        // 修改下拉菜单颜色
        spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                textView.setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        Button buttonClear = view.findViewById(R.id.button_clear);
        Button buttonSearch = view.findViewById(R.id.button_search);
        buttonClear.setOnClickListener(_view -> onClearClick());
        buttonSearch.setOnClickListener(_view -> onSearchClick());
        return window;
    }

    private static final int[][] STATES = {
            { android.R.attr.state_focused },
            {}
    };

    private static final int[] COLORS = {
            Color.CYAN, Color.WHITE
    };

    // 修改输入框下划线颜色
    private static void setTextInputColor(TextInputLayout layout) {
        layout.setBoxStrokeColorStateList(new ColorStateList(STATES, COLORS));
    }

    // "清除" 按钮
    @SuppressWarnings("all")
    private void onClearClick() {
        inputKeyword.getEditText().setText("");
        inputStartDate.getEditText().setText("");
        inputEndDate.getEditText().setText("");
        spinner.setSelection(0);
        view.clearFocus();
    }

    public void setOnSearchClickListener(OnSearchClickListener listener) {
        this.listener = listener;
    }

    public interface OnSearchClickListener {
        void onSearchClick(RequestForm requestForm);
    }

    @SuppressWarnings("all")
    private RequestForm getRequestForm() {
        return new RequestForm(
                spinner.getSelectedItem().toString(),
                inputKeyword.getEditText().getText().toString(),
                inputStartDate.getEditText().getText().toString(),
                inputEndDate.getEditText().getText().toString()
        );
    }

    // "搜索" 按钮
    private void onSearchClick() {
        window.dismiss();
        if(listener != null) {
            listener.onSearchClick(getRequestForm());
        }
    }
}