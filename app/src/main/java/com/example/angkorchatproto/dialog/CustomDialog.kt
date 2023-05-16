package com.example.angkorchatproto.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.angkorchatproto.R
import kr.co.kdnavien.naviensmart.presentation.custom.DialogNegativeBtnListener
import kr.co.kdnavien.naviensmart.presentation.custom.DialogPositiveBtnListener

class CustomDialog private constructor() {
    private var dialog: Dialog? = null
    private var context: Context? = null
    private var titleText: SpannableStringBuilder? = null
    private var descText: SpannableStringBuilder? = null
    private var desc2Text: SpannableStringBuilder? = null
    private var positiveBtnText: SpannableStringBuilder? = null
    private var negativeBtnText: SpannableStringBuilder? = null
    private var positiveBtnListener: DialogPositiveBtnListener? = null
    private var negativeBtnListener: DialogNegativeBtnListener? = null
    private var onCancelListener: DialogInterface.OnCancelListener? = null
    private var cancelable = true

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var customDialog: CustomDialog? = null
        fun create(context: Context?): CustomDialog? {
            if (customDialog == null) {
                customDialog = CustomDialog()
            } else {
                customDialog!!.onCancelListener = null
                customDialog!!.positiveBtnListener = null
                customDialog!!.negativeBtnListener = null
            }
            customDialog?.context = context
            return customDialog
        }
    }

    /**
     * 다이얼로그를 제거한다.
     */
    fun finish() {
        if (dialog != null) {
            dialog!!.dismiss()
        }
    }

    /**
     * 현재 보여지고 있는 중인지 판단한다.
     * @return Boolean
     */
    val isView: Boolean
        get() = if (dialog == null) {
            false
        } else {
            dialog!!.isShowing
        }

    /**
     * 다이얼로그 타이틀을 설정한다.
     * @param title (String)
     * @return CustomDialog
     */
    fun setTitle(title: SpannableStringBuilder?): CustomDialog {
        this.titleText = title
        return this
    }

    /**
     * 다이얼로그 내용을 설정한다.
     * @param desc (String)
     * @return CustomDialog
     */
    fun setDesc(desc: SpannableStringBuilder?): CustomDialog {
        this.descText = desc
        return this
    }

    /**
     * 다이얼로그 내용 (2)을 설정한다.
     * @param desc (String)
     * @return CustomDialog
     */
    fun setDesc2(desc: SpannableStringBuilder?): CustomDialog {
        this.desc2Text = desc
        return this
    }

    /**
     * 다이얼로그 확인 버튼 (설정 버튼) 이름을 설정한다.
     * @param text (String)
     * @return CustomDialog
     */
    fun setPositiveButtonText(text: SpannableStringBuilder?): CustomDialog {
        this.positiveBtnText = text
        return this
    }

    /**
     * 다이얼로그 취소 버튼 이름을 설정한다.
     * @param text (String)
     * @return KDCustomDialog
     */
    fun setNegativeButtonText(text: SpannableStringBuilder?): CustomDialog {
        this.negativeBtnText = text
        return this
    }

    /**
     * 다이얼로그 확인 버튼 (설정 버튼)에 대한 커스텀 리스너를 구현한다.
     * @param positiveBtnListener (DialogPositiveBtnListener)
     * @return CustomDialog
     */
    fun setPositiveBtnListener(positiveBtnListener: DialogPositiveBtnListener?): CustomDialog {
        this.positiveBtnListener = positiveBtnListener
        return this
    }

    /**
     * 다이얼로그 취소 버튼에 대한 커스텀 리스너를 구현한다.
     * @param negativeBtnListener (DialogNegativeBtnListener)
     * @return CustomDialog
     */
    fun setNegativeBtnListener(negativeBtnListener: DialogNegativeBtnListener?): CustomDialog {
        this.negativeBtnListener = negativeBtnListener
        return this
    }

    /**
     * 다이얼로그 Cancel될 때 이벤트 감지를 위한 리스너를 구현한다.
     * @param onCancelListener (DialogInterface.OnCancelListener)
     * @return KDCustomDialog
     */
    fun setOnCancelListener(onCancelListener: DialogInterface.OnCancelListener?): CustomDialog {
        this.onCancelListener = onCancelListener
        return this
    }

    /**
     * 다이얼로그 취소 가능 여부를 설정한다. (ex. false로 설정하면 백 버튼 동작으로 없어지지 않음)
     * @param cancelable (Boolean)
     * @return KDCustomDialog
     */
    fun setCancelable(cancelable: Boolean): CustomDialog {
        this.cancelable = cancelable
        return this
    }

    /**
     * 다이얼로그에 보여질 버튼을 1개만 보여준다.
     */
    fun showOneButton() {
        try {
            if (dialog?.isShowing == true) {
                dialog?.dismiss()
            }

            if (context != null) {
                dialog = Dialog(context!!, R.style.CustomDialog)
                val view: View =
                    LayoutInflater.from(context).inflate(R.layout.dialog_one_button, null)
                dialog?.setContentView(view)

                if (titleText != null) {
                    val title = (view.findViewById<View>(R.id.title) as TextView)
                    title.visibility = View.VISIBLE
                    title.text = titleText
                }

                if (descText != null) {
                    val desc = (view.findViewById<View>(R.id.desc1) as TextView)
                    desc.visibility = View.VISIBLE
                    desc.text = descText
                }

                if (desc2Text != null) {
                    val desc2 = (view.findViewById<View>(R.id.desc2) as TextView)
                    desc2.visibility = View.VISIBLE
                    desc2.text = desc2Text
                }

                val positiveButton = view.findViewById<View>(R.id.positiveButton) as Button
                positiveButton.text = positiveBtnText
                positiveButton.setOnClickListener {
                    dialog!!.dismiss()
                    if (positiveBtnListener != null) {
                        positiveBtnListener?.confirm(0)
                    }
                }
                dialog!!.setOnCancelListener(onCancelListener)
                dialog!!.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showTwoButton() {
        try {
            dialog = Dialog(context!!, R.style.CustomDialog)
            dialog?.setCancelable(cancelable)
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.dialog_two_button, null)
            dialog?.setContentView(view)

            if (titleText != null) {
                val title = (view.findViewById<View>(R.id.title) as TextView)
                title.visibility = View.VISIBLE
                title.text = titleText
            }

            if (descText != null) {
                val desc = (view.findViewById<View>(R.id.desc1) as TextView)
                desc.visibility = View.VISIBLE
                desc.text = descText
            }

            if (desc2Text != null) {
                val desc2 = (view.findViewById<View>(R.id.desc2) as TextView)
                desc2.visibility = View.VISIBLE
                desc2.text = desc2Text
            }

            val positiveButton =
                view.findViewById<View>(R.id.positiveButton) as Button
            positiveButton.text = positiveBtnText
            positiveButton.setOnClickListener {
                dialog!!.dismiss()
                if (positiveBtnListener != null) {
                    positiveBtnListener?.confirm(0)
                }
            }
            val negativeButton =
                view.findViewById<View>(R.id.negativeButton) as Button
            negativeButton.text = negativeBtnText
            negativeButton.setOnClickListener {
                dialog!!.dismiss()
                if (negativeBtnListener != null) {
                    negativeBtnListener?.cancel(0)
                }
            }
            dialog!!.setOnCancelListener(onCancelListener)
            dialog!!.show()
        } catch (e: Exception) {
        }
    }
//다이얼로그 예제
//        val title = "angkorChat@email.com"
//        val spannable = SpannableStringBuilder(title)
//        val color = getColor(R.color.mainYellow)
//        spannable.setSpan(
//            ForegroundColorSpan(color),
//            0,
//            title.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        CustomDialog.create(this)
//            ?.setTitle(spannable)
//            ?.setDesc(SpannableStringBuilder("Your email has been verified\nsuccessfully!"))
//            ?.setDesc2(SpannableStringBuilder("lakfjlksdajgklajsgkljsadlkgjlksagjklsajgkl"))
//            ?.setCancelable(true)
//            ?.setPositiveButtonText(SpannableStringBuilder("Close"))
//            ?.setNegativeButtonText(SpannableStringBuilder("Exit"))
//            ?.setPositiveBtnListener(object: DialogPositiveBtnListener {
//                    override fun confirm(division: Int) {
//                    }
//                })
//            ?.setNegativeBtnListener(object: DialogNegativeBtnListener {
//                override fun cancel(division: Int) {
//                }
//            })
//            ?.showTwoButton()

//        val title = "angkorChat@email.com"
//        val spannable = SpannableStringBuilder(title)
//        val color = getColor(R.color.mainYellow)
//        spannable.setSpan(
//            ForegroundColorSpan(color),
//            0,
//            title.length,
//            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        CustomDialog.create(this)
//            ?.setTitle(spannable)
//            ?.setDesc(SpannableStringBuilder("Your email has been verified\nsuccessfully!"))
//            ?.setCancelable(true)
//            ?.setPositiveButtonText(SpannableStringBuilder("Close"))
//            ?.setPositiveBtnListener(object: DialogPositiveBtnListener {
//                    override fun confirm(division: Int) {
//                    }
//                })
//            ?.showOneButton()
}