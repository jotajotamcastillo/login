package com.example.login
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
       //splash
        Thread.sleep(5000)//hack
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_auth)
        //setup
        setup()
    }
        private fun setup(){
            title = "Autenticación"
            btnRegistro.setOnClickListener{
                if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()){
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            mostrarInicio(it.result?.user?.email?:"", ProviderType.BASIC)
                        }else{
                            mostrarAlerta()
                        }
                    }
                }
            }
            btnLogin.setOnClickListener{
                if (editTextEmail.text.isNotEmpty() && editTextPassword.text.isNotEmpty()){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(editTextEmail.text.toString(), editTextPassword.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            mostrarInicio(it.result?.user?.email?:"", ProviderType.BASIC)
                        }else{
                            mostrarAlerta()
                        }
                    }
                }
            }
        }
        private fun mostrarAlerta(){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("Se ha producido error de autenticación")
            builder.setPositiveButton("Aceptar", null)
            val dialog: AlertDialog= builder.create()
            dialog.show()
        }

    private fun mostrarInicio(email: String, provider: ProviderType){
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}