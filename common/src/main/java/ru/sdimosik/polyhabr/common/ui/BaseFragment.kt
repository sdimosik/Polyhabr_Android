package ru.sdimosik.polyhabr.common.ui

import android.view.Gravity
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.hjq.toast.ToastParams
import com.hjq.toast.Toaster
import com.hjq.toast.style.CustomToastStyle
import ru.sdimosik.polyhabr.common.R

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    protected fun switchToastStyleToError(text: String) {
        val params = ToastParams()
        params.text = text
        params.style = CustomToastStyle(R.layout.toast_error, Gravity.TOP, 0, 30)
        Toaster.show(params)
    }

    protected fun switchToastStyleToSuccess(text: String) {
        val params = ToastParams()
        params.text = text
        params.style = CustomToastStyle(R.layout.toast_success, Gravity.TOP, 0, 30)
        Toaster.show(params)
    }
}