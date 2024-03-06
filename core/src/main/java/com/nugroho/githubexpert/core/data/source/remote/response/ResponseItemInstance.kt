package com.nugroho.githubexpert.core.data.source.remote.response

import com.google.gson.InstanceCreator
import java.lang.reflect.Type

class ResponseItemInstance : InstanceCreator<UserResponseItem> {
    override fun createInstance(type: Type?): UserResponseItem {
        return UserResponseItem(0, "", "", "")
    }
}