package framework.database

import java.io.File
import java.io.InputStream
import java.sql.*

class SqliteConnection private constructor(): DatabaseConnection {

    private lateinit var conn: Connection
    init {
        this.connect()
    }

    companion object {
        @Volatile private var instance: SqliteConnection? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: SqliteConnection().also { instance = it }
            }
    }

    override fun execute(sql: String) {
        this.conn.createStatement().executeUpdate(sql)
    }

    override fun query(sql: String): ResultSet {
        return this.conn.createStatement().executeQuery(sql)
    }

    override fun close() {
        this.conn.close()
    }

    private fun connect(): SqliteConnection {
        val database = File("src/main/resources/db/advertisements.sqlite")
        val createDatabaseFile = File("src/main/resources/db/migrations/migration.sql")

        var createDatabase = false

        if (!database.exists()) {
            createDatabase = true
        }

        if (!this::conn.isInitialized) {
            this.conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/db/advertisements.sqlite")
        }

        if (createDatabase) {
            val inputStream: InputStream = createDatabaseFile.inputStream()
            val inputString = inputStream.bufferedReader().use { it.readText() }
            this.conn.createStatement().executeUpdate(inputString)
        }

        return this
    }
}
