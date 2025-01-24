package com.vishal.bottomnav

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.vishal.bottomnav.Model.NotesDataBase
import com.vishal.bottomnav.databinding.FragmentHistoryBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HistoryFragment : Fragment(),ThirdFragmentInterface,IncomeListView.OnItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var mainActivity: MainActivity
    private var transactionArray = arrayListOf<ExpenseEntity>()
    private lateinit var notesDataBase: NotesDataBase
    private lateinit var incomeListView: IncomeListView
    var arrayList = arrayListOf<String>("Income", "Expense")
    private lateinit var arrayAdapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        notesDataBase = NotesDataBase.createDatabase(requireContext())
        incomeListView = IncomeListView(mainActivity, transactionArray, this, this)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            arrayList
        )
        binding.spinner.adapter = arrayAdapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = incomeListView

        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                when (position) {
                    0 -> {
                        getIncomeData()
                        println(arrayList[position])

                    }

                    1 -> {
                        getExpenseData()
                        println(arrayList[position])
                    }
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        getIncomeTotal()

    }

    private fun getIncomeTotal() {

        class getTotal : AsyncTask<Void, Void, Void>() {
            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg p0: Void?): Void? {
                val incomeTotal = (notesDataBase.ExpenseHistoryDao().getExpenseTotal(1))
                val expenseTotal = (notesDataBase.ExpenseHistoryDao().getExpenseTotal(2))
                println("incomeTotal $incomeTotal expense Total $expenseTotal")
                return null
            }

        }
        getTotal().execute()
    }

    fun getIncomeData() {
        transactionArray.clear()
        class incomeInfo : AsyncTask<Void, Void, Void>() {
            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg p0: Void?): Void? {
                transactionArray.addAll(notesDataBase.ExpenseHistoryDao().getExpenseData(1))
                return null
            }

            @Deprecated("Deprecated in Java")
            @SuppressLint("NotifyDataSetChanged")
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                //Toast.makeText(requireContext(), "Income", Toast.LENGTH_SHORT).show()
                incomeListView.notifyDataSetChanged()
            }
        }
        incomeInfo().execute()
    }

    fun getExpenseData() {
        transactionArray.clear()
        class expenseInfo : AsyncTask<Void, Void, Void>() {
            @Deprecated("Deprecated in Java")
            override fun doInBackground(vararg p0: Void?): Void? {
                transactionArray.addAll(notesDataBase.ExpenseHistoryDao().getExpenseData(2))
                return null
            }

            @Deprecated("Deprecated in Java")
            @SuppressLint("NotifyDataSetChanged")
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                incomeListView.notifyDataSetChanged()
                //Toast.makeText(requireContext(), "Expense", Toast.LENGTH_SHORT).show()
            }
        }
        expenseInfo().execute()
    }


    @SuppressLint("ResourceType")
    override fun onItemClick(position: Int) {
        Toast.makeText(requireContext(), "$position", Toast.LENGTH_SHORT).show()
        var clickedItem = incomeListView[position]
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.details)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.show()
        val DTamount = dialog.findViewById<EditText>(R.id.tvAmount)
        val DTcategory = dialog.findViewById<EditText>(R.id.tvCategory)
        val DTmethod = dialog.findViewById<EditText>(R.id.tvMethod)
        val DTnote = dialog.findViewById<EditText>(R.id.tvNote)
        val DTedit = dialog.findViewById<Button>(R.id.Edit)
        DTamount.setText(transactionArray[position].amount.toString())
        DTcategory.setText(transactionArray[position].category)
        DTmethod.setText(transactionArray[position].paidMethod)
        DTnote.setText(transactionArray[position].note)
        DTedit.setOnClickListener {
            dialog.dismiss()
        }


    }

    override fun deleteItem(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Are you sure you want to delete!")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                class delete : AsyncTask<Void, Void, Void>() {
                    @Deprecated("Deprecated in Java")
                    override fun doInBackground(vararg p0: Void?): Void? {
                        notesDataBase.ExpenseHistoryDao().delete(transactionArray[position])
                        return null
                    }

                    @Deprecated("Deprecated in Java")
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        incomeListView.notifyDataSetChanged()
                        getIncomeData()
                    }
                }
                delete().execute()


                Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
            }

            .show()
    }


    override fun deleteItemExpense(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Are you sure you want to delete!")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                class delete : AsyncTask<Void, Void, Void>() {
                    @Deprecated("Deprecated in Java")
                    override fun doInBackground(vararg p0: Void?): Void? {
                        notesDataBase.ExpenseHistoryDao().delete(transactionArray[position])
                        return null
                    }

                    @Deprecated("Deprecated in Java")
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        incomeListView.notifyDataSetChanged()
                        getExpenseData()
                    }
                }
                delete().execute()


                Toast.makeText(requireContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ ->
            }

            .show()
    }
}
