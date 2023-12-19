package ru.progect.rollingmaze.table

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.awaitResponse
import ru.data.CGame
import ru.data.СApiManager

data class DataRow(val numb: String, val name: String, val result: String)

class CTableManager(val dataRowList: List<DataRow>) {
    fun getItem(position: Int): DataRow {
        return dataRowList[position]
    }
    companion object {
        fun getData(): List<DataRow>{
            val data = mutableListOf<DataRow>(DataRow("№", "Имя", "Время"))

            data.add(DataRow("1", "Name1", "12.2"))
            data.add(DataRow("2", "Name2", "13.2"))
            data.add(DataRow("3", "Name3", "14.2"))
            data.add(DataRow("4", "Name4", "15.2"))
            data.add(DataRow("5", "Name5", "16.2"))
            data.add(DataRow("6", "Name6", "17.2"))
            data.add(DataRow("7", "Name2", "13.2"))
            data.add(DataRow("8", "Name3", "14.2"))
            data.add(DataRow("9", "Name4", "15.2"))
            data.add(DataRow("10", "Name5", "16.2"))
            data.add(DataRow("11", "Name6", "17.2"))
            data.add(DataRow("12", "Name2", "13.2"))
            data.add(DataRow("13", "Name3", "14.2"))
            data.add(DataRow("14", "Name4", "15.2"))
            data.add(DataRow("15", "Name5", "16.2"))
            data.add(DataRow("16", "Name6", "17.2"))
            data.add(DataRow("17", "Name2", "13.2"))
            data.add(DataRow("18", "Name3", "14.2"))
            data.add(DataRow("19", "Name4", "15.2"))
            data.add(DataRow("20", "Name5", "16.2"))
            data.add(DataRow("21", "Name6", "17.2"))
            return data.toList()
        }


        /**
         * Метод возвращает записи из таблицы рекордов
         */
        private suspend fun getListCGame(): List<CGame> {
            val apiManager = СApiManager()
            val call: Call<List<CGame>> = apiManager.apiService.getGamesList()

            return withContext(Dispatchers.IO) {
                try {
                    val response = call.awaitResponse()

                    if (response.isSuccessful) {
                        response.body() ?: emptyList()
                    } else {
                        emptyList()
                    }
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }
    }
}