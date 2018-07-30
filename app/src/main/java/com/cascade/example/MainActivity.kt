package com.cascade.example

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.cascade.toast.BlurToast

class MainActivity : Activity(), View.OnClickListener {
    //region LIFECYCLE METHODS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        initializeViews()
    }
    //endregion

    //region FLOW METHODS
    override fun onClick(view: View) {
        val viewId = view.id
        when (viewId) {
            R.id.b_show_toast -> showToast()
        }
    }
    //endregion

    //region INITIALIZATION METHODS
    private fun initializeViews() {
        val buttonShowToast = findViewById<Button>(R.id.b_show_toast)
        buttonShowToast.setOnClickListener(this)
    }
    //endregion

    //region STORY METHODS
    private fun showToast() {
        BlurToast.buildToast(this, "I am a blur toast with default blur radius!").show()
        BlurToast.buildToast(this, "I am a blur toast with custom blur radius!", 4f).show()
    }
    //endregion
}