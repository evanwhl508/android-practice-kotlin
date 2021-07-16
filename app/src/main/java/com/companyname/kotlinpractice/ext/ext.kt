import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable.disposedBy(disposeBag: CompositeDisposable) {
    disposeBag.add(this)
}

