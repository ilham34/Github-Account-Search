package com.nugroho.githubexpert.core.data.source.remote.response

import com.google.gson.InstanceCreator
import java.lang.reflect.Type

class ResponseInstance : InstanceCreator<UserResponse> {
    override fun createInstance(type: Type?): UserResponse {
        return UserResponse(0, ArrayList())
    }
}