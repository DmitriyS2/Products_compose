package com.sd.products.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.products.domain.dto.Product
import com.sd.products.domain.model.ModelCurrentProduct
import com.sd.products.domain.model.ModelProducts
import com.sd.products.domain.usecase.GetCurrentProductUseCase
import com.sd.products.domain.usecase.GetListProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getListProductsUseCase: GetListProductsUseCase,
    private val getCurrentProductUseCase: GetCurrentProductUseCase
) :ViewModel() {

    private val _dataModel: MutableLiveData<ModelProducts> = MutableLiveData()
    val dataModel: LiveData<ModelProducts>
        get() = _dataModel

    private val _currentProduct: MutableLiveData<ModelCurrentProduct> = MutableLiveData()
    val currentProduct: LiveData<ModelCurrentProduct>
        get() = _currentProduct

    private var _currentId = 0
    val currentId:Int
        get() = _currentId

    init {
        loadAllProducts()
    }

    fun loadAllProducts() {
        viewModelScope.launch {
            try {
                _dataModel.value = ModelProducts(loading = true)
//                val list = withContext(viewModelScope.coroutineContext) {
//                    getListProductsUseCase.getListProducts()
//                }
//                val list = viewModelScope.async {
//                    getListProductsUseCase.getListProducts()
//                }.await()

                _dataModel.value = ModelProducts(
              //      products = repository.loadData()
                    products = getListProductsUseCase.getListProducts()
            //        products = list
                )
                if (_dataModel.value?.products?.isEmpty() == true) {
                    _dataModel.value = ModelProducts(error = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _dataModel.value = ModelProducts(error = true)
            }
        }
    }

    fun loadCurrentProduct(id:Int) {
        _currentId=id
        viewModelScope.launch {
            try {
                _currentProduct.value = ModelCurrentProduct(loading = true)
//                val list = withContext(viewModelScope.coroutineContext) {
//                    getListProductsUseCase.getListProducts()
//                }
//                val list = viewModelScope.async {
//                    getListProductsUseCase.getListProducts()
//                }.await()

                _currentProduct.value = ModelCurrentProduct(
                    //      products = repository.loadData()
                    product = getCurrentProductUseCase.getCurrentProduct(id)
                    //        products = list
                )
                if (_currentProduct.value?.product == Product()) {
                    _currentProduct.value = ModelCurrentProduct(error = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _currentProduct.value = ModelCurrentProduct(error = true)
            }
        }
    }
}