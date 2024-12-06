package br.com.chapada

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import br.com.chapada.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Navegacao()
    }

    private fun Navegacao() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.FragmentView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.NavBar, navController)
    }
}