package com.chooongg.adapter.listener

interface DiffCallback<T> {
    fun areItemsTheSame(oldData: T, newData: T): Boolean
    fun areContentsTheSame(oldData: T, newData: T): Boolean
}