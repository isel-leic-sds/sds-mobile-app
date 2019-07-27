package com.isel.ps.sds.view.profile

import java.sql.Date


class Person(
    val name: String,
    val dateBirth: Date,
    val nif: Int,
    //val phoneNumber: Int,
    val SosContact: SosContact
)

class SosContact(
    val name: String,
    val phoneNumber: Int
)