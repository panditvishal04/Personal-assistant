package com.vishal.bottomnav.Fragments

import android.app.AlertDialog
import android.net.Uri
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
import com.vishal.bottomnav.R
import com.vishal.bottomnav.databinding.CreateFragmentBinding
import com.vishal.bottomnav.databinding.EditFragmentBinding

class EditFragment : Fragment() {

  lateinit var binding : EditFragmentBinding
    lateinit var notesDataBase : NotesDataBase
    lateinit var mainActivity: MainActivity
    var notesEntity =NotesEntity()
    var priority : Int = 1

  var Eid = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = EditFragmentBinding.inflate(layoutInflater)

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
        mainActivity = activity as MainActivity
        notesDataBase = NotesDataBase.createDatabase(mainActivity)

        arguments?.let {
            Eid = it.getInt("id")
            println("GetId $Eid")
            if(Eid > 0){
                getNotesInfo()

            }
        }
        binding.btnSave.setOnClickListener {

            if (binding.etTitle.text.toString().isEmpty()){
                binding.etTitle.error = "Enter your title"
            }
            else if (binding.etNotes.text.toString().isEmpty()){
                binding.etNotes.error = "enter your notes"
            }
            else{
                 notesEntity = NotesEntity(title = binding.etTitle.text.toString()
                     , subtitle = binding.etSubTitle.text.toString()
                     , notes = binding.etNotes.text.toString(),
                     priority = priority)
                notesEntity.id = Eid
                class updateNotes : AsyncTask<Void,Void,Void>(){
                    override fun doInBackground(vararg p0: Void?): Void? {
                        notesDataBase.NotesDao().updateNotes(notesEntity)
                        return null
                    }
                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        Toast.makeText(mainActivity,"Notes Updated", Toast.LENGTH_SHORT).show()

                        mainActivity.navController.popBackStack()

                    }

                }
                updateNotes().execute()
            }
        }

    }

    private fun getNotesInfo() {

        class getAllNotes : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg p0: Void?): Void?{
                notesEntity = notesDataBase.NotesDao().getNotesEntityId(Eid)
              return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding.etTitle.setText(notesEntity.title)
                binding.etNotes.setText(notesEntity.notes)

            }

        }
        getAllNotes().execute()
    }

}