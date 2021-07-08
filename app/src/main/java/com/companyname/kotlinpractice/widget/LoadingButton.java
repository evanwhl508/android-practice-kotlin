package com.companyname.kotlinpractice.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.companyname.kotlinpractice.R;

public class LoadingButton extends ConstraintLayout {
    private ProgressBar mProgressBar;
    private TextView mTextView;

    @Nullable
    private View.OnClickListener mLisnter;

    public LoadingButton(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public LoadingButton(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public LoadingButton(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public LoadingButton(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        View v = inflate(context, R.layout.button, this);
        mTextView = v.findViewById(R.id.button);
        mProgressBar = v.findViewById(R.id.progressbar);

        mProgressBar.setBackgroundColor(getResources().getColor(R.color.alert_red));
        mProgressBar.setProgressTintList(ColorStateList.valueOf(Color.WHITE));

        mTextView.setTextSize(20);
        mTextView.setTextColor(getResources().getColor(R.color.alert_red));

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.LoadingButton,
                    0,0);
            try {
                String text = a.getString(R.styleable.LoadingButton_buttonText);
                setButtonText(text);
            } finally {
                a.recycle();
            }
        }

        setOnClickListener(view -> {
            mProgressBar.setVisibility(VISIBLE);
            setEnabled(false);

            if (mLisnter != null) {
                mLisnter.onClick(view);
            }
        });
    }

    public void setButtonText(String text) {
        mTextView.setText(text);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mLisnter = listener;
    }

    public void finishLoading() {
        mProgressBar.setVisibility(GONE);
        setEnabled(true);
    }
}
