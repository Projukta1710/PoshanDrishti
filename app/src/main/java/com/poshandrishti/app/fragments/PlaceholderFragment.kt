
package com.poshandrishti.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.poshandrishti.app.R

class PlaceholderFragment : Fragment(R.layout.fragment_placeholder_screen) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Get the screen title
        val title = arguments?.getString(ARG_TITLE) ?: "Home"

        // Update the heading
        val titleText = view.findViewById<TextView>(
            R.id.placeholderScreenTitle
        )

        titleText.text = title
    }

    companion object {

        private const val ARG_TITLE = "screen_title"

        // Create a Fragment with a custom title
        fun newInstance(title: String): PlaceholderFragment {

            return PlaceholderFragment().apply {

                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                }
            }
        }
    }
}
