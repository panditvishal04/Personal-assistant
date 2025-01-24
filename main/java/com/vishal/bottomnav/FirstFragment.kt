package com.vishal.bottomnav

import android.app.AlertDialog
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2.Orientation
import com.vishal.bottomnav.Model.ItemClickNotes
import com.vishal.bottomnav.Model.NotesDataBase
import com.vishal.bottomnav.Model.NotesEntity
import com.vishal.bottomnav.Model.RecyclerNotes
import com.vishal.bottomnav.databinding.FragmentFirstBinding

class FirstFragment : Fragment(), ItemClickNotes {

    lateinit var binding : FragmentFirstBinding
    lateinit var mainActivity: MainActivity
    lateinit var roomNotes : NotesDataBase
    lateinit var navController: NavController
    lateinit var notesRecycler : RecyclerNotes
    var notesArray = arrayListOf<NotesEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        roomNotes = NotesDataBase.createDatabase(requireContext())
        navController = findNavController()
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater)


        notesRecycler = RecyclerNotes(mainActivity, notesArray,this)
        getNotesData()
        binding.rvNotes.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvNotes.adapter = notesRecycler

      return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnFab.setOnClickListener {
            mainActivity.navController.navigate(R.id.action_firstFragment2_to_create_Fragment)
        }
        binding.imgFilter.setOnClickListener {
            binding.rvNotes.adapter = notesRecycler
        }

        binding.tvHigh.setOnClickListener {
            var list1 = ArrayList<NotesEntity>()
            for(notes in notesArray){
                if (notes.priority == 3){
                    list1.add(notes)
                }
            }
            binding.rvNotes.adapter = RecyclerNotes(mainActivity,list1,this)
            }
        binding.tvMedium.setOnClickListener {
            var list2 = ArrayList<NotesEntity>()
            for(notes in notesArray){
                if (notes.priority == 2){
                    list2.add(notes)
                }
            }
            binding.rvNotes.adapter = RecyclerNotes(mainActivity,list2,this)

            }
        binding.tvLow.setOnClickListener {
            var list3 = ArrayList<NotesEntity>()
            for(notes in notesArray){
                if (notes.priority == 1){
                    list3.add(notes)
                }
            }
            binding.rvNotes.adapter = RecyclerNotes(mainActivity,list3,this)

        }

    }
    override fun getNotesData() {
        notesArray.clear()
        class getNotes : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                notesArray.addAll(roomNotes.NotesDao().getNotes())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                if (notesArray.isEmpty()) {
                    binding.tvEmptyNotes.visibility = View.VISIBLE
                    binding.tvNothing.visibility = View.VISIBLE
                    binding.rvNotes.visibility = View.GONE

                } else {
                    binding.rvNotes.visibility = View.VISIBLE
                    binding.tvNothing.visibility = View.GONE
                    binding.tvEmptyNotes.visibility = View.GONE
                }
                notesRecycler.notifyDataSetChanged()
            }
        }
        getNotes().execute()
    }

    override fun update(notesEntity: NotesEntity) {
        var bundle = Bundle()
        bundle.putInt("id", notesEntity.id)
        mainActivity.navController.navigate(R.id.edit_Fragment, bundle)

    }

    override fun delete(position: Int) {
        AlertDialog.Builder(requireContext(),)
            .setTitle("DELETE")
            .setMessage("Do you want to delete")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                Toast.makeText(requireContext(), "Yes click", Toast.LENGTH_SHORT).show()
                notesRecycler.notifyDataSetChanged()

                class deleteNotes : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg p0: Void?): Void? {
                        roomNotes.NotesDao().deleteNotes(notesArray[position])
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        getNotesData()
                    }
                }
                deleteNotes().execute()
            }
            .setNegativeButton("No") { _, _ ->
                Toast.makeText(requireContext(), "NO click", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("Don't say") { _, _ ->
                Toast.makeText(requireContext(), "Don't say click", Toast.LENGTH_SHORT).show()
            }
            .show()
    }





}