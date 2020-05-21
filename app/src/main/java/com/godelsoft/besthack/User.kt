package com.godelsoft.besthack

class User(
    val ID:   Long,
    var name: String,
    val type: UserType
) {
    companion object {
        private val userTest = User(0, "testName", UserType.WORKER)
        val current: User
        get() {
            //TODO user from DB
            return userTest
        }
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