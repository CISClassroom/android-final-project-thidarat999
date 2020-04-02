package th.ac.kku.cis.mobileapp.MyCookingApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_food_show.*
import kotlinx.android.synthetic.main.activity_list_nane_food.*

class FoodShowActivity : AppCompatActivity() {
    lateinit var mDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_show)


       // var id = getIntent().getExtras()!!.getString("id")
        //mDatabase = FirebaseDatabase.getInstance().reference
        //       mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)


        var name_food = getIntent().getStringExtra("name_f")
        var staple_food = getIntent().getStringExtra("staple_f")
        var step_food = getIntent().getStringExtra("step_f")

        textView5.text = name_food
        textView11.text = staple_food
        textView9.text = step_food




















//    var itemListener: ValueEventListener = object : ValueEventListener {
//
//        override fun onDataChange(dataSnapshot: DataSnapshot) {
//            // call function
//            addDataToList(dataSnapshot.child("Food"))
//        }
//
//        override fun onCancelled(databaseError: DatabaseError) {
//            // Getting Item failed, display log a message
//
//        }
//        fun addDataToList(dataSnapshot: DataSnapshot) {
//            var id = getIntent().getExtras()!!.getString("id")
//            val items = dataSnapshot.children.iterator()
//            if(items.hasNext()){
//                while (items.hasNext()){
//                    val currentItem = items.next().getValue() as HashMap<String, Any>
//                    if (currentItem.get("id")==id){
//                        textView5.text = currentItem.get("name_f") as String
//                        textView9.text = currentItem.get("step_f") as String
//
//                    }
//                }
//            }
        //       }
        //   }
    }
}
