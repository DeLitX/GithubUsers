package com.delitx.githubusers.ui.user_details_fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.delitx.githubusers.R
import com.delitx.githubusers.common.DataState
import com.delitx.githubusers.ui.utils.collectInLifecycleScope
import com.delitx.githubusers.view_models.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    private val viewModel: UserDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = context?.let {
            TransitionInflater.from(it).inflateTransition(android.R.transition.move)
        }
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_details, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        arguments?.let {
            val args = UserDetailsFragmentArgs.fromBundle(it)
            viewModel.loadUser(args.userLogin)
            userIcon.transitionName = args.userLogin
            Glide.with(view).load(args.iconUrl).into(userIcon)
        }
        observeViewModelData()
    }

    private fun observeViewModelData() {
        viewModel.user.collectInLifecycleScope(viewLifecycleOwner) {
            withContext(Dispatchers.Main) {
                when (it) {
                    is DataState.Data -> {
                        val user = it.value
                        toolbarUserName.text = user.name
                        Glide.with(requireView()).load(user.iconUrl).into(userIcon)
                        userName.text = user.name
                        userUrl.text = user.url
                        userUrl.setOnClickListener {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(user.url)))
                        }
                        userReposAmount.text = user.publicRepositories.toString()
                        userGistsAmount.text = user.publicGists.toString()
                        userFollowersAmount.text = user.followers.toString()
                    }
                    is DataState.Failure -> {
                        Toast.makeText(
                            context,
                            getText(R.string.data_not_loaded),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is DataState.Undefined -> {
                    }
                }
            }
        }
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
