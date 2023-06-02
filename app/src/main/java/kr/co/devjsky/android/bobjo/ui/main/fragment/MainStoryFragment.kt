package kr.co.devjsky.android.bobjo.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kr.co.devjsky.android.bobjo.R
import kr.co.devjsky.android.bobjo.api.ApiCallback
import kr.co.devjsky.android.bobjo.data.Constants.Companion.LOG_D
import kr.co.devjsky.android.bobjo.data.model.remote.IFMainStory
import kr.co.devjsky.android.bobjo.databinding.FragmentMainStoryBinding
import kr.co.devjsky.android.bobjo.ui.base.BaseFragment
import kr.co.devjsky.android.bobjo.ui.main.adapter.MainStoryListAdapter
import kr.co.devjsky.android.bobjo.ui.main.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainStoryFragment : BaseFragment<FragmentMainStoryBinding, MainViewModel>() {
    override val viewModel: MainViewModel by sharedViewModel()


    override fun getLayoutId(): Int {
        return R.layout.fragment_main_story
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)


    }
    private val tabTitleArray = arrayOf(
        "새소식",
        "내 스토리"

    )
    lateinit var tabLayout: TabLayout

    var mainStoryListAdapter: MainStoryListAdapter? = null

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
        viewModel.mainActivity!!.setTopMenu(false, "스토리")
        setTab()
        mainStoryListAdapter = MainStoryListAdapter()
        binding.rvList.adapter = mainStoryListAdapter
        binding.rvList.layoutManager = LinearLayoutManager(context)
        setBindings()
        setObservers()


        viewModel.refreshMainStoryStateLiveData.value = true
    }

    fun setTab(){
        tabLayout = binding.layoutTab
        for (title in tabTitleArray) {
            tabLayout.addTab(tabLayout.newTab().setText(title))
        }
        binding.layoutTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                LOG_D(TAG, "tab.position :: " + tab?.position)

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
    override fun setBindings() {

    }

    override fun setObservers() {


        viewModel.refreshMainStoryStateLiveData.observe(this, Observer {
            if(it){
                mainStoryListAdapter?.clearData()
                viewModel.refreshMainStoryStateLiveData.value = false
                apiRepo.getMainStory(object : ApiCallback{
                    override fun <T> result(isSuccess: Boolean, code: Int, msg: String, data: T?) {
                        if(isSuccess){
                            val resultData = data as IFMainStory
                            if(resultData.story_item_info != null){
                                mainStoryListAdapter?.addData(resultData.story_item_info!!)

                            }

                        }

                    }
                })
            }
        })
    }
}