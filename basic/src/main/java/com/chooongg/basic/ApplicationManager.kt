package com.chooongg.basic

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*

val APPLICATION = ApplicationManager.getApplication()

val APPLICATION_OR_NULL = ApplicationManager.getApplicationOrNull()

val ACTIVITY_TASK: List<Activity> get() = ApplicationManager.ActivityLifecycleManager.activityTask

val ACTIVITY_TOP get() = if (ApplicationManager.ActivityLifecycleManager.activityTask.isEmpty()) null else ApplicationManager.ActivityLifecycleManager.activityTask.last

internal object ApplicationManager {
    private var application: Application? = null

    internal fun initialize(application: Application) {
        if (this.application != null) throw LearnFrameException("ApplicationManager has not been initialized")
        this.application = application
        this.application!!.registerActivityLifecycleCallbacks(ActivityLifecycleManager)
    }

    internal fun getApplication(): Application {
        return application
            ?: throw LearnFrameException("ApplicationManager has not been initialized")
    }

    internal fun getApplicationOrNull() = application

    internal object ActivityLifecycleManager : Application.ActivityLifecycleCallbacks {

        internal val activityTask = LinkedList<Activity>()

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityTask.add(activity)
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            activityTask.remove(activity)
        }
    }
}