package th.ac.kku.cis.mobileapp.MyCookingApplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth : FirebaseAuth? = null
    private val TAG: String = "Login Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null){
            startActivity(Intent(this@MainActivity , ListNameFoodActivity::class.java))
            finish()
        }

        login_loginBtn.setOnClickListener{
            val email = editemail.text.toString().trim {it <= ' '}
            val password = editpassword.text.toString().trim {it <= ' '}

            if (email.isEmpty()){
                Toast.makeText(this,"Please enter your email address.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this,"Please enter your email password.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }

            mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (!task.isSuccessful){
                    if (password.length < 6) {
                        editpassword.error = "Please check your password Password. must have minimum 6 characters."
                        Log.d(TAG, "Enter password less than 6 characters.")
                    }else{
                        Toast.makeText(this,"Authentication Failed: " + task.exception, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "Authentication Failed: " + task.exception)
                    }
                }else{
                    Toast.makeText(this,"Login successfully!", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "Login successfully!")
                    startActivity(Intent(this@MainActivity,ListNameFoodActivity::class.java))
                    finish()
                }
            }
        }
        if (supportActionBar != null) // เอาแถบบนออก
            supportActionBar?.hide()
    }
}