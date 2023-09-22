package com.example.githubapi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.githubapi.adapter.FollowPagerAdapter
import com.example.githubapi.data.response.DetailUserResponse
import com.example.githubapi.databinding.FragmentFollowBinding
import com.example.githubapi.ui.model.DetailViewModel



class FollowFragment : Fragment() {
    private var position = 0
    private val bindingFragment: FragmentFollowBinding by viewBinding()
    private val detailViewModel by activityViewModels<DetailViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentFollowBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followLiveData: LiveData<ArrayList<DetailUserResponse>> =
            if (position == FOLLOWING) detailViewModel.followingData
            else detailViewModel.followersData

        followLiveData.observe(this)  { followData ->
            bindingFragment.rvFragmentFollow.apply {
                adapter = FollowPagerAdapter(followData)
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireActivity())
            }
        }

    }
    companion object {
        const val FOLLOWING = 0
        const val FOLLOWERS = 1

        fun newInstance(position: Int): FollowFragment =
            FollowFragment().apply {
                this.position = position
            }
    }

}