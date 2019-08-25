package com.isel.ps.sds.view.profile

import android.app.Application
import com.isel.ps.sds.repository
import com.isel.ps.sds.view.BaseViewModel

class ProfileViewModel(app: Application) : BaseViewModel(app) {

    val person = app.repository.person
}
