package com.example.notey.ui

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.notey.R
import com.example.notey.utils.loadImageFromStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ProfileFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var saveButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var bitmap: Bitmap
    private val PICK_IMAGE = 101
    private lateinit var circleImageView: CircleImageView
    private lateinit var imageUri: Uri
    private var myPath: String? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)!!
        saveButton = view.findViewById(R.id.button)
        nameEditText = view.findViewById(R.id.nameET)
        circleImageView = view.findViewById(R.id.user_image)
        setupViews()

        saveButton.setOnClickListener {


            val editor: SharedPreferences.Editor = sharedPreferences.edit()

            val name: String = nameEditText.text.toString()

            editor.putString("Image", saveToInternalStorage(bitmap))
            editor.putString("NAME", name)

            editor.apply()
            Navigation.findNavController(requireView()).popBackStack()

        }
        circleImageView.setOnClickListener {
            val gallary = Intent()
            gallary.setType("image/*")
            gallary.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(gallary, "Select Image"), PICK_IMAGE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.data!!
            try {
                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                circleImageView.setImageBitmap(bitmap)
                circleImageView.invalidate()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun saveToInternalStorage(bitmapImage: Bitmap): String? {
        val cw = ContextWrapper(requireContext())
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val mypath = File(directory, "profile.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (fos != null) {
                    fos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        myPath = directory.absolutePath
        return myPath
    }

    private fun setupViews() {
        nameEditText.setText(sharedPreferences.getString("NAME", "User Name ").toString())
        myPath = sharedPreferences.getString("Image", "").toString()
        circleImageView.setImageBitmap(requireContext().loadImageFromStorage(myPath!!))

    }
}
