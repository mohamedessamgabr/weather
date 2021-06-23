package com.example.firstdemo.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class AlertWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        val appContext = applicationContext
        makeNotification("Data is coming", appContext)

        return try {

            getAlerts("68.3963", "36.9419", "current,minutely,hourly,daily", "metric", "en", appContext)
            //Result.retry()
            Result.success()
        } catch (throwable : Throwable) {
            Log.e("Essam", "Error Applying")
            Result.failure()
        }
    }
}