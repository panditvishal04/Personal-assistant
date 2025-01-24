package com.vishal.bottomnav.Fragments

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.vishal.bottomnav.MainActivity
import com.vishal.bottomnav.Model.NotesDataBase
import com.vishal.bottomnav.Model.NotesEntity
import com.vishal.bottomnav.Model.RecyclerNotes
import com.vishal.bottomnav.R
import com.vishal.bottomnav.databinding.CreateFragmentBinding

class CreateFragment : Fragment() {

    lateinit var binding: CreateFragmentBinding
    lateinit var mainActivity: MainActivity
    lateinit var notesDataBase : NotesDataBase
    var priority : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        notesDataBase = NotesDataBase.createDatabase(mainActivity)
        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = CreateFragmentBinding.inflate(layoutInflater)

        binding.imgGreen.setImageResource(R.drawable.icn_done)

        binding.imgGreen.setOnClickListener {
            priority=1
            binding.imgGreen.setImageResource(R.drawable.icn_done)
            binding.imgYellow.setImageResource(0)
            binding.imgRed.setImageResource(0)

        }

        binding.imgYellow.setOnClickListener {
            priority=2
            binding.imgYellow.setImageResource(R.drawable.icn_done)
            binding.imgGreen.setImageResource(0)
            binding.imgRed.setImageResource(0)
        }

        binding.imgRed.setOnClickListener {
            priority=3
            binding.imgRed.setImageResource(R.drawable.icn_done)
            binding.imgYellow.setImageResource(0)
            binding.imgGreen.setImageResource(0)
        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {

            if (binding.etTitle.text.toString().isEmpty()) {
                binding.etTitle.error = "Write title"
            }  else if (binding.etNotes.text.toString().isEmpty()) {
                binding.etNotes.error = "Plz write notes"
            } else {
                var notesEntity = NotesEntity(
                    title = binding.etTitle.text.toString(),
                    subtitle = binding.etSubTitle.text.toString(),
                    notes = binding.etNotes.text.toString(),
                    priority = priority
                )

                class insertNotes : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg p0: Void?): Void? {
                        notesDataBase.NotesDao().insertNotes(notesEntity)
                        return null
                    }
                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        mainActivity.navController.popBackStack()
                    }
                }
                insertNotes().execute()
            }

            Toast.makeText(requireContext(), "Notes Save", Toast.LENGTH_SHORT).show()
        }
    }

}