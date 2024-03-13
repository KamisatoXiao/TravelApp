package com.example.mainactivity.util

import java.sql.Connection
import java.sql.DriverManager

class DatabaseUtils {
    companion object{
        fun connect2Database():Connection{
            Class.forName("com.mysql.jdbc.Driver")
            val connection: Connection = DriverManager.getConnection(
                "jdbc:mysql://rm-bp1c01kn7va4r8nlhqo.mysql.rds.aliyuncs.com:3306/touristapp?useSSL=true",
                "kamisatoxiao",
                "lihaipeng010717@")
            return connection
        }
    }
}