package com.deloitte.service;

import com.deloitte.base.intrfc.Machine;

public class Factory {
    public static Machine newMachine() {
        return new MachineImpl();
    }
}
