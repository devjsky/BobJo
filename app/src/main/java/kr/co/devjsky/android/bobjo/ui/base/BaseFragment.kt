package kr.co.devjsky.android.bobjo.ui.base
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kr.co.devjsky.android.bobjo.di.repository.ApiRepo
import kr.co.devjsky.android.bobjo.di.repository.DataRepo
import kr.co.devjsky.android.bobjo.di.repository.UserRepo
import kr.co.devjsky.android.bobjo.ui.base.BaseActivity
import kr.co.devjsky.android.bobjo.ui.base.BaseViewModel
import org.koin.android.ext.android.inject


abstract class BaseFragment<B : ViewDataBinding, M : BaseViewModel> : Fragment() {

    val TAG = this.javaClass.simpleName + "_LOG"

    val userRepo : UserRepo by inject()
    val apiRepo : ApiRepo by inject()
    val dataRepo : DataRepo by inject()
    lateinit var binding: B
    abstract val viewModel: M


    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun setBindings(){}
    open fun setObservers(){}

    lateinit var context: BaseActivity<*, *>

    override fun onAttach(mContext: Context) {
        super.onAttach(mContext)

        context = mContext as BaseActivity<*, *>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)

        // live data를 사용하기 위해 해줘야함
        binding.lifecycleOwner = this@BaseFragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    open fun setLoading(value: Boolean){
    }

}