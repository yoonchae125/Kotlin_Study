package com.chaeyoon.rxjavastudy

import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.ServerSocket
import java.net.Socket

class SingleThread {
    companion object{
        val RESPONSE = (
                "HTTP/1.1 200 OK\r\n" +
                "Content-length: 2\r\n"+
                "\r\n"+
                "OK").toByteArray()
    }
    fun main(){
        val serverSocket = ServerSocket(8080, 100)
        while(!Thread.currentThread().isInterrupted){
            val client = serverSocket.accept()
            handle(client)
        }
    }
    fun handle(client: Socket){
        try{
            while (!Thread.currentThread().isInterrupted()){
                readFullRequest(client)
                client.getOutputStream().write(RESPONSE)
            }
        }catch (e:Exception){
            e.printStackTrace()
            client.close()
        }
    }
    fun readFullRequest(client:Socket){
        val reader = BufferedReader( InputStreamReader(client.getInputStream()))
        var line = reader.readLine()
        while(line!=null && !line.isEmpty()){
            line = reader.readLine()
        }
    }
}