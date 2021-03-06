package com.delitx.githubusers.ui.users_list_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.delitx.githubusers.R
import com.delitx.githubusers.domain.models.BriefUserInfo
import de.hdodenhof.circleimageview.CircleImageView

class UsersListAdapter(private val interactor: Interactor) :
    ListAdapter<BriefUserInfo, UsersListAdapter.UsersListViewHolder>(
        object : DiffUtil.ItemCallback<BriefUserInfo>() {
            override fun areItemsTheSame(oldItem: BriefUserInfo, newItem: BriefUserInfo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: BriefUserInfo, newItem: BriefUserInfo) =
                oldItem.iconUrl == newItem.iconUrl &&
                    oldItem.name == newItem.name
        }) {
    companion object {
        private const val USERS_TILL_UPDATE = 5
    }

    class UsersListViewHolder(private val v: View, val interactor: Interactor) :
        RecyclerView.ViewHolder(v) {
        private val userIcon = v.findViewById<CircleImageView>(R.id.user_icon)
        private val userName = v.findViewById<TextView>(R.id.user_name)
        private var onClick = { }

        init {
            v.setOnClickListener { v: View -> onClick() }
        }

        fun bind(user: BriefUserInfo) {
            Glide.with(v.context).load(user.iconUrl).into(userIcon)
            userName.text = user.name
            userIcon.transitionName = user.name
            onClick = {
                interactor.onUserClick(user, userIcon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false)
        return UsersListViewHolder(view, interactor)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        holder.bind(getItem(position))
        if (currentList.size - position <= USERS_TILL_UPDATE) {
            interactor.maybeLoadMoreUsers()
        }
    }

    interface Interactor {
        fun onUserClick(user: BriefUserInfo, img: CircleImageView)
        fun maybeLoadMoreUsers()
    }
}
