package com.vishal.bottomnav

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vishal.bottomnav.Model.NotesDataBase
import com.vishal.bottomnav.Model.NotesEntity
import com.vishal.bottomnav.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.navController)
        binding.bottomNav.setOnItemSelectedListener {
            if(it.itemId == R.id.notes){
                navController.navigate(R.id.firstFragment2)
            }
            else if(it.itemId == R.id.setting){
                navController.navigate(R.id.thirdFragment)
            }
            return@setOnItemSelectedListener true
        }
        navController.addOnDestinationChangedListener{_, destination, _->
            when (destination.id) {
                R.id.firstFragment2 -> {
                    binding.bottomNav.menu.getItem(0).isChecked = true

                }
                R.id.thirdFragment -> {
                    binding.bottomNav.menu.getItem(1).isChecked = true
                }
            }
        }



    }


}