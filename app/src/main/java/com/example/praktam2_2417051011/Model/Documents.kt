package Model
import androidx.annotation.DrawableRes

data class Documents (
    val jenis: String,
    val jumlah: Int,
    @DrawableRes val ImageRes: Int
)