package com.dragonboatrace.tools;

import com.google.gson.Gson;

public class GsonTool {

    private final Gson instance;

    public GsonTool() {
        this.instance = new Gson();
    }

    public String toJsonString(Object o) {
        return this.instance.toJson(o);
    }
}
