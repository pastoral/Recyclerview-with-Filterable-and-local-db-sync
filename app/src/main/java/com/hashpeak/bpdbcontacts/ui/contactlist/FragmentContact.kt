package com.hashpeak.bpdbcontacts.ui.contactlist

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hashpeak.bpdbcontacts.R
import com.hashpeak.bpdbcontacts.adapters.ContactListAdapter
import com.hashpeak.bpdbcontacts.adapters.OnClickListner
import com.hashpeak.bpdbcontacts.databinding.FragmentContactListBinding

import android.widget.ArrayAdapter




class FragmentContact : Fragment() {


    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onViewCreated(), which we
     * do in this Fragment.
     */
    private val contactViewModel: ContactListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        //The ViewModelProviders (plural) is deprecated.
        //ViewModelProviders.of(this, DevByteViewModel.Factory(activity.application)).get(DevByteViewModel::class.java)
        ViewModelProvider(this, ContactListViewModel.Factory(activity.application)).get(ContactListViewModel::class.java)

    }

    /**
     * RecyclerView Adapter for converting a list of Contacts to cards.
     */
    private var contactAdapter : ContactListAdapter? = null
//    lateinit var contactAdapter : ContactListAdapter

    private lateinit var binding: FragmentContactListBinding
//    lateinit var contactViewModel: ContactListViewModel


//    val viewModelFactory = ContactListViewModelFactory(application)
//    private val contactViewModel: ContactListViewModel by lazy {
//        val activity = requireNotNull(this.activity) {
//            "You can only access the viewModel after onViewCreated()"
//        }
//        ViewModelProvider(this,viewModelFactory)
//            .get(ContactListViewModel::class.java)
//    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val application = requireNotNull(this.activity).application
//        val viewModelFactory = ContactListViewModelFactory(application)
//        contactViewModel = ViewModelProvider(this,viewModelFactory).get(ContactListViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list,container,false)

//        contactAdapter = ContactListAdapter(
//            OnClickListner {contactViewModel.displayContactDetails(it)}
//        )

// //        binding.recyclerViewContact.adapter = ContactListAdapter(
// //            OnClickListner {contactViewModel.displayContactDetails(it)}
// //        )
//        binding.recyclerViewContact.adapter = contactAdapter
//
//        contactViewModel.contactlist.observe(viewLifecycleOwner, Observer {
//            contactAdapter.setData(ArrayList(it))
//            Log.v("TAGS",ArrayList(it).size.toString() )
//        })

        binding.viewModel = contactViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactViewModel.contactlist.observe(viewLifecycleOwner, Observer {
            contactAdapter!!.setData(ArrayList(it))
            Log.v("TAGS",ArrayList(it).size.toString() )
        })

        contactAdapter = ContactListAdapter(
            OnClickListner {contactViewModel.displayContactDetails(it)}
        )


        binding.recyclerViewContact.adapter = contactAdapter

//        contactViewModel.contactlist.observe(viewLifecycleOwner, Observer {
//            contactAdapter!!.setData(ArrayList(it))
//            Log.v("TAGS",ArrayList(it).size.toString() )
//        })


        contactViewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if(null != it){
                val phoneList = arrayOf("tel:"+it.phone_1, "tel:"+it.phone_2)
                // Toast.makeText(activity,"Clicked on:"+" "+it.designition,Toast.LENGTH_SHORT).show()
//                val intent = Intent(Intent.ACTION_DIAL)
//                intent.data = Uri.parse("tel:"+it.phone_1)
//                startActivity(intent)


                val builderSingle = AlertDialog.Builder( requireNotNull(this.activity))
                builderSingle.setIcon(R.drawable.ic_phone)
                builderSingle.setTitle("Select One Number:-")

                val arrayAdapter = ArrayAdapter<String>(
                    requireNotNull(this.activity),
                    android.R.layout.select_dialog_item
                )
                arrayAdapter.add(it.phone_1)
                if(it.phone_2!=null) {
                    arrayAdapter.add(it.phone_2)
                }


//                builderSingle.setNegativeButton(
//                    "cancel"
//                ) { dialog, which -> dialog.dismiss() }

                builderSingle.setAdapter(
                    arrayAdapter
                ) { dialog, which ->
//                    val strName = arrayAdapter.getItem(which)
//                    val builderInner =
//                        AlertDialog.Builder(requireNotNull(this.activity))
//                    builderInner.setMessage(strName)
//                    builderInner.setTitle("Your Selected Item is")
//                    builderInner.setPositiveButton(
//                        "Ok"
//                    ) { dialog, which -> dialog.dismiss() }
                    //builderInner.show()
                    when (which) {
                        0 ->{
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:"+it.phone_1)
                            startActivity(intent)
                        }
                        1 -> {
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:"+it.phone_2)
                            startActivity(intent)
                        }
                    }
                }
                builderSingle.show()

                contactViewModel.displayPropertyDetailsComplete()
            }
        })


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu,menu)
        var menuItem = menu!!.findItem(R.id.search_view_menu)
        var searchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        //menuItem.expandActionView()

//        searchView.setIconifiedByDefault(true);
//        searchView.setIconified(false);

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.v("TAG", "menu > "+newText)
                contactAdapter!!.filter.filter(newText)
                return true
            }
        })
//        return super.onCreateOptionsMenu(menu)
//        return true
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu,menu)
//        var menuItem = menu!!.findItem(R.id.search_view_menu)
//        var searchView = menuItem.actionView as SearchView
//        searchView.maxWidth = 20
//
//        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                Log.v("TAG", "menu > "+newText)
//                return true
//            }
//        })
////        return super.onCreateOptionsMenu(menu)
//        return true
//    }



}