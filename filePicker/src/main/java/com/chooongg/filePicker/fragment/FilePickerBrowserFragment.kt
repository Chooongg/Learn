package com.chooongg.filePicker.fragment

import androidx.fragment.app.activityViewModels
import com.chooongg.core.fragment.BasicBindingFragment
import com.chooongg.filePicker.viewModel.FilePickerViewModel
import com.chooongg.filePicker.databinding.LearnFragmentFilePickerBrowserBinding

class FilePickerBrowserFragment : BasicBindingFragment<LearnFragmentFilePickerBrowserBinding>() {

    val model by activityViewModels<FilePickerViewModel>()


}