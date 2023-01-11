package com.chooongg.adapter.listener;

import android.view.View;

import androidx.annotation.NonNull;

import com.chooongg.adapter.LearnAdapter;

public interface OnItemClickListener {
    void onItemClick(@NonNull LearnAdapter<?, ?> adapter, @NonNull View view, int position);
}