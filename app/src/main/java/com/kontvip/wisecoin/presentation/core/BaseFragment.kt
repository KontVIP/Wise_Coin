package com.kontvip.wisecoin.presentation.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kontvip.wisecoin.databinding.ActivityMainBinding
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val bindingClass = extractGenericType(javaClass)
        val method = bindingClass.getDeclaredMethod(
            "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
        )
        _binding = method.invoke(null, layoutInflater, null, false) as B

        return binding.root
    }

    private fun extractGenericType(clazz: Class<*>): Class<*> {
        val superClass = clazz.genericSuperclass
        return try {
            val parameterizedType = superClass as ParameterizedType
            parameterizedType.actualTypeArguments[0] as Class<*>
        } catch (e: Exception) {
            extractGenericType(superClass as Class<*>)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}



class MyFragment : BaseFragment<ActivityMainBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding
    }

}