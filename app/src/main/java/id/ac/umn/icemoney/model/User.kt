package id.ac.umn.icemoney.model

data class User(
    val id: Long,
    val username: String,
    val password: String,
    val email: String,
    val phone: String? = "",
    val name: String,
)
