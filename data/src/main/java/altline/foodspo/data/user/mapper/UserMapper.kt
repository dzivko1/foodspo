package altline.foodspo.data.user.mapper

import altline.foodspo.data.user.model.User
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class UserMapper @Inject constructor() {

    operator fun invoke(raw: FirebaseUser) = User(
        uid = raw.uid
    )
}