package id.ac.umn.icemoney.model

data class User(
    var id: Long,
    var username: String,
    var password: String,
    var email: String,
    var phone: String? = "",
    var name: String,
)
