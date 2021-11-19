package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.service.UserActionService;

public abstract class UserActionServiceImpl implements UserActionService {

    private final int key;
    private final String name;

    protected UserActionServiceImpl(final int key, final String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int key() {
        return this.key;
    }

    @Override
    public String info() {
        return String.format("%s. %s", this.key, this.name);
    }
}
