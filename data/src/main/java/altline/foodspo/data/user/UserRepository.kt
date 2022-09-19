package altline.foodspo.data.user

interface UserRepository {

    fun loadUser()

    fun signOut()
}