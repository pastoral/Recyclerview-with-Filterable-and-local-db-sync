package com.hashpeak.bpdbcontacts.bindings

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hashpeak.bpdbcontacts.adapters.ContactListAdapter
import com.hashpeak.bpdbcontacts.models.Contacts

//@BindingAdapter("contactsListData")
//fun bindNewsAdapter(recyclerView: RecyclerView, data:List<Contacts>?){
//    val adapter = recyclerView.adapter as ContactListAdapter
//    adapter.submitList(data)
//}
@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

/**
 * Binding adapter used to display images from URL using Glide
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).into(imageView)
}


//@BindingAdapter("phoneCorrection")
//fun banglaPhoneCorrection(txtView: TextView, str: String) {
//    var words = str.toString().trim()
//    val numberOfInputWords = str.split("\\s+".toRegex()).size
//    if (numberOfInputWords<11){
//        words = "0".plus(str)
//    }
//    txtView.text = words
//}

@BindingAdapter("goneIfNotExists")
fun goneIfNotExists(view: View, it: Any?) {
    //view.visibility = if (it != null) View.GONE else View.VISIBLE
    if(it == null||it.toString().length<2){
        view.visibility = View.GONE
    }
    else{
        view.visibility = View.VISIBLE
    }
}
