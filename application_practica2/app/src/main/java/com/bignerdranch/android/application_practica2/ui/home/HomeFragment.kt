package com.bignerdranch.android.application_practica2.ui.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bignerdranch.android.application_practica2.database.MyData
import com.bignerdranch.android.application_practica2.database.MyDataBase
import com.bignerdranch.android.application_practica2.database.MyDataBase.Factory.getInstance
import com.bignerdranch.android.application_practica2.databinding.FragmentHomeBinding
import java.io.ByteArrayOutputStream
import java.io.File

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var ivMyImage: ImageView
    private lateinit var imageUri: Uri
    private lateinit var db: MyDataBase
    private lateinit var context: Context
    private lateinit var button: Button
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageUri = createImageUri()
        ivMyImage = binding.imageView
        editText1 = binding.edit1
        editText2 = binding.edit2
        editText3 = binding.edit3

        val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            ivMyImage.setImageURI(null)
            ivMyImage.setImageURI(imageUri)
        }

        ivMyImage.setOnClickListener {
            contract.launch(imageUri)
        }

        db = MyDataBase.getInstance(requireContext())

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val textView: TextView = binding.edit1
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        db.getDbDao().getMyData().asLiveData().observe(viewLifecycleOwner, Observer {
            Log.d("qwe", it.toString())
        })

        val name = editText1.text.toString()
        val surname = editText2.text.toString()
        val group = editText3.text.toString()
        val imageBytes = convertImageToBytes(ivMyImage)

        val data = MyData(
            PrimaryKey = 1,
            image = imageBytes,
            name = name,
            surname = surname,
            group = group
        )

        Thread {
            db.getDbDao().insert(data)
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createImageUri(): Uri {
        val image = File(requireActivity().filesDir, "myPhoto.png")
        return FileProvider.getUriForFile(
            requireContext(),
            "com.example.application_practica2.FileProvider",
            image
        )
    }

    private fun convertImageToBytes(imageView: ImageView): ByteArray {
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}