package com.f0x1d.logfox.model.logline

import androidx.annotation.Keep
import com.f0x1d.logfox.model.Identifiable
import com.f0x1d.logfox.model.preferences.ShowLogValues
import java.util.Date
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class LogLine(
    override val id: Long,
    val dateAndTime: Long,
    val uid: String,
    val pid: String,
    val tid: String,
    val packageName: String?,
    val level: LogLevel,
    val tag: String,
    val content: String,
    val originalContent: String,
) : Identifiable {

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    
    fun formatOriginal(
        values: ShowLogValues,
        formatDate: (Long) -> String = {
            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
        },
        formatTime: (Long) -> String = {
            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
        },
    ): String = values.run {
        buildString {
            if (date) append(formatDate(this@LogLine.dateAndTime) + " ")
            if (time) append(formatTime(this@LogLine.dateAndTime) + " ")
            if (uid) append(this@LogLine.uid + " ")
            if (pid) append(this@LogLine.pid + " ")
            if (tid) append(this@LogLine.tid + " ")
            if (packageName && this@LogLine.packageName != null) append(this@LogLine.packageName + " ")
            if (tag) append(this@LogLine.tag + if (content) ": " else "")
            if (content) append(this@LogLine.content)
        }
    }
}

@Keep
enum class LogLevel(val letter: String) {
    VERBOSE("V"),
    DEBUG("D"),
    INFO("I"),
    WARNING("W"),
    ERROR("E"),
    FATAL("F"),
    SILENT("S"),
}
