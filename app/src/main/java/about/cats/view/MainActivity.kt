package about.cats.view

import about.cats.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationHostFragment = supportFragmentManager.findFragmentById(R.id.navigationHostFragment)
        val navigationController = navigationHostFragment!!.findNavController()
        findViewById<BottomNavigationView>(R.id.bottomNavigationView).apply {
            setupWithNavController(navigationController)
        }
    }
}