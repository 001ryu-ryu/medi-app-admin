package com.iftikar.mediadmin.presentation.screens.users_screen

sealed class ApproveUserEvent {
    data class ShowMessage(val message: String) : ApproveUserEvent()
}
