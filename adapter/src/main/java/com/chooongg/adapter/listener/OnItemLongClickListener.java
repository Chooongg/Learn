package com.chooongg.adapter.listener;

import android.view.View;

import androidx.annotation.NonNull;

import com.chooongg.adapter.LearnAdapter;

public interface OnItemLongClickListener {
    boolean onItemLongClick(@NonNull LearnAdapter<?, ?> adapter, @NonNull View view, int position);
}
