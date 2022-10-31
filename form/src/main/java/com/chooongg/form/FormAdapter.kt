package com.chooongg.form

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.form.bean.BaseForm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

internal class FormAdapter : RecyclerView.Adapter<FormViewHolder>() {

    private val adapterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private var data = mutableListOf<BaseForm>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormViewHolder {

    }

    override fun onBindViewHolder(holder: FormViewHolder, position: Int) {

    }

    override fun onBindViewHolder(
        holder: FormViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onViewRecycled(holder: FormViewHolder) {
        super.onViewRecycled(holder)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        adapterScope.cancel()
    }
}