package kr.co.devjsky.android.bobjo.ui.schedule.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import kr.co.devjsky.android.bobjo.databinding.DialogScheduleSubMenuBinding
import kr.co.devjsky.android.bobjo.ui.base.DialogViewModel

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ScheduleSubMenuDialog() : DialogFragment() {
    companion object {
        fun newInstance() = ScheduleSubMenuDialog()
    }

    private val _viewModel: DialogViewModel by sharedViewModel()
    val size = Point()

    interface OnBtnClickEventListener {
        fun addScheduleMultiClick()
    }
    lateinit var onBtnClickEventListener: OnBtnClickEventListener

    fun setOnBtnClickListener(listener: OnBtnClickEventListener){
        onBtnClickEventListener = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogScheduleSubMenuBinding.inflate(inflater, container, false)
            .apply {
                this.lifecycleOwner = this@ScheduleSubMenuDialog
                this.viewModel = _viewModel
            }
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
//        setCancelable(true)


        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay

        display.getSize(size)

        binding.ivClose.setOnClickListener {
            dismiss()
        }
        binding.btnAddScheduleMulti.setOnClickListener {
            onBtnClickEventListener.addScheduleMultiClick()
            dismiss()
        }






        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




    }

    override fun onResume() {
        super.onResume()
        val window: Window? = dialog?.window
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //   window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        val wlp: WindowManager.LayoutParams? = window?.attributes
        wlp?.width = ViewGroup.LayoutParams.WRAP_CONTENT
        wlp?.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window?.attributes = wlp

    }

    fun setData(){

    }


}