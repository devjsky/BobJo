package kr.co.devjsky.android.bobjo.ui.main.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_E
import kr.co.devjsky.android.bobjo.databinding.FragmentMainCoupleBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseFragment
import kr.co.devjsky.android.bobjo.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MainCoupleFragment : BaseFragment<FragmentMainCoupleBinding, MainViewModel>() {
    override val viewModel: MainViewModel by sharedViewModel()

    var bottomSheet: BottomSheetBehavior<ConstraintLayout>? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_couple
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.mainActivity!!.setTopMenu(false, "")
        setBindings()
        setObservers()
       // viewModel.calculateDDay()
        setBottomSheetLayout()
    }

    override fun setBindings() {

    }

    override fun setObservers() {

    }

    fun setBottomSheetLayout(){
        bottomSheet = BottomSheetBehavior.from(binding.layoutBottomSheet)
        bottomSheet!!.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheet!!.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                LOG_E("BOTTOMSHEET_LOG","BottomSheetCallback onStateChanged:${newState}")

                when(newState){
                    BottomSheetBehavior.STATE_EXPANDED ->{
                    }
                    BottomSheetBehavior.STATE_COLLAPSED->{
                    }
                    BottomSheetBehavior.STATE_DRAGGING-> {
                    }

                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {



            }

        })
    }
}