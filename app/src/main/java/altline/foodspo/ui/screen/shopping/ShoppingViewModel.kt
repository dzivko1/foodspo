package altline.foodspo.ui.screen.shopping

import altline.foodspo.data.NEW_SHOPPING_ITEM_PREFIX
import altline.foodspo.data.ingredient.model.ShoppingItem
import altline.foodspo.domain.ingredient.AddToShoppingListUseCase
import altline.foodspo.domain.ingredient.EditShoppingListUseCase
import altline.foodspo.domain.ingredient.GetShoppingListUseCase
import altline.foodspo.domain.ingredient.RemoveFromShoppingListUseCase
import altline.foodspo.error.onError
import altline.foodspo.ui.core.ViewModelBase
import altline.foodspo.ui.ingredient.IngredientUiMapper
import altline.foodspo.ui.ingredient.component.ShoppingListItemUi
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val getShoppingListUseCase: GetShoppingListUseCase,
    private val addToShoppingListUseCase: AddToShoppingListUseCase,
    private val removeFromShoppingListUseCase: RemoveFromShoppingListUseCase,
    private val editShoppingListUseCase: EditShoppingListUseCase,
    private val ingredientUiMapper: IngredientUiMapper
) : ViewModelBase<ShoppingScreenUi>() {

    init {
        loadData()
    }

    override fun loadData() {
        setLoading(true)
        viewModelScope.launch {
            getShoppingListUseCase().collect { result ->
                setLoading(false)

                // If there is a new (unsaved) item before refresh, create one afterwards
                val hasUnsaved = uiState.data?.shoppingItems?.get(null)
                    ?.any { it.id.startsWith(NEW_SHOPPING_ITEM_PREFIX) } == true

                setUiData(
                    ingredientUiMapper.toShoppingScreenUi(
                        result,
                        onTextChange = this@ShoppingViewModel::updateItemText,
                        onAddItem = this@ShoppingViewModel::addItem,
                        onCheckedChange = this@ShoppingViewModel::setItemCheck,
                        onEditingChange = this@ShoppingViewModel::setItemEdit,
                        onRemove = this@ShoppingViewModel::removeItem
                    )
                )

                if (hasUnsaved) addItem()
            }
        }
    }

    private fun updateItemText(recipeTitle: String?, itemId: String, text: String) {
        updateItem(recipeTitle, itemId) { it.copy(text = text) }
    }

    private fun addItem() {
        var editTriggered = false
        val id = NEW_SHOPPING_ITEM_PREFIX + UUID.randomUUID()
        setUiData(
            uiState.data?.copy(
                shoppingItems = uiState.data!!.shoppingItems.plus(
                    null to uiState.data!!.shoppingItems[null].orEmpty().plus(
                        ShoppingListItemUi(
                            id = id,
                            text = "",
                            onTextChange = { text -> updateItemText(null, id, text) },
                            checked = false,
                            onCheckedChange = {},
                            editing = true,
                            onEditingChange = {
                                if (!editTriggered) {
                                    editTriggered = true
                                    val item = uiState.data!!.shoppingItems[null]!!
                                        .find { it.id == id } ?: return@ShoppingListItemUi

                                    if (item.text.isNotBlank()) storeNewItem(item)
                                    else removeItem(null, id)
                                }
                            },
                            onRemove = { removeItem(null, id) }
                        )
                    )
                )
            )
        )
    }

    private fun storeNewItem(item: ShoppingListItemUi) {
        viewModelScope.launch {
            runAction {
                // Remove from local list so that there is no extra unsaved item when checking before refresh
                removeItem(null, item.id)

                addToShoppingListUseCase(
                    recipeTitle = null,
                    ShoppingItem(
                        id = item.id,
                        text = item.text,
                        checked = item.checked
                    )
                )
            }.onError {
                showErrorSnackbar(it)
            }
        }
    }

    private fun setItemCheck(recipeTitle: String?, itemId: String, checked: Boolean) {
        viewModelScope.launch {
            runAction {
                val item = findItem(recipeTitle, itemId)!!.copy(checked = checked)
                editShoppingListUseCase(recipeTitle, item)
            }.onError {
                showErrorSnackbar(it)
            }
        }
    }

    private fun setItemEdit(recipeTitle: String?, itemId: String, editing: Boolean) {
        updateItem(recipeTitle, itemId) { it.copy(editing = editing) }
        if (!editing) {
            viewModelScope.launch {
                runAction {
                    val item = findItem(recipeTitle, itemId)!!
                    editShoppingListUseCase(recipeTitle, item)
                }.onError {
                    showErrorSnackbar(it)
                }
            }
        }
    }

    private fun removeItem(recipeTitle: String?, itemId: String) {
        if (itemId.startsWith(NEW_SHOPPING_ITEM_PREFIX)) {
            val newList = uiState.data?.shoppingItems?.get(recipeTitle)?.filter {
                it.id != itemId
            }?.ifEmpty { null }

            setUiData(
                uiState.data?.copy(
                    shoppingItems = if (newList != null) {
                        uiState.data!!.shoppingItems.plus(
                            recipeTitle to newList
                        )
                    } else uiState.data!!.shoppingItems.minus(recipeTitle)
                )
            )
        } else {
            viewModelScope.launch {
                runAction {
                    removeFromShoppingListUseCase(recipeTitle, findItem(recipeTitle, itemId)!!)
                }.onError {
                    showErrorSnackbar(it)
                }
            }
        }
    }

    private fun findItem(recipeTitle: String?, itemId: String): ShoppingItem? {
        return uiState.data?.shoppingItems?.get(recipeTitle)?.find { it.id == itemId }?.let {
            ingredientUiMapper.fromShoppingListItemUi(it)
        }
    }

    private fun updateItem(
        category: String?, itemId: String, transform: (ShoppingListItemUi) -> ShoppingListItemUi
    ) {
        setUiData(
            uiState.data?.copy(
                shoppingItems = uiState.data!!.shoppingItems.plus(
                    category to uiState.data!!.shoppingItems[category]!!.map {
                        if (it.id == itemId) transform(it)
                        else it
                    }
                )
            )
        )
    }
}