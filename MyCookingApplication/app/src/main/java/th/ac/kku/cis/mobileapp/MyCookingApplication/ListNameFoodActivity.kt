package th.ac.kku.cis.mobileapp.MyCookingApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_nane_food.*

class ListNameFoodActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var adapter: ListAdapterfood
    private var listViewItems: ListView? = null
    var toDoItemList: MutableList<food>? = null

    var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_nane_food)

        floatingActionButton4.setOnClickListener {
            startActivity(Intent(this,CreateFoodActivity::class.java))
        }

        //แสดงอีเมล์
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth!!.currentUser
        result_emailData.text = user!!.email

        //ออกจากระบบ
        result_signOutBtn.setOnClickListener {
            mAuth!!.signOut()
            Toast.makeText(this,"Signed Out", Toast.LENGTH_LONG).show()
            startActivity(Intent(this@ListNameFoodActivity,MainActivity::class.java))
            finish()
        }

         //โชว์ลิส



        listViewItems = findViewById<View>(R.id.list_food) as ListView
        toDoItemList = mutableListOf<food>()
        adapter = ListAdapterfood(this, toDoItemList!!)
        listViewItems!!.setAdapter(adapter)

        mDatabase = FirebaseDatabase.getInstance().reference

        mDatabase.child("Food").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val items = dataSnapshot.children.iterator()
                //var name = getIntent().getStringExtra("name")
                // Check if current database contains any collection
                if (items.hasNext()) {
                    while (items.hasNext()) {
                        val toDoListindex = items.next()
                        val map = toDoListindex.getValue() as HashMap<String, Any>
                       // if (map.get("Food") == name) {
                            // add data to object
                            val todoItem = food.create()

                            todoItem.name_f = map.get("name_f") as String?
                            todoItem.step_f = map.get("step_f") as String?
                            toDoItemList!!.add(todoItem);
                            adapter.notifyDataSetChanged()
                        //}
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        )

        list_food.setOnItemClickListener{parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as food
            val intent = Intent(this,FoodShowActivity::class.java)
            intent.putExtra("name_f", selectedItem.name_f)
            intent.putExtra("step_f", selectedItem.step_f)
            startActivity(intent)
            finish()
        }
    }
}
