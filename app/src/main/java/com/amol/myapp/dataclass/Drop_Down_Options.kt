package com.amol.myapp.dataclass

import com.google.gson.annotations.SerializedName

data class Drop_Down_Options (

	@SerializedName("id") val id : Int,
	@SerializedName("view_text") val view_text : String
)