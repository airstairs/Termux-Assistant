package com.xthan.termux_assistant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.widget.Toast

class TermuxAssistantSession(context: Context) : VoiceInteractionSession(context) {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)

        // Termux:Float package name is "com.termux.window"
        val floatPackage = "com.termux.window"
        val launchIntent = context.packageManager.getLaunchIntentForPackage(floatPackage)

        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(launchIntent)
        } else {
            // Fallback to standard Termux if Float isn't installed
            val termuxIntent = context.packageManager.getLaunchIntentForPackage("com.termux")
            if (termuxIntent != null) {
                termuxIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(termuxIntent)
            } else {
                Toast.makeText(context, "Termux/Termux:Float not found!", Toast.LENGTH_SHORT).show()
            }
        }

        // Instantly dismiss the assistant overlay framework so it doesn't stay stuck over your screen
        finish()
    }
}