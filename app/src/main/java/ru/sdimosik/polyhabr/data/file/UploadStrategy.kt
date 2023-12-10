package ru.sdimosik.polyhabr.data.file


interface UploadStrategy {
    suspend fun upload(readFileResult: ReadFileResult)
}
