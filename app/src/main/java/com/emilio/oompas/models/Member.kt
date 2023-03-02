package com.emilio.oompas.models

import android.os.Parcel
import android.os.Parcelable

data class Member(
    val id: Int,
    val first_name: String?,
    val last_name: String?,
    val favorite: Favorite?,
    val gender: String?,
    val image: String?,
    val profession: String?,
    val email: String?,
    val age: Int,
    val country: String?,
    val height: Int,
    val description: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Favorite::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(first_name)
        parcel.writeString(last_name)
        parcel.writeParcelable(favorite, flags)
        parcel.writeString(gender)
        parcel.writeString(image)
        parcel.writeString(profession)
        parcel.writeString(email)
        parcel.writeInt(age)
        parcel.writeString(country)
        parcel.writeInt(height)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Member> {
        override fun createFromParcel(parcel: Parcel): Member {
            return Member(parcel)
        }

        override fun newArray(size: Int): Array<Member?> {
            return arrayOfNulls(size)
        }
    }
}
