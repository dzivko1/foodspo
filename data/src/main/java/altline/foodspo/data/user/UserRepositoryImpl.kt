package altline.foodspo.data.user

import altline.foodspo.data.core.FirebaseDataSource
import altline.foodspo.data.user.mapper.UserMapper
import altline.foodspo.data.user.model.User
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val firebaseDataSource: FirebaseDataSource,
    private val mapUser: UserMapper
) : UserRepository {

    private lateinit var user: User

    override fun loadUser() {
        user = mapUser(FirebaseAuth.getInstance().currentUser!!)
        firebaseDataSource.setCurrentUser(user)
    }

    override fun signOut() {
        firebaseDataSource.signOut()
    }
}