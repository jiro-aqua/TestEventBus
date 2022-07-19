package jp.gr.aqua.testeventbus

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import jp.gr.aqua.testeventbus.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), KoinComponent {

    val eventBus by inject<EventBus>()
    var mainText = "Start!\n"
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainText.text = mainText

        lifecycleScope.launch {
            whenResumed {
                eventBus.betaState.collect {
                    mainText += "received $it\n"
                    binding.mainText.text = mainText
                }
            }
        }

        startForegroundService(Intent(this,SampleForegroundService::class.java))
    }
}