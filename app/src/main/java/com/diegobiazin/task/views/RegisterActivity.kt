package com.diegobiazin.task.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.diegobiazin.task.R
import com.diegobiazin.task.business.UserBusiness
import com.diegobiazin.task.repository.UserRepository
import kotlinx.android.synthetic.main.activity_register.*

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
        val name = editName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        //Inserção do usuário
        mUserBusiness.insert(name, email, password)
    }

    private fun setListeners() {
        buttonSave.setOnClickListener(this)
    }
}
