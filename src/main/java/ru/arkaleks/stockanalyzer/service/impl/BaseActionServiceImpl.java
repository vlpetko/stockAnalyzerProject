package ru.arkaleks.stockanalyzer.service.impl;

import ru.arkaleks.stockanalyzer.service.BaseActionService;

public abstract class BaseActionServiceImpl implements BaseActionService {

    private final int key;
    private final String name;

    protected BaseActionServiceImpl(final int key, final String name) {
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
