package com.chooongg.adapter.listener;

import android.view.View;

import androidx.annotation.NonNull;

import com.chooongg.adapter.LearnAdapter;

public interface OnItemChildLongClickListener {
    boolean onItemChildLongClick(@NonNull LearnAdapter<?, ?> adapter, @NonNull View view, int id, int position);
}
