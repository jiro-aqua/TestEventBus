package jp.gr.aqua.testeventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class Events {
    data class AlphaEvent(val value : Int) : Events()
}

class EventBus {
    private val _betaState = MutableStateFlow(0)
    val betaState = _betaState.asStateFlow()
    fun setBetaState( value : Int ) { _betaState.value = value }


    // 書いてみたけど未使用
    private val _eventbus = MutableSharedFlow<Events>(extraBufferCapacity = 10)
    val eventbus = _eventbus.asSharedFlow()
    suspend fun sendEvent(event : Events){
        _eventbus.emit(event)
    }
    fun trySendEvent(event : Events){
        _eventbus.tryEmit(event)
    }

}