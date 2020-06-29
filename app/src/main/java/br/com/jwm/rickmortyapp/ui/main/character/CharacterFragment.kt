package br.com.jwm.rickmortyapp.ui.main.character

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import br.com.jwm.rickmortyapp.R
import br.com.jwm.rickmortyapp.data.api.ApiHelper
import br.com.jwm.rickmortyapp.data.api.RetrofitBuilder
import br.com.jwm.rickmortyapp.data.model.Character
import br.com.jwm.rickmortyapp.data.model.CharacterDetails
import br.com.jwm.rickmortyapp.extensions.navigateWithAnimations
import br.com.jwm.rickmortyapp.ui.base.SharedViewModel
import br.com.jwm.rickmortyapp.ui.base.ViewModelFactory
import br.com.jwm.rickmortyapp.ui.main.adapter.CharacterAdapter
import br.com.jwm.rickmortyapp.utils.Status
import kotlinx.android.synthetic.main.fragment_character.*


class CharacterFragment : Fragment(), CharacterAdapter.OnClickListener{

    private lateinit var adapter: CharacterAdapter


    // ViewModels
    private lateinit var characterViewModel: CharacterViewModel
    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    // Subir lista
    private fun setupViewModel() {
        characterViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(CharacterViewModel::class.java)
    }

    // Atualizar UI
    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter = CharacterAdapter(arrayListOf(), this)
        recyclerView.adapter = adapter
    }

    // Atualizar lista
    private fun setupObservers() {
        characterViewModel.getCharacters().observe(viewLifecycleOwner, Observer {
            it?.let {resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { characters -> retrieveList(characters.results) }
                        //resource.data?.let { characters -> characters }

                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e("JWM", it.message.toString())
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    // Atualizar conteudo da lista
    private fun retrieveList(characters: List<Character>) {
        adapter.apply {
            addCharacters(characters)
            notifyDataSetChanged()
        }
    }

    override fun onItemClick(character: Character, position: Int) {
        model.selectCharacter(character)

        //Navegação
        findNavController().navigateWithAnimations(R.id.action_CharacterFragment_to_CharacterDetailsFragment)
    }
}
