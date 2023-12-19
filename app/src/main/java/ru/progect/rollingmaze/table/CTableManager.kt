package ru.progect.rollingmaze.table

import ru.data.BASE_URL
import ru.data.apis.DefaultApi

data class DataRow(val numb: String, val name: String, val result: String)

class CTableManager(val dataRowList: List<DataRow>) {
    fun getItem(position: Int): DataRow {
        return dataRowList[position]
    }
    companion object {

        /**
         * Метод возвращает записи из таблицы рекордов
         */
        fun getData(): List<DataRow>{
            val data = mutableListOf<DataRow>(DataRow("№", "Имя", "Время"))
            try {
                val api = DefaultApi(BASE_URL)
                val rows = api.scoresGet()

                var numb = 1;
                if (rows.size > 0) {
                    for (row in rows) {
                        data.add(DataRow(numb.toString(), row.userName, row.gameScore))
                        numb += 1
                    }
                } else {
                    data.add(DataRow("---", "---", "---"))
                }
            } catch (e: Exception) {
                data.add(DataRow("---", "---", "---"))
            }
            return data.toList()
        }
    }
}