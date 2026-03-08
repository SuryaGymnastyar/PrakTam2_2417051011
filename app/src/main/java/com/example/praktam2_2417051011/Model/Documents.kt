package Model
import androidx.annotation.DrawableRes

data class Documents (
    val jenis: String,
    val jumlah: String,
    @DrawableRes val ImageRes: Int
)