package com.dmabram15.lenghtofservice.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dmabram15.lenghtofservice.R
import com.dmabram15.lenghtofservice.databinding.LenghtOfServiceFragmentBinding
import com.dmabram15.lenghtofservice.viewModel.MainViewModel

class LengthOfServiceFragment : Fragment() {

    companion object {
        fun newInstance() = LengthOfServiceFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding : LenghtOfServiceFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = LenghtOfServiceFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun setListeners() {
        binding.showListOfPeriodsFloatingButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, PeriodsOfServiceFragment.newInstance())
                    ?.addToBackStack(null)
                    ?.commitAllowingStateLoss()
        }
    }
}