package com.kontvip.wisecoin.domain

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

inline fun <reified T> typeToken(): Type = object : TypeToken<T>() {}.type