package com.example.pruebags.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebags.data.models.CharacterResponse
import com.example.pruebags.data.models.CharactersResponse
import com.example.pruebags.domain.repositories.CharacterRepository
import com.example.pruebags.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val respository : CharacterRepository
) : ViewModel() {

    private val _res = MutableLiveData<Resource<CharacterResponse>>()
    val res get() = _res

    fun getAll() = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        try {
            val resp = respository.getAllCharacters()
            Log.e("TAG", "getAll: $resp")
            respository.getAllCharacters().let { response ->
                if(response.isSuccessful){
                    _res.postValue(Resource.success(response.body()!!))
                }else{
                    _res.postValue(Resource.error(response.code().toString(), null))
                }
            }
        }catch (e : HttpException){
            _res.postValue(Resource.error("Oops, algo salió mal, intente nuevamente.", null))
        }catch (e: IOException){
            _res.postValue(Resource.error("No podemos acceder al servidor, intente nuevamente.", null))
        }
    }

}