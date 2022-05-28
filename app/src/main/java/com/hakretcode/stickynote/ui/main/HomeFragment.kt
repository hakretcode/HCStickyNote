package com.hakretcode.stickynote.ui.main

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.hakretcode.stickynote.R
import com.hakretcode.stickynote.data.model.Note
import com.hakretcode.stickynote.data.model.Resource
import com.hakretcode.stickynote.databinding.FragmentHomeBinding
import com.hakretcode.stickynote.ui.form.FormFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var adapter: MainAdapter? = null
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(requireView())
        binding.mainFab.setOnClickListener { openFormFragment(null) }
        viewModel.getAllNotes()
        initRecycle()
        setupObserve()
        setupToolbar()
    }

    private fun setupToolbar() {
        binding.mainToolbar.menu[0].setOnMenuItemClickListener { deleteButton ->
            val selectModeEnabled = adapter?.toggleSelectMode() ?: false
            val mainIcon = binding.mainIcon
            if (selectModeEnabled) {
                mainIcon.setImageResource(R.drawable.ic_arrow_left)
                mainIcon.setOnClickListener { adapter?.toggleSelectMode() }
                deleteButton.icon = null
            } else {
            mainIcon.setImageResource(R.drawable.ic_notepad)
            mainIcon.setOnClickListener(null)
            deleteButton.icon = AppCompatResources.getDrawable(requireContext(), android.R.drawable.ic_menu_delete)
        }
            true
        }
    }

    private fun setupObserve() {
        viewModel.liveData.observe(viewLifecycleOwner) { resource ->
            val recycle = binding.mainRecycleNotes
            val emptyMessage = binding.emptyMessage
            val progressBar = binding.progressBar
            when (resource) {
                is Resource.Loading -> {
                    progressBar.visibility = VISIBLE
                }
                is Resource.Empty -> {
                    recycle.visibility = GONE
                    emptyMessage.visibility = VISIBLE
                }
                is Resource.Success -> {
                    recycle.visibility = VISIBLE
                    emptyMessage.visibility = GONE
                    adapter?.submitList(resource.data)
                }
                is Resource.Complete -> {
                    progressBar.visibility = GONE
                }
            }
        }
    }

    private fun initRecycle() {
        if (adapter == null) {
            val adapter = MainAdapter { note -> openFormFragment(note) }
            this.adapter = adapter
            binding.mainRecycleNotes.adapter = adapter
        }
    }

    private fun openFormFragment(note: Note?) {
        parentFragmentManager.beginTransaction()
            .add(
                R.id.fragment_container, FormFragment(note, this::updateNotes),
                FormFragment.TAG
            ).commit()
    }

    private fun updateNotes() {
        viewModel.getAllNotes()
    }

    companion object {
        const val TAG = "HomeFragment"
    }
}