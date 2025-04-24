package com.example.fixbug.study;

public abstract class AbstractClasGenA extends ClassA implements InterfaceStart {
    private final String a;
    private final String b;

    public AbstractClasGenA(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public abstract void start();

    public String getA() {
        return a;
    }

    public String getB() {
        return b;
    }

    @Override
    public void d() {
        super.d();
        System.out.println("đây là lớp của AbstractClasGenA");
    }
}
