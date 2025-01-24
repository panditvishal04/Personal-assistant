package com.vishal.bottomnav

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.vishal.bottomnav.Model.NotesDataBase
import com.vishal.bottomnav.Model.RecyclerNotes
import com.vishal.bottomnav.databinding.FragmentIncomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IncomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IncomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentIncomeBinding
    var transactionsArray = arrayListOf<ExpenseEntity>()
    lateinit var roomDbNotes: NotesDataBase
    lateinit var mainActivity: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomDbNotes = NotesDataBase.createDatabase(requireContext())
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIncomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //getIncomeData()
        binding.btnDone.setOnClickListener {
            if (binding.tIEtAmount.text.toString().isEmpty()) {
                binding.tILLayoutAmount.error = "Enter amount"
                return@setOnClickListener
            }

            class insertIncome : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg p0: Void?): Void? {
                    val incomeEntity = ExpenseEntity(
                        amount = binding.tIEtAmount.text.toString().toDouble(),
                        category = binding.tIEtCategory.text.toString(),
                        paidMethod = binding.tIEtPayment.text.toString(),
                        note = binding.tIEtNote.text.toString(),
                        type = 1,
                    )
                    roomDbNotes.ExpenseHistoryDao().insertExpenseData(incomeEntity)
                    return null

                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)
                    mainActivity.navController.popBackStack(R.id.thirdFragment, false)

                }
            }
            insertIncome().execute()
        }
    }

    fun getIncomeData(){
        transactionsArray.clear()
        class incomeData() : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg p0: Void?): Void? {
                transactionsArray.addAll(roomDbNotes.ExpenseHistoryDao().getExpenseData(1))
                return null

            }
        }
        incomeData().execute()
    }

}

