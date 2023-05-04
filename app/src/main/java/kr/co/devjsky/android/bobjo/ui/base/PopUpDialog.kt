package kr.co.devjsky.android.bobjo.ui.base

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.devjsky.android.bobjo.databinding.DialogPopupBinding
import kr.co.devjsky.android.bobjo.di.repository.ApiRepo
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.di.repository.UserRepo
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PopUpDialog() : DialogFragment() {
    val apiRepo : ApiRepo by inject()
    val userRepo : UserRepo by inject()
    val dataRepo : DataRepo by inject()

    companion object {
        fun newInstance() = PopUpDialog()
    }

    private val _viewModel: DialogViewModel by sharedViewModel()
    val size = Point()

    var title: String? = ""
    var msg: String? = ""
    var leftBtnText: String? = ""
    var centerBtnText: String? = ""
    var rightBtnText: String? = ""


    var isToast:Boolean = false



    interface OnBtnClickEventListener {
        fun onBtnLeftClick(view: View)
        fun onBtnCenterClick(view: View)
        fun onBtnRightClick(view: View)
    }
    lateinit var onBtnClickEventListener: OnBtnClickEventListener

    fun setOnBtnClickListener(listener: OnBtnClickEventListener){
        onBtnClickEventListener = listener
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogPopupBinding.inflate(inflater, container, false)
            .apply {
                this.lifecycleOwner = this@PopUpDialog
                this.viewModel = _viewModel
            }
        // 뒤로가기 버튼, 빈 화면 터치를 통해 dialog가 사라지지 않도록
//        setCancelable(false)

        if(isToast){
            binding.btnClose.visibility = View.INVISIBLE
        }else{
            binding.btnClose.visibility = View.VISIBLE
        }
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay

        display.getSize(size)

       binding.tvTitle.text = title
        if(msg == ""){
            binding.tvMsg.visibility = View.GONE
        }else{
            binding.tvMsg.visibility = View.VISIBLE
            binding.tvMsg.text = msg + ""
        }


        if(leftBtnText == ""){
            binding.btnLeft.visibility = View.GONE
        }else{
            binding.btnLeft.visibility = View.VISIBLE
            binding.btnLeft.text = leftBtnText + ""
            binding.btnLeft.setOnClickListener {
                onBtnClickEventListener.onBtnLeftClick(it)
            }
        }
        if(centerBtnText == ""){
            binding.btnCenter.visibility = View.GONE
        }else{
            binding.btnCenter.visibility = View.VISIBLE
            binding.btnCenter.text = centerBtnText + ""
            binding.btnCenter.setOnClickListener {
                onBtnClickEventListener.onBtnCenterClick(it)
            }
        }
        if(rightBtnText == ""){
            binding.btnRight.visibility = View.GONE
        }else{
            binding.btnRight.visibility = View.VISIBLE
            binding.btnRight.text = rightBtnText + ""
            binding.btnRight.setOnClickListener {
                onBtnClickEventListener.onBtnRightClick(it)
            }
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }







        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if(isToast){

            CoroutineScope(Dispatchers.Main).launch {

                delay(1500)

                dismiss()


            }
        }


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

    fun setData(_isToast:Boolean, _title: String, _msg: String, _leftBtnText: String, _centerBtnText: String, _rightBtnText: String) {
        isToast = _isToast
        title = _title
        msg = _msg
        leftBtnText = _leftBtnText
        centerBtnText = _centerBtnText
        rightBtnText = _rightBtnText


    }
}