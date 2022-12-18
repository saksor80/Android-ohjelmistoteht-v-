package fi.sorja.sakari.ohjelmistotehtava

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [reviewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class reviewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // Määritellään muuttujat
    lateinit var radioGroup: RadioGroup
    lateinit var selectedRadioButton: RadioButton
    lateinit var button1: Button

    // Interface datan siirtoa varten takaisin addInfoActivityyn
    interface dataPass{
        fun dataPass(data:String)
    }

    private lateinit var dataPasser: dataPass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    // dataPasserin määritystä
    override fun onAttach(context: Context) {
         super.onAttach(context)
         dataPasser = context as dataPass
    }

    fun passSomeData(data:String){
        dataPasser.dataPass(data)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_review, container, false)
        var strReview = ""
        // Määritellään radiobuttongroup arvostelua varten, ja mitä tapahtuu kun arvostelun lähetysnappia painetaan
        radioGroup = view.findViewById(R.id.radioGroup1)
        button1 = view.findViewById(R.id.button1)
        button1.setOnClickListener({
            val selectedRadioButtonId: Int = radioGroup.checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                selectedRadioButton = view.findViewById(selectedRadioButtonId)
                strReview = selectedRadioButton.text.toString()
                // laitetaan data addInfoActivitylle
                passSomeData(strReview)
            } else {
                Log.d("tulos:", "Nothing selected from the radio group")
            }
            // Navigointi fragmenttien välillä
            val action = reviewFragmentDirections.actionReviewFragmentToFeedbackFragment(strReview)
            findNavController().navigate(action)
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment reviewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            reviewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}