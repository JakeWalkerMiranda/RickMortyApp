package br.com.jwm.rickmortyapp.ui.main.character

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jwm.rickmortyapp.R
import br.com.jwm.rickmortyapp.data.api.ApiHelper
import br.com.jwm.rickmortyapp.data.api.RetrofitBuilder
import br.com.jwm.rickmortyapp.data.model.Character
import br.com.jwm.rickmortyapp.data.model.CharacterDetails
import br.com.jwm.rickmortyapp.data.model.Episode
import br.com.jwm.rickmortyapp.ui.base.SharedViewModel
import br.com.jwm.rickmortyapp.ui.base.ViewModelFactory
import br.com.jwm.rickmortyapp.ui.main.adapter.EpisodeAdapter
import br.com.jwm.rickmortyapp.ui.main.episode.EpisodeViewModel
import br.com.jwm.rickmortyapp.utils.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_character_details.*
import kotlinx.android.synthetic.main.informations_character.*
import kotlinx.android.synthetic.main.recyclerview_episode.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterDetailsFragment : Fragment() {

    private lateinit var adapter: EpisodeAdapter
    private lateinit var episodeViewModel: EpisodeViewModel
    private val model: SharedViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.selectedCharacter.observe(viewLifecycleOwner, Observer<Character> { details ->
            Toast.makeText(context, details.name, Toast.LENGTH_SHORT).show()
            if (details != null) GlobalScope.launch {
                callAPI(view, details.id.toString())
            }
        })

    }

    private fun callAPI(context: View, id: String){

        val call = RetrofitBuilder.apiService.getCharactersDetails(id)

        call.enqueue(object : Callback<CharacterDetails> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<CharacterDetails>, response: Response<CharacterDetails>) {

                response.let {
                    val details: CharacterDetails? = it.body()

                    if (details != null) {
                        //CharacterDetails
                        txtCharacterDetailsName.text = details.name
                        txtCharacterDetailsStatus.text = details.status
                        txtCharacterDetailsSpecies.text = details.species
                        val x = Uri.parse(details.image)
                        Glide.with(context)
                            .asBitmap()
                            .load(x)
                            .into(object : CustomTarget<Bitmap>(){
                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                    imgCharacterDetailsImage.setImageBitmap(resource)
                                }
                                override fun onLoadCleared(placeholder: Drawable?) {
                                    // this is called when imageView is cleared on lifecycle call or for
                                    // some other reason.
                                    // if you are referencing the bitmap somewhere else too other than this imageView
                                    // clear it here as you can no longer have the bitmap
                                    Glide.get(context.context).clearMemory()
                                }
                            })

                        //CharacterInformation
                        txtCharacterDetailsGender.text = details.gender
                        txtCharacterDetailsOrigin.text = details.origin.name
                        txtCharacterDetailsType.text =  if (details.type.isEmpty()) "Unknown" else details.type
                        txtCharacterDetailsLocation.text = details.location.name

                        val episodesList = details.episode.toString()
                        val oldValue = "https://rickandmortyapi.com/api/episode/"
                        val newValue = ""

                        val range = episodesList.replace(oldValue, newValue)


                        Toast.makeText(context.context, range , Toast.LENGTH_LONG).show()

                        setupViewModel()
                        setupUI()
                        setupObservers(range)
                    }
                }
            }

            /* Caso ocorra uma falha na resposta lançamos um erro no log */
            override fun onFailure(call: Call<CharacterDetails>?, t: Throwable?) {
                Log.e("Erro", t?.message)
            }
        })
    }

    // Subir lista
    private fun setupViewModel() {
        episodeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(EpisodeViewModel::class.java)
    }

    // Atualizar UI
    private fun setupUI() {
        episodeRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = EpisodeAdapter(arrayListOf())
        episodeRecyclerView.addItemDecoration(
            DividerItemDecoration(
                episodeRecyclerView.context,
                (episodeRecyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        episodeRecyclerView.adapter = adapter
    }

    // Atualizar lista
    private fun setupObservers(range: String) {
        episodeViewModel.getCharacterEpisodes(range).observe(viewLifecycleOwner, Observer {
            it?.let {resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        episodeRecyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { episodes -> retrieveList(episodes) }
                        //resource.data?.let { characters -> characters }

                    }
                    Status.ERROR -> {
                        episodeRecyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e("JWM", it.message.toString())
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        episodeRecyclerView.visibility = View.GONE
                    }
                }
            }
        })
    }

    // Atualizar conteudo da lista
    private fun retrieveList(episodes: List<Episode>) {
        adapter.apply {
            addEpisodes(episodes)
            notifyDataSetChanged()
        }
    }
}
