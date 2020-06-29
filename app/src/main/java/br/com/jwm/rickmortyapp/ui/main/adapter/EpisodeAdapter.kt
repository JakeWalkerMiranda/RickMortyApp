package br.com.jwm.rickmortyapp.ui.main.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.jwm.rickmortyapp.R
import br.com.jwm.rickmortyapp.data.model.Episode
import kotlinx.android.synthetic.main.episode_item_layout.view.*

class EpisodeAdapter(private val episodes: ArrayList<Episode>):
    RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(episodes: Episode) {
            itemView.apply {
                txtEpisodeEpisode.text = episodes.episode
                txtEpisodeName.text = episodes.name
                txtEpisodeAirDate.text = episodes.air_date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.episode_item_layout, parent, false))

    override fun getItemCount() : Int = episodes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    fun addEpisodes(episodes: List<Episode>){
        this.episodes.apply {
            clear()
            addAll(episodes)
        }
    }

}