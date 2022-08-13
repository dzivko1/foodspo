package altline.foodspo.data

object FirestoreSchema {
    
    object Users {
        override fun toString() = "users"
        
        object OwnRecipes {
            override fun toString() = "ownRecipes"
        }
    
        object SavedRecipes {
            override fun toString() = "savedRecipes"
        }
    }
    
    object RecipeModel {
        const val additionTime = "additionTime"
    }
}