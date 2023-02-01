package com.example.bottomsheet

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.bottomsheet.databinding.FragmentBottomSheetBinding

import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment(private val image: ImageView) : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.cameraLyt.setOnClickListener {
            if (checkPermissions(activity, 2)) {
                Log.d("permission", "granted")

                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE), 2)
            }
        }

        binding.galleryLyt.setOnClickListener {
            if (checkPermissions(activity, 1)) {
                Log.d("permission", "granted")
                val intent = Intent()
                intent.apply {
                    type = "image/*"
                    putExtra(Intent.ACTION_GET_CONTENT, true)
                    action = Intent.ACTION_GET_CONTENT
                }

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
            }
        }

        return view
    }


    private fun checkPermissions(activity: FragmentActivity?, i: Int): Boolean {
        return when (i) {
            1 -> if (ContextCompat.checkSelfPermission(
                    binding.root.context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (activity != null) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        PackageManager.PERMISSION_GRANTED
                    )
                }

                false
            } else {
                true
            }

            else -> if (ContextCompat.checkSelfPermission(
                    binding.root.context,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (activity != null) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.CAMERA),
                        PackageManager.PERMISSION_GRANTED
                    )
                }

                false
            } else {
                true
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Get the url of the image from data

            // Get the url of the image from data
            val selectedImageUri: Uri? = data?.data
            if (null != selectedImageUri) {
                // update the preview image in the layout
                image.setImageURI(selectedImageUri)
                this.dismiss()
            }
        }

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {

            val capturedImage = data?.extras?.get("data") as Bitmap

            image.setImageBitmap(capturedImage)
            this.dismiss()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}