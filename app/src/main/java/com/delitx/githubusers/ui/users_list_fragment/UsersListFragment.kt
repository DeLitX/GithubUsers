package com.delitx.githubusers.ui.users_list_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.delitx.githubusers.R

class UsersListFragment : Fragment() {
    val adapter = UsersListAdapter {}

    val swipeRefreshLayout by lazy { requireView().findViewById<SwipeRefreshLayout>(R.id.users_list_refresh_layout) }
    val recycler by lazy { requireView().findViewById<RecyclerView>(R.id.users_list_recycler) }
    val appBar by lazy { requireView().findViewById<Toolbar>(R.id.users_list_app_bar) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.adapter = adapter
        recycler.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        swipeRefreshLayout.setOnRefreshListener {
            // TODO
        }
    }
}
