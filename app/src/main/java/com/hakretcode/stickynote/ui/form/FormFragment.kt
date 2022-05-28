package com.hakretcode.stickynote.ui.form

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hakretcode.stickynote.R
import com.hakretcode.stickynote.data.model.Note
import com.hakretcode.stickynote.data.model.Resource
import com.hakretcode.stickynote.databinding.FragmentFormBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FormFragment(private val note: Note? = null, private val result: (() -> Unit)? = null) : Fragment(R.layout.fragment_form) {
    private lateinit var binding: FragmentFormBinding
    private val viewModel by viewModels<FormViewModel>()

    override fun onCreate(saved: Bundle?) {
        super.onCreate(saved)
        viewModel.note = note
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFormBinding.bind(requireView())
        val binding = this.binding
        binding.fragment = this
        binding.note = note
        binding.buttonReady.setOnClickListener { viewModel
            .saveNote(binding.etTitle.text.toString(), binding.etBody.text.toString()) }
        setupObserve()
    }

    private fun setupObserve() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    result?.invoke()
                    returnToHome()
                } is Resource.Error -> { Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show() }
            }
        }
    }

    fun returnToHome(v: View? = null) {
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    companion object { const val TAG = "FormFragment" }
}