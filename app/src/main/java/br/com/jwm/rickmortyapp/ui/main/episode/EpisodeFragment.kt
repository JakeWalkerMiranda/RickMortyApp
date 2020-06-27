package br.com.jwm.rickmortyapp.ui.main.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.jwm.rickmortyapp.R

class EpisodeFragment : Fragment() {

    private lateinit var episodeViewModel: EpisodeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        episodeViewModel =
                ViewModelProvider(this).get(EpisodeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_episode, container, false)
        val textView: TextView = root.findViewById(R.id.text_episode)
        episodeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
