package com.chooongg.mediaPicker.provider

import android.net.Uri
import android.provider.MediaStore

abstract class IMediaProvider {

    protected val queryUri: Uri = MediaStore.Files.getContentUri("external")

}