package Model
import com.google.gson.annotations.SerializedName
data class Documents (
    @SerializedName("jenis")
    val jenis: String,
    @SerializedName("jumlah")
    val jumlah: Int,
    @SerializedName("image_url")
    val imageUrl: String
)