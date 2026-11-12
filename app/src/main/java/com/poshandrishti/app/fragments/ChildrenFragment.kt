package com.poshandrishti.app.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.poshandrishti.app.R

class ChildrenFragment : Fragment(R.layout.fragment_children) {

    private var selectedFilter = "All"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val search = view.findViewById<EditText>(R.id.etSearchChildren)
        val childrenList = view.findViewById<LinearLayout>(R.id.childrenList)
        val emptyMessage = view.findViewById<TextView>(R.id.txtNoChildren)

        val totalCount = view.findViewById<TextView>(
            R.id.totalChildrenCount
        )

        val filterAll = view.findViewById<TextView>(R.id.filterAll)
        val filterNormal = view.findViewById<TextView>(R.id.filterNormal)
        val filterMAM = view.findViewById<TextView>(R.id.filterMAM)
        val filterSAM = view.findViewById<TextView>(R.id.filterSAM)

        fun refreshChildren() {
            val children = ChildStorage.getChildren(requireContext())
            val query = search.text.toString().trim()

            childrenList.removeAllViews()

            totalCount.text = children.size.toString()

            filterAll.text = "All (${children.size})"
            filterNormal.text = "Normal (${children.count { it.weight.isNotBlank() && it.height.isNotBlank() }})"
            filterMAM.text = "MAM (0)"
            filterSAM.text = "SAM (0)"

            val filtered = children.filter { child ->
                val matchesSearch =
                    child.name.contains(query, ignoreCase = true) ||
                            child.guardian.contains(query, ignoreCase = true)

                val matchesFilter = selectedFilter == "All"

                matchesSearch && matchesFilter
            }

            emptyMessage.visibility =
                if (children.isEmpty()) View.VISIBLE else View.GONE

            filtered.forEach { child ->
                val card = TextView(requireContext()).apply {
                    text = "${child.name}\nDOB: ${child.dob}  |  ${child.gender}\nGuardian: ${child.guardian}"
                    textSize = 16f
                    setTextColor(android.graphics.Color.BLACK)
                    setPadding(20, 20, 20, 20)
                    setBackgroundResource(R.drawable.bg_card_white)
                }

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 12
                }

                childrenList.addView(card, params)

                card.setOnClickListener {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.pageContainer, ChildProfileFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }

            if (children.isNotEmpty() && filtered.isEmpty()) {
                emptyMessage.text = "No matching children found."
                emptyMessage.visibility = View.VISIBLE
            } else {
                emptyMessage.text =
                    "No children registered yet.\nTap + Add Child to get started."
            }
        }

        search.addTextChangedListener {
            refreshChildren()
        }

        filterAll.setOnClickListener {
            selectedFilter = "All"
            refreshChildren()
        }

        filterNormal.setOnClickListener {
            selectedFilter = "Normal"
            refreshChildren()
        }

        filterMAM.setOnClickListener {
            selectedFilter = "MAM"
            refreshChildren()
        }

        filterSAM.setOnClickListener {
            selectedFilter = "SAM"
            refreshChildren()
        }

        view.findViewById<Button>(R.id.btnAddChild).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.pageContainer, AddChildFragment())
                .addToBackStack(null)
                .commit()
        }

        refreshChildren()
    }

    override fun onResume() {
        super.onResume()
        view?.let { view ->
            val search = view.findViewById<EditText>(R.id.etSearchChildren)
            search.setText(search.text)
        }
    }
}
