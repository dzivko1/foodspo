package altline.foodspo.domain.user

import altline.foodspo.data.user.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() {
        userRepository.signOut()
    }
}