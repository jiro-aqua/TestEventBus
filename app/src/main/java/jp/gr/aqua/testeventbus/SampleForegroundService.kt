package jp.gr.aqua.testeventbus

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SampleForegroundService : Service(), KoinComponent {

    val eventBus by inject<EventBus>()

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notification = NotificationCompat.Builder(this, "CHANNEL" ).apply {
            setContentTitle("TestEventBus")
            setContentText("Foreground Service")
            setSmallIcon(R.mipmap.ic_launcher)
        }.build()

        scope.launch {
            (0 until 20).forEach { _ ->
                val newValue = eventBus.betaState.value + 1
                eventBus.setBetaState(newValue)
                Log.d("===","send $newValue")
                delay(1000L)
            }
            stopSelf()
        }.start()

        startForeground(1, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        Log.d("===","service destroyed")
    }

}