package com.shelter.testjetpack

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

/**
 * @author: Shelter
 * Create time: 2022/8/17, 16:49.
 */
class TwoWayBindingViewModel: BaseObservable() {
    val book = Book("《资治通鉴》", "司马迁")

    @Bindable
    fun getAuthor() = book.author

    fun setAuthor(author: String) {
        if (author != book.author) {
            book.author = author
            notifyPropertyChanged(BR.author)
        }
    }

    @Bindable
    fun getName() = book.name

    fun setName(name: String) {
        if (name != book.name) {
            book.name = name
            notifyPropertyChanged(BR.name)
        }
    }
}