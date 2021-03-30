package com.dmabram15.lenghtofservice.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmabram15.lenghtofservice.viewModel.EditPeriodViewModel
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.databinding.EditPeriodFragmentBinding

class EditPeriodFragment : Fragment() {

    companion object {
        fun newInstance() = EditPeriodFragment()
    }

    private lateinit var viewModel: EditPeriodViewModel
    private lateinit var binding: EditPeriodFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EditPeriodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditPeriodViewModel::class.java)
        // TODO: Use the ViewModel
    }

}