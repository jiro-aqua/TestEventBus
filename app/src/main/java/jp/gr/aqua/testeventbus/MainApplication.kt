package jp.gr.aqua.testeventbus

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Create the NotificationChannel
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL", "ForegroundService", importance)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        val appModule = module {
            // single instance
            single { EventBus() }
        }

        // Start Koin
        startKoin {
            modules(appModule)
        }

    }

}