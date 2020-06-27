package br.com.jwm.rickmortyapp.ui.main.adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView
import br.com.jwm.rickmortyapp.R
import br.com.jwm.rickmortyapp.data.model.Character
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.cardview_item_character.view.*


class CharacterAdapter(private val characters: ArrayList<Character>) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(characters: Character) {
            itemView.apply {
                txtCharacterName.text = characters.name
                txtCharacterStatus.text = characters.status


                val x = Uri.parse(characters.image)
                Glide.with(this)
                    .asBitmap()
                    .load(x)
                    .into(object : CustomTarget<Bitmap>(){
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            imgCharacterImage.setImageBitmap(resource)
                        }
                        override fun onLoadCleared(placeholder: Drawable?) {
                            // this is called when imageView is cleared on lifecycle call or for
                            // some other reason.
                            // if you are referencing the bitmap somewhere else too other than this imageView
                            // clear it here as you can no longer have the bitmap
                            Glide.get(context).clearDiskCache()
                            Glide.get(context).clearMemory()
                        }
                    })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
         ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cardview_item_character, parent, false))


    override fun getItemCount() : Int = characters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    fun addCharacters(characters: List<Character>){
        this.characters.apply {
            clear()
            addAll(characters)
        }
    }
}