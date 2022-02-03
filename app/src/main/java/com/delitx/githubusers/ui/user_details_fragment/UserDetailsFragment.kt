package com.delitx.githubusers.ui.user_details_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.delitx.githubusers.R
import com.delitx.githubusers.ui.utils.forceFindViewById
import de.hdodenhof.circleimageview.CircleImageView

class UserDetailsFragment : Fragment() {

    val toolbarBackButton: ImageView by lazy { forceFindViewById(R.id.user_details_back) }
    val toolbarUserName: TextView by lazy { forceFindViewById(R.id.user_details_app_bar_name) }
    val userIcon: CircleImageView by lazy { forceFindViewById(R.id.user_details_icon) }
    val userName: TextView by lazy { forceFindViewById(R.id.user_details_name) }
    val userUrl: TextView by lazy { forceFindViewById(R.id.user_details_url) }
    val userReposAmount: TextView by lazy { forceFindViewById(R.id.user_details_repos_amount) }
    val userGistsAmount: TextView by lazy { forceFindViewById(R.id.user_details_gists_amount) }
    val userFollowersAmount: TextView by lazy { forceFindViewById(R.id.user_details_followers_amount) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        TODO("Not yet implemented")
    }
}
