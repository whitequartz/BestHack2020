package com.godelsoft.besthack

class User(
    val ID:   Long,
    var name: String,
    val type: UserType
) {
    companion object {
        private val userTest = User(0, "testName", UserType.WORKER)
        var current: User = User(0, "", UserType.WORKER)
//        get() {
//            // TODO user from DB
//            // User data setting after authorization
//            return userTest
//        }
    }
}

enum class UserType(val displayName: String) {
    WORKER("Работник"),
    SUPPORT("Тех. поддержка"),
    TEAM_LEAD("Тимлид");

    override fun toString(): String {
        return displayName
    }
}