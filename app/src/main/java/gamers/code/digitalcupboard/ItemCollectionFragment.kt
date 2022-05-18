package gamers.code.digitalcupboard


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import gamers.code.digitalcupboard.data.ListAdapterItemRV
import gamers.code.digitalcupboard.data.model.Item
import gamers.code.digitalcupboard.databinding.FragmentItemCollectionBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemCollectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemCollectionFragment : Fragment(), ListAdapterItemRV.Interaction {

    private var param1: String? = null
    private var param2: String? = null
    lateinit var itemListAdapter: ListAdapterItemRV
    private var _binding: FragmentItemCollectionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemCollectionBinding.inflate(inflater, container, false)
        val root: View = binding.root
        adapter()
        return root
    }

    fun adapter(){
        val recycler_view = binding.itemCollectionRV
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            itemListAdapter = ListAdapterItemRV(this@ItemCollectionFragment)
            adapter = itemListAdapter

        }
        val db = Firebase.firestore
        val list = mutableListOf<Item>()
        db.collection("category")
            .get()
            .addOnSuccessListener { result ->

                for ((x, document1) in result.withIndex()) {
                    db.collection("category").document(document1.id).collection("item").get()
                        .addOnSuccessListener { r ->

                            for ((i, d) in r.withIndex()) {

                                Log.d("OMG", "FOUND IT"+d.toString())
                                list.add(
                                    i, Item(
                                        d.id.toString(),
                                        d.data.get("name") as String?,
                                        d.data.get("dateAcquired") as com.google.firebase.Timestamp?,
                                        d.data.get("description") as String?,
                                    )
                                )
                            }
                        }
                }
                list.sort()
                itemListAdapter.submitList(list)
                Log.d("OMG", list.toString())
            }
            .addOnFailureListener {
                Toast.makeText(activity, "Failed to get database", Toast.LENGTH_LONG).show()
            }
        Log.d("OMG", "MADE IT")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ItemCollectionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ItemCollectionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemSelected(position: Int, item: Item) {

    }
}