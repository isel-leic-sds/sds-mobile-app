package com.isel.ps.sds.view.clinicalHistory.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.isel.ps.sds.view.menu.MenuActivity

class HistoryFinalFragment:Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(activity, MenuActivity::class.java)
        startActivity(intent)
    }

}