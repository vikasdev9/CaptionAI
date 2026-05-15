package com.example.captionai.ui.screens.profile

import android.net.Uri

sealed class ProfileEvents {
    data class UpdateProfile(val name: String, val handle: String) : ProfileEvents()
    data class UploadImage(val uri: Uri) : ProfileEvents()
    object Logout : ProfileEvents()
    object LoadProfile : ProfileEvents()
}
