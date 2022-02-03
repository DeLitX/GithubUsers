package com.delitx.githubusers.ui.users_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.delitx.githubusers.R
import com.delitx.githubusers.domain.models.BriefUserInfo
import com.delitx.githubusers.ui.utils.collectInLifecycleScope
import com.delitx.githubusers.ui.utils.forceFindViewById
import com.delitx.githubusers.view_models.UsersListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class UsersListFragment : Fragment() {
    private val swipeRefreshLayout: SwipeRefreshLayout by lazy { forceFindViewById(R.id.users_list_refresh_layout) }
    private val recycler: RecyclerView by lazy { forceFindViewById(R.id.users_list_recycler) }
    private val toolbar: Toolbar by lazy { forceFindViewById(R.id.users_list_app_bar) }

    private val adapter = UsersListAdapter(
        object : UsersListAdapter.Interactor {
            override fun onUserClick(user: BriefUserInfo) {
            }

            override fun maybeLoadMoreUsers() {
                viewModel.maybeLoadMoreUsers()
            }
        }
    )

    private val viewModel: UsersListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.maybeRefreshUsers()
        return inflater.inflate(R.layout.fragment_users_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        observeViewModelData()
    }

    private fun observeViewModelData() {
        viewModel.apply {
            isMoreUsersLoading.collectInLifecycleScope(viewLifecycleOwner) {
                // TODO
            }
            isUsersRefreshing.collectInLifecycleScope(viewLifecycleOwner) {
                swipeRefreshLayout.isRefreshing = it
            }
            internetError.collectInLifecycleScope(viewLifecycleOwner) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        context?.getText(R.string.data_not_loaded),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            users.collectInLifecycleScope(viewLifecycleOwner) {
                withContext(Dispatchers.Main) {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun bindViews(view: View) {
        recycler.adapter = adapter
        recycler.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.maybeRefreshUsers()
        }
    }
}
