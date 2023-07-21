package com.osamaaftab.teya.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.osamaaftab.teya.R
import com.osamaaftab.teya.databinding.FragmentGalleryImagesBinding
import com.osamaaftab.teya.presentation.common.GlideImageLoader


class ImageFragment : Fragment() {

    lateinit var fragmentImageBinding: FragmentGalleryImagesBinding

    companion object {
        private const val URL = "URL"
        private const val IMAGE_SIZE = "350"
        private const val IMAGE_EXTENSION = "bb.png"

        fun newInstance(url: String) = ImageFragment().apply {
            arguments = Bundle().apply { putString(URL, url) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentImageBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_gallery_images, container,
                false
            )
        val url = arguments?.getString(URL)
        val options = getRequestOption()

        GlideImageLoader(fragmentImageBinding.galleryImage, fragmentImageBinding.progressBar).load(
            getUpdatedUrl(url),
            options
        )
        return fragmentImageBinding.root
    }

    private fun getUpdatedUrl(url: String?): String? {
        return url?.replaceAfterLast(
            "/", IMAGE_SIZE + "x" + IMAGE_SIZE
                    + IMAGE_EXTENSION
        )
    }

    private fun getRequestOption() = RequestOptions()
        .centerCrop()
        .error(R.drawable.ic_broken)
        .priority(Priority.HIGH)
}