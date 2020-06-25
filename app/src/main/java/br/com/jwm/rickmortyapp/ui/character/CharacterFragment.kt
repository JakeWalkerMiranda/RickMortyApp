package br.com.jwm.rickmortyapp.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import br.com.jwm.rickmortyapp.R

class CharacterFragment : Fragment() {

    private lateinit var characterViewModel: CharacterViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        characterViewModel =
                ViewModelProvider(this).get(CharacterViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_character, container, false)
        val textView: TextView = root.findViewById(R.id.text_character)
        characterViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.button_character).setOnClickListener {
            val action = CharacterFragmentDirections
                    .actionHomeFragmentToHomeSecondFragment("From CharacterFragment")
            NavHostFragment.findNavController(this@CharacterFragment)
                    .navigate(action)
        }
    }
}
