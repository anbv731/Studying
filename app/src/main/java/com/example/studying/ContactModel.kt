package com.example.studying

class ContactModel {
    private var name:String?=null

    fun setNames(name:String?){
        this.name=name
    }
    fun getNames(): String {
        return name.toString()
    }
}