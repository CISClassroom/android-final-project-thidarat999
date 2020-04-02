package th.ac.kku.cis.mobileapp.MyCookingApplication

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_create_food.*
import java.net.URI

class CreateFoodActivity : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    lateinit var imageview : ImageView
    private val PERMISSION_CODE =1000 ;
    lateinit var chooseImage : Button
    lateinit var AddFood : Button
    lateinit var imguri : Uri
    lateinit var storageRef : StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_food)

        storageRef = FirebaseStorage.getInstance().getReference("images")
        imageview = findViewById<ImageView>(R.id.imageView)
        mDatabase = FirebaseDatabase.getInstance().reference
        chooseImage = findViewById(R.id.addImage_bt) as Button
        AddFood = findViewById(R.id.save_bt) as Button

        chooseImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    //permission was not enabled
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request permission
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    FileChoose()
                }
            }
            else{
                //system os is < marshmallow
                FileChoose()
            }

        }
        save_bt.setOnClickListener {

            AddData()
            FileUploader()

        }
    }
    private fun getExtension(uri: Uri): String? {
        var cr = contentResolver
        var mimetypemap = MimeTypeMap.getSingleton()
        return mimetypemap.getExtensionFromMimeType(cr.getType(uri))
    }
    private fun FileUploader(){

        var ref = storageRef.child(""+System.currentTimeMillis()+"."+getExtension(imguri))
        ref.putFile(imguri)
            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot -> // Get a URL to the uploaded content
                // val downloadUrl: Uri = taskSnapshot.uploadSessionUri!!
                Toast.makeText(this,"Image success", Toast.LENGTH_LONG).show()
            })
            .addOnFailureListener(OnFailureListener {
                // Handle unsuccessful uploads
                // ...
            })
    }

    private fun FileChoose(){
        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,1)
    }


    fun AddData() {

        var name = getIntent().getStringExtra("name")
        var newData: food = food.create()
        val obj = mDatabase.child("Food").push()

        newData.name_f = AddNameFood.text.toString()
        newData.staple_f = AddNameStaple.text.toString()
        newData.step_f = AddNameStep.text.toString()

//        newData.EvenId = obj.key
        obj.setValue(newData)

        var i = Intent(this, ListNameFoodActivity::class.java)
//        FileUploader()
        i.putExtra("name",name)
        startActivity(i)
        finish()//ถ้ากดปุ่มเสร็จจะเด้งออกไปหน้า ก่อนหน้านี้

        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){

            imguri = data.getData()!!
            imageview.setImageURI(imguri)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    FileChoose()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
