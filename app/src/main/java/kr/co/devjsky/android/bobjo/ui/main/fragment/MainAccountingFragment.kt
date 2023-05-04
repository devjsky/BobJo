package kr.co.devjsky.android.bobjo.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.databinding.FragmentMainMyPageBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseFragment
import kr.co.devjsky.android.bobjo.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainAccountingFragment : BaseFragment<FragmentMainMyPageBinding, MainViewModel>() {
    override val viewModel: MainViewModel by sharedViewModel()


    override fun getLayoutId(): Int {
        return R.layout.fragment_main_my_page
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
        viewModel.mainActivity!!.setTopMenu(true, "마이페이지")
        setBindings()
        setObservers()


    }

    override fun setBindings() {
        binding.btnAaa.setOnClickListener { viewModel.testDataLiveData.postValue("mypage") }
    }

    override fun setObservers() {

    }
}