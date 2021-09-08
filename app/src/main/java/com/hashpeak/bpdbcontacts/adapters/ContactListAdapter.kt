package com.hashpeak.bpdbcontacts.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hashpeak.bpdbcontacts.databinding.FragmentContactListBinding
import com.hashpeak.bpdbcontacts.databinding.ItemContactlistRecyclerviewBinding
import com.hashpeak.bpdbcontacts.models.Contacts


class ContactListAdapter(
    var clickListner: OnClickListner,
)
    :RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder>(), Filterable{

    var contactList = ArrayList<Contacts>()
    var filteredContactList = ArrayList<Contacts>()

    fun setData(contactList: ArrayList<Contacts>){
        this.contactList = contactList
        this.filteredContactList = contactList
        notifyDataSetChanged()
    }

//    companion object DiffCallBack : DiffUtil.ItemCallback<Contacts>(){
//        override fun areContentsTheSame(oldItem: Contacts, newItem: Contacts): Boolean {
//            return oldItem.designition == newItem.designition
//        }
//
//        override fun areItemsTheSame(oldItem: Contacts, newItem: Contacts): Boolean {
//            return oldItem === newItem
//        }
//    }
//
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        return ContactListViewHolder(ItemContactlistRecyclerviewBinding.inflate(LayoutInflater.from(parent.context)))
    }



    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        val contacts = contactList[position]
        holder.initialize(contacts)
        holder.itemView.setOnClickListener{
            clickListner.onClick(contacts)
        }
    }



    override fun getItemCount(): Int {
        return contactList.size
    }


    class ContactListViewHolder(private var binding: ItemContactlistRecyclerviewBinding)
        :RecyclerView.ViewHolder(binding.root){
        fun initialize(items:Contacts){
            binding.contact = items
            binding.executePendingBindings()
        }
    }


    override fun getFilter(): Filter {
        return object:Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filterResult = FilterResults()

//                contactList.clear()

                if(constraint==null||constraint.isEmpty()){
                    filterResult.count = filteredContactList.size
                    filterResult.values = filteredContactList
                }
                else{
                    var searchChr:String = constraint.toString().toLowerCase()
                    var contacts = ArrayList<Contacts>()
                    for(items in filteredContactList){
                        if(items.designition.toLowerCase().contains(searchChr)
                            ||items.office!!.toLowerCase().contains(searchChr)
                            ||items.zone!!.toLowerCase().contains(searchChr)){

                            contacts.add(items)
                        }
                    }
                    filterResult.count = contacts.size
                    filterResult.values = contacts
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactList = results!!.values as ArrayList<Contacts>
                notifyDataSetChanged()
            }
        }
    }

}


class OnClickListner(val clicklistner : (contacts:Contacts)->Unit){
    fun onClick(contacts: Contacts){
        clicklistner(contacts)
    }


}
