package br.com.jwm.rickmortyapp.ui.main.episode

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jwm.rickmortyapp.R
import br.com.jwm.rickmortyapp.data.api.ApiHelper
import br.com.jwm.rickmortyapp.data.api.RetrofitBuilder
import br.com.jwm.rickmortyapp.data.model.Episode
import br.com.jwm.rickmortyapp.ui.base.ViewModelFactory
import br.com.jwm.rickmortyapp.ui.main.adapter.EpisodeAdapter
import br.com.jwm.rickmortyapp.utils.Status
import kotlinx.android.synthetic.main.recyclerview_episode.*

class EpisodeFragment : Fragment() {

    private lateinit var adapter: EpisodeAdapter
    private lateinit var episodeViewModel: EpisodeViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_episode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupUI()
        setupObservers()
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
    private fun setupObservers() {
        episodeViewModel.getEpisodes().observe(viewLifecycleOwner, Observer {
            it?.let {resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        episodeRecyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { episodes -> retrieveList(episodes.results) }
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
