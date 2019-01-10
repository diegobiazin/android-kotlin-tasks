package com.diegobiazin.task.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.diegobiazin.task.R
import com.diegobiazin.task.business.UserBusiness
import com.diegobiazin.task.repository.UserRepository
import com.diegobiazin.task.util.ValidationException
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Exception

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //eventos
        setListeners()

        //Intanciar variaveis da classe
        mUserBusiness = UserBusiness(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonSave -> {
                handleSave()
            }
        }
    }

    private fun handleSave() {

        try {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            //Inserção do usuário
            mUserBusiness.insert(name, email, password)
        } catch (e: ValidationException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.erro_inesperado), Toast.LENGTH_LONG).show()
        }
    }

    private fun setListeners() {
        buttonSave.setOnClickListener(this)
    }
}
