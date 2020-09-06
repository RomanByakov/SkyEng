package com.rbyakov.skyeng.network.models

import java.io.Serializable

data class Word(
    val id: Int,
    val text: String,
    val meanings: List<Meaning>,
    var expanded: Boolean
) : Serializable

data class Meaning(
    val id: Int,
    val previewUrl: String,
    val imageUrl: String,
    val transcription: String,
    val translation: Translation
) : Serializable

data class Translation(
    val text: String,
    val note: String
) : Serializable