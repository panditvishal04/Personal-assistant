package com.vishal.bottomnav

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vishal.bottomnav.Model.NotesDataBase
import com.vishal.bottomnav.databinding.FragmentExpenseBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExpenseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpenseFragment : Fragment()
{

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentExpenseBinding
    var expenseArray = arrayListOf<ExpenseEntity>()
    private lateinit var roomDbNotes: NotesDataBase
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?)
    {
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
    ): View
    {
        binding = FragmentExpenseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDone.setOnClickListener {
            if( binding.tIEtAmount.text.toString().isEmpty())
            {
                binding.tILLayoutAmount.error = "Enter amount"
                return@setOnClickListener
            }

            class insertExpense : AsyncTask<Void, Void, Void>() {
                override fun doInBackground(vararg p0: Void?): Void? {
                    val expenseEntity = ExpenseEntity(
                        amount = binding.tIEtAmount.text.toString().toDouble(),
                        category = binding.tIEtCategory.text.toString(),
                        paidMethod = binding.tIEtPayment.text.toString(),
                        note = binding.tIEtNote.text.toString(),
                        type = 2,

                    )
                    roomDbNotes.ExpenseHistoryDao().insertExpenseData(expenseEntity)
                    return null

                }
            }
            insertExpense().execute()
            mainActivity.navController.popBackStack(R.id.thirdFragment,false)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExpenseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExpenseFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}