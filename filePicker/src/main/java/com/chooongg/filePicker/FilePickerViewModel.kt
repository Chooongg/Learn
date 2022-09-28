package com.chooongg.filePicker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chooongg.basic.ext.launchIO
import com.chooongg.basic.ext.withMain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.util.*

class FilePickerViewModel : ViewModel() {

    val currentFolderList: StateFlow<MutableList<File>?> = MutableStateFlow(null)

    /**
     * 获取文件夹下的子文件和目录
     */
    fun findCurrentFolder(queryPath: String) {
        viewModelScope.launchIO {
            var fileList: List<File> = ArrayList()
            val file = File(queryPath)
            val files = file.listFiles(
                PickerFileFilter(
                    FilePickerConstant.filterTypes, FilePickerConstant.filterTypeMode
                )
            )
            if (!files.isNullOrEmpty()) fileList = files.toList()
            fileList = fileList.sortedWith(FilePickerUtils.SortByName())
            val returnList = FileItem.getPickerFileList(fileList)
            withMain {
                onFindFileListListener?.onFindFileList(queryPath, returnList)
            }
        }
    }

    /**
     * 查找文件夹下的所有文件
     */
    fun findCurrentFolderAllFile(paths: List<String>) {
        viewModelScope.launchIO {
            val fileList: ArrayList<File> = ArrayList()
            val files: Queue<File> = LinkedList()
            files.addAll(List(paths.size) { File(paths[it]) })
            while (!files.isEmpty()) {
                val file = files.remove()
                if (file.isDirectory) {
                    val listFiles = file.listFiles(
                        PickerFileFilter(
                            FilePickerConstant.filterTypes, FilePickerConstant.filterTypeMode
                        )
                    )
                    if (!listFiles.isNullOrEmpty()) files.addAll(listFiles.toList())
                } else if (file.exists()) {
                    fileList.add(file)
                }
            }
            val sortList = fileList.sortedWith(FilePickerUtils.SortByTime())
            val returnList = FileItem.getPickerFileList(sortList)
            withMain {
                onFindFileListListener?.onFindFileList("", returnList)
            }
        }
    }

}