package com.companyname.kotlinpractice.ext

import android.util.Log
import com.google.android.gms.tasks.Task
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.disposedBy(disposeBag: CompositeDisposable) {
    disposeBag.add(this)
}

fun <T, RT> Task<RT>.asObservable(onSuccess: (res: RT) -> T): Observable<T> {
    return Observable.create {
        this.addOnSuccessListener { result ->
                it.onNext(onSuccess(result))
                it.onComplete()
            }
            .addOnFailureListener { e ->
                it.onError(e)
                it.onComplete()
            }
            .addOnCompleteListener{ _ ->
                it.onComplete()
            }
    }
}