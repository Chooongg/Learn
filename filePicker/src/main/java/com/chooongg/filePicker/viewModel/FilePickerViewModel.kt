package com.chooongg.filePicker.viewModel

import androidx.lifecycle.ViewModel
import com.chooongg.basic.ext.withMain
import com.chooongg.filePicker.FilePickerSortByName
import com.chooongg.filePicker.model.PickerFileFilter
import java.io.File
import java.util.*

class FilePickerViewModel : ViewModel() {

//    /**
//     * 获取文件夹下的子文件和目录
//     */
//    suspend fun findCurrentFolder(queryPath: String):MutableList<File> {
//        var fileList: List<File> = ArrayList()
//        val file = File(queryPath)
//        val files = file.listFiles(
//            PickerFileFilter(
//                FilePickerConstant.filterTypes, FilePickerConstant.filterTypeMode
//            )
//        )
//        if (!files.isNullOrEmpty()) fileList = files.toList()
//        fileList = fileList.sortedWith(FilePickerSortByName())
//        return FileItem.getPickerFileList(fileList)
//    }

    /**
     * 查找文件夹下的所有文件
     */
    suspend fun findCurrentFolderAllFile(paths: List<String>, block: (MutableList<File>) -> Unit) {
//        viewModelScope.launchIO {
//            val fileList: ArrayList<File> = ArrayList()
//            val files: Queue<File> = LinkedList()
//            files.addAll(List(paths.size) { File(paths[it]) })
//            while (!files.isEmpty()) {
//                val file = files.remove()
//                if (file.isDirectory) {
//                    val listFiles = file.listFiles(
//                        PickerFileFilter(
//                            FilePickerConstant.filterTypes, FilePickerConstant.filterTypeMode
//                        )
//                    )
//                    if (!listFiles.isNullOrEmpty()) files.addAll(listFiles.toList())
//                } else if (file.exists()) {
//                    fileList.add(file)
//                }
//            }
//            val sortList = fileList.sortedWith(FilePickerSortByTime())
//            val returnList = FileItem.getPickerFileList(sortList)
//            withMain { block.invoke(returnList) }
//        }
    }

}