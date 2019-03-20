package com.isel.ps.sds.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders


abstract class BaseActivity<VM: BaseViewModel> : AppCompatActivity() {

    abstract fun defineViewModel() : Class<VM>

    @LayoutRes
    abstract fun layoutToInflate(): Int

    val viewModel: VM by lazy {
        ViewModelProviders
            .of(this)
            .get(defineViewModel())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutToInflate())

        observeLoadingState()
        observeErrors()

        doOnCreate(savedInstanceState)
    }

    abstract fun doOnCreate(savedInstanceState: Bundle?)

    /**
     *  Loading logic
     */
    private fun observeLoadingState() {
        viewModel.provideLoadingState().observe(this, Observer {
            /** TODO : loading  */
        })
    }

    /**
     *  Error handling
     */
    private fun observeErrors() {
        viewModel.provideErrors().observe(this, Observer {

//            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        })
    }
}