package ru.sdimosik.polyhabr.presentaion.main.create

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.sdimosik.polyhabr.R
import ru.sdimosik.polyhabr.common.ui.BaseFragment
import ru.sdimosik.polyhabr.databinding.FragmentCreateBinding

@AndroidEntryPoint
class CreateFragment : BaseFragment(R.layout.fragment_create) {
    companion object {
        fun newInstance() = CreateFragment()
    }

    private val binding by viewBinding(FragmentCreateBinding::bind)

    private val viewModel by viewModels<CreateViewModel>()

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        result.data?.let { data ->
            viewModel.savePdf(data.data)
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            selectPdf()
        } else {
            switchToastStyleToError("В разрешении отказано")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setup()
            subscribe()
        }
    }

    private fun FragmentCreateBinding.setup() {
        ivReset.setOnClickListener {
            acetTitleArticle.setText("")
            acetPreviewArticle.setText("")
            acetTextArticle.setText("")
            viewModel.clearFields()
            switchToastStyleToSuccess("Поля очищены")
        }
    }

    private fun FragmentCreateBinding.subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collectLatest {
                    switchToastStyleToError(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.showSuccess.collectLatest {
                    switchToastStyleToSuccess(getString(R.string.fragment_create_article_success))
                }
            }
        }
        tvPickPdfFile.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                selectPdf()
            }
        }
        tvDeletePdfFile.setOnClickListener {
            viewModel.deletePdf()
        }
        viewModel.pdfUri.observe(viewLifecycleOwner) { uri ->
            switchButtonLoadPdf(goLoad = uri == null)
        }
        viewModel.articleType.observe(viewLifecycleOwner) { list ->
            createListRadioButton(fmArticleTypeContainer, list) {
                viewModel.storeArticleType(it)
            }
        }
        viewModel.disciplines.observe(viewLifecycleOwner) { list ->
            createListRadioButton(fmDisciplineContainer, list) {
                viewModel.storeDiscipline(it)
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                btnCreateArticle.showLoading()
            } else {
                btnCreateArticle.hideLoading()
            }
        }
        btnCreateArticle.setOnClickListener {
            viewModel.createArticle()
        }
        acetTitleArticle.addTextChangedListener {
            viewModel.title = it.toString()
        }
        acetPreviewArticle.addTextChangedListener {
            viewModel.previewText = it.toString()
        }
        acetTextArticle.addTextChangedListener {
            viewModel.text = it.toString()
        }
    }

    private fun FragmentCreateBinding.createListRadioButton(
        baseView: FrameLayout,
        list: List<String>,
        onStore: (String) -> Unit,
    ) {
        val radioButtonGroup = RadioGroup(requireContext())
        list.forEachIndexed { index, title ->
            val radioButton = AppCompatRadioButton(requireContext()).apply {
                text = title
                id = View.generateViewId()
                buttonTintList = ContextCompat.getColorStateList(
                    requireContext(),
                    ru.sdimosik.polyhabr.common.R.color.green
                )
            }
            if (index == 0) {
                radioButton.isChecked = true
                onStore(radioButton.text.toString())
            }
            radioButtonGroup.addView(radioButton)
        }
        baseView.addView(radioButtonGroup)
        radioButtonGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.root.findViewById<RadioButton>(checkedId)?.apply {
                onStore(this.text.toString())
            }
        }
    }

    private fun selectPdf() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        activityResultLauncher.launch(intent)
    }

    private fun FragmentCreateBinding.switchButtonLoadPdf(goLoad: Boolean) {
        if (goLoad) {
            tvPickPdfFile.visibility = View.VISIBLE
            tvDeletePdfFile.visibility = View.GONE
        } else {
            tvPickPdfFile.visibility = View.GONE
            tvDeletePdfFile.visibility = View.VISIBLE
        }
    }
}
