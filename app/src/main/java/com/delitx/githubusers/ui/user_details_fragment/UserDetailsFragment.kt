package com.delitx.githubusers.ui.user_details_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.delitx.githubusers.R
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class UserDetailsFragment : Fragment() {

    private lateinit var toolbarBackButton: ImageView
    private lateinit var toolbarUserName: TextView
    private lateinit var userIcon: CircleImageView
    private lateinit var userName: TextView
    private lateinit var userUrl: TextView
    private lateinit var userReposAmount: TextView
    private lateinit var userGistsAmount: TextView
    private lateinit var userFollowersAmount: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        with(view) {
            toolbarBackButton = findViewById(R.id.user_details_back)
            toolbarUserName = findViewById(R.id.user_details_app_bar_name)
            userIcon = findViewById(R.id.user_details_icon)
            userName = findViewById(R.id.user_details_name)
            userUrl = findViewById(R.id.user_details_url)
            userReposAmount = findViewById(R.id.user_details_repos_amount)
            userGistsAmount = findViewById(R.id.user_details_gists_amount)
            userFollowersAmount = findViewById(R.id.user_details_followers_amount)
        }
        toolbarBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
