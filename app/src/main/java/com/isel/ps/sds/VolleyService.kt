package com.isel.ps.sds

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

object VolleyService {
    private lateinit var queue: RequestQueue

    public fun init(context: Context) {
        // Instantiate the RequestQueue.

        queue = Volley.newRequestQueue(context)
    }

    private fun request(method: Int, url: String, cb: (String) -> Unit) {
        val req = StringRequest(
            method, url,
            onResponse(cb),
            onError()
        )


        queue.add(req)
    }

    private fun onResponse(cb: (String) -> Unit): Response.Listener<String> = Response.Listener { str -> cb(str) }

    private fun onError(): Response.ErrorListener = Response.ErrorListener { err -> throw Exception(err.message)
        Log.i("tag", "There was a error, pls verify your connectivty and try again " )
        //todo mandar um toast
    }


    fun getQuiz(url: String, cb: (String) -> Unit) {
        request(Request.Method.GET,url,cb)
    }
}
