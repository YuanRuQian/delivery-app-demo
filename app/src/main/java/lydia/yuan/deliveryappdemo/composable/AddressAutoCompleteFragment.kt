package lydia.yuan.deliveryappdemo.composable

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import lydia.yuan.deliveryappdemo.R
import lydia.yuan.deliveryappdemo.databinding.FragmentAddressAutoCompleteBinding

class AddressAutoCompleteFragment : Fragment() {


    private var autoCompleteFragment: AutocompleteSupportFragment? = null
    private var _binding: FragmentAddressAutoCompleteBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddressAutoCompleteBinding.inflate(inflater, container, false)

        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        autoCompleteFragment = null
    }
}