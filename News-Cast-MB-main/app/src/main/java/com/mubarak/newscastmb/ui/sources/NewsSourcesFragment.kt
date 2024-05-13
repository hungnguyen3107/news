package com.mubarak.newscastmb.ui.sources

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mubarak.newscastmb.R
import com.mubarak.newscastmb.databinding.FragmentNewsSourcesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsSourcesFragment : Fragment() {

    private lateinit var binding: FragmentNewsSourcesBinding
    private lateinit var sourcePagingAdapter: NewsSourcesPagingAdapter
    private val viewModel: NewsSourcesViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNewsSourcesBinding.inflate(
            layoutInflater,
            container,
            false
        )

        sourcePagingAdapter = NewsSourcesPagingAdapter()
        setUpRecyclerView(binding.newsSourcesList)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.sourcesToolbar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when(it.itemId){
                R.id.opt_search_sources ->{
                    findNavController().navigate(R.id.action_newsSourcesFragment_to_searchNewsFragment)
                    true
                }else -> false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAllNewsSources.collect {
                    sourcePagingAdapter.submitData(lifecycle, it)
                }
            }
        }

        sourcePagingAdapter.onSourceNewsItemClick {

            val action =
                NewsSourcesFragmentDirections.actionNewsSourcesFragmentToNewsSourcesDetailedFragment(
                    it
                )
            findNavController().navigate(action)
        }
    }


    private fun setUpRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = sourcePagingAdapter
        }
    }

}