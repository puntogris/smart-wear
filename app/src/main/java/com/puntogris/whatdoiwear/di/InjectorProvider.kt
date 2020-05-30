package com.puntogris.whatdoiwear.di

import androidx.fragment.app.Fragment

interface InjectorProvider{
    val component: AppComponent
}

val Fragment.injector get() = (requireActivity().application as InjectorProvider).component
