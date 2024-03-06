package com.nugroho.githubexpert.core.data.source.remote.response

import com.google.gson.InstanceCreator
import java.lang.reflect.Type

class DetailInstance : InstanceCreator<UserDetail> {
    override fun createInstance(type: Type?): UserDetail {
        return UserDetail(0, "", "", "", 0, 0, "", "")
    }
}