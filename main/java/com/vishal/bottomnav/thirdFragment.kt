package com.vishal.bottomnav

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Typeface
import android.icu.lang.UCharacter.EastAsianWidth
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.vishal.bottomnav.Model.NotesDataBase
import com.vishal.bottomnav.databinding.FragmentThirdBinding
import kotlin.math.exp

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [thirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class thirdFragment : Fragment()
{
    private var param1: String? = null
    private var param2: String? = null
    private var incomeTotal : Long =0L
    private var expenseTotal : Long = 0L
    private  var expense_per: Long = 0L
    private  var balance_per: Long = 0L


    // declaring parameters
    private lateinit var binding:FragmentThirdBinding
    lateinit var navController: NavController
    private lateinit var roomDbNotes: NotesDataBase

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        //adapter = ExpenseTrackerAdapter()
        navController = findNavController()
        arguments?.let{
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        // returning binding
       binding = FragmentThirdBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        roomDbNotes = NotesDataBase.createDatabase(requireContext())

        binding.cvIncome.setOnClickListener {
            navController.navigate(R.id.incomeFragment)
        }
        binding.cvExpense.setOnClickListener{
            navController.navigate(R.id.expenseFragment)
        }
        binding.cvHistory.setOnClickListener {
            navController.navigate(R.id.historyFragment)
        }
        getIncomeTotal()


    }

    companion object
    {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment thirdFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            thirdFragment().apply{
                arguments = Bundle().apply{
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun getIncomeTotal() {

        class getTotal : AsyncTask<Void, Void, Void>()
        {
            @SuppressLint("SetTextI18n")
            override fun doInBackground(vararg p0: Void?): Void? {
                 incomeTotal = (roomDbNotes.ExpenseHistoryDao().getExpenseTotal(1))
                 expenseTotal = (roomDbNotes.ExpenseHistoryDao().getExpenseTotal(2))

                return null
            }

            @SuppressLint("SetTextI18n")
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                System.out.println("incomeTotal $incomeTotal expense Total $expenseTotal")
                binding.tvBalance.text = "Balance:\nRs ${incomeTotal-expenseTotal}-/"
                binding.tvIncomeFinal.text = "Total Income:\nRs $incomeTotal-/"
                binding.tvExpenseFinal.text = "Total Expense:\nRs $expenseTotal-/"



            }

        }
        getTotal().execute()
    }

}