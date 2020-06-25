package br.com.jwm.rickmortyapp.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import br.com.jwm.rickmortyapp.R

class CharacterDetailsFragment : Fragment() {

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.textview_character_details).text =
                getString(R.string.hello_character_details, args.myArg)

        view.findViewById<Button>(R.id.button_character_details).setOnClickListener {
            findNavController().navigate(R.id.action_CharacterDetailsFragment_to_CharacterFragment)
        }
    }
}
