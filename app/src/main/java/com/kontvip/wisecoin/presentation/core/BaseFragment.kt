package com.kontvip.wisecoin.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.kontvip.wisecoin.databinding.ActivityMainBinding
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val inflateMethod = extractBindingInflateMethod(javaClass)
        _binding = inflateMethod.invoke(null, layoutInflater, null, false) as B
        return binding.root
    }

    private fun extractBindingInflateMethod(clazz: Class<*>): Method {
        val superClass = clazz.genericSuperclass
        return try {
            val parameterizedTypes = (superClass as ParameterizedType).actualTypeArguments

            val bindingClass = parameterizedTypes.find {
                (it as? Class<*>)?.declaredMethods?.any { method ->
                    method.name == "inflate"
                } == true
            } as Class<*>

            bindingClass.getDeclaredMethod(
                "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
            )
        } catch (e: Exception) {
            extractBindingInflateMethod(superClass as Class<*>)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}