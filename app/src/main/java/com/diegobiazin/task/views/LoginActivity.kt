package com.diegobiazin.task.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.diegobiazin.task.R
import com.diegobiazin.task.business.UserBusiness
import com.diegobiazin.task.constants.TaskConstants
import com.diegobiazin.task.util.SecurityPreferences
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mSecurityPreferences: SecurityPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Instanciar variáveis da classe
        mUserBusiness = UserBusiness(this)
        mSecurityPreferences = SecurityPreferences(this)

        setListeners()

        verifyLoggedUser()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLogin -> {
                handleLogin()
            }
        }
    }

    private fun handleLogin() {
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        if (mUserBusiness.login(email, password)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, getString(R.string.usuario_senha_incorretos), Toast.LENGTH_LONG).show()
        }
    }

    private fun setListeners() {
        buttonLogin.setOnClickListener(this)
    }

    private fun verifyLoggedUser() {
        val userId = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_ID)
        val name = mSecurityPreferences.getStoredString(TaskConstants.KEY.USER_NAME)

        // Usuário logado
        if (userId != "" && name != "") {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
