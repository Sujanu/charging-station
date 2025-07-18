package com.example.chargingstation.activites

object ValidationUtil {
    fun isFieldEmpty(input: String): Boolean {
        return input.trim().isEmpty()
    }

    fun isNumericField(input: String): Boolean {
        return input.trim().isNotEmpty() && input.trim().toLongOrNull() != null
    }

    fun isDoubleField(input: String): Boolean {
        return input.trim().isNotEmpty() && input.trim().toDoubleOrNull() != null
    }

    fun isValidPhoto(photo: String?): Boolean {
        return !photo.isNullOrEmpty()
    }
}